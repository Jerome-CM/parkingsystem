package com.parkit.parkingsystem;

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
    public void CarRatePerOneHour() {
    	double price = Fare.CAR_RATE_PER_HOUR;
    	assertEquals(1.5, price);
    }
    
    @Test
    public void BikeRatePerOneHour() {
    	double price = Fare.BIKE_RATE_PER_HOUR;
    	assertEquals(1, price);
    }

    @Test
    public void calculateFareCar(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR);
    }

    @Test
    public void calculateFareBike(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(ticket.getPrice(), Fare.BIKE_RATE_PER_HOUR);
    }

    @Test
    public void calculateFareUnkownType(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, null,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Test
    public void calculateFareBikeWithFutureInTime(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() + (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
    }
    
    
    @Test
    public void calculateFareBikeWithLessThanHalfHourParkingTime(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  29 * 60 * 1000) );//29 minutes parking time should free fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((0), ticket.getPrice() );
    }
    
    @Test
    public void calculateFareCarWithLessThanHalfHourParkingTime(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  29 * 60 * 1000) );//29 minutes parking time should free fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((0), ticket.getPrice() );
    }
    

    @Test
    public void calculateFareBikeWithLessThanOneHourParkingTime(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }

    @Test
    public void calculateFareCarWithLessThanOneHourParkingTime(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( Math.round((0.75 * Fare.CAR_RATE_PER_HOUR) * 100.0) / 100.0 , ticket.getPrice());
    }

    
    @Test
    public void calculateFareForReccuringUserWithCar() {

    	// Arrange
    	 Date inTime = new Date();
         inTime.setTime( System.currentTimeMillis() - (  120 * 60 * 1000) );//2 hours parking
         Date outTime = new Date();
         ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

         ticket.setInTime(inTime);
         ticket.setOutTime(outTime);
         ticket.setParkingSpot(parkingSpot);
         ticket.setReccurentUser(true);
         
    	// Act
         fareCalculatorService.calculateFare(ticket);
         double expectedReducePrice = (2 * Fare.CAR_RATE_PER_HOUR) * 0.95; // 0.95 = 5%
         expectedReducePrice = Math.round(expectedReducePrice * 100.0) / 100.0;
         
    	// Assert
         assertEquals(expectedReducePrice, ticket.getPrice());
    }
    
    @Test
    public void calculateFareForReccuringUserWithBike() {

    	// Arrange
    	 Date inTime = new Date();
         inTime.setTime( System.currentTimeMillis() - (  120 * 60 * 1000) );//2 hours parking
         Date outTime = new Date();
         ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

         ticket.setInTime(inTime);
         ticket.setOutTime(outTime);
         ticket.setParkingSpot(parkingSpot);
         ticket.setReccurentUser(true);
         
    	// Act
         fareCalculatorService.calculateFare(ticket);
         double expectedReducePrice = (2 * Fare.BIKE_RATE_PER_HOUR) * 0.95; // 0.95 = 5%
         expectedReducePrice = Math.round(expectedReducePrice * 100.0) / 100.0;
         
    	// Assert
         assertEquals(expectedReducePrice, ticket.getPrice());
    }
    
    
    @Test
    public void calculateFareCarWithADayParkingTime(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  24 * 60 * 60 * 1000) );//24 hours parking time should give 24 * parking fare per hour
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (24 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }
    
    @Test
    public void calculateFareCarWithMoreThanADayParkingTime(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  25 * 60 * 60 * 1000) );//25 hours parking time should give 25 * parking fare per hour
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (25 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }
    
    @Test
    public void exceptionTest() {
    	try {
	    	Date inTime = new Date();
	        inTime.setTime( System.currentTimeMillis() - (  2 * 60 * 60 * 1000) ); //2 hours parking time
	        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
	        ticket.setInTime(inTime);
	        ticket.setOutTime(null);
	        ticket.setParkingSpot(parkingSpot);

    	} catch(IllegalArgumentException ex) {
    		assertEquals("Out time provided is incorrect:", ex.getMessage());
    	}
        
    }
    
   
    
}
