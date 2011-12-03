package photos;
import java.sql.Timestamp;
import java.util.*;

import javax.ejb.EJB;
import javax.jws.*;

import phasebook.photo.Photo;
import phasebook.photo.PhotoRemote;
import phasebook.post.Post;
import phasebook.user.*;
import posts.PostInfo;

@WebService(name = "Photo", targetNamespace = "http://PhasebookWS/Photo")  
public class Photos  
{  
	@EJB(mappedName = "PhotoBean/remote")
	PhotoRemote photoRemote;
	
	@WebMethod
	public List<PhotoInfo> getPhotos(@WebParam(name = "userId") int userId, 
			@WebParam(name = "token") String token,
			@WebParam(name = "current") long current,
			@WebParam(name = "expiration") long expiration,
			@WebParam(name = "photoIds") String photoIdsString)  
	{ 
		System.out.println("\n\n\n\n Cheguei Ao WS de Photos \n\n\n\n");
		
		List<Photo> photos = new ArrayList<Photo>();
		
		List<String> photoIds = Arrays.asList(photoIdsString.split(","));
		
		Iterator it = photoIds.iterator();
		while(it.hasNext()){
			photos.add(photoRemote.getPhotoById((String)it.next(), 0, ""));
		}
		
		List<PhotoInfo> result = new ArrayList<PhotoInfo>();
		
		it = photos.iterator();
		while(it.hasNext())
		{
			Photo photo = (Photo) it.next();
			result.add(new PhotoInfo(photo.getId(), photo.getName()));
		}
		
		System.out.println("\n\n\n\n Vou Sair Do WS de Photos \n\n\n\n");
		return result;
	}	
	
}