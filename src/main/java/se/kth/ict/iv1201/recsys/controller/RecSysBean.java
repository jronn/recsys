/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.controller;

import javax.ejb.Stateless;

/**
 *
 * @author jronn
 */
@Stateless
public class RecSysBean {
    
    public String retrieveMessage() {
        return "A message from the E-J-B";
    }
}
