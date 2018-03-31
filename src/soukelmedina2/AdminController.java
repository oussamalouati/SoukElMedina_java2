/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soukelmedina2;

import com.jfoenix.controls.JFXButton;
import tests.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static soukelmedina2.MainController.nom;
import static soukelmedina2.MainController.prenom;
import utils.Delta;

/**
 *
 * @author INETEL
 */
public class AdminController implements Initializable {

    final Delta dragDelta = new Delta();
    @FXML
    private Label usr_corrd;
    @FXML
    private JFXButton logout;

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
        Stage current_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Parent main_interface = FXMLLoader.load(getClass().getResource("/gui/Acceuil.fxml"));
        Scene main_scene = new Scene(main_interface);
        main_scene.setFill(Color.TRANSPARENT);
        current_stage.close();
        current_stage.setScene(main_scene);
        current_stage.show();
        main_interface.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dragDelta.x = current_stage.getX() - mouseEvent.getScreenX();
                dragDelta.y = current_stage.getY() - mouseEvent.getScreenY();
            }
        });
        main_interface.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                current_stage.setX(mouseEvent.getScreenX() + dragDelta.x);
                current_stage.setY(mouseEvent.getScreenY() + dragDelta.y);
            }
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usr_corrd.setText(nom + " " + prenom);
    }

}
