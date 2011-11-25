package phasebook.external.lottery;

import java.util.Hashtable;
import javax.naming.InitialContext;

public class ExternalLotteryMain {
	
	/*
	 * Should only be run to start the external lottery service if it's not already running
	 */
	public static void main(String[] args) throws Exception {  
		Hashtable ht = new Hashtable();
		ht.put(InitialContext.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");
		ht.put(InitialContext.PROVIDER_URL,"jnp://localhost:1099");
		ht.put(InitialContext.URL_PKG_PREFIXES,"org.jboss.naming:org.jnp.interfaces");
		
		InitialContext ctx = new InitialContext(ht);
		
		ExternalLotteryRemote timer = (ExternalLotteryRemote) ctx.lookup("ExternalLotteryBean/remote");
		timer.reset();
		timer.scheduleTimer(1);
	}

}