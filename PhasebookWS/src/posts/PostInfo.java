package posts;

public class PostInfo {
	private int fromUserId;
	private int toUserId;
	private boolean private_;
	private boolean read_;
	private int photo_id = -1;
	private String text;
	
	public PostInfo(int id, int fromUserId, int toUserId, boolean private_, boolean read_, int photo_id, String text){
		this.setId(id);
		this.setFromUserId(fromUserId);
		this.setToUserId(toUserId);
		this.setPrivate_(private_);
		this.setRead_(read_);
		this.setPhoto_id(photo_id);
		this.setText(text);
	}
	
	public PostInfo(){
		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}

	public int getToUserId() {
		return toUserId;
	}

	public void setToUserId(int toUserId) {
		this.toUserId = toUserId;
	}

	public boolean isPrivate_() {
		return private_;
	}

	public void setPrivate_(boolean private_) {
		this.private_ = private_;
	}

	public int getPhoto_id() {
		return photo_id;
	}

	public void setPhoto_id(int photo_id) {
		this.photo_id = photo_id;
	}

	private int id;
	public boolean isRead_() {
		return read_;
	}

	public void setRead_(boolean read_) {
		this.read_ = read_;
	}
}
