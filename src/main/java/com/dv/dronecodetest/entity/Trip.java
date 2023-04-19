package com.dv.dronecodetest.entity;

import java.util.List;

public class Trip {

	private String name;
	private Drone drone;
	private List<Location> locationList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Drone getDrone() {
		return drone;
	}

	public void setDrone(Drone drone) {
		this.drone = drone;
	}

	public List<Location> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}

}
