/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import Iservices.ImagasinService;
import entities.Magasin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static soukelmedina2.MainController.id;
import static soukelmedina2.VendeurController.nbrMag;
import utils.Connexion;

/**
 *
 * @author INETEL
 */
public class MagasinService implements ImagasinService {

    Connection conn;

    public MagasinService() {
        conn = Connexion.getInstance().getConnection();
    }

    @Override
    public void insertMagasin(Magasin magasin) {
        String reqAjtMag = "INSERT INTO magazin (nom_magazin,description,adresse,latitude,longitude,img,proprietaire) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement st4 = conn.prepareStatement(reqAjtMag);
            st4.setString(1, magasin.getNom_magasin());
            st4.setString(2, magasin.getDescription());
            st4.setString(3, magasin.getAdresse());
            st4.setDouble(4, magasin.getLatitude());
            st4.setDouble(5, magasin.getLongitude());
            st4.setString(6, magasin.getUrlimg());
            st4.setInt(7, magasin.getProprietaire());
            st4.executeUpdate();
        } catch (Exception ex) {
            System.out.println("erreur d'insertion du magasin");
        }
    }

    @Override
    public void modfierMagasin(Magasin magasin) {
        String reqUpd = "UPDATE magazin SET nom_magazin=?,description=?,adresse=?,latitude=?,longitude=?,img=? WHERE id= ?";
        try {
            PreparedStatement stupd = conn.prepareStatement(reqUpd);
            stupd.setString(1, magasin.getNom_magasin());
            stupd.setString(2, magasin.getDescription());
            stupd.setString(3, magasin.getAdresse());
            stupd.setDouble(4, magasin.getLatitude());
            stupd.setDouble(5, magasin.getLongitude());
            stupd.setString(6, magasin.getUrlimg());
            stupd.setInt(7, magasin.getId());
            stupd.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("erreur de modification des informations du magasin" + ex.getMessage());
        }
    }

    @Override
    public void supprimerMagasin(int idMag) {
        String reqSup = "DELETE FROM magazin where id='" + idMag + "'";
        try {
            Statement stmsupp = conn.createStatement();
            stmsupp.executeUpdate(reqSup);
        } catch (Exception ex) {
            System.out.println("erreur d'affichage du magasin");
        }
    }

    @Override
    public ResultSet afficherTMagasins() {
        ResultSet rsm = null;
        try {
            String affmag = "SELECT * FROM magazin WHERE proprietaire=?";
            PreparedStatement stm1 = conn.prepareStatement(affmag);
            stm1.setInt(1, id);
            rsm = stm1.executeQuery();
        } catch (Exception ex) {
            System.out.println("erreur d'affichage des magasins");
        }
        return rsm;
    }

    @Override
    public int nbmags() {
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM magazin");
            r.next();
            nbrMag = r.getInt("rowcount");
            r.close();
        } catch (Exception ex) {
            System.out.println("erreur de calcul des magasins");
        }
        return nbrMag;
    }

    @Override
    public ResultSet afficherMagasin(int idmag) {
       ResultSet rsm = null;
        try {
            String affmag = "SELECT * FROM magazin WHERE id=?";
            PreparedStatement stm1 = conn.prepareStatement(affmag);
            stm1.setInt(1, idmag);
            rsm = stm1.executeQuery();
        } catch (Exception ex) {
            System.out.println("erreur d'affichage du magasin");
        }
        return rsm;
    }

}
