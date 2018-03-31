/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import utils.Delta;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author INETEL
 */
public class Main extends Application {
       /* Delta est  une classe qui contient deux variables quantitatives X et Y 
    pour determiner la postion de la fenetre */
        
       final Delta dragDelta = new Delta(); 
    @Override
    
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/Acceuil.fxml"));
        
        /* la ligne 33 permet de cacher le "windows bar/borders"  */
        stage.initStyle(StageStyle.TRANSPARENT);
            
            /*les deux fonction "setOnMousePressed" et "setOnMouseDragged"
              servent à deplacer la fenetre.  */
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                  dragDelta.x = stage.getX() - mouseEvent.getScreenX();
                  dragDelta.y = stage.getY() - mouseEvent.getScreenY();
                }
              });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
              @Override public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() + dragDelta.x);
                stage.setY(mouseEvent.getScreenY() + dragDelta.y);
              }
            });
        
            
            
           
            
        Scene scene = new Scene(root);
        
         
         
        scene.setFill(Color.TRANSPARENT);// cette ligne sert a rendre l'arriere plan transparent .
        
        stage.setScene(scene);
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
