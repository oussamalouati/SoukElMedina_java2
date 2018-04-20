/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import static controllers.ClientController.gridpanePpan;
import static controllers.ClientController.panier;
import static controllers.MainController.clictrl;
import entities.Produit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
/**
 * FXML Controller class
 *
 * @author INETEL
 */
public class ProduitpanierController implements Initializable {
    Produit produit;
    int qtePan;
    int indexprod ;
   
    public void setIndexprod(int indexprod) {
        this.indexprod = indexprod;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQtePan() {
        return qtePan;
    }

    public void setQtePan(int qtePan) {
        this.qtePan = qtePan;
    }
    
     @FXML
    private JFXButton btn_return1;

    @FXML
    private ImageView imgprod;

    @FXML
    private Label nomProduit;

    @FXML
    private Label qte;

    @FXML
    private Label prix;

    @FXML
    void supprimerProdPanier(ActionEvent event) {
        gridpanePpan.getChildren().remove(this.indexprod);
        panier.getMapPan().remove(produit);
        clictrl.getAfficherpan().fire();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        imgprod.setImage(new Image(this.produit.getImg(), true));
        nomProduit.setText(this.produit.getNom());
        qte.setText(toString(qtePan));
        prix.setText(toString(this.produit.getPrix()));

    }
    
    private String toString(float aInt) {
        return ""+aInt;
    }   
    private String toString(int aInt) {
        return ""+aInt;
    }   
    
}
