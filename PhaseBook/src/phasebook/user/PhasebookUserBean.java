package phasebook.user;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import phasebook.friendship.Friendship;
import phasebook.photo.Photo;
import phasebook.photo.PhotoBean;
import phasebook.post.Post;
import phasebook.auth.Auth;
import phasebook.email.*;

/**
 * Session Bean implementation class PhasebookUserBean
 */
@Stateless
public class PhasebookUserBean implements PhasebookUserRemote {

    /**
     * Default constructor. 
     */
    public PhasebookUserBean() {
    }
	
	public int create(String name, String email, String password)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		// Return -1 if email already exists
		try {
			Query q = em.createQuery("SELECT u FROM PhasebookUser u " +
						"WHERE u.email LIKE :email");
			q.setParameter("email",email);
			PhasebookUser user = ((PhasebookUser)q.getSingleResult());
			em.close();
			emf.close();
			return -1;
		} catch(NoResultException ex){
		} catch(NonUniqueResultException ex){
			em.close();
			emf.close();
			return -1;
		}
		
		tx.begin();
    	PhasebookUser user = new PhasebookUser(name, email, password);
		em.persist(user);
		em.refresh(user);
		tx.commit();
		int id = user.getId();
		em.close();
		emf.close();
		return id;
	}
	
	/* (non-Javadoc)
	 * @see phasebook.user.PhasebookUserRemote#login(java.lang.String, java.lang.String)
	 */
	public int login(String email, String password)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		try {
			int returnValue = -1;
			Query q = em.createQuery("SELECT u FROM PhasebookUser u " +
						"WHERE u.email LIKE :email AND " +
						"u.password LIKE :password");
			q.setParameter("email",email);
			q.setParameter("password",password);
			
			PhasebookUser user = ((PhasebookUser)q.getSingleResult());
			
			em.merge(user);
			em.refresh(user);
			
			em.close();
			emf.close();
			return user.getId();
		} catch(NoResultException ex){
			em.close();
			emf.close();
			ex.printStackTrace();
			return -1;
		} catch(NonUniqueResultException ex){
			em.close();
			emf.close();
			ex.printStackTrace();
			return -1;
		}
	}
	
	public List<Post> getUserReceivedPosts(Object userId,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		PhasebookUser user = em.find(PhasebookUser.class, Integer.parseInt(userId.toString()));
		
		try{
			Query q = em.createQuery("SELECT u FROM Post u " +
					"WHERE u.toUserId = :user AND " +
					"u.deletedAt is NULL");
			q.setParameter("user",user.getId());
			
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
		if (Auth.authenticate(authId, authPass))
			return null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		PhasebookUser user = em.find(PhasebookUser.class, Integer.parseInt(userId.toString()));
		
		try{
			Query q = em.createQuery("SELECT u FROM Post u " +
					"WHERE u.toUserId = :user AND " +
					"u.private_ = :private_ AND u.deletedAt is NULL");
			q.setParameter("user",user.getId());
			q.setParameter("private_",false);
			
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
	
	public PhasebookUser getUserById(Object id,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		int userId = Integer.parseInt(id.toString());
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		try {
			PhasebookUser user = em.find(PhasebookUser.class, userId);
			em.persist(user);
			em.refresh(user);
			em.close();
			emf.close();
			return user;
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
	
	public List getUsersFromSearch(Object search,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		List users = new ArrayList();
		List results = new ArrayList();
		String s = search.toString().toLowerCase();

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();

		try {
			Query q = em.createQuery("SELECT u FROM PhasebookUser u ");
			users = q.getResultList();
			if (s != null)
			{
				Pattern pattern = Pattern.compile(s);
				for (int i=0; i<users.size(); i++)
				{
					PhasebookUser user = (PhasebookUser)users.get(i);
					boolean found = false;
					Matcher matcher = pattern.matcher(user.getName().toLowerCase());
					if (matcher.find())
						found = true;
					matcher = pattern.matcher(user.getEmail().toLowerCase());
					if (matcher.find())
						found = true;
					if (found)
						results.add(user);
				}
			}
			em.close();
			emf.close();
			return results;
		} catch (Exception e) {
			em.close();
			emf.close();
			return users;
		}
	}
	
	public void addPost(PhasebookUser from, PhasebookUser to, String text, String privacy,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return;
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
		if(!from.equals(to))
			EmailUtils.postSent(to, from, text, null, getNUnreadUserPosts(to, authId, authPass));
	}
	
	public void addPost(PhasebookUser from, PhasebookUser to, String text, String photoLink, String privacy,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		//TODO isto ainda depende das photos
		Photo photo = new Photo(photoLink); 
		em.persist(photo);
		em.refresh(photo);
		
    	Post post = new Post(from.getId(), to.getId(), text, photo.getId(), privacy);
		em.persist(post);
		em.refresh(post);
		
		tx.commit();
		if(!from.equals(to))
			EmailUtils.postSent(to, from, text, photo, getNUnreadUserPosts(to, authId, authPass));
		em.close();
		emf.close();
	}
	
	public Photo addPhoto(String photoLink,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		//TODO isto ainda depende das photos
		Photo photo = new Photo(photoLink); 
		em.persist(photo);
		em.refresh(photo);

		tx.commit();
		em.close();
		emf.close();
		return photo;
	}
	
	public void invite(PhasebookUser hostUser, PhasebookUser invitedUser,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
    	Friendship fship = new Friendship(hostUser, invitedUser);
		em.persist(fship);
		em.refresh(fship);
		tx.commit();
		EmailUtils.sentInvite(hostUser, invitedUser);
		em.close();
		emf.close();
	}
	
	public void setProfilePicture(PhasebookUser user, int photo_id,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		em.merge(user);
		user.setPhotoId(photo_id);
		em.merge(user);
		tx.commit();
		em.close();
		emf.close();
		
	}

	public void deposit(Object id, Float money,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		PhasebookUser user = getUserById(id, authId, authPass);
		user.setMoney(user.getMoney() + money);
		em.merge(user);
		tx.commit();
		em.close();
		emf.close();
	}
	
	public List<PhasebookUser> getUserFriendships(String id,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		PhasebookUser user = em.find(PhasebookUser.class, Integer.parseInt(id.toString()));
		em.persist(user);
		em.refresh(user);
		List friends1 = user.getReceivedInvites();
		List friends2 = user.getSentInvites();
		List<PhasebookUser> friends = new ArrayList<PhasebookUser>();
		for (int i=0; i<friends1.size(); i++){
			Friendship friendship = (Friendship) friends1.get(i);
			em.persist(friendship);
			if (friendship.isAccepted_())
				friends.add(friendship.getHostUser());
		}
		for (int i=0; i<friends2.size(); i++){
			Friendship friendship = (Friendship) friends2.get(i);
			em.persist(friendship);
			if (friendship.isAccepted_())
				friends.add(friendship.getInvitedUser());
		}
		em.close();
		emf.close();
		return friends;
	}
	
	public void editAccount(Object id, String name, String photo, String password,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		PhasebookUser user = getUserById(id, authId, authPass);
		user.setName(name);
		PhotoBean photoEJB = new PhotoBean();
		int photoId = -1;
		try {
			photoId = Integer.parseInt(photo);
		}
		catch (NumberFormatException e) {
			photoId = -1;
		}
		user.setPhotoId(photoId);
		if (password != null && password.length() > 0)
			user.setPassword(password);
		em.merge(user);
		tx.commit();
		em.close();
		emf.close();
	}
	
	public int getNUnreadUserPosts(PhasebookUser user,
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
	
	public int getUserPhotoId(PhasebookUser user,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return -1;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		em.merge(user);
		
		em.close();
		emf.close();
		
		return user.getPhotoId();
	}
}
