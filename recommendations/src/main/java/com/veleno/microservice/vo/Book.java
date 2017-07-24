package com.veleno.microservice.vo;


public class Book {
	
	public String title;
	public Book() {}
	public Book(String title) { this.title = title; }
	public final String getTitle() { return this.title; }
	public void setTitle(String title) { this.title = title; }

}
