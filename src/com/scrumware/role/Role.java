package com.scrumware.role;

/**
 * @author emily kubic
 */

public class Role {
	
	private int id;
	private int active;
	private String rolename;
	private String description;
	
	public Role() {
		
		this.id=0;
		this.active=0;
		this.rolename="";
		this.description="";
		
	}
	
	public Role(int id, int active, String rolename, String description) {
		
		this.id = id;
		this.active = active;
		this.rolename = rolename;
		this.description = description;
		
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getActive() {
		return this.active;
	}
	
	public void setActive(int active) {
		this.active = active;
	}
	
	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
