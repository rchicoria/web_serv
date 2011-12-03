package info;

public class PostDetailsInfo {
	private int postId;
	private String postText;
	private boolean postPrivate;
	
	private int postPhotoId;
	private String postPhotoName;
	
	private int userId;
	private String userName;
	
	private int userPhotoId;
	private String userPhotoName;
	
	public PostDetailsInfo(){
	}
	
	public PostDetailsInfo(int postId, String postText, boolean postPrivate,
			int postPhotoId, String postPhotoName, int userId, String userName,
			int userPhotoId, String userPhotoName) {
		super();
		this.postId = postId;
		this.postText = postText;
		this.postPrivate = postPrivate;
		this.postPhotoId = postPhotoId;
		this.postPhotoName = postPhotoName;
		this.userId = userId;
		this.userName = userName;
		this.userPhotoId = userPhotoId;
		this.userPhotoName = userPhotoName;
	}

	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getPostText() {
		return postText;
	}
	public void setPostText(String postText) {
		this.postText = postText;
	}

	public boolean isPostPrivate() {
		return postPrivate;
	}

	public void setPostPrivate(boolean postPrivate) {
		this.postPrivate = postPrivate;
	}

	public int getPostPhotoId() {
		return postPhotoId;
	}
	public void setPostPhotoId(int postPhotoId) {
		this.postPhotoId = postPhotoId;
	}
	public String getPostPhotoName() {
		return postPhotoName;
	}
	public void setPostPhotoName(String postPhotoName) {
		this.postPhotoName = postPhotoName;
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
