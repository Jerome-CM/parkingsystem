package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	/**
	 * Calculates the price of a ticket, depending on whether it's a car or a bike and depending on the number of hours of parking
	 * @param ticket
	 */
    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        // Date In and Out on Timestamp format
        long millisecIn = ticket.getInTime().getTime();
        long millisecOut = ticket.getOutTime().getTime();
        
        // Get numbers of minutes in the parking
        int minuteInParking = (int)( millisecOut - millisecIn) / 60000;
        

        double duration = 0.00;
        
        // If the hour isn't complete, the user pay only 3/4 of an hour
        if(minuteInParking < 60){
        		
    	   duration = 0.75;
      
        } else {
        	
        	double NumberOfHours = minuteInParking / 60;
        	int NumberOfCompleteHours = (int)Math.ceil(NumberOfHours);
	
        	duration = NumberOfCompleteHours;
        	
        }
        
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