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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modele.Article;
import modele.Detailvente;

public class VenteController implements Initializable {

    @FXML
    private TextField txtRecherche;

    @FXML
    private TableView<Article> tabArticle;
    
    @FXML
    private TableView<Detailvente> tabDetailVente;

    @FXML
    private TableColumn<Article, String> colNomArticle;

    @FXML
    private TableColumn<Article, String> colPrixArticle;

    @FXML
    private TableColumn<Article, String> colQuantiteStock;

    @FXML
    private TableColumn<Detailvente, String> colDetailArticle;

    @FXML
    private TableColumn<Detailvente, String> colDetailPrixUnitaire;

    @FXML
    private TableColumn<Detailvente, String> colDetailQuantite;

    @FXML
    private TableColumn<Detailvente, String> colDetailPrixTotal;

    @FXML
    private Label labTaux;

    @FXML
    private Label labMontantTotalFC;

    @FXML
    private Label labMontantTotalUSD;

    @FXML
    private Label labMontantTotalTVA;

    @FXML
    void ajouterPanier(ActionEvent event) {

    }

    @FXML
    void reduireQuantitePanier(ActionEvent event) {

    }

    @FXML
    void retirerPanier(ActionEvent event) {

    }

    @FXML
    void terminerVente(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Récupération des articles afin de les placer dans la tableview articles 
        ObservableList<Article> listArticles = Article.recuperationArticle();
        //Nous placons la liste dans le tableview
        tabArticle.setItems(listArticles);
        //configuration de collone
        colNomArticle.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrixArticle.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colQuantiteStock.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        
    }

}
