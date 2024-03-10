/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modele.Article;

public class FenetrePrincipaleController implements Initializable {

    Article artilce;
    @FXML
    private Button btnArticle;

    @FXML
    private TableView<Article> tabArticle;

    @FXML
    private TableColumn<Article, String> colArticle;

    @FXML
    private TextField txtRechercheArticle;

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
    void lancerHistorique(ActionEvent event) {
        Stage stageHistorique = lancerFenetre("/ui/Historique.fxml", "Historique");
        stageHistorique.setResizable(false);
        stageHistorique.show();

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
        Stage stageVente = lancerFenetre("/ui/Vente.fxml", "Vente");
        stageVente.setResizable(false);
        stageVente.show();
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

    @FXML
    private void supprimerArticle() {
    }

    @FXML
    private void modifierArticle() {

    }

    @FXML
    private void approvisionnerArticle() {
        Article articleSelected = tabArticle.getSelectionModel().getSelectedItem();
        if (articleSelected != null) {
            try {
                //Chargement du fichier fxml
                FXMLLoader appro = new FXMLLoader(getClass().getResource("/ui/Appro.fxml"));
                Parent roo = appro.load();

                //Nous récupérons le controlleur Appro afin d'envoyer l'objet article
                ApproController approcontroller = appro.getController();

                //on passe l'objet articleSelected au controlleur de la fentre d'appro
                approcontroller.setArticle(articleSelected);

                Scene scene = new Scene(roo);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("Approvisionnememnt");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Article> listArticle = Article.recuperationArticle();
        tabArticle.setItems(listArticle);
        colArticle.setCellValueFactory(new PropertyValueFactory<>("Nom"));

        FilteredList<Article> listArticleFiltre = new FilteredList<>(listArticle, list -> true);

        txtRechercheArticle.textProperty().addListener((Observable, oldValue, newValue) -> {
            listArticleFiltre.setPredicate((article) -> {
                
                if (newValue.isEmpty()) {
                    return true;
                }
                
                String rechercheEnMiniscule = newValue.toLowerCase();
                
                String nomArticleMiniscule = article.getNom().toLowerCase();
                String codeArticleMiniscule = article.getCode().toLowerCase();
                
                if (nomArticleMiniscule.contains(rechercheEnMiniscule)) {
                    return true;
                }else if (codeArticleMiniscule.contains(rechercheEnMiniscule)) {
                    return true;
                }
                
                return false;
                
            });

        });
        
        SortedList<Article> ListeArticleTriee = new SortedList<>(listArticleFiltre);
        
        ListeArticleTriee.comparatorProperty().bind(tabArticle.comparatorProperty());
        
        tabArticle.setItems(ListeArticleTriee);
    }

    public void refresh() {
        ObservableList<Article> listArticles = Article.recuperationArticle();
        tabArticle.setItems(listArticles);
        tabArticle.refresh();
    }

}
