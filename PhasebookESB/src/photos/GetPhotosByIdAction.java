package photos;

import java.util.*;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

public class GetPhotosByIdAction extends AbstractActionLifecycle
{

	protected ConfigTree _config;
	
	public GetPhotosByIdAction(ConfigTree config) {
		_config = config;
	}
	
	public Message request(Message message) {
		 
		HashMap requestMap = new HashMap();
		
		int userId = ((Integer)message.getBody().get("userId")).intValue();
	    String token = (String)message.getBody().get("token");
	    long current = ((Long)message.getBody().get("current")).longValue();
	    long expiration = ((Long)message.getBody().get("expiration")).longValue();
	    List<Integer> photoIds = ((List<Integer>)message.getBody().get("postsPhotosIds"));
	     
	    List<Integer> temp = new ArrayList<Integer>();
	    String photoIdsString = ""; 
	    
	    Iterator it = photoIds.iterator();
	    while(it.hasNext()){
	    	int id = Integer.parseInt((String)it.next());
	    	if(!temp.contains(id)){
	    		temp.add(id);
	    		photoIdsString += id+",";
	    	}
	    }
	     
	    if(photoIdsString.length() != 0)
	    	photoIdsString = photoIdsString.substring(0, photoIdsString.length()-1);

		requestMap.put("getPhotos.userId", userId);
		requestMap.put("getPhotos.token", token);
		requestMap.put("getPhotos.current", current);
		requestMap.put("getPhotos.expiration", expiration);
		requestMap.put("getPhotos.postsPhotosIds", photoIdsString);

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
				HashMap<String, Object> photo = new HashMap<String, Object>();
				String id = (String)responseMsg.get(it.next());
				photo.put("id", id);
				photo.put("name", responseMsg.get(it.next()));
				map.put(id,photo);
			}
		}
		catch (NoSuchElementException ex){
			map.put("0",new HashMap<String, Object>());
		}
		
		message.getBody().add("postsPhotos", map);
		
		return message;  
	}

}