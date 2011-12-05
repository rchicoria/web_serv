package main;
import info.AuthInfo;
import info.PostDetailsInfo;
import info.UserInfo;

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
		System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");
		Message esbMessage = MessageFactory.getInstance().getMessage();
		
		esbMessage.getBody().add("email", email);
		esbMessage.getBody().add("password", password);
		esbMessage.getBody().add("current", current);
		
		try {
			ServiceInvoker si = new ServiceInvoker("Login_User_Service", "send");
			Message retMessage = si.deliverSync(esbMessage, 10000L);

			return new AuthInfo(Integer.parseInt((String)retMessage.getBody().get("id")),
					(String)retMessage.getBody().get("token"), 
					Long.parseLong((String)retMessage.getBody().get("expiration")));
		}
		catch (MessageDeliverException e) {e.printStackTrace();}
		catch (FaultMessageException e) {e.printStackTrace();}
		catch (RegistryException e) {e.printStackTrace();}
		
		return null;
	}
	
	@WebMethod
	public AuthInfo createUser(String name, String email, String password, long current) {
		System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");
		Message esbMessage = MessageFactory.getInstance().getMessage();
		
		esbMessage.getBody().add("name", name);
		esbMessage.getBody().add("email", email);
		esbMessage.getBody().add("password", password);
		esbMessage.getBody().add("current", current);
	
		try {
			ServiceInvoker si = new ServiceInvoker("Create_User_Service", "send");
			Message retMessage = si.deliverSync(esbMessage, 10000L);
			
			return new AuthInfo(Integer.parseInt((String)retMessage.getBody().get("id")),
					(String)retMessage.getBody().get("token"), 
					Long.parseLong((String)retMessage.getBody().get("expiration")));
		}
		catch (MessageDeliverException e) {e.printStackTrace();}
		catch (FaultMessageException e) {e.printStackTrace();}
		catch (RegistryException e) {e.printStackTrace();}
		
		return null;
	}
	
	@WebMethod
	public PostsContainer getPosts(long current, int currentUserId, int friend,
			int userId, String token, long expiration) {
		System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");
		Message esbMessage = MessageFactory.getInstance().getMessage();
		
		List<HashMap<String, Object>> posts = null;
		List<PostDetailsInfo> list = new ArrayList<PostDetailsInfo>();
		try {
			// Get user posts
			esbMessage.getBody().add("userId", userId);
			esbMessage.getBody().add("token", token);
			esbMessage.getBody().add("expiration", expiration);
			esbMessage.getBody().add("current", current);
			esbMessage.getBody().add("currentUserId", currentUserId);
			esbMessage.getBody().add("friend", friend);
			
			ServiceInvoker si = new ServiceInvoker("Get_Posts_Service", "send");
			Message retMessage = si.deliverSync(esbMessage, 10000L);
			
			posts = (List<HashMap<String, Object>>)retMessage.getBody().get("posts");
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
				catch(ClassCastException e){}
			}
			// Get users
			List<String> userIds = new ArrayList<String>();
			Iterator it = posts.iterator();
			while(it.hasNext()) {
				String postUserId = (String)((Map)it.next()).get("fromUserId");
				if (!userIds.contains(postUserId))
					userIds.add(postUserId);
			}
			
			esbMessage.getBody().add("userId", userId);
			esbMessage.getBody().add("token", token);
			esbMessage.getBody().add("expiration", expiration);
			esbMessage.getBody().add("current", current);
			esbMessage.getBody().add("userIds", userIds);
			
			si = new ServiceInvoker("Get_Users_Service", "send");
			retMessage = si.deliverSync(esbMessage, 10000L);
			HashMap<String, HashMap<String, Object>> users = (HashMap<String, HashMap<String, Object>>)retMessage.getBody().get("users");
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
			
			esbMessage.getBody().add("userId", userId);
			esbMessage.getBody().add("token", token);
			esbMessage.getBody().add("expiration", expiration);
			esbMessage.getBody().add("current", current);
			esbMessage.getBody().add("photoIds", photoIds);
			
			si = new ServiceInvoker("Get_Photos_Service", "send");
			retMessage = si.deliverSync(esbMessage, 10000L);
			HashMap<String, HashMap<String, Object>> photos = (HashMap<String, HashMap<String, Object>>)retMessage.getBody().get("photos");
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
			
			esbMessage.getBody().add("userId", userId);
			esbMessage.getBody().add("token", token);
			esbMessage.getBody().add("expiration", expiration);
			esbMessage.getBody().add("current", current);
			esbMessage.getBody().add("photoIds", photoIds);
			
			si = new ServiceInvoker("Get_Photos_Service", "send");
			retMessage = si.deliverSync(esbMessage, 10000L);
			HashMap<String, HashMap<String, Object>> usersPhotos = (HashMap<String, HashMap<String, Object>>)retMessage.getBody().get("photos");
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
			
		}
		catch (MessageDeliverException e) {e.printStackTrace();}
		catch (FaultMessageException e) {e.printStackTrace();}
		catch (RegistryException e) {e.printStackTrace();}
		
		return null;
	}

	@WebMethod
	public UserInfo getUserById(long current, int currentUserId,
			int userId, String token, long expiration) {
		System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");
		Message esbMessage = MessageFactory.getInstance().getMessage();
		List<String> userIds = new ArrayList<String>();
		
		userIds.add(currentUserId+"");
		
		ServiceInvoker si;
		try {
			
			esbMessage.getBody().add("userId", userId);
			esbMessage.getBody().add("token", token);
			esbMessage.getBody().add("expiration", expiration);
			esbMessage.getBody().add("current", current);
			esbMessage.getBody().add("userIds", userIds);
			
			si = new ServiceInvoker("Get_Users_Service", "send");
			Message retMessage = si.deliverSync(esbMessage, 10000L);

			HashMap<String, HashMap<String, Object>> users = (HashMap<String, HashMap<String, Object>>)retMessage.getBody().get("users");
			System.out.println("Saio daqui com este user: "+retMessage.getBody().get("users"));
			if(users.containsKey("0")){
				return new UserInfo();
			}
			HashMap temp = (HashMap<String, Object>)users.get(users.keySet().iterator().next());
			return new UserInfo(Integer.parseInt((String)temp.get("id")), 
					(String)temp.get("name"), (String)temp.get("email"), Float.parseFloat((String)temp.get("money")),
					Integer.parseInt((String)temp.get("photoId")));
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
	public int createPost(int from, int to, String text, String privacy, String photoLink,
			long current, int userId, String token, long expiration) {
		System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");
		Message esbMessage = MessageFactory.getInstance().getMessage();
		List<String> userIds = new ArrayList<String>();
		
		ServiceInvoker si;
		try {
			
			userIds.add(from+"");
			userIds.add(to+"");
			
			esbMessage.getBody().add("userId", userId);
			esbMessage.getBody().add("token", token);
			esbMessage.getBody().add("expiration", expiration);
			esbMessage.getBody().add("current", current);
			esbMessage.getBody().add("userIds", userIds);
			
			si = new ServiceInvoker("Get_Users_Service", "send");
			Message retMessage = si.deliverSync(esbMessage, 10000L);
			
			System.out.println("Vou Buscar Users");

			HashMap<String, HashMap<String, Object>> users = (HashMap<String, HashMap<String, Object>>)retMessage.getBody().get("users");
			if(users.containsKey("0")){
				return -1;
			}
			
			UserInfo fromUser = null, toUser = null;
			
			HashMap temp = (HashMap<String, Object>)users.get(users.keySet().iterator().next());
			if(Integer.parseInt((String)temp.get("id")) == from){
				fromUser = new UserInfo(Integer.parseInt((String)temp.get("id")), 
					(String)temp.get("name"), (String)temp.get("email"), Float.parseFloat((String)temp.get("money")),
					Integer.parseInt((String)temp.get("photoId")));
				temp = (HashMap<String, Object>)users.get(users.keySet().iterator().next());
				
				toUser = new UserInfo(Integer.parseInt((String)temp.get("id")), 
						(String)temp.get("name"), (String)temp.get("email"), Float.parseFloat((String)temp.get("money")),
						Integer.parseInt((String)temp.get("photoId")));
			}
			else {
				toUser = new UserInfo(Integer.parseInt((String)temp.get("id")), 
					(String)temp.get("name"), (String)temp.get("email"), Float.parseFloat((String)temp.get("money")),
					Integer.parseInt((String)temp.get("photoId")));
				temp = (HashMap<String, Object>)users.get(users.keySet().iterator().next());
				
				fromUser = new UserInfo(Integer.parseInt((String)temp.get("id")), 
						(String)temp.get("name"), (String)temp.get("email"), Float.parseFloat((String)temp.get("money")),
						Integer.parseInt((String)temp.get("photoId")));
			}
			
			System.out.println("NOMES "+toUser.getName()+" "+fromUser.getName());
			
			esbMessage.getBody().add("userId", userId);
			esbMessage.getBody().add("token", token);
			esbMessage.getBody().add("expiration", expiration);
			esbMessage.getBody().add("current", current);
			esbMessage.getBody().add("fromId", fromUser.getId());
			esbMessage.getBody().add("fromName", fromUser.getName());
			esbMessage.getBody().add("fromEmail", fromUser.getEmail());
			esbMessage.getBody().add("fromMoney", fromUser.getMoney());
			esbMessage.getBody().add("fromPhotoId", fromUser.getPhotoId());
			esbMessage.getBody().add("toId", toUser.getId());
			esbMessage.getBody().add("toName", toUser.getName());
			esbMessage.getBody().add("toEmail", toUser.getEmail());
			esbMessage.getBody().add("toMoney", toUser.getMoney());
			esbMessage.getBody().add("toPhotoId", toUser.getPhotoId());
			esbMessage.getBody().add("text", text);
			esbMessage.getBody().add("privacy", privacy);
			esbMessage.getBody().add("photoLink", photoLink);
			
			si = new ServiceInvoker("Create_Post_Service", "send");
			System.out.println("Vai enviar esta msg para o ESB: "+esbMessage.getBody());
			retMessage = si.deliverSync(esbMessage, 10000L);

			return Integer.parseInt((String)retMessage.getBody().get("resp"));

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
		return -1;
		
	}
}
