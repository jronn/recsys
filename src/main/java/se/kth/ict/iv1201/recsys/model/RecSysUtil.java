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
    
    /**
     * Validates a string, making sure it's of appropriate length and with possibly
     * with no special characters
     * @param text input text to be validated
     * @param min min allowed length of string
     * @param max max allowed length of string
     * @param onlyAlphabetChars Boolean, true = makes sure no non-alphabet signs in string
     * @return boolean, true = valid
     */
    public static boolean validateString(String text, int min, int max,
                        boolean onlyAlphabetChars) {
        
        if(text == null)
            return false;
        
        if(onlyAlphabetChars && !text.matches("^[a-zA-ZåäöÅÄÖ]+$"))
            return false;
        
        if(text.length() < min || text.length() > max)
            return false;
        
        return true;
    }
    
    /**
     * Validates an email, so that its in an appropriate format
     * @param email input to be validated
     * @return boolean, true = valid
     */
    public static boolean validateEmail(String email) {
        if(email == null)
            return false;
        return email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*" +
            "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }
}
