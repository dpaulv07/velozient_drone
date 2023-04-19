package com.dv.dronecodetest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.dv.dronecodetest.entity.Drone;
import com.dv.dronecodetest.entity.Location;
import com.dv.dronecodetest.entity.Trip;
import com.dv.dronecodetest.service.DeliveryService;

public class Main {
	static String FILE_PATH_INPUT = "\\src\\main\\resources\\dataInput.txt";
	static String FILE_PATH_OUTPUT = "\\src\\main\\resources\\dataOutput.txt";
	private static DeliveryService deliveryService;
	private static List<Drone> drones;
	private static List<Location> locations;

	public static void main(String[] args) {
		drones = new ArrayList<Drone>();
		locations = new ArrayList<Location>();
		deliveryService = new DeliveryService();
		readData();
		buildOutputData();
	}

	public static void readData() {
		try {
			Path currentRelativePath = Paths.get("");
			String filePath = currentRelativePath.toAbsolutePath().toString() + FILE_PATH_INPUT;
			try (final InputStream myInputStream = Files.newInputStream(Paths.get(filePath))) {
				String result = IOUtils.toString(myInputStream, "UTF-8");
				List<String> data = Arrays.asList(result.replace("[", "").replace("]", "").split("\n"));
				for (int i = 0; i < data.size(); i++) {
					if (i == 0) {
						drones = deliveryService.fillDroneInfo(data.get(i));
					} else {
						Location location = deliveryService.fillLocationInfo(data.get(i));
						locations.add(location);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void buildOutputData() {
		Path currentRelativePath = Paths.get("");
		String filePathName = currentRelativePath.toAbsolutePath().toString() + FILE_PATH_OUTPUT;
		List<Trip> trips = deliveryService.generateTrips(locations, drones);
		String filename = filePathName;

		try (FileWriter fw = new FileWriter(filename);
				BufferedWriter bw = new BufferedWriter(fw)) {
			String drone = "";
			for (Trip item : trips) {
				if(!drone.equals(item.getDrone().getName().trim())) {
					bw.newLine();
					bw.write(new StringBuffer("[").append(item.getDrone().getName().trim()).append("]").toString());
					bw.newLine();	
					drone = item.getDrone().getName().trim();				
				}
				bw.write(item.getName());
				bw.newLine();
				StringBuffer locationsStr = new StringBuffer("");
				for (Location location : item.getLocationList()) {
					locationsStr.append("[").append(location.getName()).append("]").append(", ");
				}
				bw.write(locationsStr.toString().substring(0, locationsStr.length() - 2));
				bw.newLine();

			}

			System.out.println("Successfully wrote the List to the file.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
