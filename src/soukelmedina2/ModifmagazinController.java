/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soukelmedina2;

import entities.Magasin;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import services.MagasinService;
import static soukelmedina2.MagazinController.SupStatus;
import static soukelmedina2.MagazinController.Updmag;
import static soukelmedina2.MainController.id;
import static soukelmedina2.MapController.lat;
import static soukelmedina2.MapController.lng;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import utils.Uploadimg;

/**
 * FXML Controller class
 *
 * @author INETEL
 */
public class ModifmagazinController implements Initializable {

    String urlImgMag;
    int idmagmodif;
    File img;
    MagasinService MS = new MagasinService();
    MapController mapctrl;
    @FXML
    private AnchorPane an_modifMagasin;

    @FXML
    private JFXTextField nom_mag;

    @FXML
    private JFXTextArea descri_mag;

    @FXML
    private JFXTextArea adresse_mag;

    @FXML
    private JFXButton upload_mag;

    @FXML
    private JFXButton modif_mag;
    @FXML
    private AnchorPane mapanchor;
    public int getIdmagmodif() {
        return idmagmodif;
    }

    public void setIdmagmodif(int idmagmodif) {
        this.idmagmodif = idmagmodif;
    }

    @FXML
    void annuler(ActionEvent event) {
        Stage current_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        current_stage.close();
        Updmag = false;
    }
    
     public void markeradress(String adresseCMarker){
     adresse_mag.setText(adresseCMarker);
    }
    @FXML
    void modifer(ActionEvent event) {
        Stage current_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //slection de l'image/logo du magasin 

        upload_mag.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fc_mag = new FileChooser();
                fc_mag.getExtensionFilters().addAll(new FileChooser.ExtensionFilter[]{
                    new FileChooser.ExtensionFilter("Image Files", new String[]{"*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"}),
                    new FileChooser.ExtensionFilter("JPG", new String[]{"*.jpg"}),
                    new FileChooser.ExtensionFilter("JPEG", new String[]{"*.jpeg"}),
                    new FileChooser.ExtensionFilter("BMP", new String[]{"*.bmp"}),
                    new FileChooser.ExtensionFilter("PNG", new String[]{"*.png"}),
                    new FileChooser.ExtensionFilter("GIF", new String[]{"*.gif"})});
                img = fc_mag.showOpenDialog(null);
                urlImgMag = "http://localhost:80/skmedina/imgmag/" + img.getName();
                Uploadimg upimg = new Uploadimg(img);
            }
        });
        //upload de l'image vers le serveur

        Magasin magasin = new Magasin();
        magasin.setNom_magasin(nom_mag.getText());
        magasin.setDescription(descri_mag.getText());
        magasin.setAdresse(adresse_mag.getText());
        magasin.setUrlimg(urlImgMag);
        magasin.setLatitude(lat);
        magasin.setLongitude(lng);
        magasin.setId(idmagmodif);
        MS.modfierMagasin(magasin);

        current_stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ResultSet rsm2 = MS.afficherMagasin(idmagmodif);
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Map.fxml"));
         mapctrl = new MapController();
        loader.setController(mapctrl);
        mapanchor.getChildren().add(loader.load());
            
        } catch (IOException ex) {
            Logger.getLogger(ModifmagazinController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rsm2.next();
            nom_mag.setText(rsm2.getString("nom_magazin"));
            descri_mag.setText(rsm2.getString("description"));
            adresse_mag.setText(rsm2.getString("adresse"));
            lat = rsm2.getDouble("latitude");
            lng = rsm2.getDouble("longitude");
            urlImgMag = rsm2.getString("img");
            
            rsm2.close();
        } catch (SQLException ex) {
            Logger.getLogger(ModifmagazinController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
