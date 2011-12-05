package info;

import java.io.Serializable;

public class UserInfo implements Serializable {

	private int id;
	private String name;
	private String email;
	private float money;
	private int photoId;
	
	public UserInfo(int id, String name, String email, float money, int photoId){
		this.setId(id);
		this.setName(name);
		this.setEmail(email);
		this.setMoney(money);
		this.setPhotoId(photoId);
	}
	
	public UserInfo() {
		
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

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}
}