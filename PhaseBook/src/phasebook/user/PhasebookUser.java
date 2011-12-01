package phasebook.user;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import phasebook.friendship.Friendship;
import phasebook.photo.Photo;
import phasebook.lotterybet.LotteryBet;
import phasebook.post.Post;

@Entity
public class PhasebookUser implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PHASEBOOK_USER_ID")
	private int id;
	
	private String name, email, password;
	private float money;
	
	@Column(name="CREATED_AT")
	private Timestamp createdAt = new Timestamp(new Date().getTime());
	
	@Column(name="DELETED_AT")
	private Timestamp deletedAt;
	
	/*@OneToOne
	@JoinColumn(name="PHOTO_ID", referencedColumnName = "PHOTO_ID")
	private Photo photo;*/
	
	@Column(name="PHOTO_ID")
	private int photo_id = -1;
	
	/*@ElementCollection
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "hostUser") 
	private List<Friendship> sentInvites = new ArrayList<Friendship>();
	
	@ElementCollection
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "invitedUser")
	private List<Friendship> receivedInvites = new ArrayList<Friendship>();*/
	
	/*@ElementCollection
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "user")
	private List<LotteryBet> lotteryBets = new ArrayList<LotteryBet>();*/
	
	/*@ElementCollection  
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "fromUser")
	private List<Post> sentPosts = new ArrayList<Post>();
	
	@ElementCollection
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "toUser")
	private List<Post> receivedPosts = new ArrayList<Post>();*/
	
	public PhasebookUser()
	{
		super();
	}
	
	public PhasebookUser(String name, String email, String password, float money)
	{
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.money = money;
	}
	
	public PhasebookUser(String name, String email, String password)
	{
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	@Override
	public String toString()
	{
		return "[" + this.id + "] " + this.name + ": " + this.email + ", " + this.password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	protected String getPassword() {
		return password;
	}

	protected void setPassword(String password) {
		this.password = password;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*public List<Friendship> getSentInvites() {
		return sentInvites;
	}

	public void setSentInvites(List<Friendship> sentInvites) {
		this.sentInvites = sentInvites;
	}

	public List<Friendship> getReceivedInvites() {
		return receivedInvites;
	}

	public void setReceivedInvites(List<Friendship> receivedInvites) {
		this.receivedInvites = receivedInvites;
	}*/

	/*public List<LotteryBet> getLotteryBets() {
		return lotteryBets;
	}

	public void setLotteryBets(List<LotteryBet> lotteryBets) {
		this.lotteryBets = lotteryBets;
	}*/

	/*public List<Post> getReceivedPosts() {
		return receivedPosts;
	}

	public void setReceivedPosts(List<Post> receivedPosts) {
		this.receivedPosts = receivedPosts;
	}

	public List<Post> getSentPosts() {
		return sentPosts;
	}

	public void setSentPosts(List<Post> sentPosts) {
		this.sentPosts = sentPosts;
	}*/
	
	public boolean equals(PhasebookUser user) {
		return user.getId() == this.getId();
	}

	public int getPhotoId() {
		return photo_id;
	}

	public void setPhotoId(int photo_id) {
		this.photo_id = photo_id;
	}
}