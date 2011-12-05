package phasebook.photo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;

import phasebook.auth.Auth;

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
		int photoId = Integer.parseInt(id);
		if (photoId == 0)
			return null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		
		System.out.println("AQUIIIIIIIIIIIIIIIIIIIIIIIIIIIII!");
		
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
	
	public int addPhoto(String photoLink,
			Object authId, Object authPass)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhaseBook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		Photo photo = new Photo(photoLink); 
		em.persist(photo);
		em.refresh(photo);

		tx.commit();
		em.close();
		emf.close();
		return photo.getId();
	}

}
