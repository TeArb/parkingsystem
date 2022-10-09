package com.parkit.parkingsystem.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.config.DataBaseTestConfig;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.DataBasePrepareService;

class TicketDAOTest {
	private static DataBasePrepareService dataBasePrepareService;
    private static TicketDAO ticketDAO;
    
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = new DataBaseTestConfig();
        dataBasePrepareService = new DataBasePrepareService();
        dataBasePrepareService.clearDataBaseEntries();
	}
	
	@AfterAll
    private static void tearDown(){
		dataBasePrepareService.clearDataBaseEntries();
    }

	@Test
	void saveTicketGenerated_outTimeNull() throws Exception {
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        Ticket ticket = new Ticket();
        
        ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
        ticket.setOutTime(null);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("974564");

        assertEquals(true, ticketDAO.saveTicket(ticket));
	}
	
	@Test
	void saveTicketGenerated_outTimeNotNull() throws Exception {
        Ticket ticket = new Ticket();
        
        ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
        ticket.setOutTime(new Date());
        ticket.setParkingSpot(new ParkingSpot(1, ParkingType.CAR, false));
        ticket.setVehicleRegNumber("789632");

        assertEquals(true, ticketDAO.saveTicket(ticket));
	}

	@Test
	void getTicketGenerated() throws Exception {
		saveTicketGenerated_outTimeNull();
		
		Ticket ticket = ticketDAO.getTicket("974564");
		
        assertNotNull(ticket);
	}

	@Test
	void updateTicketGenerated() throws Exception {
		Ticket ticket = ticketDAO.getTicket("974564");
		
        ticket.setOutTime(new Date());
        
		assertEquals(true, ticketDAO.updateTicket(ticket));
	}

}
