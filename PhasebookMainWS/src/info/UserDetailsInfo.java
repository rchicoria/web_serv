package info;

public class UserDetailsInfo {
	
	private int userId;
	private String userName;
	
	private int userPhotoId;
	private String userPhotoName;
	
	public UserDetailsInfo(){
	}
	
	public UserDetailsInfo(int userId, String userName,
			int userPhotoId, String userPhotoName) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPhotoId = userPhotoId;
		this.userPhotoName = userPhotoName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserPhotoId() {
		return userPhotoId;
	}
	public void setUserPhotoId(int userPhotoId) {
		this.userPhotoId = userPhotoId;
	}
	public String getUserPhotoName() {
		return userPhotoName;
	}
	public void setUserPhotoName(String userPhotoName) {
		this.userPhotoName = userPhotoName;
	}

}
