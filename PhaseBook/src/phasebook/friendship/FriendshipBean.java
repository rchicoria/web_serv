package phasebook.friendship;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import phasebook.post.Post;
import phasebook.user.PhasebookUser;
import phasebook.user.PhasebookUserBean;
import phasebook.auth.Auth;
import phasebook.email.*;

@Stateless
public class FriendshipBean implements FriendshipRemote {
	
	public int friendshipStatus(int user_a_id, int user_b_id,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return -1;
		Friendship myFriendship = searchFriendship(user_a_id,user_b_id, authId, authPass);
		
		if(myFriendship == null && user_a_id != user_b_id)
			return 0;
		else if(myFriendship == null && user_a_id == user_b_id)
			return -1;
		else if(!myFriendship.isAccepted_() && myFriendship.getHostUserId() == user_a_id)
			return 1;
		else if(!myFriendship.isAccepted_() && myFriendship.getHostUserId() == user_b_id)
			return 2;
		else if(myFriendship.isAccepted_())
			return 3;
		else
			return -1;
	}

	@SuppressWarnings("finally")
	public Friendship searchFriendship(int user_a_id, int user_b_id,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		Friendship result = null;
		
		Query q = em.createQuery("SELECT u FROM Friendship u " +
				"WHERE (u.hostUserId = :user_a AND " +
				"u.invitedUserId = :user_b) OR"+
				"(u.hostUserId = :user_b AND " +
				"u.invitedUserId = :user_a)");
		q.setParameter("user_a",user_a_id);
		q.setParameter("user_b",user_b_id);
		
		try
		{
			result = (Friendship) q.getSingleResult();
		}
		catch(NoResultException e)
		{
			System.out.println("<Não foram encontrados resultados>");
		}
		catch(NonUniqueResultException e)
		{
			System.out.println("<Foi encontrado mais de um resultado>");
		}
		finally
		{
			em.close();
			emf.close();
			return result;
		}
		
	}

	public void acceptFriendship(int hostUserId, int invitedUserId,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
    	Friendship friend = searchFriendship(invitedUserId, hostUserId, authId, authPass);
    	em.merge(friend);
    	friend.setAccepted_(true);
    	em.merge(friend);
		tx.commit();
		PhasebookUserBean userBean = new PhasebookUserBean();
		EmailUtils.acceptedInvite(userBean.getUserById(hostUserId, authId, authPass), userBean.getUserById(invitedUserId, authId, authPass));
		em.close();
		emf.close();
	}
	
	public Object getNewFriendshipInvites(int userId,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		List<?> result = null;
		
		Query q = em.createQuery("SELECT u FROM Friendship u WHERE u.invitedUserId = :user"
				+" AND u.accepted_ = :acceptedStatus");
		q.setParameter("user",userId);
		q.setParameter("acceptedStatus", false);
		
		try
		{
			result = q.getResultList();
		}
		catch(NoResultException e)
		{
			System.out.println("<Não foram encontrados resultados>");
		}
		
		finally
		{
			em.close();
			emf.close();
			return result;
		}
		
	}
	
	public Object getNewFriendshipAcceptances(int userId,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		List<?> result = null;
		
		Query q = em.createQuery("SELECT u FROM Friendship u WHERE u.hostUserId = :user"
				+" AND u.accepted_ = :accepted AND u.read_ = :readStatus");
		q.setParameter("user",userId);
		q.setParameter("accepted", true);
		q.setParameter("readStatus", false);
		
		try
		{
			result = q.getResultList();
		}
		catch(NoResultException e)
		{
			System.out.println("<Não foram encontrados resultados>");
		}
		
		finally
		{
			em.close();
			emf.close();
			return result;
		}
		
	}
	
	public void readUnreadFriendshipInvites(int userId,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		List<?> result = null;
		
		Query q = em.createQuery("SELECT u FROM Friendship u WHERE u.hostUserId = :user"
				+" AND u.accepted_ = :readStatus");
		q.setParameter("user",userId);
		q.setParameter("readStatus", false);
		
		try
		{
			result=q.getResultList();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			Friendship friendship;
			for(Object object : result)
			{
				friendship = (Friendship)object;
				em.merge(friendship);
				friendship.setRead(true);
				em.merge(friendship);
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
	
	public void readUnreadFriendshipAcceptances(int userId,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		List<?> result = null;
		
		Query q = em.createQuery("SELECT u FROM Friendship u WHERE u.hostUserId = :user"
				+" AND u.accepted_ = :accepted AND u.read_ = :readStatus");
		q.setParameter("user",userId);
		q.setParameter("accepted", true);
		q.setParameter("readStatus", false);
		
		try
		{
			result=q.getResultList();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			Friendship friendship;
			for(Object object : result)
			{
				friendship = (Friendship)object;
				em.merge(friendship);
				friendship.setRead(true);
				em.merge(friendship);
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
	
	public void invite(int hostUserId, int invitedUserId,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
    	Friendship fship = new Friendship(hostUserId, invitedUserId);
		em.persist(fship);
		em.refresh(fship);
		tx.commit();
		PhasebookUserBean userBean = new PhasebookUserBean();
		EmailUtils.acceptedInvite(userBean.getUserById(hostUserId, authId, authPass), userBean.getUserById(invitedUserId, authId, authPass));
		em.close();
		emf.close();
	}
	
	public List getUserFriendships(int userId,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		List<?> result = null;
		
		Query q = em.createQuery("SELECT u FROM Friendship u WHERE (u.invitedUserId = :user OR u.hostUserId = :user)"
				+" AND u.accepted_ = :acceptedStatus");
		q.setParameter("user",userId);
		q.setParameter("acceptedStatus", true);
		
		try
		{
			result = q.getResultList();
		}
		catch(NoResultException e)
		{
			System.out.println("<Não foram encontrados resultados>");
		}
		
		finally
		{
			em.close();
			emf.close();
			return (List) result;
		}
	}

}
