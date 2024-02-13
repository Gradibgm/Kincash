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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("champs vide");
            alert.setContentText("veuillez saisir le mot de passe");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        System.out.println(password);
        String role = cmbRole.getValue();
        if (role == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("champs vide");
            alert.setContentText("veuillez selection le bouton");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        System.out.println(role);

        Utilisateur utilisateur = new Utilisateur(username, password, role);
        boolean insertion = utilisateur.insertionUtilisateur();
        if (insertion == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Enregistrement");
            alert.setContentText("L'enregistrement effectué avec succès!");
            alert.setHeaderText(null);
            alert.showAndWait();

            txtUsername.setText("");
            pwdPassword.setText("");
            cmbRole.setValue("");

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Enregistrement");
            alert.setContentText("L'enregistrement a échoué, veuillez reprendre.");
            alert.setHeaderText(null);
            alert.showAndWait();
        }

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
