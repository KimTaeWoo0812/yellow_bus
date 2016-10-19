package com.javalec.member;

public class Member_Dto {
	private String id;
	private String name;
	private String role;
	private String parent1;
	private String parent2;
	
	public Member_Dto() {

	}
	public String GetId(){
		return id;
	}
	public String GetName(){
		return name;
	}
	public String GetRole(){
		return role;
	}
	public String GetParent1(){
		return parent1;
	}
	public String GetParent2(){
		return parent2;
	}
	
	public void SetId(String id){
		this.id = id;
	}
	public void SetName(String name){
		this.name = name;
	}
	public void SetRole(String role){
		this.role = role;
	}
	public void SetParent1(String parent1){
		this.parent1 = parent1;
	}
	public void SetParent2(String parent2){
		this.parent2 = parent2;
	}
}
