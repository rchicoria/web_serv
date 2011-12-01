package phasebook.post;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Post implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="POST_ID")
	private int id;
	
	@Column(name="FROM_USER")
	private int fromUserId;
	
	@Column(name="TO_USER")
	private int toUserId;
	
	@Column(name="PRIVATE_")
	private boolean private_;
	
	@Column(name="READ_")
	private boolean read_;
	
	@Column(name="CREATED_AT")
	private Timestamp createdAt = new Timestamp(new Date().getTime());
	
	@Column(name="DELETED_AT")
	private Timestamp deletedAt = new Timestamp(0);
	
	@Column(name="PHOTO_ID")
	private int photo_id = -1;
	
	@Column(name="TEXT")
	private String text;
	
	public Post()
	{
		super();
	}
	
	public Post(int from_id, int to_id, String text, String privacy)
	{
		super();
		this.fromUserId = from_id;
		this.toUserId = to_id;
		this.text = text;
		if (privacy.compareTo("0")==0)
			this.private_ = false;
		else
			this.private_ = true;
	}
	
	public Post(int from_id, int to_id, String text, int photo_id, String privacy)
	{
		super();
		this.fromUserId = from_id;
		this.toUserId = to_id;
		this.text = text;
		this.photo_id = photo_id;
		if (privacy.compareTo("0")==0)
			this.private_ = false;
		else
			this.private_ = true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne
	public int getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}

	public int getToUserId() {
		return toUserId;
	}

	public void setToUser(int toUserId) {
		this.toUserId = toUserId;
	}

	public boolean isPrivate_() {
		return private_;
	}

	public void setPrivate_(boolean private_) {
		this.private_ = private_;
	}

	public boolean isRead_() {
		return read_;
	}

	public void setRead_(boolean read_) {
		this.read_ = read_;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getPhotoId() {
		return photo_id;
	}

	public void setPhotoId(int photo_id) {
		this.photo_id = photo_id;
	}
	
}
