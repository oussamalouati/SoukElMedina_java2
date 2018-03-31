/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author INETEL
 */
public class Connexion {
       private static Connexion instance;
       private String url="jdbc:mysql://localhost:3306/soukelmedina";
       private String login ="root";
       private String mdp="";
       private Connection cnx;
    public Connexion() {
        try {
                cnx = DriverManager.getConnection(url,login,mdp);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("connexion etablie");
    }
    
    public static Connexion getInstance(){
     if(instance == null){
       instance = new Connexion();
     }
     return instance;
    }
    
    public Connection getConnection(){
        return cnx;
    }
       
}
