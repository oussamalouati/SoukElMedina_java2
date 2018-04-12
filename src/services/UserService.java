/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import Iservices.IuserService;
import entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import static soukelmedina2.MainController.id;
import static soukelmedina2.MainController.login;
import static soukelmedina2.MainController.nom;
import static soukelmedina2.MainController.prenom;
import utils.Connexion;

/**
 *
 * @author INETEL
 */
public class UserService implements IuserService {

    Connection conn;

    public UserService() {
        conn = Connexion.getInstance().getConnection();
    }

    @Override
    public void insertUser(User user) {

        String reqIns = "INSERT INTO users (nom,prenom,username,date_naissance,email,tel,adresse,mdp,status) VALUES  (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement st = conn.prepareStatement(reqIns);
                st.setString(1, user.getNom());
                st.setString(2, user.getPrenom());
                st.setString(3, user.getUsername());
                st.setString(4, user.getDate_naiss());
                st.setString(5, user.getEmail());
                st.setString(6, user.getTel());
                st.setString(7, user.getAdresse());
                st.setString(8, user.getMdp());
                st.setString(9, user.getTypeCompte());
            st.executeUpdate();
        } catch (Exception ex) {
            System.out.println("erreur d'insertion d'utilisateur");
        }
    }

    @Override
    public void modfierUser(User user) {
       String reqUpd = "UPDATE users SET nom=?,prenom=?,username=?,date_naissance=?,email=?,tel=?,adresse=?,mdp=?  WHERE id= ?";
       try {
       PreparedStatement st2 = conn.prepareStatement(reqUpd);
            st2.setString(1,user.getNom());
            st2.setString(2,user.getPrenom());
            st2.setString(3,user.getUsername());
            st2.setString(4,user.getDate_naiss());
            st2.setString(5,user.getEmail());
            st2.setString(6,user.getTel());
            st2.setString(7,user.getAdresse());
            st2.setString(8,user.getMdp());
            st2.setInt(9,id);
        st2.executeUpdate();    
       } catch (SQLException ex) {
            System.out.println("erreur de modification des informations de l'utilisateur"+ex.getMessage());
        }
    }

    @Override
    public void supprimerUser(int id) {
        String reqSup = "DELETE FROM users where id=?";
        try {
        PreparedStatement st3 = conn.prepareStatement(reqSup);
           st3.setInt(1,id);
           st3.executeUpdate();
        } catch (Exception ex) {
            System.out.println("erreur de supression de l'utilisateur");
        }            
    }

    @Override
    public String authentification(String username, String password) {
        String reqcnx = "SELECT * FROM users WHERE username=?";
        PreparedStatement st;
        String stat = "";
        try {
            st = conn.prepareStatement(reqcnx);
            st.setString(1, username);
            ResultSet rs;
            rs = st.executeQuery();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    String mdp = rs.getString("mdp");
                    nom = rs.getString("nom");
                    prenom = rs.getString("prenom");
                    id = rs.getInt("id");
                    if (password.equals(mdp)) {
                        stat = rs.getString("status");
                    } else {
                        System.out.println("mdp incorrect");
                    }
                }
            } else {
                System.out.println("username incorrect");
            }
        } catch (SQLException ex) {
            System.out.println("probleme d'authentification");
        }
        return stat;

    }

    @Override
    public User afficherUser(int id) {
        String reqGet = "SELECT * FROM users WHERE id=?";
        PreparedStatement st;
        User user = new User();
        try {
            st = conn.prepareStatement(reqGet);
            st.setInt(1, id);
            ResultSet rs;
            rs = st.executeQuery();
            while (rs.next()) {
        user.setNom(rs.getString("nom"));
        user.setPrenom(rs.getString("prenom"));
        user.setUsername(rs.getString("username"));
        user.setDate_naiss(rs.getString("date_naissance"));
        user.setEmail(rs.getString("email"));
        user.setTel(rs.getString("tel"));
        user.setAdresse(rs.getString("adresse"));
        user.setTypeCompte(rs.getString("status"));
        user.setMdp(rs.getString("mdp"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

}
