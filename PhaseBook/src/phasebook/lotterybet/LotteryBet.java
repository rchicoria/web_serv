package phasebook.lotterybet;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class LotteryBet implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="BET_ID")
	private int id;
	
	@Column(name="PHASEBOOK_USER_ID")
	private int userId;
	
	@Column(name="BET_VALUE")
	private float betValue;
	
	@Column(name="BET_NUMBER")
	private int betNumber;
	
	@Column(name="VALUE_WON")
	private float valueWon;
	
	@Column(name="CREATED_AT")
	private Timestamp createdAT = new Timestamp(new Date().getTime());
	
	@Column(name="READ_")
	private boolean read_;
	
	@Column(name="LOTTERY_ID")
	private int lotteryId = -1;
	
	@Column(name="LOTTERY_NUMBER")
	private int lotteryNumber;
	
	@Column(name="LOTTERY_DATE")
	private Timestamp lotteryDate = new Timestamp(new Date().getTime());
	
	public LotteryBet()
	{
		super();
	}
	
	public LotteryBet(int id)
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

	public int getUserId() {
		return userId;
	}

	protected void setUserId(int userId) {
		this.userId = userId;
	}

	public float getBetValue() {
		return betValue;
	}

	protected void setBetValue(float betValue) {
		this.betValue = betValue;
	}

	public int getBetNumber() {
		return betNumber;
	}

	protected void setBetNumber(int betNumber) {
		this.betNumber = betNumber;
	}

	protected Date getCreatedAT() {
		return createdAT;
	}

	protected void setCreatedAT(Timestamp createdAT) {
		this.createdAT = createdAT;
	}

	public int getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}

	public float getValueWon() {
		return valueWon;
	}

	public void setValueWon(float valueWon) {
		this.valueWon = valueWon;
	}

	public boolean isRead_() {
		return read_;
	}

	public void setRead_(boolean read_) {
		this.read_ = read_;
	}

	public Timestamp getLotteryDate() {
		return lotteryDate;
	}

	public void setLotteryDate(Timestamp lotteryDate) {
		this.lotteryDate = lotteryDate;
	}

	public int getLotteryNumber() {
		return lotteryNumber;
	}

	public void setLotteryNumber(int lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}
	
}