package com.javalec.beacon;

public class Beacon_Dto {

	private String schoolName;
	private String beaconName;
	private String identifier;
	private String makeDate;
	private String isCar;
	public Beacon_Dto() {

	}

	public Beacon_Dto(String schoolName, String beaconName, String identifier, String makeDate) {
		this.schoolName = schoolName;
		this.beaconName = beaconName;
		this.identifier = identifier;
		this.makeDate = makeDate;
	}

	public String GetSchoolName() {
		return schoolName;
	}

	public String GetBeaconName() {
		return beaconName;
	}

	public String GetIdentifier() {
		return identifier;
	}
	public String GetIsCar(){
		return isCar;
	}
	public String GetMakeDate() {
		return makeDate;
	}

	public void SetSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public void SetBeaconName(String beaconName) {
		this.beaconName = beaconName;
	}

	public void SetIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public void SetIdCar(String isCar){
		this.isCar = isCar;
	}
	public void SetMakeDate(String makeDate) {
		this.makeDate = makeDate;
	}

}
