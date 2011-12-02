package action;
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
import org.jboss.soa.esb.message.Message;

public class InitializeListenerAction extends AbstractActionLifecycle
{

 protected ConfigTree _config;

 public InitializeListenerAction(ConfigTree config) {
  _config = config;
 }


 public Message init(Message message) throws Exception{
  System.out.println("---------------------------------- Initialize ----------------------------------");
  String msgbody = (String) message.getBody().get();
  String [] vec = msgbody.split(" ", 2);
  String user = vec[0];
  String text = vec[1];
  message.getBody().add("userName", user);
  message.getBody().add(text);
  System.out.println("Initialize got the following data. User = " + user + ". Text = " + text);
  System.out.println("-------------------------------- end initialize --------------------------------");
  return message;
 }


}