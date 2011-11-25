package phasebook.external.lottery;

import java.util.Calendar;

import javax.ejb.Remote;

@Remote
public interface ExternalLotteryRemote {
	
	public void scheduleTimer(long milliseconds);
	public void reset();
	public int getNumber();
	public Calendar getNextDraw();
	public int getTimerInterval();

}