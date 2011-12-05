package photos;

import java.util.*;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

public class CreatePhotoAction extends AbstractActionLifecycle
{

	protected ConfigTree _config;
	
	public CreatePhotoAction(ConfigTree config) {
		_config = config;
	}
	
	public Message request(Message message) {
		 
		HashMap requestMap = new HashMap();
		
		System.out.println("Entrou na request action com a seguinte msg: "+message.getBody());
		
		int userId = ((Integer)message.getBody().get("userId")).intValue();
	    String token = (String)message.getBody().get("token");
	    long current = ((Long)message.getBody().get("current")).longValue();
	    long expiration = ((Long)message.getBody().get("expiration")).longValue();
	    String photoLink = (String)message.getBody().get("photoLink");

		requestMap.put("addPhoto.userId", userId);
		requestMap.put("addPhoto.token", token);
		requestMap.put("addPhoto.current", current);
		requestMap.put("addPhoto.expiration", expiration);
		requestMap.put("addPhoto.photoLink", photoLink);

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