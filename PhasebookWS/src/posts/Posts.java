package posts;
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
			@WebParam(name = "expiration") long expiration)  
	{ 
		System.out.println("\n\n\n\n Cheguei Ao WS de Posts \n\n\n\n");
		
		List<Post> posts = postRemote.getUserReceivedPosts(userId, 0, "");
		List<PostInfo> result = new ArrayList<PostInfo>();
		
		Iterator it = posts.iterator();
		while(it.hasNext())
		{
			Post post = (Post)it.next();
			result.add(new PostInfo(post.getId(), post.getFromUserId(), post.getToUserId(), post.isPrivate_(), post.isRead_(), post.getPhotoId(), post.getText()));
		}
		
		System.out.println("\n\n\n\n Vou Sair Do WS de Posts \n\n\n\n");
		return result;
	}	
}