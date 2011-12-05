package posts;

import java.util.*;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

public class CreatePostAction extends AbstractActionLifecycle
{

	protected ConfigTree _config;
	
	public CreatePostAction(ConfigTree config) {
		_config = config;
	}
	
	public Message request(Message message) {
		 
		HashMap requestMap = new HashMap();
		
		System.out.println("Entrou na request action com a seguinte msg: "+message.getBody());
		
		int userId = ((Integer)message.getBody().get("userId")).intValue();
	    String token = (String)message.getBody().get("token");
	    long current = ((Long)message.getBody().get("current")).longValue();
	    long expiration = ((Long)message.getBody().get("expiration")).longValue();
	    int fromId = ((Integer)message.getBody().get("fromId")).intValue();
	    String fromName = ((String)message.getBody().get("fromName"));
	    String fromEmail = ((String)message.getBody().get("fromEmail"));
	    float fromMoney = ((Float)message.getBody().get("fromMoney")).floatValue();
	    int fromPhotoId = ((Integer)message.getBody().get("fromPhotoId")).intValue();
	    int toId = ((Integer)message.getBody().get("toId")).intValue();
	    String toName = ((String)message.getBody().get("toName"));
	    String toEmail = ((String)message.getBody().get("toEmail"));
	    float toMoney = ((Float)message.getBody().get("toMoney")).floatValue();
	    int toPhotoId = ((Integer)message.getBody().get("toPhotoId")).intValue();
	    String privacy = (String)message.getBody().get("privacy");
	    String text = (String)message.getBody().get("text");
	    String photoLink = (String)message.getBody().get("photoLink");

		requestMap.put("addPost.userId", userId);
		requestMap.put("addPost.token", token);
		requestMap.put("addPost.text", text);
		requestMap.put("addPost.current", current);
		requestMap.put("addPost.expiration", expiration);
		requestMap.put("addPost.fromId", fromId);
		requestMap.put("addPost.fromName", fromName);
		requestMap.put("addPost.fromEmail", fromEmail);
		requestMap.put("addPost.fromMoney", fromMoney);
		requestMap.put("addPost.fromPhotoId", fromPhotoId);
		requestMap.put("addPost.toId", toId);
		requestMap.put("addPost.toName", toName);
		requestMap.put("addPost.toEmail", toEmail);
		requestMap.put("addPost.toMoney", toMoney);
		requestMap.put("addPost.toPhotoId", toPhotoId);
		requestMap.put("addPost.privacy", privacy);
		requestMap.put("addPost.photoLink", photoLink);

		message.getBody().add(requestMap);
		
		System.out.println("Sai da request action com a seguinte msg: "+message.getBody());
	    
	    return message;  
	}
	
	public Message response(Message message) {
		  
		Map responseMsg = (Map) message.getBody().get(Body.DEFAULT_LOCATION);
		System.out.println("RESPONSE "+message.getBody().get(Body.DEFAULT_LOCATION));
		Iterator it = responseMsg.keySet().iterator();
		while(it.hasNext()){
			message.getBody().add("resp", responseMsg.get(it.next()));
		}
		System.out.println("RESPONSE SAIDA"+message.getBody().get("resp"));
		return message;  
	}

}