package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {
	
	private double duration;
	private double price;

	/**
	 * Calculates the price of a ticket, depending on whether it's a car or a bike and depending on the number of hours of parking
	 * @param ticket
	 */
    public void calculateFare(Ticket ticket){
    	
    	
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"); // +ticket.getOutTime().toString()
        }
        
        // Date In and Out on Timestamp format
        long millisecIn = ticket.getInTime().getTime();
        long millisecOut = ticket.getOutTime().getTime();
        
        // Get numbers of minutes in the parking
        double minuteInParking = ( millisecOut - millisecIn) / 60000;
        
        // If the time of park is under 30 minutes, it's free
        if(minuteInParking < 30){
        	
        	duration = 0.00;
        	
        } else {
	        	
        	double NumberOfHours = minuteInParking / 60;
	
        	duration = NumberOfHours;
        	
        }
        
        // Calculate price
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
            	price = duration * Fare.CAR_RATE_PER_HOUR;
                break;
            }
            case BIKE: {
            	price = duration * Fare.BIKE_RATE_PER_HOUR;
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }  
       
        // Reduce application if available
    	if(ticket.isReccurentUser() == true) {
    		price *= 0.95;
    	}
	    	
    	ticket.setPrice(Math.round(price * 100.0) / 100.0);
        
    }
}