/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author INETEL
 */
public class CmdcliController implements Initializable {
           private String ref ;
           private String date ;
           private String prixtot;

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrixtot(String prixtot) {
        this.prixtot = prixtot;
    }
    
    @FXML
    private Label reflabel;

    @FXML
    private Label datecmdlabel;

    @FXML
    private Label montantcmdlabel;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        reflabel.setText(ref);
        datecmdlabel.setText(date);
        montantcmdlabel.setText(prixtot);
        
    }    
    
}
