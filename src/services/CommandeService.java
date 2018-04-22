/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import Iservices.IcommandeService;
import static controllers.ClientController.panier;
import static controllers.VendeurController.nbrMag;
import entities.Commande;
import entities.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Set;
import utils.Connexion;

/**
 *
 * @author INETEL
 */
public class CommandeService implements IcommandeService {

    Connection conn;

    public CommandeService() {
        conn = Connexion.getInstance().getConnection();
    }
    
    public int totcom(){
        int nbcom=0;
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM commande");
            r.next();
            nbcom = r.getInt("rowcount");
            r.close();
        } catch (Exception ex) {
            return 0;
        }
        return nbcom;
    }
    
    @Override
    public void insertCMD(Commande cmd) {
        String reqAjtCmd = "INSERT INTO commande (reference,date,idclient,totalprix) VALUES(?,?,?,?)";
        String reqAjtLCmd = "INSERT INTO lignecommande (reference,idproduit,qté) VALUES(?,?,?)";
        
        try {
            PreparedStatement stm = conn.prepareStatement(reqAjtCmd);
            stm.setString(1, cmd.getRef());
            stm.setString(2, cmd.getDate());
            stm.setInt(3, cmd.getId_client());
            stm.setString(4,cmd.getTotal());
            stm.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("erreur d'insertion du commande" + ex.getMessage());
        }
        //insertion de ligne de commande 
        Set cles = panier.getMapPan().keySet();
        Iterator it = cles.iterator();
        try{
            PreparedStatement stm2 = conn.prepareStatement(reqAjtLCmd);
        while (it.hasNext()) {
            Produit cle = (Produit) it.next();
            int qte = panier.getMapPan().get(cle);
            stm2.setString(1, cmd.getRef());
            stm2.setInt(2, cle.getId());
            stm2.setInt(3, qte);
            stm2.executeUpdate();
        }
        }catch(Exception ex){
                 System.out.println("erreur d'insertion de lignecommande" + ex.getMessage());
        }
        
    }

    

    private String toString(float aInt) {
        return "" + aInt;
    }

    @Override
    public ResultSet afficherTCMDclient(int idclient) {
            ResultSet rsm = null;
        try {
            
            String affcmd = "SELECT * FROM commande WHERE idclient=?";
            PreparedStatement stm1 = conn.prepareStatement(affcmd);
            stm1.setInt(1, idclient);
            rsm = stm1.executeQuery();
            
        } catch (Exception ex) {
            System.out.println("erreur d'affichage des commandes du client");
        }
        return rsm;
    }

}
