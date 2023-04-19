package com.itwillbs.test2.vo;

public class MemberVO {
	private String id;
	private String passwd;
	
	public MemberVO() {
		super();
	}

	public MemberVO(String id, String passwd) {
		super();
		this.id = id;
		this.passwd = passwd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
}
