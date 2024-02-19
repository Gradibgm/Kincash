/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modele.Article;
import modele.Categorie;

/**
 * FXML Controller class
 *
 * @author MEGA
 */
public class ArticleController implements Initializable {

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtPrix;

    @FXML
    private TextField txtQuantite;

    @FXML
    private ComboBox<Categorie> cmbCategorie;

    @FXML
    void Enregistrer(ActionEvent event) {

        String nom = txtNom.getText();
        if (nom.isEmpty()) {
            showArlertError("Veillez saisir le nom");
            return;
        }

        String code = txtCode.getText();
        if (code.isEmpty()) {
            showArlertError("Veillez saisir le code");
            return;
        }

        String prix = txtPrix.getText();
        if (prix.isEmpty()) {
            showArlertError("Veillez saisir le prix");
            return;
        }
        Double prixConverti = convertirStringToDouble(prix);
        if (prixConverti == null) {
            showArlertError("Veillez saisir que les valeurs numérique");
        }
        
        
        String quantite = txtQuantite.getText();
        if (quantite.isEmpty()) {
            showArlertError("veillez saisir entréé une quantités");
            return;
        }
        Integer quantiteConverti = convertirStringToInt(quantite);

        Categorie categorie = cmbCategorie.getValue();
        if (categorie == null) {
            showArlertError("Veillez choisir une catégorie");
            return;
        }
        
        Article article = new Article(nom, prixConverti, code, quantiteConverti, categorie);
        boolean insertion = article.insertionArticle();
        if (insertion) {
            showArlertInformation("Un article est ajoutée", "Enregistrement");
            
        }else{
            showArlertError("L'enregistrement a échouée");}
    }

    //Méthode d'affiche des alertes
    private void showArlertError(String description) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("champs vide");
        alert.setHeaderText(null);
        alert.setContentText(description);
        alert.showAndWait();
    }
    
    private void showArlertInformation(String description, String titre) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(description);
        alert.showAndWait();
    }

    //Méthode pour convertir un String en double
    private Double convertirStringToDouble(String valeur) {
        try {
            double valeurConverti = Double.parseDouble(valeur);
            return valeurConverti;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Méthode pour convertir un String en int
    private Integer convertirStringToInt(String valeur) {
        try {
            int valeurConverti = Integer.parseInt(valeur);
            return valeurConverti;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
        
        ObservableList<Categorie> Listcategorie = Categorie.getCategories();
        cmbCategorie.setItems(Listcategorie);
    }
    
    

}
