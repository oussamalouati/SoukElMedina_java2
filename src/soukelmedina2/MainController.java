/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soukelmedina2;

import utils.Connexion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.Button;
import static java.awt.SystemColor.window;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Delta;

/**
 *
 * @author INETEL
 */
public class MainController implements Initializable {
    static boolean playStatus=false;
    MediaPlayer mediaPlayer;
    Media sound;
    public static int id;
    public static String nom;
    public static String prenom;
    public static String login;
    final Delta dragDelta = new Delta();
    @FXML
    private JFXButton btn_ins, btn_return, btn_cnx;
    @FXML
    private AnchorPane an_cnx, an_ins, cnx_ban, ins_ban, left_ctrl;
    @FXML
    private JFXTextField cnx_f_field, nom_field, prenom_field, username_field, email_field, tel_field;
    @FXML
    private JFXPasswordField cnx_mdp_field, mdp_field;
    @FXML
    private JFXComboBox<String> type_acc;
    @FXML
    private JFXDatePicker date_picker;
    @FXML
    private JFXTextArea adresse_area;

    private FadeTransition fadeIn1 = new FadeTransition(
            Duration.millis(1000)
    );
    private FadeTransition fadeIn2 = new FadeTransition(
            Duration.millis(500)
    );
    @FXML
    private JFXButton btn_signup;
    @FXML
    private JFXButton btn_login;

    @FXML
    private JFXButton show_mdp, show_mdp2, music_btn, music_btn_stop;
    @FXML
    private JFXTextField pass_text;
    @FXML
    private JFXTextField pass_text2;

    @FXML
    private void handleClose() {
        System.exit(0);
    }

