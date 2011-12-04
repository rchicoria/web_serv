package users;
import java.sql.Timestamp;
import java.util.*;

import javax.ejb.EJB;
import javax.jws.*;

import phasebook.post.Post;
import phasebook.user.*;
import posts.PostInfo;
import utils.Utils;

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
		int id = userRemote.login(email, password);
		// Time após um minuto
		long expiration = current + 20*1000;
		String token = Utils.byteArrayToHexString(Utils.computeHash(id + "salt"
				+ expiration));
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
			@WebParam(name = "expiration") long expiration,
			@WebParam(name = "userIds") String userIdsString)  
	{ 		
		String myToken = Utils.byteArrayToHexString(Utils.computeHash(userId + "salt"
				+ expiration));
		
		List<UserInfo> result = new ArrayList<UserInfo>();
		
		if(expiration < current || !token.equals(myToken)){
			result.add(new UserInfo());
			return result;
		}
		
		List<PhasebookUser> users = new ArrayList<PhasebookUser>();
		
		List<String> userIds = Arrays.asList(userIdsString.split(","));
		
		Iterator it = userIds.iterator();
		while(it.hasNext()){
			users.add(userRemote.getUserById(Integer.parseInt((String)it.next()), 0, ""));
		}
		
		it = users.iterator();
		while(it.hasNext())
		{
			PhasebookUser user = (PhasebookUser) it.next();
			result.add(new UserInfo(user.getId(), user.getName(), user.getEmail(), user.getMoney(), user.getPhotoId()));
		}
		
		return result;
	}	
	
}