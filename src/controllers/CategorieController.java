/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modele.Categorie;

/**
 * FXML Controller class
 *
 * @author MEGA
 */
public class CategorieController implements Initializable {

    private Categorie categorie;

    @FXML
    private Button btnAnnuler;

    @FXML
    private Button btnEnregistrer;

    @FXML
    private Button btnModifier;

    @FXML
    private TextField txtNom;

    @FXML
    private TextArea txtDescription;
    /*Mon tableView*/
    @FXML
    private TableView<Categorie> tabCategorie;

    @FXML
    private TableColumn<Categorie, String> colNom;

    @FXML
    private TableColumn<Categorie, String> colDescription;

    /*Mon tableView*/
    @FXML
    private void enregistrer(ActionEvent event) {
        String nom = txtNom.getText();
        if (nom.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("champs vide");
            alert.setContentText("veuillez saisir le nom");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        String description = txtDescription.getText();

        Categorie verifcation = new Categorie(nom, description);
        boolean verifier = verifcation.verificationCategorie();
        if (verifier != true) {
            Categorie categorie = new Categorie(nom, description);
            boolean insertion = categorie.insertionCategorie();
            if (insertion == true) {
                //Alert info
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Categorie");
                alert.setContentText("Une categorie a été ajoutée !");
                alert.setHeaderText(null);
                alert.showAndWait();

                txtDescription.setText("");
                txtNom.setText("");

                //méthode pour actualisé
                refresh();

            } else {
                //Alert erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Categorie");
                alert.setContentText("Une erreur s'est produite, veuillez recommencer");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        } else {
            //System.out.println("Erreur");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Categorie");
            alert.setContentText("la Categorie " + nom + " existe déjà");
            alert.setHeaderText(null);
            alert.showAndWait();
        }

    }

    @FXML
    private void supprimerCategorie() {
        //Récupération de la categorie selectionnée
        Categorie categorieSelected = tabCategorie.getSelectionModel().getSelectedItem();
        //Verifier si la ligne selectionnée n'est pas null
        if (categorieSelected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Voulez-vous vraiment supprimer la categorie: " + categorieSelected.getNom() + " ?");
            alert.setTitle("Suppression de la categorie");
            alert.setHeaderText(null);
            Optional<ButtonType> choix = alert.showAndWait();

            if (choix.get() == ButtonType.OK) {
                boolean suppression = categorieSelected.suppressionCategorie();
                refresh();

            } else {
                alert.close();
            }

        }

    }
    //Méthode pour placer les élément dans le formulaire pour unr une modification

    @FXML
    private void modfierCategorie() {
        Categorie categorieSelected = tabCategorie.getSelectionModel().getSelectedItem();
        if (categorieSelected != null) {
            //Récuperation des information pour l'affecter au champ textField
            txtNom.setText(categorieSelected.getNom());
            txtDescription.setText(categorieSelected.getDescription());

            //On affiche le boutton de modififcation
            btnModifier.setVisible(true);
            btnAnnuler.setVisible(true);
            //0n cache le button enregistrer
            btnEnregistrer.setVisible(false);
            //Nous rendons accessible la catégorie selectionnée du controller
            this.categorie = categorieSelected;
        }
    }

    //Méthode pour modifier les élément apres les avoir placer dans le formulaire
    @FXML
    private void modifier(ActionEvent event) {
        String nom = txtNom.getText();
        String description = txtDescription.getText();
        if (nom.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("champs vide");
            alert.setContentText("veuillez saisir le nom");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        //Affectation de l'id dans la variable idCategorie
        int idCategorie = categorie.getIdCategorie();

        Categorie categorieModifier = new Categorie(idCategorie, nom, description);
        //System.out.println(idCategorie);
        boolean categorieModifiers = categorieModifier.modificationCategorie();
        if (categorieModifiers) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification");
            alert.setContentText("La modification a réussi!");
            alert.setHeaderText(null);
            alert.showAndWait();
            refresh();
            txtNom.setText("");
            txtDescription.setText("");

            btnModifier.setVisible(false);
            btnAnnuler.setVisible(false);
            btnEnregistrer.setVisible(true);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modification");
            alert.setContentText("La modification a échouée!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }

    }

    @FXML
    private void annuler(ActionEvent event) {
        txtNom.setText("");
        txtDescription.setText("");

        btnModifier.setVisible(false);
        btnAnnuler.setVisible(false);
        btnEnregistrer.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<Categorie> Listcategorie = Categorie.getCategories();

        //Nous placons la liste des categories dans notre TableView
        tabCategorie.setItems(Listcategorie);
        //La valeur à passer dans la méthode setVellValuesFactory() 
        //doit correspondre avec le nom de la proprieté au niveau de la classe
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

    }

    //Méthode pour actualiser notre TableView Categorie
    public void refresh() {
        ObservableList<Categorie> ListcaCategories = Categorie.getCategories();
        tabCategorie.setItems(ListcaCategories);
        tabCategorie.refresh();
    }
}
