/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.MagazinController.Updmag;
import static controllers.MapController.lat;
import static controllers.MapController.lng;
import static controllers.VendeurController.gridpane;
import static controllers.VendeurController.nomMag;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.MagasinService;
import services.ProduitService;

/**
 * FXML Controller class
 *
 * @author INETEL
 */
public class MagDetailsController implements Initializable {

    public int idmagd;
    GridPane gridpaneP;
    

    @FXML
    private Label nom_mag;

    @FXML
    private Text description;
    @FXML
    private AnchorPane map_anchor, an_prod_cli;
    @FXML
    private ImageView imgMag;
    public static boolean detailp = false;
    public String urlImgMag;
    MapController mapctrl = new MapController();
    MagasinService MS = new MagasinService();
    ProduitService PS = new ProduitService();

    public void setIdmagd(int idmagd) {
        this.idmagd = idmagd;
    }

    @FXML
    void annuler(ActionEvent event) {
        Stage current_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        current_stage.close();
        Updmag = false;
        detailp = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Updmag = true;
        detailp = true;
        ResultSet rsm2 = MS.afficherMagasin(idmagd);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Map.fxml"));
            mapctrl = new MapController();
            loader.setController(mapctrl);
            Parent anmap = loader.load();
            
            map_anchor.getChildren().add(anmap);
            /* map_anchor.getChildren(anmap).setLayoutX(530.0);
            map_anchor.getChildren(anmap).setLayoutY(1.0); */
        } catch (IOException ex) {
            Logger.getLogger(ModifmagazinController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rsm2.next();
            nom_mag.setText(rsm2.getString("nom_magazin"));
            description.setText(rsm2.getString("description"));
            // adresse_mag.setText(rsm2.getString("adresse"));
            lat = rsm2.getDouble("latitude");
            lng = rsm2.getDouble("longitude");
            urlImgMag = rsm2.getString("img");
            imgMag.setImage(new Image(urlImgMag, true));
            rsm2.close();
        } catch (SQLException ex) {
            Logger.getLogger(ModifmagazinController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //affichage des produits relative a la magazin courrante
        ResultSet rsmP = PS.afficherTProduitCli(idmagd);
        int nbprods = PS.nbprods();
        gridpaneP = new GridPane();
        gridpaneP.setPadding(new Insets(10, 10, 10, 10));
        gridpaneP.setHgap(10);
        gridpaneP.setVgap(10);
        int col = 1;
        int row = 0;
        try {
            while (rsmP.next()) {
                
                int idProd = rsmP.getInt("id");
                FXMLLoader loaderP = new FXMLLoader(getClass().getResource("/gui/Produit.fxml"));
                ProduitController prodctrl = new ProduitController();
                prodctrl.setIdprod(idProd);
                loaderP.setController(prodctrl);
                Parent prodAnchor = loaderP.load();

                gridpaneP.add(prodAnchor, col, row);
                col++;
                if (col == 3) {
                    col = 1;
                    row++;
                }
            }
        } catch (IOException exc) {
            System.out.println("load produit problem"+exc.getMessage());

        } catch (SQLException ex) {
            Logger.getLogger(MagDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        an_prod_cli.getChildren().add(gridpaneP);
    }

}
