package main;
import info.AuthInfo;

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
			//System.out.println("****************"+(String) responseMsg.get("loginResponse.return")+"***************");
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
	public void getPosts(int userId, String token, long expiration, long current){
		// Setting the ConnectionFactory such that it will use scout
		System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");

		Message esbMessage = MessageFactory.getInstance().getMessage();
		HashMap requestMap = new HashMap();
		
		requestMap.put("userId", userId);
		requestMap.put("token", token);
		requestMap.put("expiration", expiration);
		requestMap.put("current", current);
		
		esbMessage.getBody().add(requestMap);
		
		Message retMessage = null;
		
		ServiceInvoker si;
		try {
			si = new ServiceInvoker("Get_Posts_Service", "send");
			retMessage = si.deliverSync(esbMessage, 10000L);
			
			//Ir buscar os posts de um User
			//List posts = (List)retMessage.getBody().get(Body.DEFAULT_LOCATION);
			
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
	}
}
