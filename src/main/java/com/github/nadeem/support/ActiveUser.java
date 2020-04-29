package com.github.nadeem.support;

import java.io.Serializable;
import java.util.List;

public class ActiveUser implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	List<String> roles;

	public ActiveUser(String id, List<String> roles) {
		this.id  = id;
		this.roles = roles;		
	}

	public String getId() {
		return id;
	}

	public List<String> getRoles() {
		return roles;
	}

	@Override
	public String toString() {
		return "ActiveUser [id=" + id + ", roles=" + roles + "]";
	}	
}
