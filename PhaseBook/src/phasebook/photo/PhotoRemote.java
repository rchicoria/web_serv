package phasebook.photo;
import javax.ejb.Remote;

@Remote
public interface PhotoRemote {
	
	public Photo getPhotoById(String id, Object authId, Object authPass);
	public int addPhoto(String photoLink, Object authId, Object authPass);

}
