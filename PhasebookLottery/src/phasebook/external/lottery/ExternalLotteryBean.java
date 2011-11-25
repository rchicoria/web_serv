package phasebook.external.lottery;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

@Stateless
public class ExternalLotteryBean implements ExternalLotteryRemote {

	private static Calendar nextDraw;
	private static int number;
	private static int timerInterval = 1000 * 60;
	@Resource
	private TimerService t;
	
	private @Resource SessionContext ctx;
	  
	public void scheduleTimer(long milliseconds) {
		Collection<Timer> timers = t.getTimers();
		for (Timer i : timers)
			i.cancel();
		ctx.getTimerService().createTimer(new Date(new Date().getTime() + milliseconds), "Hello World");  
	}
	
	public void reset() {
		ctx.getTimerService().getTimers().clear();
	}
	
	@Timeout
	public void timeout(Timer timer){
		nextDraw = Calendar.getInstance();
		nextDraw.add(Calendar.MILLISECOND,  timerInterval);
		Random r = new Random();
		number = r.nextInt(100) + 1;
		System.out.println("External Lottery: number " + number);
		this.scheduleTimer(timerInterval);
	}
	
	public int getNumber() {
		return number;
	}
	
	public Calendar getNextDraw() {
		return nextDraw;
	}
	
	public int getTimerInterval() {
		return this.timerInterval;
	}

}
