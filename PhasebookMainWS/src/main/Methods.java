package main;
import info.AuthInfo;
import info.PostDetailsInfo;

import java.util.*;

import java.sql.Timestamp;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;

import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.couriers.FaultMessageException;
import org.jboss.soa.esb.listeners.message.MessageDeliverException;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.services.registry.RegistryException;

import containers.PostsContainer;

@WebService
public class Methods {
	
	@WebMethod
	public AuthInfo loginUser(String email, String password, long current) {
		// Setting the ConnectionFactory such that it will use scout
		System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");
	
		Message esbMessage = MessageFactory.getInstance().getMessage();
		HashMap requestMap = new HashMap();
		requestMap.put("email",email);
		requestMap.put("password",password);
		requestMap.put("current",current);
		esbMessage.getBody().add(requestMap);
		
		Message retMessage = null;
	
		ServiceInvoker si;
		try {
			si = new ServiceInvoker("Login_User_Service", "send");
			retMessage = si.deliverSync(esbMessage, 10000L);
			HashMap map = (HashMap)retMessage.getBody().get(Body.DEFAULT_LOCATION);

			AuthInfo temp = new AuthInfo(Integer.parseInt((String)map.get("id")), (String)map.get("token"), 
				Long.parseLong((String)map.get("expiration")));
			return temp;
		} catch (MessageDeliverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FaultMessageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RegistryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@WebMethod
	public AuthInfo createUser(String name, String email, String password, long current) {
		// Setting the ConnectionFactory such that it will use scout
		System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");
	
		Message esbMessage = MessageFactory.getInstance().getMessage();
		HashMap requestMap = new HashMap();
		
		requestMap.put("name",name);
		requestMap.put("email",email);
		requestMap.put("password",password);
		requestMap.put("current", current);
		esbMessage.getBody().add(requestMap);
		
		
		Message retMessage = null;
	
		ServiceInvoker si;
		try {
			si = new ServiceInvoker("Create_User_Service", "send");
			retMessage = si.deliverSync(esbMessage, 10000L);
			HashMap map = (HashMap)retMessage.getBody().get(Body.DEFAULT_LOCATION);
			AuthInfo temp = new AuthInfo(Integer.parseInt((String)map.get("id")), (String)map.get("token"), 
				Long.parseLong((String)map.get("expiration")));
			return temp;
		} catch (MessageDeliverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FaultMessageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RegistryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@WebMethod
	public PostsContainer getPosts(int userId, String token, long expiration, long current, int currentUserId,int friend){
		// Setting the ConnectionFactory such that it will use scout
		System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");

		Message esbMessage = MessageFactory.getInstance().getMessage();
		
		Message retMessage = null;
		
		ServiceInvoker si;
		List<HashMap<String, Object>> posts = null;
		List<PostDetailsInfo> list = new ArrayList<PostDetailsInfo>();
		try {
			// Get user posts
			HashMap<String, Object> requestMap = new HashMap();
			
			requestMap.put("userId", userId);
			requestMap.put("token", token);
			requestMap.put("expiration", expiration);
			requestMap.put("current", current);
			requestMap.put("currentUserId", currentUserId);
			requestMap.put("friend", friend);
						
			esbMessage.getBody().add(requestMap);
			si = new ServiceInvoker("Get_Posts_Service", "send");
			retMessage = si.deliverSync(esbMessage, 10000L);
			System.out.println("MAIN WS POSTS: "+retMessage.getBody().get(Body.DEFAULT_LOCATION));
			posts = (List<HashMap<String, Object>>)retMessage.getBody().get(Body.DEFAULT_LOCATION);
			if(posts.size() == 0){
				return new PostsContainer(list);
			}
			else{
				// falhou autenticaçao
				try{
					if(((Integer)posts.get(0).get("id")).intValue() == 0){
						list.add(new PostDetailsInfo());
						return new PostsContainer(list);
					}
				}
				catch(ClassCastException ex){
					
				}
			}
			// Get users
			List<String> userIds = new ArrayList<String>();
			Iterator it = posts.iterator();
			while(it.hasNext()) {
				String postUserId = (String)((Map)it.next()).get("fromUserId");
				if (!userIds.contains(postUserId))
					userIds.add(postUserId);
			}
			requestMap.put("userId", userId);
			requestMap.put("token", token);
			requestMap.put("expiration", expiration);
			requestMap.put("current", current);
			requestMap.put("userIds", userIds);
			esbMessage.getBody().add(requestMap);
			si = new ServiceInvoker("Get_Users_Service", "send");
			retMessage = si.deliverSync(esbMessage, 10000L);
			HashMap<String, HashMap<String, Object>> users = (HashMap<String, HashMap<String, Object>>)retMessage.getBody().get(Body.DEFAULT_LOCATION);
			// falhou autenticaçao
			if(users.containsKey("0")){
				list.add(new PostDetailsInfo());
				return new PostsContainer(list);
			}
						
			// Get posts photos
			List<String> photoIds = new ArrayList<String>();
			it = posts.iterator();
			while(it.hasNext()) {
				String postPhotoId = (String)((Map)it.next()).get("photoId");
				if (!photoIds.contains(postPhotoId) && !postPhotoId.equals("-1"))
					photoIds.add(postPhotoId);
			}
			requestMap.put("userId", userId);
			requestMap.put("token", token);
			requestMap.put("expiration", expiration);
			requestMap.put("current", current);
			requestMap.put("photoIds", photoIds);
			esbMessage.getBody().add(requestMap);
			si = new ServiceInvoker("Get_Photos_Service", "send");
			retMessage = si.deliverSync(esbMessage, 10000L);
			HashMap<String, HashMap<String, Object>> photos = (HashMap<String, HashMap<String, Object>>)retMessage.getBody().get(Body.DEFAULT_LOCATION);
			// falhou autenticaçao
			if(photos.containsKey("0")){
				list.add(new PostDetailsInfo());
				return new PostsContainer(list);
			}
			
			// Get user photos
			List<HashMap<String, Object>> temp = new ArrayList<HashMap<String, Object>>(users.values());
			List<String> userPhotoIds = new ArrayList<String>();
			it = temp.iterator();
			while(it.hasNext()) {
				String userPhotoId = (String)((Map)it.next()).get("photoId");
				if (!userPhotoIds.contains(userPhotoId) && !userPhotoId.equals("-1"))
					userPhotoIds.add(userPhotoId);
			}
			requestMap.put("userId", userId);
			requestMap.put("token", token);
			requestMap.put("expiration", expiration);
			requestMap.put("current", current);
			requestMap.put("photoIds", userPhotoIds);
			esbMessage.getBody().add(requestMap);
			si = new ServiceInvoker("Get_Photos_Service", "send");
			retMessage = si.deliverSync(esbMessage, 10000L);
			HashMap<String, HashMap<String, Object>> usersPhotos = (HashMap<String, HashMap<String, Object>>)retMessage.getBody().get(Body.DEFAULT_LOCATION);
			// falhou autenticaçao
			if(usersPhotos.containsKey("0")){
				list.add(new PostDetailsInfo());
				return new PostsContainer(list);
			}
			it = posts.iterator();
			while(it.hasNext()){
				HashMap<String,Object> tempPost = (HashMap<String, Object>) it.next();
				PostDetailsInfo postDetails = new PostDetailsInfo();
				postDetails.setPostId(Integer.parseInt((String)tempPost.get("id")));
				postDetails.setPostText((String)tempPost.get("text"));				
				postDetails.setPostPrivate(Boolean.parseBoolean((String)tempPost.get("private")));
				int postPhotoId = Integer.parseInt((String)tempPost.get("photoId"));
				postDetails.setPostPhotoId(postPhotoId);
				if(postPhotoId!=-1)
					postDetails.setPostPhotoName((String)photos.get(postPhotoId+"").get("name"));
				int postUserId = Integer.parseInt((String)tempPost.get("fromUserId"));
				postDetails.setUserId(postUserId);
				postDetails.setUserName((String)users.get(postUserId+"").get("name"));
				int userPhotoId = Integer.parseInt((String)users.get(postUserId+"").get("photoId"));
				postDetails.setUserPhotoId(userPhotoId);
				if(userPhotoId!=-1){
					postDetails.setUserPhotoName((String)usersPhotos.get(userPhotoId+"").get("name"));
				}
				list.add(postDetails);
			}
			return new PostsContainer(list);
			
		} catch (MessageDeliverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FaultMessageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RegistryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
