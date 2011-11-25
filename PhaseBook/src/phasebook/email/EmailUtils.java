package phasebook.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import phasebook.photo.Photo;
import phasebook.user.PhasebookUser;

public class EmailUtils {
	
	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final int SMTP_HOST_PORT = 465;
    private static final String SMTP_AUTH_USER = "phasebookmessager@gmail.com";
    private static final String SMTP_AUTH_PWD  = "grrgrrgrr";
	public static String MAIN_PATH   = "http://localhost:8080/photos/";
	public static String DEFAULT_PATH = "http://localhost:8080/PhaseBookWeb/";
	public static int IMG_DEFAULT_WIDTH = 300;
	
	public static void notifyUser(PhasebookUser user, String subject, String body){
		Properties props = new Properties();

        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", SMTP_HOST_NAME);
        props.put("mail.smtps.auth", "true");
        // props.put("mail.smtps.quitwait", "false");

        Session mailSession = Session.getDefaultInstance(props);
        mailSession.setDebug(true);
        Transport transport = null;
		try {
			transport = mailSession.getTransport();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        MimeMessage message = new MimeMessage(mailSession);
        try {
			message.setSubject(subject);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
    		message.setContent(body, "text/html");
			message.addRecipient(Message.RecipientType.TO,
		             new InternetAddress(user.getEmail()));
			transport.connect
	          (SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);

	        transport.sendMessage(message,
	            message.getRecipients(Message.RecipientType.TO));
	        transport.close();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void postSent(PhasebookUser to, PhasebookUser from, String text, Photo photo, int unread)
	{
		String subject = "PHASEBOOK: You have a new post";
		String body = "";
		if(!text.equals(""))
			body = from.getName()+" posted a message on your wall:<br><br>\""+text+"\"<br><br>";
		else
			body = from.getName()+" posted a photo on your wall:<br><br>";
		if(photo != null)
			body += img(photo.getName(), to.getId())+"<br><br>";
		if(unread-1 == 1)
			body += "You have also "+(unread-1)+" more post to read.<br><br>";
		else if(unread-1 > 1)
			body += "You have also "+(unread-1)+" more posts to read.<br><br>";
		body += EmailUtils.a("","You wall");
		notifyUser(to, subject, body);
	}
	
	public static void sentInvite(PhasebookUser hostUser, PhasebookUser invitedUser){
		String subject = "PHASEBOOK: "+hostUser.getName()+" invited you";
		String body=hostUser.getName()+" invited you to be his friend<br><br>"
				+EmailUtils.a("notifications","Your notifications");
		notifyUser(invitedUser, subject, body);
	}
	
	public static void acceptedInvite(PhasebookUser hostUser, PhasebookUser invitedUser){
		String subject = "PHASEBOOK: "+invitedUser.getName()+" accepted your friendship request";
		String body="You and "+invitedUser.getName()+" are friends now!<br><br>"+
				EmailUtils.a("notifications","Go to your notifications");
		notifyUser(hostUser, subject, body);
	}
	
	public static String img(String url, int id)
	{
		return "<img src=\""+MAIN_PATH+id+"/"+url+"\" width=\""+IMG_DEFAULT_WIDTH+"\" />";
	}
	
	// Creates a link to a URL in HTML
	public static String a(String url, String text)
	{
		if (url.length() == 0)
			return "<a href='"+DEFAULT_PATH+"'>" + text + "</a>";
		return "<a href='"+DEFAULT_PATH+"?p=" + url + "'>" + text + "</a>";
	}

}
