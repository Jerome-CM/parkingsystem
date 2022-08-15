package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

public class TicketDAOTest {
	
	TicketDAO ticketDAO = new TicketDAO();
	
	private Ticket ticket;
	
    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
        
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setId(99);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        ticket.setPrice(10.99);
        ticket.setVehicleRegNumber("ABCDEF");
        ticketDAO.saveTicket(ticket);
    }
    
    @Test 
    public void updateTicketTest() {
    	ticket = new Ticket();
        
        Date outTime = new Date();
        ticket.setId(99);
        ticket.setOutTime(outTime);
        ticket.setPrice(10);
        ticketDAO.updateTicket(ticket);
        assertEquals(10, ticket.getPrice());
    	
    }
    
}
