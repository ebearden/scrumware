package com.scrumware.login;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * @author Emily Kubic
 */

public final class PasswordService
{
	private static PasswordService instance;
	private static final String salt = "i am a g0lden g0d.";
	
	private PasswordService()
	{
		
	}
	
	public synchronized String encrypt(String plaintext) throws SystemUnavailableException
	{
		MessageDigest md = null;
		plaintext = plaintext + salt;
		try {
			md = MessageDigest.getInstance("SHA"); //step 2
		}	catch(NoSuchAlgorithmException e) {
			throw new SystemUnavailableException(e.getMessage());
		}
		
		try {
			md.update(plaintext.getBytes("UTF-8")); //step 3
		} catch(UnsupportedEncodingException e) {
			throw new SystemUnavailableException(e.getMessage());
		}
	
		StringBuilder hash = new StringBuilder();
		byte[] hashedBytes = md.digest();
		char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	        'a', 'b', 'c', 'd', 'e', 'f' };
		for (int idx = 0; idx < hashedBytes.length; idx++) {
		    byte b = hashedBytes[idx];
		    hash.append(digits[(b & 0xf0) >> 4]);
		    hash.append(digits[b & 0x0f]);
		}
	   
		return hash.toString();
	
}
  
	public static synchronized PasswordService getInstance() {
		
		if(instance == null) {
			instance = new PasswordService(); 
		}
		
		return instance;
		
	}
  
}