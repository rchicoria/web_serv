package phasebook.lotterybet;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import phasebook.auth.Auth;
import phasebook.lottery.*;
import phasebook.post.Post;
import phasebook.user.*;

@Stateless
public class LotteryBetBean implements LotteryBetRemote {
	
	public boolean createBet(Object id, int number,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return false;
		PhasebookUserBean userEJB = new PhasebookUserBean();
		PhasebookUser user = userEJB.getUserById(id, authId, authPass);
		LotteryBean lotteryEJB = new LotteryBean();
		Lottery lottery = lotteryEJB.getCurrentDraw();
		
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
			EntityManager em = emf.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			
			tx.begin();
			LotteryBet bet = new LotteryBet();
			bet.setBetNumber(number);
			bet.setBetValue(1);
			bet.setValueWon(-1);
			bet.setUser(user);
			bet.setLottery(lottery);
			em.persist(bet);
			em.refresh(bet);
			tx.commit();
			em.close();
			emf.close();
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public List<LotteryBet> userCurrentBets(Object id,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		return userBets(id, false, authId, authPass);
	}
	
	public List<LotteryBet> userOldBets(Object id,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		return userBets(id, true, authId, authPass);
	}
	
	private List<LotteryBet> userBets(Object id, boolean old,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		PhasebookUserBean userEJB = new PhasebookUserBean();
		PhasebookUser user = userEJB.getUserById(id, authId, authPass);
		List<LotteryBet> allBets = user.getLotteryBets();
		List<LotteryBet> theseBets = new ArrayList<LotteryBet>();
		for (int i=0; i<allBets.size(); i++) {
			LotteryBet bet = allBets.get(i);
			if ((old && bet.getValueWon() > -1) ||
					(!old && bet.getValueWon() == -1))
				theseBets.add(bet);
		}
		return theseBets;
	}
	
	public List getAllBets() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		Query q = em.createQuery("SELECT u FROM LotteryBet u ");
		List bets = q.getResultList();
		em.close();
		emf.close();
		return bets;
	}
	
	public List getAllCurrentBets() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		Query q = em.createQuery("SELECT u FROM LotteryBet u WHERE u.valueWon = -1");
		List bets = q.getResultList();
		em.close();
		emf.close();
		return bets;
	}
	
	public void updateBet(LotteryBet bet, float won) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		bet.setValueWon((int)won);
		em.merge(bet);
		tx.commit();
		em.close();
		emf.close();
	}
	
	public Object checkUnreadBetResults(PhasebookUser entry,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		Query q = em.createQuery("SELECT u FROM LotteryBet u WHERE u.user = :user AND u.read_ = :readStatus AND u.valueWon > -1");
		q.setParameter("user", entry);
		q.setParameter("readStatus", false);
		List<?> bets = q.getResultList();
		em.close();
		emf.close();
		return bets;
	}
	
	public void readUnreadBets(PhasebookUser entry,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		List<?> result = null;
		
		Query q = em.createQuery("SELECT u FROM LotteryBet u WHERE u.user = :user AND u.read_ = :readStatus AND u.valueWon > -1");
		q.setParameter("user", entry);
		q.setParameter("readStatus", false);
		
		try
		{
			result=q.getResultList();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			LotteryBet bet;
			for(Object object : result)
			{
				bet = (LotteryBet)object;
				em.merge(bet);
				bet.setRead_(true);
				em.merge(bet);
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
