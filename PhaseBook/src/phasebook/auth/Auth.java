package phasebook.auth;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Auth {
	
	public static boolean authenticate(Object id, Object password)
	{
		int userID = Integer.parseInt(id.toString());
		String pass = password.toString();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		try {
			Query q = em.createQuery("SELECT u FROM PhasebookUser u " +
						"WHERE u.id = :id AND u.password LIKE :password");
			q.setParameter("id",userID);
			q.setParameter("password",pass);
			
			q.getSingleResult();
			
			em.close();
			emf.close();
			return false;
		} catch(NoResultException ex){
			em.close();
			emf.close();
			return true;
		} catch(NonUniqueResultException ex){
			em.close();
			emf.close();
			return true;
		}
	}

}
