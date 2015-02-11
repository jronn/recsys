/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Provides various utility methods
 * @author jronn
 */
public class RecSysUtil {
    
    /**
     * Hashes a string with SHA-256 and returns the value as a hexString 
     * @param text input to be hashed
     * @return hex string representing hashed text 
     */
    public static String hashText(String text) 
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(text.getBytes("UTF-8"));
            
        StringBuilder hexString = new StringBuilder();
        for (int i=0;i<hash.length;i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
   	    if(hex.length()==1) 
                hexString.append('0');
   	    hexString.append(hex);
        }
        return hexString.toString();            
    }
}
