package com.parkit.parkingsystem.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptedDatabaseId {
	
	private String username = "root";
	private String password = "Java-Mysql3306";
	
	private String usernameHash = null;
	private String passwordHash = null;
	
//	private String UsernameHash(String username) {
//		try{  
//	        /* MessageDigest instance for MD5. */  
//	        MessageDigest m = MessageDigest.getInstance("MD5");  
//	          
//	        /* Add plain-text password bytes to digest using MD5 update() method. */  
//	        m.update(username.getBytes());  
//	          
//	        /* Convert the hash value into bytes */   
//	        byte[] bytes = m.digest();  
//	          
//	        /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */  
//	        StringBuilder s = new StringBuilder();  
//	        for(int i=0; i< bytes.length ;i++)  
//	        {  
//	            s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
//	        }  
//	          
//	        /* Complete hashed password in hexadecimal format */  
//	        usernameHash = s.toString();
//	        
//	    }   
//	    	catch (NoSuchAlgorithmException e){  
//	        e.printStackTrace();  
//	    } 
//		
//		return usernameHash;
//	}
//	
//	private String PasswordHash(String password) {
//		try{  
//	        /* MessageDigest instance for MD5. */  
//	        MessageDigest m = MessageDigest.getInstance("MD5");  
//	          
//	        /* Add plain-text password bytes to digest using MD5 update() method. */  
//	        m.update(password.getBytes());  
//	          
//	        /* Convert the hash value into bytes */   
//	        byte[] bytes = m.digest();  
//	          
//	        /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */  
//	        StringBuilder s = new StringBuilder();  
//	        for(int i=0; i< bytes.length ;i++)  
//	        {  
//	            s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
//	        }  
//	          
//	        /* Complete hashed password in hexadecimal format */  
//	        passwordHash = s.toString();
//	        
//	    }   
//	    	catch (NoSuchAlgorithmException e){  
//	        e.printStackTrace();  
//	    } 
//		
//		return passwordHash;
//	}
//	      
	
	public String getEncryptedUser() {
		return username; //  UsernameHash(username)
	}
	
	public String getEncryptedPass() {
		return password; // PasswordHash(password)
	}





}
