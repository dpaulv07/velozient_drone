package com.dv.dronecodetest.entity;

public class Drone {
	
	private String name;
	private Double maxWeight;
	
	public Drone(String name, Double maxWeight) {
		super();
		this.name = name;
		this.maxWeight = maxWeight;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getMaxWeight() {
		return maxWeight;
	}
	public void setMaxWeight(Double maxWeight) {
		this.maxWeight = maxWeight;
	}
}
