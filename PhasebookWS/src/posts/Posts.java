package posts;

import utils.*;
import info.*;

import java.util.*;

import javax.ejb.EJB;
import javax.jws.*;

import phasebook.post.*;

@WebService(name = "Posts", targetNamespace = "http://PhasebookWS/Posts")  
public class Posts
{  	
	static String TOKEN = "12345"; 
	@EJB(mappedName = "PostBean/remote")
	PostRemote postRemote;
	
	@WebMethod
	public List<PostInfo> getPosts(@WebParam(name = "userId") int userId, 
			@WebParam(name = "token") String token,
			@WebParam(name = "current") long current,
			@WebParam(name = "expiration") long expiration,
			@WebParam(name = "currentUserId") int currentUserId,
			@WebParam(name = "friend") int friend)  
	{ 
		String myToken = Utils.byteArrayToHexString(Utils.computeHash(userId + "salt"
				+ expiration));
		
		List<PostInfo> result = new ArrayList<PostInfo>();
				
		if(expiration < current || !token.equals(myToken)){
			result.add(new PostInfo());
			return result;
		}
	
		List<Post> posts = null;
		if(friend == 1)
			posts = postRemote.getUserReceivedPosts(currentUserId, 0, "");
		else
			posts = postRemote.getUserPublicPosts(currentUserId, 0, "");
		
		Iterator it = posts.iterator();
		while(it.hasNext())
		{
			Post post = (Post)it.next();
			result.add(new PostInfo(post.getId(), post.getFromUserId(), post.getToUserId(), post.isPrivate_(), post.isRead_(), post.getPhotoId(), post.getText()));
		}
		
		return result;
	}
	
	@WebMethod
	public int addPost(@WebParam(name = "userId") int userId, 
			@WebParam(name = "token") String token,
			@WebParam(name = "current") long current,
			@WebParam(name = "expiration") long expiration,
			@WebParam(name = "fromId") int fromId,
			@WebParam(name = "fromName") String fromName,
			@WebParam(name = "fromEmail") String fromEmail,
			@WebParam(name = "fromMoney") float fromMoney,
			@WebParam(name = "fromPhotoId") int fromPhotoId,
			@WebParam(name = "toId") int toId,
			@WebParam(name = "toName") String toName,
			@WebParam(name = "toEmail") String toEmail,
			@WebParam(name = "toMoney") float toMoney,
			@WebParam(name = "toPhotoId") int toPhotoId,
			@WebParam(name = "text") String text,
			@WebParam(name = "privacy") String privacy,
			@WebParam(name = "photoLink") String photoLink,
			@WebParam(name = "photoId") int photoId)
	{
		System.out.println("ALGO DE ERRADO SE PASSOU");
		
		String myToken = Utils.byteArrayToHexString(Utils.computeHash(userId + "salt"
				+ expiration));
		
		if(expiration < current || !token.equals(myToken)){
			return -1;
		}
		
		UserInfo from = new UserInfo(fromId, fromName, fromEmail, fromMoney, fromPhotoId);
		UserInfo to = new UserInfo(toId, toName, toEmail, toMoney, toPhotoId);
		
		if(photoLink.equals("") || photoLink==null){
			System.out.println("Vai Adicionar a Mensagem");
			postRemote.addPost(from, to, text, privacy);
		}
		else
			postRemote.addPost(from, to, text, photoLink, photoId, privacy);
		return 0;
	}
}