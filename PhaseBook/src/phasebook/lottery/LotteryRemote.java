package phasebook.lottery;

import javax.ejb.Remote;

@Remote
public interface LotteryRemote {
	
	void scheduleTimer(long milliseconds, String pass);
	public void reset(String pass);
	public String nextDrawDate(Object authId, Object authPass);

}
