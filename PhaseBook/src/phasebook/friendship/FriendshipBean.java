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
import phasebook.auth.Auth;
import phasebook.email.*;

@Stateless
public class FriendshipBean implements FriendshipRemote {
	
	public int friendshipStatus(PhasebookUser user_a, PhasebookUser user_b,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return -1;
		Friendship myFriendship = searchFriendship(user_a,user_b, authId, authPass);
		
		if(myFriendship == null && !user_a.equals(user_b))
			return 0;
		else if(myFriendship == null && user_a.equals(user_b))
			return -1;
		else if(!myFriendship.isAccepted_() && myFriendship.getHostUser().equals(user_a))
			return 1;
		else if(!myFriendship.isAccepted_() && myFriendship.getHostUser().equals(user_b))
			return 2;
		else if(myFriendship.isAccepted_())
			return 3;
		else
			return -1;
	}

	@SuppressWarnings("finally")
	public Friendship searchFriendship(PhasebookUser user_a, PhasebookUser user_b,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		Friendship result = null;
		
		Query q = em.createQuery("SELECT u FROM Friendship u " +
				"WHERE (u.hostUser = :user_a AND " +
				"u.invitedUser = :user_b) OR"+
				"(u.hostUser = :user_b AND " +
				"u.invitedUser = :user_a)");
		q.setParameter("user_a",user_a);
		q.setParameter("user_b",user_b);
		
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

	public void acceptFriendship(PhasebookUser hostUser, PhasebookUser invitedUser,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
    	Friendship friend = searchFriendship(invitedUser, hostUser, authId, authPass);
    	em.merge(friend);
    	friend.setAccepted_(true);
    	em.merge(friend);
		tx.commit();
		EmailUtils.acceptedInvite(hostUser, invitedUser);
		em.close();
		emf.close();
	}
	
	public Object getNewFriendshipInvites(PhasebookUser entry,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		List<?> result = null;
		
		Query q = em.createQuery("SELECT u FROM Friendship u WHERE u.invitedUser = :user"
				+" AND u.accepted_ = :acceptedStatus");
		q.setParameter("user",entry);
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
	
	public Object getNewFriendshipAcceptances(PhasebookUser entry,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		List<?> result = null;
		
		Query q = em.createQuery("SELECT u FROM Friendship u WHERE u.hostUser = :user"
				+" AND u.accepted_ = :accepted AND u.read_ = :readStatus");
		q.setParameter("user",entry);
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
	
	public void readUnreadFriendshipInvites(PhasebookUser entry,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		List<?> result = null;
		
		Query q = em.createQuery("SELECT u FROM Friendship u WHERE u.hostUser = :user"
				+" AND u.accepted_ = :readStatus");
		q.setParameter("user",entry);
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
	
	public void readUnreadFriendshipAcceptances(PhasebookUser entry,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		List<?> result = null;
		
		Query q = em.createQuery("SELECT u FROM Friendship u WHERE u.hostUser = :user"
				+" AND u.accepted_ = :accepted AND u.read_ = :readStatus");
		q.setParameter("user",entry);
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

}
