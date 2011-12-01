package phasebook.lottery;

import java.util.Hashtable;
import javax.naming.InitialContext;

public class LotteryMain {
	
	private static String pass = "thispassword";
	
	/*
	 * Should be run to start the internal lottery service if it's not already running
	 */
	public static void main(String[] args) throws Exception {  
		Hashtable<String, String> ht = new Hashtable<String, String>();
		ht.put(InitialContext.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");
		ht.put(InitialContext.PROVIDER_URL,"jnp://localhost:1099");
		ht.put(InitialContext.URL_PKG_PREFIXES,"org.jboss.naming:org.jnp.interfaces");
		
		InitialContext ctx = new InitialContext(ht);
		
		LotteryRemote timer = (LotteryRemote) ctx.lookup("LotteryBean/remote");
		timer.reset(pass);
		timer.scheduleTimer(1, pass);
	}

}
