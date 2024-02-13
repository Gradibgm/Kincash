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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nom d'utilisateur");
            alert.setContentText("veuillez remplir le champs username");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;

        }
        System.out.println(username);

        String password = pwdPassword.getText();
        if (password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Mot de passe");
            alert.setContentText("veuillez remplir le champs password");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        System.out.println(password);

        Utilisateur utilisateur = new Utilisateur(username, password);
        
        boolean connexion = utilisateur.seConnecter();
        if (connexion == true) {
            
            /*
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("connexion");
            alert.setContentText("L'utilisateur existe");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;*/
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connexion");
            alert.setContentText("Vos identifiants sont incorrects, veuillez ressayer");
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
