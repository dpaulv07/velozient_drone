import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dv.dronecodetest.entity.Drone;
import com.dv.dronecodetest.entity.Location;
import com.dv.dronecodetest.entity.Trip;
import com.dv.dronecodetest.service.DeliveryService;

public class DeliveryServiceTest {

	private DeliveryService service = new DeliveryService();

	@Test
	public void shouldFillDroneInfo() throws Exception {
		String data = "DroneA, 200, DroneB, 250, DroneC, 100";
		List<Drone> droneData = service.fillDroneInfo(data);
		assertEquals(3, droneData.size());
		assertEquals("DroneA", droneData.get(0).getName());
		assertEquals(200.0, droneData.get(0).getMaxWeight());
	}

	@Test(expected = Exception.class)
	public void shouldFillDroneInfoThrowsException() throws Exception {
		String data = "DroneA";
		service.fillDroneInfo(data);
		assertThrows("java.lang.AssertionError: Expected exception: java.lang.Exception", null, null);
	}

	@Test
	public void shouldFillLocationInfo() throws Exception {
		String data = "LocationA, 200";
		Location location = service.fillLocationInfo(data);
		assertTrue(location.getName().equals("LocationA"));
		assertTrue(location.getPackageWeight().equals(200.0));
	}

	@Test(expected = Exception.class)
	public void shouldFillLocationInfoThrowsException() throws Exception {
		DeliveryService service = new DeliveryService();
		String data = "LocationA";
		service.fillLocationInfo(data);
		assertThrows("java.lang.AssertionError: Expected exception: java.lang.Exception", null, null);
	}

	@Test
	public void shouldGetDronesByWeight() throws Exception {
		Drone droneA = new Drone("DroneA", 200.0);
		Drone droneB = new Drone("DroneB", 250.0);
		Drone droneC = new Drone("DroneC", 100.0);
		Location locationA = new Location("LocationA", 250.0);

		List<Drone> drones = List.of(droneA, droneB, droneC);
		Drone drone = service.getDroneByWeight(droneA.getMaxWeight(), locationA.getPackageWeight(), drones, 0);
		assertTrue(drone.getName().equals("DroneB"));
	}

	@Test
	public void shouldGenerateTrips() throws Exception {
		Drone droneA = new Drone("DroneA", 200.0);
		Drone droneB = new Drone("DroneB", 500.0);
		Drone droneC = new Drone("DroneC", 100.0);
		Location locationA = new Location("LocationA", 230.0);
		Location locationB = new Location("LocationB", 250.0);
		Location locationC = new Location("LocationC", 100.0);
		Location locationD = new Location("LocationD", 50.0);

		List<Location> locations = new ArrayList<>();
		locations.addAll(List.of(locationA, locationB, locationC, locationD));
		List<Drone> drones = new ArrayList<>();
		drones.addAll(List.of(droneA, droneB, droneC));
		List<Trip> trips = service.generateTrips(locations, drones);
		assertTrue(trips.size() == 3);
		assertTrue(trips.get(0).getDrone().getName().equals("DroneC"));
		assertTrue(trips.get(0).getLocationList().size() == 1);
		assertTrue(trips.get(1).getDrone().getName().equals("DroneA"));
		assertTrue(trips.get(1).getLocationList().size() == 1);
		assertTrue(trips.get(2).getDrone().getName().equals("DroneB"));
		assertTrue(trips.get(2).getLocationList().size() == 2);
	}
}
