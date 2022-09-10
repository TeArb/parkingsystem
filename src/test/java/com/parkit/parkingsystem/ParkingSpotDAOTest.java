package com.parkit.parkingsystem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.model.ParkingSpot;

class ParkingSpotDAOTest {

	private static ParkingSpotDAO parkingSpotDAO;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		parkingSpotDAO = new ParkingSpotDAO();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void getNextAvaiableSlotCar() {
		assertNotEquals(1, parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
	}

	@Test
	void getNextAvaiableSlotBike() {
		assertNotEquals(1, parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE));
	}
	
	@Test
	void updateParkingSpot() {
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		
		assertEquals(true, parkingSpotDAO.updateParking(parkingSpot));
	}
}
