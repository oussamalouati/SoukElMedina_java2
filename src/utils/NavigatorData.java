/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.lynden.gmapsfx.javascript.object.LatLong;

/**
 *
 * @author INETEL
 */
public class NavigatorData {
    private final static NavigatorData instance = new NavigatorData();

    public static NavigatorData getInstance() {
        return instance;
    } 
    private Double Lat ;
    private Double Long;
    private String adr;

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }
    

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double Lat) {
        this.Lat = Lat;
    }

    public Double getLong() {
        return Long;
    }

    public void setLong(Double Long) {
        this.Long = Long;
    }
    

    
    
}
