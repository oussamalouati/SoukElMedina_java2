/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import static controllers.ClientController.panier;
import entities.Produit;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import services.ProduitService;

/**
 * FXML Controller class
 *
 * @author INETEL
 */
public class ProduitController implements Initializable {
    private int idprod;
    String urlimg;
    ProduitService PS =new ProduitService();
     @FXML
    private ImageView imgProd;
     @FXML
    private Text descriptionp;
    @FXML
    private Label nom_prodlabel;
    @FXML
    private Spinner<Integer> qtespin;
    @FXML
    private Label prix;

    public int getIdprod() {
        return idprod;
    }

    public void setIdprod(int idprod) {
        this.idprod = idprod;
    }
    @FXML
    void ajouterPanier(ActionEvent event) {
        Produit p = new Produit();
        p.setId(idprod);
        p.setNom(nom_prodlabel.getText());
        p.setPrix(Float.parseFloat(prix.getText()));
        p.setDescription(descriptionp.getText());
        p.setImg(urlimg);
        panier.ajouterProduit(p, qtespin.getValue());
        
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ResultSet rsp = PS.afficherProduit(idprod);
        try {
            rsp.next();
            urlimg = rsp.getString("img");
            nom_prodlabel.setText(rsp.getString("nom_produit"));
            prix.setText(toString(rsp.getInt("prix")));
            descriptionp.setText(rsp.getString("description"));
            rsp.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        imgProd.setImage(new Image(urlimg, true));
    }    

    private String toString(int aInt) {
        return ""+aInt;
    }
    
}
