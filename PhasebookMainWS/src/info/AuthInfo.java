package info;

import java.io.Serializable;

public class AuthInfo implements Serializable {
	private int id;
	private String token;
	private long expiration;
	public AuthInfo(int id, String token, long expiration){
		this.id = id;
		this.token = token;
		this.expiration =expiration;
	}
	
	public AuthInfo(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}

	public long getExpiration() {
		return expiration;
	}

}
