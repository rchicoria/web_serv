package phasebook.lottery;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Lottery implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="LOTTERY_ID")
	private int id;
	
	@Column(name="LOTTERY_NUMBER")
	private int lotteryNumber;
	
	@Column(name="LOTTERY_DATE")
	private Timestamp lotteryDate = new Timestamp(new Date().getTime());
	
	public Lottery()
	{
		super();
	}
	
	public Lottery(int id)
	{
		super();
		this.id=id;
	}

	public int getId() {
		return id;
	}

	protected void setId(int id) {
		this.id = id;
	}

	public int getLotteryNumber() {
		return lotteryNumber;
	}

	protected void setLotteryNumber(int lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}

	public Timestamp getLotteryDate() {
		return lotteryDate;
	}

	protected void setLotteryDate(Timestamp lotteryDate) {
		this.lotteryDate = lotteryDate;
	}
	
}