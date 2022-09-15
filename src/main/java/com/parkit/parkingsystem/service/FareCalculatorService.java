package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {
	/**
	* 
	* @param calculates the fare of the ticket according to the out time and the vehicle
	*/
    @SuppressWarnings("deprecation")
	public void calculateFare(Ticket ticket) { 
        // Display an error if the out time is null or after in time
        if((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        } 
       
        int inDay = ticket.getInTime().getDay();
        int outDay = ticket.getOutTime().getDay();
        int inHour = ticket.getInTime().getHours();
        int outHour = ticket.getOutTime().getHours();
        int inMinute = ticket.getInTime().getMinutes();
        int outMinute = ticket.getOutTime().getMinutes();

        double inTime = (((double)inDay * 24) + inHour + ((double)inMinute / 60));
        double outTime = (((double)outDay * 24) + outHour + ((double)outMinute / 60));

        double duration = outTime - inTime;
        // Calculates the fare of the ticket according to the vehicle
        /*  try {
        	if(duration < 30) {
            	if(ticket.getParkingSpot().getParkingType().equals(ParkingType.CAR)) {
            		ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
            		System.out.println("Fare car");
            	}
            	else if (ticket.getParkingSpot().getParkingType().equals(ParkingType.BIKE)) {
            		ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
            		System.out.println("Fare bike");
            	}
            }
            else if (duration >= 30) {
            	ticket.setPrice(0);
        		System.out.println("Free fare");	
            }
        }
        catch(IllegalArgumentException e) {
        	e.printStackTrace();
        	throw new IllegalArgumentException("Unkown Parking Type");
        }*/
         // Calculates the fare of the ticket according to the vehicle
      
        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
            	ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);     	
                break;
            }
            case BIKE: {
            	ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
            	break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }

}