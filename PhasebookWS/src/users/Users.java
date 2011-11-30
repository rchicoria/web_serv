package users;
import java.sql.Timestamp;
import java.util.*;

import javax.ejb.EJB;
import javax.jws.*;
import phasebook.user.*;

@WebService(name = "User", targetNamespace = "http://PhasebookWS/User")  
public class Users  
{  
	@EJB(mappedName = "PhasebookUserBean/remote")
	PhasebookUserRemote user;
	
	@WebMethod
	public List loginUser(@WebParam(name = "email") String email, 
			@WebParam(name = "password") String password,
			@WebParam(name = "current") long current)  
	{ 
		int id = user.login(email, password);
		Calendar temp = new GregorianCalendar();
		temp.setTimeInMillis(current);
		temp.add(Calendar.MINUTE, 1);
		// Time após um minuto
		long expiration = temp.getTimeInMillis();
		String token = "12345";
		List list = new ArrayList();
		list.add(id);
		list.add(token);
		list.add(expiration);
		return list;
	}
	
	@WebMethod
	public List createUser(@WebParam(name = "name") String name, 
			@WebParam(name = "email") String email, 
			@WebParam(name = "password") String password,
			@WebParam(name = "current") long current)  
	{ 
		int id = user.create(name, email, password);
		Calendar temp = new GregorianCalendar();
		temp.setTimeInMillis(current);
		temp.add(Calendar.MINUTE, 1);
		// Time após um minuto
		long expiration = temp.getTimeInMillis();
		String token = "12345";
		List list = new ArrayList();
		list.add(id);
		list.add(token);
		list.add(expiration);
		return list;

	}
	
}