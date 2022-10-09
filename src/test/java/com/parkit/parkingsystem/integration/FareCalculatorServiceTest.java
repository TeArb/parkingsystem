package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;

public class FareCalculatorServiceTest {

    private static FareCalculatorService fareCalculatorService;
    private Ticket ticket;

    @BeforeAll
    private static void setUp() {
        fareCalculatorService = new FareCalculatorService();
    }

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
    }

    @Test
    public void calculateFareCar(){
        Date inTime = new Date();
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        
        assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR);
    }

    @Test
    public void calculateFareBike(){
        Date inTime = new Date();
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);
        
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        
        assertEquals(ticket.getPrice(), Fare.BIKE_RATE_PER_HOUR);
    }

    @Test
    public void calculateFareUnkownType(){
        Date inTime = new Date();
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, null, false);
        
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Test
    public void calculateFareBike_WithFutureInTime(){
    	Date inTime = new Date();        
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);
        
        inTime.setTime(System.currentTimeMillis() + (60 * 60 * 1000));

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
            
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
    }
    
    @Test
    public void freeFareBike_WithThirtyMinutes_ParkingTime(){
    	Date inTime = new Date();    	
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);
        
        // 30 minutes parking time for free
        inTime.setTime(System.currentTimeMillis() - (25 * 60 * 1000));

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        
        assertEquals(0.0, ticket.getPrice());
    }

    @Test
    public void calculateFareBike_WithLessThanOneHour_ParkingTime(){
        Date inTime = new Date();       
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        
        // 45 minutes parking time should give 3/4th parking fare
        inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);

        assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice());
    }
    
    @Test
    public void calculateFareBike_WithMoreThanADay_ParkingTime(){
        Date inTime = new Date();       
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);
        
        // 24 hours parking time should give 24 * parking fare per hour
        inTime.setTime(System.currentTimeMillis() - (24 * 60 * 60 * 1000));

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        
        assertEquals((24 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice());
    }

    @Test
    public void calculateFareCar_WithFutureInTime(){
        Date inTime = new Date();
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        
        inTime.setTime(System.currentTimeMillis() + (60 * 60 * 1000));

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
    }
   
    @Test
    public void freeFareCar_WithThirtyMinutes_ParkingTime(){
    	Date inTime = new Date();
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        
        // 30 minutes parking time for free
        inTime.setTime(System.currentTimeMillis() - (25 * 60 * 1000));

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        
        assertEquals(0.0, ticket.getPrice());
    }
    
    @Test
    public void calculateFareCar_WithLessThanOneHour_ParkingTime(){
        Date inTime = new Date();       
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        
        // 45 minutes parking time should give 3/4th parking fare
        inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        
        assertEquals((0.75 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());
    }

    @Test
    public void calculateFareCar_WithMoreThanADay_ParkingTime(){
        Date inTime = new Date();        
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        
        // 24 hours parking time should give 24 * parking fare per hour
        inTime.setTime(System.currentTimeMillis() - (24 * 60 * 60 * 1000));

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        
        assertEquals((24 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());
    }

}
