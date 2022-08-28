package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    @SuppressWarnings("deprecation")
	public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
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

        switch (ticket.getParkingSpot().getParkingType()){
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