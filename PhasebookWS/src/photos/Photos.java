package photos;

import java.sql.Timestamp;
import java.util.*;

import info.*;

import javax.ejb.EJB;
import javax.jws.*;

import phasebook.photo.Photo;
import phasebook.photo.PhotoRemote;
import phasebook.post.Post;
import phasebook.user.*;
import utils.Utils;

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
			@WebParam(name = "postsPhotosIds") String photoIdsString)  
	{ 		
		String myToken = Utils.byteArrayToHexString(Utils.computeHash(userId + "salt"
				+ expiration));
		
		List<PhotoInfo> result = new ArrayList<PhotoInfo>();
		
		if(expiration < current || !token.equals(myToken)){
			result.add(new PhotoInfo());
			return result;
		}
		
		List<Photo> photos = new ArrayList<Photo>();
		
		List<String> photoIds = Arrays.asList(photoIdsString.split(","));
		
		Iterator it = photoIds.iterator();
		while(it.hasNext()){
			photos.add(photoRemote.getPhotoById((String)it.next(), 0, ""));
		}
		
		it = photos.iterator();
		while(it.hasNext())
		{
			Photo photo = (Photo) it.next();
			result.add(new PhotoInfo(photo.getId(), photo.getName()));
		}
		
		return result;
	}
	
	@WebMethod
	public int addPhoto(@WebParam(name = "userId") int userId, 
			@WebParam(name = "token") String token,
			@WebParam(name = "current") long current,
			@WebParam(name = "expiration") long expiration,
			@WebParam(name = "photoLink") String photoLink)  
	{ 		
		String myToken = Utils.byteArrayToHexString(Utils.computeHash(userId + "salt"
				+ expiration));
		
		if(expiration < current || !token.equals(myToken)){
			return -1;
		}
		
		int id = photoRemote.addPhoto(photoLink, 0, "");
		return id;
	}
}