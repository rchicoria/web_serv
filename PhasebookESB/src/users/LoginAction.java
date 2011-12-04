package users;

import java.util.*;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

public class LoginAction extends AbstractActionLifecycle
{

	protected ConfigTree _config;
	
	public LoginAction(ConfigTree config) {
		_config = config;
	}
	
	public Message request(Message message) {
		 
		HashMap requestMap = new HashMap();

		String email = (String) message.getBody().get("email");
		String password = (String) message.getBody().get("password");
		Long current = ((Long) message.getBody().get("current")).longValue();

		requestMap.put("loginUser.email", email);
		requestMap.put("loginUser.password", password);
		requestMap.put("loginUser.current", current);

		message.getBody().add(requestMap);
	    
	    return message;  
	}
	
	public Message response(Message message) {
		  
		Map responseMsg = (Map) message.getBody().get(Body.DEFAULT_LOCATION);
		
		message.getBody().add("id", responseMsg.get("loginUserResponse[0]"));
		message.getBody().add("token", responseMsg.get("loginUserResponse[1]"));
		message.getBody().add("expiration", responseMsg.get("loginUserResponse[2]"));
		
		return message;  
	}

}