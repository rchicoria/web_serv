package phasebook.user;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import phasebook.friendship.Friendship;
import phasebook.lotterybet.LotteryBet;

@Generated(value="Dali", date="2011-11-30T19:40:21.028+0000")
@StaticMetamodel(PhasebookUser.class)
public class PhasebookUser_ {
	public static volatile SingularAttribute<PhasebookUser, Integer> id;
	public static volatile SingularAttribute<PhasebookUser, String> name;
	public static volatile SingularAttribute<PhasebookUser, String> email;
	public static volatile SingularAttribute<PhasebookUser, String> password;
	public static volatile SingularAttribute<PhasebookUser, Float> money;
	public static volatile SingularAttribute<PhasebookUser, Timestamp> createdAt;
	public static volatile SingularAttribute<PhasebookUser, Integer> photo_id;
	public static volatile ListAttribute<PhasebookUser, Friendship> sentInvites;
	public static volatile ListAttribute<PhasebookUser, Friendship> receivedInvites;
	public static volatile ListAttribute<PhasebookUser, LotteryBet> lotteryBets;
	public static volatile SingularAttribute<PhasebookUser, Timestamp> deletedAt;
}
