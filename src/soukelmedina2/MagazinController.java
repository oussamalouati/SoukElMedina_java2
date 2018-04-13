/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soukelmedina2;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.MagasinService;
import static soukelmedina2.MainController.id;
import static soukelmedina2.VendeurController.gridpane;
import static soukelmedina2.VendeurController.nbrMag;
//import static soukelmedina2.VendeurController.nbrMag;
import static soukelmedina2.VendeurController.nomMag;

import utils.Connexion;
import utils.Delta;

/**
 * FXML Controller class
 *
 * @author INETEL
 */
public class MagazinController implements Initializable {

    private int index;

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    private String nommagcurrent;
    private int idmagcurrent;
    MagasinService MS = new MagasinService();
    public static ModifmagazinController modifmagctrl;

    public void setIdmagcurrent(int idmagcurrent) {
        this.idmagcurrent = idmagcurrent;
    }
    final Delta dragDelta = new Delta();
    public static boolean SupStatus = false;
    public static boolean Updmag = false;
    public static boolean reloadsup = false;
    String URLimg;
    @FXML
    private Label nom_maglabel;
    @FXML
    private ImageView imgMag;
    @FXML
    private JFXButton supp_mag;
    @FXML
    private JFXButton modif_mag;

    @FXML
    void modifMag(ActionEvent event) throws IOException {
        Updmag = true;
        Stage modif_stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Modifmagazin.fxml"));
        modifmagctrl = new ModifmagazinController();
        modifmagctrl.setIdmagmodif(idmagcurrent);
        loader.setController(modifmagctrl);
        Parent modif_interface = loader.load();

        modif_stage.initStyle(StageStyle.UNDECORATED);
        Scene modif_scene = new Scene(modif_interface);
        modif_scene.setFill(Color.TRANSPARENT);
        modif_stage.setScene(modif_scene);
        modif_stage.initModality(Modality.APPLICATION_MODAL);
        modif_stage.showAndWait();

    }

    @FXML
    void suppMag(ActionEvent event) throws IOException, SQLException {

        Stage supp_stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Suppmagazin.fxml"));

        SuppmagazinController supmagctrl = new SuppmagazinController();
        supmagctrl.setNametodel(nommagcurrent);
        loader.setController(supmagctrl);
        Parent supp_interface = loader.load();

        supp_stage.initStyle(StageStyle.TRANSPARENT);
        Scene supp_scene = new Scene(supp_interface);
        supp_scene.setFill(Color.TRANSPARENT);
        supp_stage.setScene(supp_scene);
        supp_stage.initModality(Modality.APPLICATION_MODAL);
        supp_stage.showAndWait();

        if (SupStatus) {
            MS.supprimerMagasin(idmagcurrent);
            reloadsup = true;
            gridpane.getChildren().remove(this.index);
            
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Connexion cn1 = Connexion.getInstance();
        Connection conn = cn1.getConnection();
        try {
            String reqIMG = "SELECT img FROM magazin WHERE nom_magazin ='" + nomMag + "'";
            Statement stmimg = conn.createStatement();
            ResultSet rsimg = stmimg.executeQuery(reqIMG);
            rsimg.next();
            URLimg = rsimg.getString("img");
            rsimg.close();
        } catch (SQLException ex) {
            Logger.getLogger(MagazinController.class.getName()).log(Level.SEVERE, null, ex);
        }
        nom_maglabel.setText(nomMag);
        this.nommagcurrent = nomMag;
        imgMag.setImage(new Image(URLimg, true));
    }

}
