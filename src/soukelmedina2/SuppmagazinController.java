/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soukelmedina2;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import static soukelmedina2.MagazinController.SupStatus;

//import static soukelmedina2.VendeurController.nbrMag;

/**
 * FXML Controller class
 *
 * @author INETEL
 */

public class SuppmagazinController implements Initializable {

    public void setNametodel(String nametodel) {
        this.nametodel = nametodel;
    }
    String nametodel;
    @FXML
    private JFXButton suppMagValider;

    @FXML
    private JFXButton suppMagAnnuler;
    
    @FXML
    private Label nommagAsupp;
    
    @FXML
    void annuler(ActionEvent event) {
        Stage current_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        current_stage.close();
        SupStatus=false;
    }

    @FXML
    void supprimer(ActionEvent event) {
        Stage current_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        current_stage.close();
        
        SupStatus=true;
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nommagAsupp.setText(nametodel);
    }    
    
}
