package com.itwillbs.test3_mybatis.vo;

import java.sql.Date;

public class MemberVO {
	private int idx;
	private String name;
	private String id;
	private String passwd;
	private String email;
	private String gender;
	private Date date;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "MemberVO [idx=" + idx + ", name=" + name + ", id=" + id + ", passwd=" + passwd + ", email=" + email
				+ ", gender=" + gender + ", date=" + date + "]";
	}
	
	
}













