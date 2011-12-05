package phasebook.post;

import info.UserInfo;

import java.sql.Timestamp;
import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import phasebook.auth.Auth;
import phasebook.email.EmailUtils;
import phasebook.photo.Photo;
import phasebook.user.PhasebookUser;

@Stateless
public class PostBean implements PostRemote {
	
	public void readUnreadPosts(int entry_id,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		List<?> result = null;
		
		Query q = em.createQuery("SELECT u FROM Post u WHERE u.toUserId = :user AND u.read_ = :status");
		q.setParameter("user",entry_id);
		q.setParameter("status",false);
		
		try
		{
			result=q.getResultList();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			Post post;
			for(Object object : result)
			{
				post = (Post)object;
				em.merge(post);
				post.setRead_(true);
				em.merge(post);
			}
			tx.commit();
			em.close();
			emf.close();
		}
		catch(NoResultException e)
		{
			em.close();
			emf.close();
			System.out.println("<Não foram encontrados posts por ler>");
		}
	}
	
	public void removePost(String myPostId,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		Post result = null;
		
		Query q = em.createQuery("SELECT u FROM Post u WHERE u.id = :postid");
		q.setParameter("postid",Integer.parseInt(myPostId));
		
		try
		{
			result=(Post) q.getSingleResult();
			EntityTransaction tx = em.getTransaction();
			tx.begin();

			em.merge(result);
			result.setDeletedAt(new Timestamp(new Date().getTime()));
			em.merge(result);
				
			tx.commit();
			em.close();
			emf.close();
		}
		catch(NoResultException e)
		{
			em.close();
			emf.close();
			System.out.println("<Não foram encontrados posts por ler>");
		}		
	}
	
	public List<?> getUnreadPosts(int entry_id,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		Query q = em.createQuery("SELECT u FROM Post u WHERE u.fromUserId != :me AND u.toUserId = :user AND u.read_ = :readStatus");
		q.setParameter("me",entry_id);
		q.setParameter("user",entry_id);
		q.setParameter("readStatus", false);
		
		List<?> result = q.getResultList();

		em.close();
		emf.close();
		
		return result;
	}
	
	public Post getPostById(Object id,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		int postId = Integer.parseInt(id.toString());
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		try {
			Post post = em.find(Post.class, postId);
			em.persist(post);
			em.refresh(post);
			em.close();
			emf.close();
			return post;
		} catch(NoResultException ex){
			em.close();
			emf.close();
			return null;
		} catch(NonUniqueResultException ex){
			em.close();
			emf.close();
			return null;
		}
	}
	
	// ****************************************************************
	
	public List<Post> getUserReceivedPosts(Object userId,
			Object authId, Object authPass)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		PhasebookUser user = em.find(PhasebookUser.class, Integer.parseInt(userId.toString()));
		
		try{
			Query q = em.createQuery("SELECT u FROM Post u " +
					"WHERE u.toUserId = :user AND " +
					"u.deletedAt = :min");
			q.setParameter("user",user.getId());
			q.setParameter("min", new Timestamp(0));
			
			em.clear();
			emf.close();
			
			return q.getResultList();
		} catch(NoResultException e){
			em.close();
			emf.close();
			List<Post> empty = new ArrayList<Post>();
			return empty;
		}
	}
	
	public List getUserPublicPosts(Object userId,
			Object authId, Object authPass)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		PhasebookUser user = em.find(PhasebookUser.class, Integer.parseInt(userId.toString()));
		
		try{
			Query q = em.createQuery("SELECT u FROM Post u " +
					"WHERE u.toUserId = :user AND " +
					"u.private_ = :private_ AND u.deletedAt = :min");
			q.setParameter("user",user.getId());
			q.setParameter("private_",false);
			q.setParameter("min", new Timestamp(0));
			
			em.clear();
			emf.close();
			
			return q.getResultList();
		} catch(NoResultException e){
			em.close();
			emf.close();
			List<Post> empty = new ArrayList<Post>();
			return empty;
		}
	}

	public void addPost(UserInfo from, UserInfo to, String text, String privacy)
	{

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
    	Post post = new Post(from.getId(), to.getId(), text, privacy);
		em.persist(post);
		em.refresh(post);
		tx.commit();
		em.close();
		emf.close();
		
		if(from!=to)
			EmailUtils.postSent(to, from, text, null, getNUnreadUserPosts(to, 0, ""));
	}
	
//	public void addPost(PhasebookUser from, PhasebookUser to, String text, String photoLink, String privacy,
//			Object authId, Object authPass)
//	{
//		if (Auth.authenticate(authId, authPass))
//			return;
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
//		EntityManager em = emf.createEntityManager();
//		EntityTransaction tx = em.getTransaction();
//		
//		tx.begin();
//		//TODO isto ainda depende das photos
//		Photo photo = new Photo(photoLink); 
//		em.persist(photo);
//		em.refresh(photo);
//		
//    	Post post = new Post(from.getId(), to.getId(), text, photo.getId(), privacy);
//		em.persist(post);
//		em.refresh(post);
//		
//		tx.commit();
//		if(!from.equals(to))
//			EmailUtils.postSent(to, from, text, photo, getNUnreadUserPosts(to, authId, authPass));
//		em.close();
//		emf.close();
//	}
	
	public int getNUnreadUserPosts(UserInfo user,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return -1;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		List<?> posts = null;
		
		Query q = em.createQuery("SELECT u FROM Post u WHERE u.toUserId = :user AND u.read_ = :status AND u.deletedAt = NULL");
		q.setParameter("user",user.getId());
		q.setParameter("status",false);
		
		int result = q.getResultList().size();
		em.close();
		emf.close();
		return result;
	}
	
}
