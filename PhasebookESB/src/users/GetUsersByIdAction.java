package users;

import java.util.*;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

public class GetUsersByIdAction extends AbstractActionLifecycle
{

	protected ConfigTree _config;
	
	public GetUsersByIdAction(ConfigTree config) {
		_config = config;
	}
	
	public Message request(Message message) {
		 
		HashMap requestMap = new HashMap();
		
		int userId = ((Integer)message.getBody().get("userId")).intValue();
	    String token = (String)message.getBody().get("token");
	    long current = ((Long)message.getBody().get("current")).longValue();
	    long expiration = ((Long)message.getBody().get("expiration")).longValue();
	    List<Integer> userIds = ((List<Integer>)message.getBody().get("postsUsersIds"));
	    
	    List<Integer> temp = new ArrayList<Integer>();
	    String userIdsString = ""; 
	     
	    Iterator it = userIds.iterator();
	    while(it.hasNext()){
	    	int id = Integer.parseInt((String)it.next());
	    	if(!temp.contains(id)){
	    		temp.add(id);
	    		userIdsString += id+",";
	    	}
	    }
	     
	    if(userIdsString.length() != 0)
	    	userIdsString = userIdsString.substring(0, userIdsString.length()-1);

		requestMap.put("getUsers.userId", userId);
		requestMap.put("getUsers.token", token);
		requestMap.put("getUsers.current", current);
		requestMap.put("getUsers.expiration", expiration);
		requestMap.put("getUsers.userIds", userIdsString);

		message.getBody().add(requestMap);
	    
	    return message;  
	}
	
	public Message response(Message message) {
		
		Map responseMsg = (Map) message.getBody().get(Body.DEFAULT_LOCATION);
		Iterator it = responseMsg.keySet().iterator();
		HashMap<String, HashMap<String, Object>> map = new HashMap<String, HashMap<String, Object>>();
		if(responseMsg.keySet().size()==0){
			message.getBody().add("");
		}
		try{
			while(it.hasNext()){
				HashMap<String, Object> user = new HashMap<String, Object>();
				user.put("email", responseMsg.get(it.next()));
				String id = (String)responseMsg.get(it.next());
				user.put("id", id);
				user.put("money", responseMsg.get(it.next()));
				user.put("name", responseMsg.get(it.next()));
				user.put("photoId", responseMsg.get(it.next()));
				map.put(id,user);
			}
		}
		catch (NoSuchElementException ex){
			map.put("0",new HashMap<String, Object>());
		}
		
		message.getBody().add("postsUsers", map);
		
		return message;  
	}

}