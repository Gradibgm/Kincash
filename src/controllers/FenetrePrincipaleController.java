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
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FenetrePrincipaleController implements Initializable {

    @FXML
    private Button btnArticle;

    @FXML
    public void lancerCategorie(ActionEvent event) {
        Stage stageCategorie = lancerFenetre("/ui/Categorie.fxml", "Nouveau Categorie");
        stageCategorie.setResizable(false);
        stageCategorie.show();

    }

    @FXML
    public void lancerArticle(ActionEvent event) {

        Stage stageArticle = lancerFenetre("/ui/article.fxml", "Nouveau Article");
        stageArticle.setResizable(false);
        stageArticle.show();
        //   btnArticle.setDisable(true);

        /*  if (!btnArticle) {
            btnArticle.setDisable(true);
            
        } else {
            btnArticle.setDisable(false);
        }
         */
    }

    @FXML
    public void lancerClient(ActionEvent event) {
        Stage stageClient = lancerFenetre("/ui/Gradi", "Client");
        stageClient.setResizable(false);
        stageClient.show();

    }

    @FXML
    public void lancerTaux(ActionEvent event) {

        Stage stageTaux = lancerFenetre("/ui/Taux.fxml", "Taux du jour");
        stageTaux.setResizable(false);
        stageTaux.show();

    }

    @FXML
    public void lancerUtilise(ActionEvent event) {
        Stage stageUtilisateur = lancerFenetre("/ui/Utilisateur.fxml", "Identité de L'utilisateur");
        stageUtilisateur.setResizable(false);
        stageUtilisateur.show();

    }

    @FXML
    public void lancerVente(ActionEvent event) {

    }

    @FXML
    public void client(ActionEvent event) {
        Stage stageClient = lancerFenetre("/ui/Client.fxml", "Ajouter Client");
        stageClient.setResizable(false);
        stageClient.show();
    }

    public Stage lancerFenetre(String cheminFenetre, String titreFenetre) {
        try {
            Parent fenetre = FXMLLoader.load(getClass().getResource(cheminFenetre));
            Scene scene = new Scene(fenetre);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(titreFenetre);
            stage.show();
            return stage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
