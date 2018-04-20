/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import static controllers.MainController.id;
import static controllers.MainController.mediaPlayer;
import static controllers.MainController.nom;
import static controllers.MainController.playstatus;
import static controllers.MainController.prenom;
import static controllers.MapController.lat;
import static controllers.MapController.lng;

import static controllers.VendeurController.gridpane;
import static controllers.VendeurController.nomMag;
import entities.Magasin;
import entities.Panier;
import entities.Produit;
import entities.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import services.MagasinService;
import services.UserService;
import utils.Delta;
import utils.Uploadimg;

/**
 * FXML Controller class
 *
 * @author INETEL
 */
public class ClientController implements Initializable {
   
    public static GridPane gridpanePpan;
    public GridPane gridpane;
    final Delta dragDelta = new Delta();
    public static String nomMag;
    public static int nbrMag;
    public static boolean isClient = false;

    public static Panier panier = new Panier();

    UserService US = new UserService();
    MagasinService MS = new MagasinService();

    File img;
    String urlImgMag;
    @FXML
    private JFXButton supp_mag;
    @FXML
    private AnchorPane validerSuppMag;
    @FXML
    private ScrollPane scrollPanemags,scrollpanier;
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
    private AnchorPane an_gestCompte, validerSupp, an_Mags,produitpanier,votrepanier;
    @FXML
    private AnchorPane dashboard, mapanchor;
    @FXML
    private Label usr_corrd,totalpanier,nbprodpanlabel;
    @FXML
    private JFXButton logout_btn, gestionMag_btn,afficherpan;

    public JFXButton getAfficherpan() {
        return afficherpan;
    }
    @FXML
    private JFXButton createMag, upload_mag, insert_mag, music_btn, music_btn_stop ;

    @FXML
    private AnchorPane an_gestMag;
    private FadeTransition fadeIn1 = new FadeTransition(
            Duration.millis(500)
    );

    @FXML
    private void handleClose() {
        isClient = false;
        System.exit(0);
    }

    @FXML
    private void handleMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void logout(ActionEvent event) throws IOException, Exception {
        isClient = false;
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
        scrollPanemags.setVisible(false);

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
        scrollPanemags.setVisible(false);
        votrepanier.setVisible(false);
    }

    @FXML
    void consulter_mag(ActionEvent event) throws SQLException, IOException {
        scrollPanemags.setVisible(true);
        an_gestCompte.setVisible(false);
        dashboard.setVisible(false);

        MS.nbmags();//determiner le nbr de magazin

        gridpane = new GridPane();
        gridpane.setPadding(new Insets(65, 10, 10, 10));
        gridpane.setHgap(10);
        gridpane.setVgap(10);

        ResultSet rsm = MS.afficherTMagasinsCli();

        int col = 1;
        int row = 0;
        int index = -1;

        while (rsm.next()) {

            int idmag = rsm.getInt("id");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Magazin.fxml"));
            MagazinController magctrl = new MagazinController();
            index++;

            magctrl.setIdmagcurrent(idmag);
            magctrl.setNommagcurrent(rsm.getString("nom_magazin"));
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
        scrollPanemags.setFitToHeight(true);
        an_Mags.getChildren().add(gridpane);
        
        
    }

    @FXML
    void afficherpanier(ActionEvent event) throws IOException {
        int row = 0;
        
        produitpanier.getChildren().remove(gridpanePpan);
        
        votrepanier.setVisible(true);
        totalpanier.setText(toString(panier.calculerPanier()));
        
       
        Set cles = panier.getMapPan().keySet();
        Iterator it = cles.iterator();
        
        gridpanePpan = new GridPane();
        gridpanePpan.setPadding(new Insets(5, 5, 5, 5));
        gridpanePpan.setHgap(5);
        gridpanePpan.setVgap(5);
        int indexprod = -1;
        while (it.hasNext()) {
            Produit cle = (Produit) it.next();
            int qte = panier.getMapPan().get(cle);
            
            FXMLLoader loaderPpan = new FXMLLoader(getClass().getResource("/gui/Produitpanier.fxml"));
            ProduitpanierController prodpanctrl = new ProduitpanierController();
            prodpanctrl.setProduit(cle);
            prodpanctrl.setQtePan(qte);
            indexprod++;
            prodpanctrl.setIndexprod(indexprod);
            loaderPpan.setController(prodpanctrl);
            Parent prodpanAnchor = loaderPpan.load();
            
            
            gridpanePpan.add(prodpanAnchor, 1, row);
            row++;
        }
        scrollpanier.setFitToHeight(true);
        produitpanier.getChildren().add(gridpanePpan);
        
        
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
        isClient = true;
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
    private String toString(float aInt) {
        return ""+aInt;
    }    

}
