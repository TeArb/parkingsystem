package com.parkit.parkingsystem.integration;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

    private static ParkingService parkingService;
    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static TicketDAO ticketDAO;

    @BeforeEach
    private void setUpPerTest() {
        parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    }
    
    @Test
    public void getNextParkingNumberIfAvailableTest() throws Exception {
    	// Feigned a user input for the vehicle and the parking slot type
    	when(inputReaderUtil.readSelection()).thenReturn(1);
    	when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);
    	
    	assertNotEquals(null, parkingService.getNextParkingNumberIfAvailable());
    }	    

	@Test
    public void processIncomingVehicleTest() throws Exception {
		// Feigned a user input for the vehicle and the parking slot type
		when(inputReaderUtil.readSelection()).thenReturn(1);
    	when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);

        parkingService.processIncomingVehicle();
        
        verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
    }	

    @Test
    public void processExitingVehicleTest() throws Exception {
    	try {
    		// Feigned a user input for the vehicle registration number
        	when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        	
        	ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
            Ticket ticket = new Ticket();
             
            // Set the data of the ticket
            ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicleRegNumber("ABCDEF");
        	parkingSpot.setAvailable(false);
             
            // Feigned taking this methods
            when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
            when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
            when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);    	
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to set up test mock objects");
        }
       
    	parkingService.processExitingVehicle();
        // Verify if the method updateParking is called
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class)); 
    }
}
