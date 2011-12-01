package phasebook.photo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Photo implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PHOTO_ID")
	private int id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="CREATED_AT")
	private Timestamp createdAt = new Timestamp(new Date().getTime());
	
	@Column(name="DELETED_AT")
	private Timestamp deletedAt = new Timestamp(0);
	
	@Column(name="LABEL")
	private String label;
	
	public Photo()
	{
		super();
	}
	
	public Photo(String name)
	{
		super();
		this.name = name;
	}
	
	public Photo(String name, String label)
	{
		super();
		this.name = name;
		this.label = label;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}