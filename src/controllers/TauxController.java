/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import modele.Taux;


public class TauxController implements Initializable {

     @FXML

    private TextField txtMontant;

    @FXML
  private  void enregistrer(ActionEvent event) {
       String montant = txtMontant.getText();
           if (montant.isEmpty()) {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("TAUX");
              alert.setContentText("vous avez mis a jour le Taux");
              alert.setHeaderText(null);
              alert.showAndWait();
               return;
                 
           }
           // on place le block try....catch pour se prevenir des mauvaises conversion venant
           try {
               double montantConverti=Double.parseDouble(montant);
                         
           Taux taux = new Taux(montantConverti);
          boolean insertion = taux.insertionTaux();
           if (insertion == true) {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Enregistrement");
            alert.setContentText("L'enregistrement effectué avec succès!");
            alert.setHeaderText(null);
            alert.showAndWait();
             }
         
            txtMontant.setText("");
           
           
           
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Montant");
            alert.setContentText("Veuillez saisir uniquement des valeurs numeriques !");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
     
    }

   @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
  
    

