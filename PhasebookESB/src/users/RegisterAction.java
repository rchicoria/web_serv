package users;

import java.util.*;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

public class RegisterAction extends AbstractActionLifecycle
{

	protected ConfigTree _config;
	
	public RegisterAction(ConfigTree config) {
		_config = config;
	}
	
	public Message request(Message message) {
		 
		HashMap requestMap = new HashMap();

		String name = (String) message.getBody().get("name");
		String email = (String) message.getBody().get("email");
		String password = (String) message.getBody().get("password");
		Long current = ((Long) message.getBody().get("current")).longValue();

		requestMap.put("createUser.name", name);
		requestMap.put("createUser.email", email);
		requestMap.put("createUser.password", password);
		requestMap.put("createUser.current", current);

		message.getBody().add(requestMap);
	    
	    return message;  
	}
	
	public Message response(Message message) {
		  
		Map responseMsg = (Map) message.getBody().get(Body.DEFAULT_LOCATION);
		
		message.getBody().add("id", responseMsg.get("createUserResponse[0]"));
		message.getBody().add("token", responseMsg.get("createUserResponse[1]"));
		message.getBody().add("expiration", responseMsg.get("createUserResponse[2]"));
		
		return message;  
	}

}