    @FXML
    private void handleMinimize(ActionEvent event) {
        Stage stage = (Stage) an_cnx.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void showpass() {
        show_mdp.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cnx_mdp_field.setVisible(false);
                pass_text.setVisible(true);
                pass_text.setText(cnx_mdp_field.getText());
            }
        });
        show_mdp.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cnx_mdp_field.setVisible(true);
                pass_text.setVisible(false);
            }
        });
        show_mdp2.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mdp_field.setVisible(false);
                pass_text2.setVisible(true);
                pass_text2.setText(mdp_field.getText());
            }
        });
        show_mdp2.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mdp_field.setVisible(true);
                pass_text2.setVisible(false);
            }
        });
    }

    @FXML
    private void btnSwipHandler(ActionEvent event) {
        //le bloc ci dessous permet l'affichage des 'AnchorPane's relatives a l'inscription
        if (event.getSource() == btn_ins) {
            an_ins.setVisible(true);
            ins_ban.setVisible(true);
            fadeIn1.playFromStart();
            left_ctrl.setVisible(false);
            an_cnx.setVisible(false);
            cnx_ban.setVisible(false);
        }
        // le bloc ci dessous permet  un retour a l'etat initiale (la connexion)     
        if (event.getSource() == btn_return) {
            an_ins.setVisible(false);
            ins_ban.setVisible(false);
            left_ctrl.setVisible(true);
            an_cnx.setVisible(true);
            cnx_ban.setVisible(true);
            fadeIn2.playFromStart();
        }
        // focus dans le premier JFXTextField du form de connexion
        if (event.getSource() == btn_cnx) {
            cnx_f_field.requestFocus();
        }
    }

    @FXML
    private void Connexion(ActionEvent event) throws SQLException, IOException {
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Connexion cn1 = Connexion.getInstance();
        Connection conn = cn1.getConnection();
        login = cnx_f_field.getText();
        System.out.println(login);
        String reqcnx = "SELECT * FROM users WHERE username=?";
        PreparedStatement st = conn.prepareStatement(reqcnx);
        st.setString(1, login);
        ResultSet rs;
        rs = st.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                String mdp = rs.getString("mdp");
                nom = rs.getString("nom");
                prenom = rs.getString("prenom");
                id = rs.getInt("id");
                if (cnx_mdp_field.getText().equals(mdp)) {
                    String stat = rs.getString("status");
                    switch (stat) {
                        case "Admin":
                            Parent admin_interface = FXMLLoader.load(getClass().getResource("/gui/Admin.fxml"));
                            Scene admin_scene = new Scene(admin_interface);
                            main_stage.close();
                            main_stage.setScene(admin_scene);
                            main_stage.show();

                            /*les deux fonction "setOnMousePressed" et "setOnMouseDragged"
              servent à deplacer la fenetre.  */
                            admin_interface.setOnMousePressed(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    dragDelta.x = main_stage.getX() - mouseEvent.getScreenX();
                                    dragDelta.y = main_stage.getY() - mouseEvent.getScreenY();
                                }
                            });
                            admin_interface.setOnMouseDragged(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    main_stage.setX(mouseEvent.getScreenX() + dragDelta.x);
                                    main_stage.setY(mouseEvent.getScreenY() + dragDelta.y);
                                }
                            });
                            break;
                        case "Vendeur":
                            Parent vendeur_interface = FXMLLoader.load(getClass().getResource("/gui/Vendeur.fxml"));
                            Scene vendeur_scene = new Scene(vendeur_interface);
                            main_stage.close();
                            main_stage.setScene(vendeur_scene);
                            main_stage.show();
                            /*les deux fonction "setOnMousePressed" et "setOnMouseDragged"
              servent à deplacer la fenetre.  */
                            vendeur_interface.setOnMousePressed(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    dragDelta.x = main_stage.getX() - mouseEvent.getScreenX();
                                    dragDelta.y = main_stage.getY() - mouseEvent.getScreenY();
                                }
                            });
                            vendeur_interface.setOnMouseDragged(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    main_stage.setX(mouseEvent.getScreenX() + dragDelta.x);
                                    main_stage.setY(mouseEvent.getScreenY() + dragDelta.y);
                                }
                            });
                            break;
                        case "Client":  ;
                            break;
                        default:
                            System.out.println("Probléme de connexion");
                            break;
                    }
                } else {
                    System.out.println("mdp incorrect");
                }
            }

        } else {
            System.out.println("username incorrect");
        }
    }

    @FXML
    private void inscription() throws SQLException {
        Connexion cn1 = Connexion.getInstance();
        Connection conn = cn1.getConnection();
        //recuperation des information a partir du formulaire d'inscription
        String nom = nom_field.getText();
        String prenom = prenom_field.getText();
        String username = username_field.getText();
        String date_naiss = date_picker.getValue().toString();
        String email = email_field.getText();
        String tel = tel_field.getText();
        String adresse = adresse_area.getText();
        String typeCompte = type_acc.getSelectionModel().getSelectedItem();
        String mdp = mdp_field.getText();
        //insertion dans la BD 
        String reqIns = "INSERT INTO users (nom,prenom,username,date_naissance,email,tel,adresse,mdp,status) VALUES  (?,?,?,?,?,?,?,?,?)";
        PreparedStatement st = conn.prepareStatement(reqIns);
        st.setString(1, nom);
        st.setString(2, prenom);
        st.setString(3, username);
        st.setString(4, date_naiss);
        st.setString(5, email);
        st.setString(6, tel);
        st.setString(7, adresse);
        st.setString(8, mdp);
        st.setString(9, typeCompte);
        st.executeUpdate();
        //retour à l'interface de l'inscription
        an_ins.setVisible(false);
        ins_ban.setVisible(false);
        left_ctrl.setVisible(true);
        an_cnx.setVisible(true);
        cnx_ban.setVisible(true);
        fadeIn2.playFromStart();
    }

    @FXML
    public void playaudio(ActionEvent event) {
        music_btn.setVisible(false);
        music_btn_stop.setVisible(true);
        mediaPlayer.pause();
        music_btn_stop.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                music_btn.setVisible(true);
                music_btn_stop.setVisible(false);
                mediaPlayer.play();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fadeIn1.setNode(ins_ban);
        fadeIn2.setNode(cnx_ban);
        //initialisation des animations
        fadeIn1.setFromValue(0.0);
        fadeIn1.setToValue(1.0);
        fadeIn1.setCycleCount(1);
        fadeIn1.setAutoReverse(false);

        fadeIn2.setFromValue(0.0);
        fadeIn2.setToValue(1.0);
        fadeIn2.setCycleCount(1);
        fadeIn2.setAutoReverse(false);
        //ajout des items pour le combo box  "type de compte"
        type_acc.getItems().add("Client");
        type_acc.getItems().add("Vendeur");
        //valeur par defaut si l'utilisateur n'a pas slectionner une date de naissance
        date_picker.setValue(LocalDate.now());
        //audio play
        sound = new Media(new File("src/audio/audio.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.18);
        if(!playStatus){
        mediaPlayer.play();
        playStatus=true;
        }

    }

}
