package phasebook.lottery;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-11-30T14:45:34.520+0000")
@StaticMetamodel(Lottery.class)
public class Lottery_ {
	public static volatile SingularAttribute<Lottery, Integer> id;
	public static volatile SingularAttribute<Lottery, Integer> lotteryNumber;
	public static volatile SingularAttribute<Lottery, Timestamp> lotteryDate;
}
