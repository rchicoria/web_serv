package main;
import javax.jws.WebMethod;
import javax.jws.WebService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.jboss.soa.esb.listeners.message.MessageDeliverException;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.services.registry.RegistryException;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.couriers.FaultMessageException;
@WebService
public class Methods {
	
	@WebMethod
	public void authenticate(String email, String password) {
		// Setting the ConnectionFactory such that it will use scout
		System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");
	
		String names[] = {"Jose", "Joao", "Pedro", "Paula", "Joao"};
		List<String> mylist = Arrays.asList(names);
	
		Message esbMessage = MessageFactory.getInstance().getMessage();
		esbMessage.getBody().add(mylist);
		
		Message retMessage = null;
	
		ServiceInvoker si;
		try {
			si = new ServiceInvoker("Objects_in_Messages_Service", "send");
			retMessage = si.deliverSync(esbMessage, 10000L);
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
		
		@SuppressWarnings("unchecked")
		Set<String> resultset = (Set<String>) retMessage.getBody().get();
		System.out.println("Results. Only one Joao should exist...");
		for (String name : resultset)
			System.out.println(name);
		}
}
