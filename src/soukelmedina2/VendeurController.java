/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soukelmedina2;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import entities.Magasin;
import entities.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import services.MagasinService;
import services.UserService;

import static soukelmedina2.MainController.id;

import static soukelmedina2.MainController.mediaPlayer;
import static soukelmedina2.MainController.nom;
import static soukelmedina2.MainController.playstatus;

import static soukelmedina2.MainController.prenom;
import static soukelmedina2.MapController.lat;
import static soukelmedina2.MapController.lng;
import utils.Delta;
import utils.Uploadimg;

/**
 *
 * @author INETEL
 */
public class VendeurController implements Initializable {

    public static GridPane gridpane;
    final Delta dragDelta = new Delta();
    public static String nomMag;
    public static int nbrMag;
    
    UserService US = new UserService();
    MagasinService MS = new MagasinService();
    
    File img;
    String urlImgMag;
    @FXML
    private JFXButton supp_mag;
    @FXML
    private AnchorPane validerSuppMag;
    @FXML
    private ScrollPane scrollPanemag;
    @FXML
    private JFXTextField pass_text, nom_mag;
    @FXML
    private JFXTextField nom_field, prenom_field, username_field, email_field, tel_field;
    @FXML
    private JFXDatePicker date_picker;
    @FXML
    private JFXTextArea adresse_area, descri_mag, adresse_mag;
    @FXML
    private JFXPasswordField mdp_field;
    @FXML
    private JFXButton show_mdp;
    @FXML
    private JFXButton btn_valider;
    @FXML
    private JFXButton gest_compte, suppCompteStep1, suppCompteStep2, suppAnnuler;
    @FXML
    private AnchorPane an_gestCompte, validerSupp, an_createMagasin;
    @FXML
    private AnchorPane dashboard, mapanchor;
    @FXML
    private Label usr_corrd;
    @FXML
    private JFXButton logout_btn, gestionMag_btn;
    @FXML
    private JFXButton createMag, upload_mag, insert_mag, music_btn, music_btn_stop;
    
    @FXML
    private  AnchorPane an_gestMag;
    private FadeTransition fadeIn1 = new FadeTransition(
            Duration.millis(500)
    );

    @FXML
    private void handleClose() {
        System.exit(0);
    }

