package com.ns.util;

public class Security {

	public static byte[] MD5_Digest(String s) throws Exception{
		java.security.MessageDigest md;
             
        md = java.security.MessageDigest.getInstance("MD5");
       
       return md.digest(s.getBytes());
     
	}
	

}
