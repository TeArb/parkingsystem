package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

@ExtendWith(MockitoExtension.class)
class TicketDAOTest {
	
	private static TicketDAO ticketDAO;
	static DataBasePrepareService dataBasePrepareService;
	static DataBaseTestConfig dataBaseTestConfig;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
        ticketDAO = new TicketDAO();
       // ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
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
	void saveTicketGenerated() throws Exception {
		Ticket ticket = new Ticket();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber(null);
        ticket.setPrice(0);
        ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
		
		
		assertEquals(true, ticketDAO.saveTicket(ticket));
	}
	/*
	@Test
	void getTicketGenerated() throws Exception {
		assertNotEquals(null, ticketDAO.getTicket(""));
	}
	
	@Test
	void updateTicketGenerated() {	
		assertEquals(false, ticketDAO.updateTicket(ticket));
	}*/

}
