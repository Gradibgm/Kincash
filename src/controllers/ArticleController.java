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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Categorie> Listcategorie = Categorie.getCategories();
        cmbCategorie.setItems(Listcategorie);
    }

}
