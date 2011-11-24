package data;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.listeners.message.MessageDeliverException;
import org.jboss.soa.esb.message.Message;

public class MyListenerAction extends AbstractActionLifecycle
{

 protected ConfigTree _config;

 public MyListenerAction(ConfigTree config) {
  _config = config; 
 } 

 public Message transform(Message message) throws MessageDeliverException {
  return message;
 }

}