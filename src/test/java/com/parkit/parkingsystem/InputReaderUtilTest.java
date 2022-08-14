package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
public class InputReaderUtilTest {
	
	 @Mock
	 private static InputReaderUtil inputReaderUtil;
	
	@BeforeEach
	public void setUpTestIputReader() throws Exception {
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn(null);
	}
	
	@Test
	public void inputReaderTest() throws Exception {
		try {
    		String test = inputReaderUtil.readVehicleRegistrationNumber();
    		
    	} catch(IllegalArgumentException ex) {
    		assertEquals("Invalid input provided", ex.getMessage());
    	}
	}
	
	
	
}
