package phasebook.lotterybet;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface LotteryBetRemote {
	
	public boolean createBet(Object id, int number, Object authId, Object authPass);
	public List<LotteryBet> userCurrentBets(Object id, Object authId, Object authPass);
	public List<LotteryBet> userOldBets(Object id, Object authId, Object authPass);
	public List<?> checkUnreadBetResults(int user_id, Object authId, Object authPass);
	public void readUnreadBets(int user_id, Object authId, Object authPass);
}
