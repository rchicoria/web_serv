package phasebook.lottery;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import phasebook.lotterybet.LotteryBet;

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
	
	@ElementCollection  
	private List<LotteryBet> lotteryBets = new ArrayList<LotteryBet>();
	
	public Lottery()
	{
		super();
	}
	
	public Lottery(int id)
	{
		super();
		this.id=id;
	}

	protected int getId() {
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

	@OneToMany(cascade=CascadeType.ALL, mappedBy="lottery")
	public List<LotteryBet> getLotteryBets() {
		return lotteryBets;
	}
	
	public void setLotteryBets(List<LotteryBet> lotteryBets) {
		this.lotteryBets = lotteryBets;
	}
	
	
}
