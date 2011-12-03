package users;
import java.util.*;
/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and others contributors as indicated
* by the @authors tag. All rights reserved.
* See the copyright.txt in the distribution for a
* full listing of individual contributors.
* This copyrighted material is made available to anyone wishing to use,
* modify, copy, or redistribute it subject to the terms and conditions
* of the GNU Lesser General Public License, v. 2.1.
* This program is distributed in the hope that it will be useful, but WITHOUT A
* WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
* PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
* You should have received a copy of the GNU Lesser General Public License,
* v.2.1 along with this distribution; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
* MA  02110-1301, USA.
*
* (C) 2005-2006,
* @author JBoss Inc.
*/
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

public class GetUsersRequestAction extends AbstractActionLifecycle
{

	 protected ConfigTree _config;
	
	 public GetUsersRequestAction(ConfigTree config) {
		 _config = config;
	 }
	
	 public Message process(Message message) {
		  
	     Map requestMsg = ((Map)message.getBody().get(Body.DEFAULT_LOCATION));
	     int userId = ((Integer)requestMsg.get("userId")).intValue();
	     String token = (String)requestMsg.get("token");
	     long current = ((Long)requestMsg.get("current")).longValue();
	     long expiration = ((Long)requestMsg.get("expiration")).longValue();
	     List<Integer> userIds = ((List<Integer>)requestMsg.get("userIds"));
	     
	     List<Integer> temp = new ArrayList<Integer>();
	     String userIdsString = ""; 
	     
	     Iterator it = userIds.iterator();
	     while(it.hasNext()){
	    	 int id = Integer.parseInt((String)it.next());
	    	 if(!temp.contains(id)){
	    		 temp.add(id);
	    		 userIdsString += id+",";
	    	 }
	     }
	     
	     if(userIdsString.length() != 0)
	    	 userIdsString = userIdsString.substring(0, userIdsString.length()-1);
	 
	     Map send = new HashMap();
	     send.put("getUsers.userId", userId);
	     send.put("getUsers.token", token);
	     send.put("getUsers.current", current);
	     send.put("getUsers.expiration", expiration);
	     send.put("getUsers.userIds", userIdsString);
	     
	     message.getBody().add(send);
	     
	     System.out.println("GET USERS REQUEST"+message.getBody().get(Body.DEFAULT_LOCATION));
	     
	     return message;  
	 }

}