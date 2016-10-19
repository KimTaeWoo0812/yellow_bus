package com.javalec.login;

public class School_Dto {

	private String num;

	public String name;
	private String location;
	private String lat;
	private String lon;
	private String manager;
	private String isNotice;
	private String notice;

	public School_Dto(){
		
	}
	
	public School_Dto(String num, String name, 
			String location, String lat, String lon,
			String manager, String isNotice, String notice) {
		
		this.num = num;

		this.num = name;
		this.num = location;
		this.num = lat;
		this.num = lon;
		this.num = manager;
		this.num = isNotice;
		this.num = notice;
	}



	public String GetNum() {
		return this.num;
	}

	public String GetName() {
		return this.name;
	}

	public String GetLocation() {
		return this.location;
	}

	public String GetLat() {
		return this.lat;
	}

	public String GetLon() {
		return this.lon;
	}

	public String GetManager() {
		return this.manager;
	}

	public String GetIsNotice() {
		return this.isNotice;
	}

	public String GetNotice() {
		return this.notice;
	}

	public void SetNum(String num) {
		this.num = num;
	}

	public void SetName(String name) {
		this.name = name;
	}

	public void SetLocation(String location) {
		this.location = location;
	}

	public void SetLat(String lat) {
		this.lat = lat;
	}

	public void SetLon(String lon) {
		this.lon = lon;
	}

	public void SetManager(String manager) {
		this.manager = manager;
	}

	public void SetIsNotice(String isNotice) {
		this.isNotice = isNotice;
	}

	public void SetNotice(String notice) {
		this.notice = notice;
	}
}
