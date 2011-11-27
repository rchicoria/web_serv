package main;
import java.util.HashMap;
import java.util.Map;

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
	public int loginUser(String email, String password) {
		// Setting the ConnectionFactory such that it will use scout
		System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");
	
		Message esbMessage = MessageFactory.getInstance().getMessage();
		HashMap requestMap = new HashMap();
		requestMap.put("loginUser.email",email);
		requestMap.put("loginUser.password",password);
		esbMessage.getBody().add(requestMap);
		
		Message retMessage = null;
	
		ServiceInvoker si;
		try {
			si = new ServiceInvoker("Login_User_Service", "send");
			retMessage = si.deliverSync(esbMessage, 10000L);
			Map responseMsg = (Map) retMessage.getBody().get(Body.DEFAULT_LOCATION);
			//System.out.println("****************"+(String) responseMsg.get("loginResponse.return")+"***************");
			return Integer.parseInt((String)responseMsg.get("loginUserResponse.return"));
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
	
	@WebMethod
	public int createUser(String name, String email, String password) {
		// Setting the ConnectionFactory such that it will use scout
		System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");
	
		Message esbMessage = MessageFactory.getInstance().getMessage();
		HashMap requestMap = new HashMap();
		requestMap.put("createUser.name",name);
		requestMap.put("createUser.email",email);
		requestMap.put("createUser.password",password);
		esbMessage.getBody().add(requestMap);
		
		Message retMessage = null;
	
		ServiceInvoker si;
		try {
			si = new ServiceInvoker("Create_User_Service", "send");
			retMessage = si.deliverSync(esbMessage, 10000L);
			Map responseMsg = (Map) retMessage.getBody().get(Body.DEFAULT_LOCATION);
			//System.out.println("****************"+(String) responseMsg.get("loginResponse.return")+"***************");
			return Integer.parseInt((String)responseMsg.get("createUserResponse.return"));
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
