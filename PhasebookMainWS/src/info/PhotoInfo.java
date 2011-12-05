package info;

public class PhotoInfo {

	private int id;
	private String name;	
	public PhotoInfo(int id, String name){
		this.setId(id);
		this.setName(name);
	}
	
	public PhotoInfo() {
		
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
}