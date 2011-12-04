package users;
import java.sql.Timestamp;
import java.util.*;

import javax.ejb.EJB;
import javax.jws.*;

import phasebook.post.Post;
import phasebook.user.*;
import posts.PostInfo;

@WebService(name = "User", targetNamespace = "http://PhasebookWS/User")  
public class Users  
{  
	@EJB(mappedName = "PhasebookUserBean/remote")
	PhasebookUserRemote userRemote;
	
	@WebMethod
	public List loginUser(@WebParam(name = "email") String email, 
			@WebParam(name = "password") String password,
			@WebParam(name = "current") long current)  
	{
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n############################################");
		System.out.println("email: " + email);
		System.out.println("############################################\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		int id = userRemote.login(email, password);
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n############################################");
		System.out.println("email: " + email);
		System.out.println("id: " + id);
		System.out.println("############################################\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
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
		int id = userRemote.create(name, email, password);
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
	public List<UserInfo> getUsers(@WebParam(name = "userId") int userId, 
			@WebParam(name = "token") String token,
			@WebParam(name = "current") long current,
			@WebParam(name = "expiration") long expiration)  
	{ 
		System.out.println("\n\n\n\n Cheguei Ao WS de Users \n\n\n\n");
		
		List<PhasebookUser> users = userRemote.getUsersFromSearch("", 0, "");
		List<UserInfo> result = new ArrayList<UserInfo>();
		
		Iterator it = users.iterator();
		while(it.hasNext())
		{
			PhasebookUser user = (PhasebookUser) it.next();
			result.add(new UserInfo(user.getId(), user.getName(), user.getEmail(), user.getMoney(), user.getPhotoId()));
		}
		
		System.out.println("\n\n\n\n Vou Sair Do WS de Users \n\n\n\n");
		return result;
	}	
	
}