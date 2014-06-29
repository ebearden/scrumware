package com.scrumware.config;

public enum Status {
	TODO(1, "To Do"),
	IN_PROCESS(2, "In Process"),
	TO_VERIFY(3, "To Verify"),
	DONE(4, "Done");
	
	private int code;
	private String description;
 
	private Status(int c, String d) {
		code = c;
		description = d;
	}
 
	public int getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
}
