package users;
import javax.ejb.EJB;
import javax.jws.*;
import phasebook.user.*;

@WebService(name = "LoginUser", targetNamespace = "http://PhasebookWS/LoginUser")  
public class Users  
{  
	@EJB(mappedName = "PhasebookUserBean/remote")
	PhasebookUserRemote user;
	
	@WebMethod  
	public int loginUser(@WebParam(name = "email") String email, 
			@WebParam(name = "password") String password)  
	{ 
		int id = user.login(email, password);
	    return id;
	}
	
	@WebMethod
	public int createUser(@WebParam(name = "name") String name, 
			@WebParam(name = "email") String email, 
			@WebParam(name = "password") String password)  
	{ 
		int id = user.create(name, email, password);
	    return id;
	}
	
}