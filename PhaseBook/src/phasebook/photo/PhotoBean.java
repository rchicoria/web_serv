package phasebook.photo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;

import phasebook.auth.Auth;
import phasebook.user.PhasebookUser;

/**
 * Session Bean implementation class PhotoBean
 */
@Stateless
public class PhotoBean implements PhotoRemote {

    /**
     * Default constructor. 
     */
    public PhotoBean() {}
    
    public Photo getPhotoById(String id,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return null;
		int photoId = Integer.parseInt(id);
		if (photoId == 0)
			return null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		try {
			Photo photo = em.find(Photo.class, photoId);
			em.persist(photo);
			em.refresh(photo);
			em.close();
			emf.close();
			return photo;
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
