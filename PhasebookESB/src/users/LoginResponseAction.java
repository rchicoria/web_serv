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

public class LoginResponseAction extends AbstractActionLifecycle
{

	protected ConfigTree _config;

	public LoginResponseAction(ConfigTree config) {
		_config = config;
	}

	public Message process(Message message) {
	  
		Map responseMsg = (Map) message.getBody().get(Body.DEFAULT_LOCATION);
		System.out.println(message.getBody().get(Body.DEFAULT_LOCATION));
		HashMap map = new HashMap();
		map.put("id", responseMsg.get("loginUserResponse[0]"));
		map.put("token", responseMsg.get("loginUserResponse[1]"));
		map.put("expiration", responseMsg.get("loginUserResponse[2]"));
		message.getBody().add(map);
		System.out.println(message.getBody().get(Body.DEFAULT_LOCATION));
		return message;  
	}

}