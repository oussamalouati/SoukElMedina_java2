/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iservices;

import entities.Commande;
import java.sql.ResultSet;

/**
 *
 * @author INETEL
 */
public interface IcommandeService {
    public  void insertCMD (Commande cmd);
    public  ResultSet afficherTCMDclient(int idclient);
}
