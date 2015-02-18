/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.model;

import java.util.Date;

/**
 * Models an availability entity, but excludes JPA and database specific
 * code
 * @author jronn
 */
public class AvailabilityListing {
    
    public Date fromDate;
    public Date toDate;
    
    
    public AvailabilityListing(Date fromDate, Date toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
    
    @Override
    public String toString(){
        return "From: " + fromDate + " To: " + toDate;
        
    }
}
