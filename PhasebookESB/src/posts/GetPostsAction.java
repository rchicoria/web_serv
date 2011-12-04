package posts;

import java.util.*;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

public class GetPostsAction extends AbstractActionLifecycle
{

	protected ConfigTree _config;
	
	public GetPostsAction(ConfigTree config) {
		_config = config;
	}
	
	public Message request(Message message) {
		 
		HashMap requestMap = new HashMap();
		
		int userId = ((Integer)message.getBody().get("userId")).intValue();
	    String token = (String)message.getBody().get("token");
	    long current = ((Long)message.getBody().get("current")).longValue();
	    long expiration = ((Long)message.getBody().get("expiration")).longValue();
	    int currentUserId = ((Integer)message.getBody().get("currentUserId")).intValue();
	    int friend = ((Integer)message.getBody().get("friend")).intValue();

		requestMap.put("getPosts.userId", userId);
		requestMap.put("getPosts.token", token);
		requestMap.put("getPosts.current", current);
		requestMap.put("getPosts.expiration", expiration);
		requestMap.put("getPosts.currentUserId", currentUserId);
		requestMap.put("getPosts.friend", friend);

		message.getBody().add(requestMap);
	    
	    return message;  
	}
	
	public Message response(Message message) {
		  
		Map responseMsg = (Map) message.getBody().get(Body.DEFAULT_LOCATION);
		Iterator it = responseMsg.keySet().iterator();
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		// falhou autenticaçao
		if(responseMsg.keySet().size()==0){
			message.getBody().add("");
		}
		try{
			while(it.hasNext()){
				HashMap<String, Object> post = new HashMap<String, Object>();
				post.put("fromUserId", responseMsg.get(it.next()));
				post.put("id", responseMsg.get(it.next()));
				post.put("photoId", responseMsg.get(it.next()));
				post.put("private", responseMsg.get(it.next()));
				post.put("read", responseMsg.get(it.next()));
				post.put("text", responseMsg.get(it.next()));
				post.put("toUserId", responseMsg.get(it.next()));
				list.add(post);
			}
		} catch(NoSuchElementException ex){
			HashMap<String, Object> temp = new HashMap<String, Object>();
			temp.put("id",0);
			list.add(temp);
		}
		message.getBody().add("posts", list);
		
		return message;  
	}

}