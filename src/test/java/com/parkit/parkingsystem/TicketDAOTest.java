package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

class TicketDAOTest {
	
	 private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	 private static TicketDAO ticketDAO;
	 private static DataBasePrepareService dataBasePrepareService;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		dataBasePrepareService.clearDataBaseEntries();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void saveTicketGenerated() {
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		Ticket ticket = new Ticket();
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
        ticket.setPrice(0);
		ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
        ticket.setOutTime(null);
        assertEquals(false, ticketDAO.saveTicket(ticket));
	}
	/*
	@Test
	void getTicketGenerated() throws Exception {
		assertNotEquals(null, ticketDAO.getTicket("ABCDEF"));
	}
	
	@Test
	void updateTicketGenerated() {	
		assertEquals(false, ticketDAO.updateTicket(ticket));
	}
*/
}
