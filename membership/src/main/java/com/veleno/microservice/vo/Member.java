package com.veleno.microservice.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Member implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2847524983695512906L;
	private String user;
	private Integer age;

	public Member() {
	}

	public Member(String name, Integer age) {
		this.user = name;
		this.age = age;
	}

	public final String getUser() {
		return user;
	}

	public final void setUser(String name) {
		this.user = name;
	}

	public final Integer getAge() {
		return age;
	}

	public final void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Member [user=" + user + ", age=" + age + "]";
	}
	
	
}
