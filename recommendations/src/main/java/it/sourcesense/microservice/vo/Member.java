package it.sourcesense.microservice.vo;

public class Member {
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
}
