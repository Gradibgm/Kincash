/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modele.Article;
import modele.historiqueStock;

/**
 * FXML Controller class
 *
 * @author omombo
 */
public class HistoriqueController implements Initializable {

    @FXML
    private TableView<Article> tabArticle;

    @FXML
    private TableColumn<Article, String> colNom;

    @FXML
    private TableColumn<Article, String> colPrix;

    @FXML
    private TableColumn<Article, String> colQuantite;

    @FXML
    private TableColumn<Article, String> colTotal;

    @FXML
    private TextField txtRecherche;

    @FXML
    private Label labNombre;

    @FXML
    private DatePicker dpDateRecherche;

    @FXML
    private TableView<historiqueStock> tabHistorique;

    @FXML
    private TableColumn<historiqueStock, String> colArticle;

    @FXML
    private TableColumn<historiqueStock, String> colType;

    @FXML
    private TableColumn<historiqueStock, String> colQuantiteModifier;

    @FXML
    private TableColumn<historiqueStock, String> colDate;

    @FXML
    void imprimer(ActionEvent event) {

    }

    @FXML
    void imprimerHistorique(ActionEvent event) {

    }

    @FXML
    void recherche(ActionEvent event) {

    }

    @FXML
    void rechercher(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Article> ListArticle = Article.recuperationArticle();
        tabArticle.setItems(ListArticle);
        colNom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        colTotal.setCellValueFactory(
                (cell) -> {
                    //Récupérer l'article de la cellule
                    Article article = cell.getValue();
                    double total = article.getPrix() * article.getQuantite();
                    String totalConverti = String.valueOf(total);
                    
                    return new SimpleStringProperty(totalConverti); 
                }
        );
        
        ObservableList<historiqueStock> listHistorique = historiqueStock.getHistorique();
        tabHistorique.setItems(listHistorique);
        colArticle.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colQuantiteModifier.setCellValueFactory(new PropertyValueFactory<>("quantiteModifie"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        
    }
    
    

}
