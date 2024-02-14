/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modele.Utilisateur;

/**
 * FXML Controller class
 *
 * @author MEGA
 */
public class UtilisateurController implements Initializable {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField pwdPassword;

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    private void enregistrer(ActionEvent event) {
        String username = txtUsername.getText();
        if (username.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("champs vide");
            alert.setContentText("veuillez saisir le nom de L'utilsateur");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;

        }
        System.out.println(username);

        String password = pwdPassword.getText();
        if (password.isEmpty()) {
            showArlertError("veuillez saisir le mot de passe", "champs vide");
            return;
        }
        System.out.println(password);
        String role = cmbRole.getValue();
        if (role == null) {
            showArlertError("veuillez selection le bouton", "champs vide");
            return;
        }
        System.out.println(role);

        Utilisateur utilisateur = new Utilisateur(username, password, role);
        boolean insertion = utilisateur.insertionUtilisateur();
        if (insertion == true) {
            showArlertInformation("L'enregistrement effectué avec succès!", "Enregistrement");

            txtUsername.setText("");
            pwdPassword.setText("");
            cmbRole.setValue("");

        } else {
            showArlertError("L'enregistrement a échoué, veuillez reprendre.", "Enregistrement");
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
        ObservableList<String> listRole = FXCollections.observableArrayList();
        listRole.add("admin");
        listRole.add("administrateur");
        listRole.add("gerant");
        cmbRole.setItems(listRole);

    }

}
