package com.itwillbs.test2.vo;

// TestController2 클래스의 redirect 데이터 전달용으로 사용할 PersonVO 클래스 정의
public class PersonVO {
	private String name;
	private int age;
	
	// 파라미터 생성자 정의 시 기본 생성자 수동 정의 필수!
	public PersonVO() {
		super();
	}
	
	public PersonVO(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
