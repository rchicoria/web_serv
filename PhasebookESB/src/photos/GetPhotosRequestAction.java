package photos;
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

public class GetPhotosRequestAction extends AbstractActionLifecycle
{

	 protected ConfigTree _config;
	
	 public GetPhotosRequestAction(ConfigTree config) {
		 _config = config;
	 }
	
	 public Message process(Message message) {
		  
	     Map requestMsg = ((Map)message.getBody().get(Body.DEFAULT_LOCATION));
	     int userId = ((Integer)requestMsg.get("userId")).intValue();
	     String token = (String)requestMsg.get("token");
	     long current = ((Long)requestMsg.get("current")).longValue();
	     long expiration = ((Long)requestMsg.get("expiration")).longValue();
	     List<Integer> photoIds = ((List<Integer>)requestMsg.get("photoIds"));
	     
	     List<Integer> temp = new ArrayList<Integer>();
	     String photoIdsString = ""; 
	     
	     Iterator it = photoIds.iterator();
	     while(it.hasNext()){
	    	 int id = Integer.parseInt((String)it.next());
	    	 if(!temp.contains(id)){
	    		 temp.add(id);
	    		 photoIdsString += id+",";
	    	 }
	     }
	     
	     if(photoIdsString.length()!= 0)
	    	 photoIdsString = photoIdsString.substring(0, photoIdsString.length()-1);
	 
	     Map send = new HashMap();
	     send.put("getPhotos.userId", userId);
	     send.put("getPhotos.token", token);
	     send.put("getPhotos.current", current);
	     send.put("getPhotos.expiration", expiration);
	     send.put("getPhotos.photoIds", photoIdsString);
	     
	     message.getBody().add(send);
	     
	     System.out.println("GET USERS REQUEST"+message.getBody().get(Body.DEFAULT_LOCATION));
	     
	     return message;  
	 }

}