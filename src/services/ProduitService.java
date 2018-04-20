/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import Iservices.IproduitService;
import static controllers.MainController.id;
import static controllers.VendeurController.nbrMag;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import utils.Connexion;

/**
 *
 * @author INETEL
 */
public class ProduitService implements IproduitService{
    Connection conn;
    int nbprod;
    public ProduitService() {
        conn = Connexion.getInstance().getConnection();
    }
    
    @Override
    public ResultSet afficherTProduitCli(int idmag) {
        ResultSet rsm = null;
        try {
            String affmag = "SELECT * FROM produit WHERE id_mag=?";
            PreparedStatement stm1 = conn.prepareStatement(affmag);
            stm1.setInt(1, idmag);
            rsm = stm1.executeQuery();
        } catch (Exception ex) {
            System.out.println("erreur d'affichage des magasins");
        }
        return rsm;
    }

    @Override
    public int nbprods() {
       try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM produit");
            r.next();
            nbprod = r.getInt("rowcount");
            r.close();
        } catch (Exception ex) {
            System.out.println("erreur de calcul des produits");
        }
        return nbprod;
    }

    @Override
    public ResultSet afficherProduit(int idprod) {
        ResultSet rsm = null;
        try {
            String affprod = "SELECT * FROM produit WHERE id=?";
            PreparedStatement stm1 = conn.prepareStatement(affprod);
            stm1.setInt(1, idprod);
            rsm = stm1.executeQuery();
        } catch (Exception ex) {
            System.out.println("erreur d'affichage du produit");
        }
        return rsm;
    }
    
}
