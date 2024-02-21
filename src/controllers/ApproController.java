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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author omombo
 */
public class ApproController implements Initializable {

    @FXML
    private Label labNomArticle;

    @FXML
    private Label labQuantite;

    @FXML
    private TextField txtQuantite;

    @FXML
    private void approvisionner(ActionEvent event) {
        String quantite = txtQuantite.getText();
        if (quantite.isEmpty()) {
            ArticleController.showArlertError("Veillez saisir la quantités", "Champ vide");
            return;
        }
        Integer quantiteConverti = ArticleController.convertirStringToInt(quantite);
        if (quantiteConverti == null) {
            ArticleController.showArlertError("Veillez saisir du numérique", "Erreur");
        }
        
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
