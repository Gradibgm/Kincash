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
import modele.Article;
import modele.historiqueStock;

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
    
    private Article article;

    @FXML
    private void approvisionner(ActionEvent event) {
        String typeAppro = "Entrée";
        String quantite = txtQuantite.getText();
        if (quantite.isEmpty()) {
            ArticleController.showArlertError("Veillez saisir la quantités", "Champ vide");
            return;
        }
        Integer quantiteConverti = ArticleController.convertirStringToInt(quantite);
        if (quantiteConverti == null) {
            ArticleController.showArlertError("Veillez saisir du numérique", "Erreur");
        }
        
        historiqueStock historiqueStock = new historiqueStock(typeAppro, quantiteConverti, article);
        
    }
    
    public void setArticle(Article articleSelected){
        
        this.article = articleSelected;
        labNomArticle.setText(articleSelected.getNom());
        
        String qauntiteConvertie = String.valueOf(articleSelected.getQuantite());
        labQuantite.setText(qauntiteConvertie);
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
