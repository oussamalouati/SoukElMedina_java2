/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Map;

/**
 *
 * @author INETEL
 */
public class Commande {
    private String date ;
    private String ref;
    private String total;
    private int id_client;
    
    public String getRef() {
        return ref;
    }

    public void setRef(int valal) {
        this.ref = "cli-" + this.id_client +"-"+valal;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }
    public String getDate() {
        return date;
    }
     public void setDate() {
        this.date =  new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());  
    }
}
