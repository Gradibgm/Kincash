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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modele.Utilisateur;

/**
 * FXML Controller class
 *
 * @author MEGA
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField pwdPassword;

    @FXML
    private void seConnecter(ActionEvent event) {
        String username = txtUsername.getText();
        if (username.isEmpty()) {
            showArlertError("veuillez remplir le champs username", "Nom d'utilisateur");
            return;

        }
        System.out.println(username);

        String password = pwdPassword.getText();
        if (password.isEmpty()) {
            showArlertError("veuillez remplir le champs password", "Mot de passe");
            return;
        }
        System.out.println(password);

        Utilisateur utilisateur = new Utilisateur(username, password);
        
        boolean connexion = utilisateur.seConnecter();
        if (connexion == true) {
            try {
                //1.chargement du fichier fxml
                Parent fenetrePrincipale = FXMLLoader.load(getClass().getResource("/ui/FenetrePrincipale.fxml"));
                //2. on prend le fichier charge, nous le mettons dans la scene ou on place le fichier chargé dans la scène.
                Scene scene = new Scene(fenetrePrincipale);
                //3. on creer le stage ou nous plaçons la scene dans le stage
                Stage stage = new Stage();
                stage.setScene(scene);
                //4. afficher le stage
                stage.setTitle("KinCash - Home");
                stage.setMaximized(true);
                stage.setMinHeight(600);
                stage.setMinWidth(1000);
                stage.show();
                
                //fermeture de la fenetre login
                Stage login = (Stage) txtUsername.getScene().getWindow();
                login.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } else {
            showArlertError("Vos identifiants sont incorrects, veuillez ressayer", "Connexion");
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
