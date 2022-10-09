package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.config.DataBaseTestConfig;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.DataBasePrepareService;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;
    private ParkingService parkingService;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        dataBasePrepareService.clearDataBaseEntries();
        parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    }

    @AfterAll
    private static void tearDown(){
    }
    
    @Test
    public void testParkingACar() throws Exception {
    	// Fainted the selection of the vehicle and the registration number
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
    	
        parkingService.processIncomingVehicle();
        
        Ticket ticket = ticketDAO.getTicket(inputReaderUtil.readVehicleRegistrationNumber());
        
        assertNotNull(ticket, "Ticket saved");
        assertEquals(false, ticket.getParkingSpot().isAvailable(), "Parking is not available");
    }
    
    @Test
    public void testParkingABike() throws Exception {
    	// Fainted the selection of the vehicle and the registration number
        when(inputReaderUtil.readSelection()).thenReturn(2);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("BCDEFG");
    	
        parkingService.processIncomingVehicle();
        
        Ticket ticket = ticketDAO.getTicket(inputReaderUtil.readVehicleRegistrationNumber());
        
        assertNotNull(ticket, "Ticket saved");
        assertEquals(false, ticket.getParkingSpot().isAvailable(), "Parking is not available");
    }
    
    @Test
    public void testParkingLotExitACar() throws Exception {
    	testParkingACar();
    	
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        Ticket ticket = ticketDAO.getTicket(inputReaderUtil.readVehicleRegistrationNumber());
        
        parkingService.processExitingVehicle();

        //assertNotNull(ticket.getOutTime(), "Recorded exit time");
        assertNotEquals(0, ticket.getPrice(), "Fare generate");
    }

}
