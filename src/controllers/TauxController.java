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
               showArlertError("vous avez mis a jour le Taux", "TAUX");
               return;
           }

           
           // on place le block try....catch pour se prevenir des mauvaises conversion venant
           try {
               double montantConverti=Double.parseDouble(montant);
                         
           Taux taux = new Taux(montantConverti);
          boolean insertion = taux.insertionTaux();
           if (insertion == true) {
               showArlertInformation("L'enregistrement effectué avec succès!", "Enregistrement");
             }
            txtMontant.setText("");
           
        } catch (Exception e) {
            e.printStackTrace();
               showArlertError("Veuillez saisir uniquement des valeurs numeriques !", "Montant");
            return;
        }
     
    }
  
  //Méthode d'affiche des alertes
    private void showArlertError(String description, String titre) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(description);
        alert.showAndWait();
    }
    
    //Méthode d'affiche des alertes
    private void showArlertInformation(String description, String titre) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(description);
        alert.showAndWait();
    }

   @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
  
    

