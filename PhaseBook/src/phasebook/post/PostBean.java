package phasebook.post;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import phasebook.auth.Auth;
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
	
	public Object getUnreadPosts(int entry_id,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		List<Object> result = null;
		
		Query q = em.createQuery("SELECT u FROM Post u WHERE u.fromUserId != :me AND u.toUserId = :user AND u.read_ = :readStatus");
		q.setParameter("me",entry_id);
		q.setParameter("user",entry_id);
		q.setParameter("readStatus", false);
		
		result=(List<Object>) q.getResultList();

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
	
}
