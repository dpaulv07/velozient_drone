package com.dv.dronecodetest.entity;

public class Location {
	
	private String name;
	private Double packageWeight;
	
	public Location(String name, Double packageWeight) {
		super();
		this.name = name;
		this.packageWeight = packageWeight;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPackageWeight() {
		return packageWeight;
	}
	public void setPackageWeight(Double packageWeight) {
		this.packageWeight = packageWeight;
	}
	
	

}
