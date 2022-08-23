package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;

public class ParkingSpotTest {
	
	ParkingSpot parkingSpot;
	
	@Test
	public void setIdParkingSpotTest() {
		parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		parkingSpot.setId(2);
		assertEquals(2, parkingSpot.getId());
	}
	
	@Test
	public void setTypeParkingSpotTest() {
		parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		parkingSpot.setParkingType(ParkingType.BIKE);
		assertEquals(ParkingType.BIKE, parkingSpot.getParkingType());
	}
	

}
