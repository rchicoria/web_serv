package phasebook.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import phasebook.auth.Auth;
import phasebook.friendship.FriendshipRemote;
import phasebook.photo.PhotoRemote;
import phasebook.post.PostRemote;
import phasebook.lottery.LotteryRemote;
import phasebook.lotterybet.LotteryBetRemote;
import phasebook.user.PhasebookUser;
import phasebook.user.PhasebookUserRemote;

public class Utils {
	
	public static String MAIN_PATH   = "http://localhost:8080/photos/";
	public static int IMG_DEFAULT_WIDTH = 100; 
	
	// Creates a link to a URL in HTML
	public static String a(String url, String text)
	{
		if (url.length() == 0)
			return "<a href='/PhaseBookWeb'>" + text + "</a>";
		return "<a href='?p=" + url + "'>" + text + "</a>";
	}
	
	// Creates a link to a URL in HTML
	public static String aAbsolute(String url, String text)
	{
		return "<a href=\"" + url + "\">" + text + "</a>";
	}
	
	public static String img(String url)
	{
		return "<img src=\""+url+"\" width=\""+IMG_DEFAULT_WIDTH+"\" />";
	}
	
	public static String smallImg(String url)
	{
		return "<img src=\""+url+"\" width=\""+IMG_DEFAULT_WIDTH/2+"\" />";
	}
	
	// Prints the correct URL
	public static String url(String url)
	{
		if (url.length() == 0)
			return "";
		return "?p=" + url;
	}
	
	//Get user bean
	public static PhasebookUserRemote getUserBean()
	{
		InitialContext ctx = null;
		try {
			ctx = new InitialContext();
			return (PhasebookUserRemote) ctx.lookup("PhasebookUserBean/remote");
		} catch (NamingException e) {
			return null;
		}
	}
	
	//Get photo bean
	public static PhotoRemote getPhotoBean()
	{
		InitialContext ctx = null;
		try {
			ctx = new InitialContext();
			return (PhotoRemote) ctx.lookup("PhotoBean/remote");
		} catch (NamingException e) {
			return null;
		}
	}
	
	//Get lottery bean
	public static LotteryRemote getLotteryBean()
	{
		InitialContext ctx = null;
		try {
			ctx = new InitialContext();
			return (LotteryRemote) ctx.lookup("LotteryBean/remote");
		} catch (NamingException e) {
			return null;
		}
	}
	
	public static FriendshipRemote getFriendshipBean()
	{
		InitialContext ctx = null;
		try {
			ctx = new InitialContext();
			FriendshipRemote friendship;
			friendship = (FriendshipRemote) ctx.lookup("FriendshipBean/remote");
			return friendship;
		} catch (NamingException e) {
			return null;
		}
	}
	
	//Get lotterybet bean
	public static LotteryBetRemote getLotteryBetBean()
	{
		InitialContext ctx = null;
		try {
			ctx = new InitialContext();
			return (LotteryBetRemote) ctx.lookup("LotteryBetBean/remote");
		} catch (NamingException e) {
			return null;
		}
	}
	
	//Get friendship bean
	public static PostRemote getPostBean()
	{
		InitialContext ctx = null;
		try {
			ctx = new InitialContext();
			PostRemote post;
			post = (PostRemote) ctx.lookup("PostBean/remote");
			return post;
		} catch (NamingException e) {
			return null;
		}
	}
	
	//Get escaped text
	public static String text(String text)
	{
		if (text == null)
			return "";
		
		StringBuffer sb = new StringBuffer();
		int n = text.length();
		for (int i=0; i<n; i++)
		{
			char c = text.charAt(i);
			switch (c)
			{
				case '<': sb.append("&lt;"); break;
				case '>': sb.append("&gt;"); break;
				case '&': sb.append("&amp;"); break;
				case '"': sb.append("&quot;"); break;
				default: sb.append(c); break;
			}
		}
		
		return sb.toString();
	}
	
	// Get the number of notifications
	public static int getNumberNotifications(PhasebookUser user,
			Object authId, Object authPass)
	{
		if (Auth.authenticate(authId, authPass))
			return -1;
		int count = 0;
		count += ((List<Object>)getPostBean().getUnreadPosts(user,
				authId, authPass)).size();
		count += ((List<Object>)getLotteryBetBean().checkUnreadBetResults(user,
				authId, authPass)).size();
		count += ((List<Object>)getFriendshipBean().getNewFriendshipInvites(user,
				authId, authPass)).size();
		count += ((List<Object>)getFriendshipBean().getNewFriendshipAcceptances(user,
				authId, authPass)).size();
		return count;
	}

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16)
				sb.append('0');
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();
	}

	public static byte[] computeHash(String x) {
		java.security.MessageDigest d = null;
		try {
			d = java.security.MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			d = null;
		}
		d.reset();
		d.update(x.getBytes());
		return d.digest();
	}
}
