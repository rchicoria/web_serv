package users;
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


import org.jboss.internal.soa.esb.addressing.helpers.EPRHelper;
import org.jboss.soa.esb.UnmarshalException;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.addressing.EPR;
import org.jboss.soa.esb.addressing.MalformedEPRException;
import org.jboss.soa.esb.couriers.Courier;
import org.jboss.soa.esb.couriers.CourierException;
import org.jboss.soa.esb.couriers.CourierFactory;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

public class LoginSendToMWSAction extends AbstractActionLifecycle
{

	protected ConfigTree _config;

	public LoginSendToMWSAction(ConfigTree config) {
		_config = config;
	}

	public Message process(Message message) {
		String xml = (String) message.getBody().get("reply");
		EPR jmsepr;
		System.out.println("Olá, eu sou o LoginSendToMWS\n"+xml);
		try {
			jmsepr = (EPR) EPRHelper.fromXMLString(xml);
			message.getHeader().getCall().setTo(jmsepr);
			Courier courier;
			courier = CourierFactory.getCourier(jmsepr);
			courier.deliver(message);
			System.out.println("Tudo OK!!!!!!!!!!!!");
		} catch (UnmarshalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (CourierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedEPRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}