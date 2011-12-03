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

public class GetPhotosResponseAction extends AbstractActionLifecycle
{

	protected ConfigTree _config;

	public GetPhotosResponseAction(ConfigTree config) {
		_config = config;
	}

	public Message process(Message message) {
	  
		Map responseMsg = (Map) message.getBody().get(Body.DEFAULT_LOCATION);
		System.out.println("GET PHOTOS RESPONSE"+message.getBody().get(Body.DEFAULT_LOCATION));
		Iterator it = responseMsg.keySet().iterator();
		HashMap<String, HashMap<String, Object>> map = new HashMap<String, HashMap<String, Object>>();
		try{
			while(it.hasNext()){
				HashMap<String, Object> photo = new HashMap<String, Object>();
				String id = (String)responseMsg.get(it.next());
				photo.put("id", id);
				photo.put("name", responseMsg.get(it.next()));
				System.out.println(photo);
				map.put(id,photo);
			}
		} catch(NoSuchElementException ex){
			System.out.println("Não há photos");
		}
		message.getBody().add(map);
		//message.getBody().add(map);
		//System.out.println(message.getBody().get(Body.DEFAULT_LOCATION));
		return message;  
	}

}