    @FXML
    private void handleMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void logout(ActionEvent event) throws IOException, Exception {
        music_btn.fire();

        Stage current_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        current_stage.close();

        Stage main_stage = new Stage();
        Parent main_interface = FXMLLoader.load(getClass().getResource("/gui/Acceuil.fxml"));
        Scene first_scene = new Scene(main_interface);
        first_scene.setFill(Color.TRANSPARENT);
        main_stage.setScene(first_scene);
        main_stage.initStyle(StageStyle.TRANSPARENT);

        main_stage.show();
        main_interface.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dragDelta.x = main_stage.getX() - mouseEvent.getScreenX();
                dragDelta.y = main_stage.getY() - mouseEvent.getScreenY();
            }
        });
        main_interface.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                main_stage.setX(mouseEvent.getScreenX() + dragDelta.x);
                main_stage.setY(mouseEvent.getScreenY() + dragDelta.y);
            }
        });

    }

    @FXML
    private void gestionCompte(ActionEvent event) throws SQLException {
        an_gestCompte.setVisible(true);
        dashboard.setVisible(false);
        an_createMagasin.setVisible(false);
        scrollPanemag.setVisible(false);
        mapanchor.getChildren().clear();
 
        //affichage des information de l'utilisateur authentifié 
        User user = US.afficherUser(id);
        nom_field.setText(user.getNom());
        prenom_field.setText(user.getPrenom());
        username_field.setText(user.getUsername());
        date_picker.setValue(LocalDate.parse(user.getDate_naiss()));
        email_field.setText(user.getEmail());
        tel_field.setText(user.getTel());
        adresse_area.setText(user.getAdresse());
        mdp_field.setText(user.getMdp());

        btn_valider.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                User userupd = new User();
                userupd.setNom(nom_field.getText());
                userupd.setPrenom(prenom_field.getText());
                userupd.setUsername(username_field.getText());
                userupd.setDate_naiss(date_picker.getValue().toString());
                userupd.setEmail(email_field.getText());
                userupd.setTel(tel_field.getText());
                userupd.setAdresse(adresse_area.getText());
                userupd.setMdp(mdp_field.getText());
                //update
                US.modfierUser(userupd);  
            }
        });
        suppCompteStep2.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                    US.supprimerUser(id);
                    logout_btn.fire();              
            }
        });
        suppCompteStep1.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                validerSupp.setVisible(true);
            }
        });
        suppAnnuler.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                validerSupp.setVisible(false);
                fadeIn1.playFromStart();

            }
        });
    }

    @FXML
    private void showpass() {
        show_mdp.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mdp_field.setVisible(false);
                pass_text.setVisible(true);
                pass_text.setText(mdp_field.getText());
            }

        });
        show_mdp.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mdp_field.setVisible(true);
                pass_text.setVisible(false);
            }
        });

    }

    @FXML
    public void playaudio(ActionEvent event) {
        music_btn.setVisible(true);
        music_btn_stop.setVisible(false);
        mediaPlayer.play();
    }

    @FXML
    public void stopaudio(ActionEvent event) {
        music_btn.setVisible(false);
        music_btn_stop.setVisible(true);
        mediaPlayer.pause();
    }

    @FXML
    private void backtodash(ActionEvent event) {
        dashboard.setVisible(true);
        an_gestCompte.setVisible(false);
        an_createMagasin.setVisible(false);
        scrollPanemag.setVisible(false);
        mapanchor.getChildren().clear();
    }

    @FXML
    void createMagasin(ActionEvent event) throws IOException {
        an_createMagasin.setVisible(true);
        dashboard.setVisible(false);
        mapanchor.getChildren().add(FXMLLoader.load(getClass().getResource("/gui/Map.fxml")));
        //slection de l'image/logo du magasin 
        upload_mag.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fc_mag = new FileChooser();
                fc_mag.getExtensionFilters().addAll(new ExtensionFilter[]{
                    new ExtensionFilter("Image Files", new String[]{"*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"}),
                    new ExtensionFilter("JPG", new String[]{"*.jpg"}),
                    new ExtensionFilter("JPEG", new String[]{"*.jpeg"}),
                    new ExtensionFilter("BMP", new String[]{"*.bmp"}),
                    new ExtensionFilter("PNG", new String[]{"*.png"}),
                    new ExtensionFilter("GIF", new String[]{"*.gif"})});
                img = fc_mag.showOpenDialog(null);
                urlImgMag = "http://localhost:80/skmedina/imgmag/" + img.getName();
            }
        });
        insert_mag.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Magasin magasin = new Magasin();
                 magasin.setNom_magasin(nom_mag.getText()); 
                 magasin.setDescription(descri_mag.getText());
                 magasin.setAdresse(adresse_mag.getText());
                 magasin.setUrlimg(urlImgMag);
                 magasin.setLatitude(lat);
                 magasin.setLongitude(lng);
                 magasin.setProprietaire(id);
                 
                 MS.insertMagasin(magasin);
                 
                 Uploadimg upimg = new Uploadimg(img);
            }
        });
    }

    @FXML
    public void gestionMag(ActionEvent event) throws SQLException, IOException {

        scrollPanemag.setVisible(true);
        dashboard.setVisible(false);
        an_gestCompte.setVisible(false);
        an_createMagasin.setVisible(false);
        mapanchor.getChildren().clear();

        MS.nbmags(); //calcul du nbr de magasins
        /*************************AFFICHAGE***************************************/
        gridpane = new GridPane();
        gridpane.setPadding(new Insets(65, 10, 10, 10));
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        
        ResultSet rsm = MS.afficherTMagasins(); //lecture des magasins a partir de la base de données
        
        int col = 1;
        int row = 0;
        int index = -1;

        while (rsm.next()) {
            nomMag = rsm.getString("nom_magazin"); 
            int idmag=rsm.getInt("id");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Magazin.fxml"));
            MagazinController magctrl = new MagazinController();
            index++;
            
            magctrl.setIdmagcurrent(idmag);
            
            magctrl.setIndex(index);
            loader.setController(magctrl);
            Parent MagAnchor = loader.load();

            gridpane.add(MagAnchor, col, row);

            col++;
            if (col == 3) {
                col = 1;
                row++;
            }
        }
        scrollPanemag.setFitToHeight(true);
        an_gestMag.getChildren().add(gridpane);
        an_gestMag.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gridpane.getChildren().clear();
                gestionMag_btn.fire();
            }
        });
        /*************************FIN AFFICHAGE***************************************/
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usr_corrd.setText(nom + " " + prenom);
        fadeIn1.setNode(validerSupp);
        fadeIn1.setFromValue(0.0);
        fadeIn1.setToValue(1.0);
        fadeIn1.setCycleCount(1);
        fadeIn1.setAutoReverse(true);

        if (playstatus) {
            music_btn.setVisible(false);
            music_btn_stop.setVisible(true);
        } else {
            music_btn.setVisible(true);
            music_btn_stop.setVisible(false);
            playstatus = true;
        }

    }

}
