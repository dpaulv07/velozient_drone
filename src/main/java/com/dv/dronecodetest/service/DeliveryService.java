package com.dv.dronecodetest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.dv.dronecodetest.entity.Drone;
import com.dv.dronecodetest.entity.Location;
import com.dv.dronecodetest.entity.Trip;

public class DeliveryService {

	Boolean newTrip = false;

	public List<Drone> fillDroneInfo(String data) throws Exception {
		List<Drone> drones = new ArrayList<Drone>();
		String[] droneData = data.split(",");
		String name = "";
		for (int i = 0; i < droneData.length; i++) {
			if (i % 2 == 0) {
				name = droneData[i];
			} else {
				Drone drone = new Drone(name, Double.parseDouble(droneData[i].trim()));
				drones.add(drone);
			}
		}
		return drones;
	}

	public Location fillLocationInfo(String data) throws Exception {
		Location locations = null;
		String[] locationData = data.split(",");
		locations = new Location(locationData[0], Double.parseDouble(locationData[1].trim()));
		return locations;
	}

	public List<Trip> generateTrips(List<Location> locations, List<Drone> drones) {
		List<Trip> trips = new ArrayList<Trip>();
		Collections.sort(locations, new Comparator<Location>() {
			@Override
			public int compare(Location location1, Location location2) {
				return location1.getPackageWeight().compareTo(location2.getPackageWeight());
			}
		});
		Collections.sort(drones, new Comparator<Drone>() {
			@Override
			public int compare(Drone drone1, Drone drone2) {
				return drone1.getMaxWeight().compareTo(drone2.getMaxWeight());
			}
		});
		if (!drones.isEmpty()) {
			Trip trip = new Trip();
			List<Location> tripLocations = new ArrayList<Location>();
			Drone currentDrone = drones.get(0);
			Double currentDroneWeight = currentDrone.getMaxWeight();
			int tripNumber = 1;
			for (Location location : locations) {
				Drone newDrone = getDroneByWeight(currentDroneWeight, location.getPackageWeight(), drones,
						drones.indexOf(currentDrone));
				if (currentDrone.equals(newDrone)) {
					currentDroneWeight -= location.getPackageWeight();
					tripLocations.add(location);
				} else {
					trip.setName("Trip #" + (tripNumber));
					trip.setDrone(currentDrone);
					trip.setLocationList(tripLocations);
					trips.add(trip);
					if (newTrip) {
						tripNumber++;
						newTrip = false;
					}
					trip = new Trip();
					tripLocations = new ArrayList<Location>();
					if (newDrone != null) {
						currentDrone = newDrone;
						currentDroneWeight = currentDrone.getMaxWeight() - location.getPackageWeight();
					} else {
						tripNumber++;
						currentDroneWeight = currentDrone.getMaxWeight() - location.getPackageWeight();
					}
					tripLocations.add(location);
				}
			}
			trip.setName("Trip #" + (tripNumber));
			trip.setDrone(currentDrone);
			trip.setLocationList(tripLocations);
			trips.add(trip);
		}
		return trips;
	}

	public Drone getDroneByWeight(Double droneMaxWeight, Double locationWeight, List<Drone> drones, Integer index) {
		Drone drone = null;
		if (locationWeight <= droneMaxWeight) {
			drone = drones.get(index);
		} else {
			index++;
			if (index < drones.size()) {
				drone = getDroneByWeight(drones.get(index).getMaxWeight(), locationWeight, drones, index);
			} 
		}
		return drone;
	}
}
