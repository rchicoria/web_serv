package login;
import javax.ejb.EJB;
import javax.jws.*;
import phasebook.user.*;

@WebService(name = "LoginUser", targetNamespace = "http://PhasebookWS/LoginUser")  
public class LoginUser  
{  
	@EJB(mappedName = "PhasebookUserBean/remote")
	PhasebookUserRemote user;
	
	@WebMethod  
	public String login(@WebParam(name = "email")  
	String email, @WebParam(name = "password") String password)  
	{ 
		String id = Integer.toString(user.login(email, password));
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$"+id+"$$$$$$$$$$$$$$$$$");
	    return id;
	}
}