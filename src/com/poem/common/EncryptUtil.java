package com.poem.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class EncryptUtil {
	private static final Logger logger = Logger.getLogger(EncryptUtil.class.getName());

	public static String getSHA256(String str) {
		String rtnSHA = "";
		
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(str.getBytes());
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			
			for(int i = 0; i < byteData.length; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			rtnSHA = sb.toString();
		} catch (NoSuchAlgorithmException nsae){
			nsae.printStackTrace();
			rtnSHA = null;
		}
		
		return rtnSHA;
	}
	
	public static String getEncryptSHA256(String str) {
		String encryptedSHA256 = "";
		MessageDigest md = null;
		
		try{
			md = MessageDigest.getInstance("SHA-256");
			md.update(str.getBytes(),0, str.length());
			encryptedSHA256 = new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}
		
		return encryptedSHA256;
	}
}
