/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iservices;

import entities.Magasin;
import java.sql.ResultSet;

/**
 *
 * @author INETEL
 */
public interface ImagasinService {
     public void insertMagasin(Magasin magazin);
     public void modfierMagasin(Magasin magasin);
     public void supprimerMagasin(int idMag);
     public ResultSet afficherMagasin(int idMag);
     public ResultSet afficherTMagasins();
     public int nbmags();
     public ResultSet afficherTMagasinsCli();
}
