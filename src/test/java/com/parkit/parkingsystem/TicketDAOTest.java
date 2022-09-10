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
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

class TicketDAOTest {
  //  private static DataBasePrepareService dataBasePrepareService;
    private static TicketDAO ticketDAO;
    
    private Ticket ticket;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
        ticketDAO = new TicketDAO();
       // dataBasePrepareService = new DataBasePrepareService();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
	}

	@BeforeEach
	void setUp() throws Exception {
		ticket = new Ticket();
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void saveTicketGenerated() throws Exception {
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        Ticket ticket = new Ticket();
        
        ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("974564");

        assertEquals(true, ticketDAO.saveTicket(ticket));
	}

	@Test
	void getTicketGenerated() throws Exception {
		String vehicleRegNumber = "974564";
		
        ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
        ticket.setParkingSpot(new ParkingSpot(2, ParkingType.CAR, false));
		ticket.setVehicleRegNumber(vehicleRegNumber);
        ticketDAO.saveTicket(ticket);   

        assertNotEquals(null, ticketDAO.getTicket(vehicleRegNumber).getVehicleRegNumber());		
	}
	
	@Test
	void updateTicketGenerated() throws Exception {
		Ticket ticket = ticketDAO.getTicket("974564");
		
        ticket.setOutTime(new Date());
        
		assertEquals(true, ticketDAO.updateTicket(ticket));
	}

}
