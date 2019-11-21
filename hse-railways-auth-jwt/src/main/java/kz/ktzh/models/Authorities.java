package kz.ktzh.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Authorities {

	@Id
	String username;
	String authority;

	protected Authorities() {
	}

	public Authorities(String username, String authority) {
		this.username = username;
		this.authority = authority;
	}

	public String getUsername() {
		return username;
	}

	public String getAuthority() {
		return authority;
	}

}
