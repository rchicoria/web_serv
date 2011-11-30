package phasebook.lotterybet;

import java.util.List;

import javax.ejb.Remote;

import phasebook.user.PhasebookUser;

@Remote
public interface LotteryBetRemote {
	
	public boolean createBet(Object id, int number, Object authId, Object authPass);
	public List<LotteryBet> userCurrentBets(Object id, Object authId, Object authPass);
	public List<LotteryBet> userOldBets(Object id, Object authId, Object authPass);
	public Object checkUnreadBetResults(int user_id, Object authId, Object authPass);
	public void readUnreadBets(int user_id, Object authId, Object authPass);
}
