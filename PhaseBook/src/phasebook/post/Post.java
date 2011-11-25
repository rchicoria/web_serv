package phasebook.post;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import phasebook.photo.Photo;
import phasebook.user.PhasebookUser;


@Entity
public class Post implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="POST_ID")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="FROM_USER", referencedColumnName = "PHASEBOOK_USER_ID")
	private PhasebookUser fromUser;
	
	@ManyToOne
	@JoinColumn(name="TO_USER", referencedColumnName = "PHASEBOOK_USER_ID")
	private PhasebookUser toUser;
	
	@Column(name="PRIVATE_")
	private boolean private_;
	
	@Column(name="READ_")
	private boolean read_;
	
	@Column(name="CREATED_AT")
	private Timestamp createdAt = new Timestamp(new Date().getTime());
	
	@Column(name="DELETED_AT")
	private Timestamp deletedAt;
	
	@OneToOne
	@JoinColumn(name="PHOTO_ID", referencedColumnName = "PHOTO_ID")
	private Photo photo;
	
	@Column(name="TEXT")
	private String text;
	
	public Post()
	{
		super();
	}
	
	public Post(PhasebookUser from, PhasebookUser to, String text, String privacy)
	{
		super();
		this.fromUser = from;
		this.toUser = to;
		this.text = text;
		if (privacy.compareTo("0")==0)
			this.private_ = false;
		else
			this.private_ = true;
	}
	
	public Post(PhasebookUser from, PhasebookUser to, String text, Photo photo, String privacy)
	{
		super();
		this.fromUser = from;
		this.toUser = to;
		this.text = text;
		this.photo = photo;
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
	public PhasebookUser getFromUser() {
		return fromUser;
	}

	public void setFromUser(PhasebookUser fromUser) {
		this.fromUser = fromUser;
	}

	public PhasebookUser getToUser() {
		return toUser;
	}

	public void setToUser(PhasebookUser toUser) {
		this.toUser = toUser;
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

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	
	
}
