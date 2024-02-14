/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modele.Client;

/**
 * FXML Controller class
 *
 * @author omombo
 */
public class ClientController implements Initializable {

    Client client;
    @FXML
    private Button btnEnregistrer;
    @FXML
    private Button btnSauvegarder;

    @FXML
    private Button btnAnnuler;
    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtTelephone;

    @FXML
    private ComboBox<String> cmbSexe;

    @FXML
    private ComboBox<String> cmbType;

    @FXML
    private TableView<Client> tabClient;

    @FXML
    private TableColumn<Client, String> colNom;

    @FXML
    private TableColumn<Client, String> colTelephone;

    @FXML
    private TableColumn<Client, String> colSexe;

    @FXML
    private TableColumn<Client, String> colType;

    @FXML
    void enregistrer(ActionEvent event) {
        String nom = txtNom.getText();
        if (nom.isEmpty()) {
           showArlertError("veuillez entrée le nom", "champs vide");
            return;
        }
        String telephone = txtTelephone.getText();
        if (telephone.isEmpty()) {
            showArlertError("veuillez entrée le numéro de téléphone", "champs vide");
            return;
        }

        String sexe = cmbSexe.getValue();
        if (sexe == null) {
            showArlertError("veuillez entrée le sexe", "champs vide");
            return;
        }

        String type = cmbType.getValue();
        if (type == null) {
            showArlertError("veuillez entrée le type", "champs vide");
            return;
        }

        Client client = new Client(nom, telephone, sexe, type);
        boolean insertion = client.insertionClient();

        if (insertion == true) {
            //Alert info
            showArlertInformation("Un client a été ajouté !", "Client");

            txtNom.setText("");
            txtTelephone.setText("");
            cmbSexe.setValue("");
            cmbType.setValue("");
            //méthode pour actualisé
            refresh();

        } else {
            //Alert erreur
            showArlertError("Une erreur s'est produite, veuillez recommencer", "Client");
        }

    }

    //Suupression du client
    @FXML
    void supprimerCLient(ActionEvent event) {
        //Récupération de la categorie selectionnée
        Client clientSelected = tabClient.getSelectionModel().getSelectedItem();
        //Verifier si la ligne selectionnée n'est pas null
        if (clientSelected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Voulez-vous vraiment supprimer le client: " + clientSelected.getNom() + " ?");
            alert.setTitle("Suppression d'un client");
            alert.setHeaderText(null);
            Optional<ButtonType> choix = alert.showAndWait();

            if (choix.get() == ButtonType.OK) {
                boolean suppression = clientSelected.suppressionClient();
                refresh();

            } else {
                alert.close();
            }

        }
    }

    //Modification du client(récupération des information depuis la tableView
    //Visibilité des buttons
    @FXML
    void modifierClient(ActionEvent event) {
        Client clientSelected = tabClient.getSelectionModel().getSelectedItem();
        if (clientSelected != null) {
            //Récuperation des information pour l'affecter au champ textField
            txtNom.setText(clientSelected.getNom());
            txtTelephone.setText(clientSelected.getTelephone());
            cmbSexe.setValue(clientSelected.getSexe());
            cmbType.setValue(clientSelected.getType());

            //On affiche le boutton de modififcation
            btnSauvegarder.setVisible(true);
            btnAnnuler.setVisible(true);
            //0n cache le button enregistrer
            btnEnregistrer.setVisible(false);
            //Nous rendons accessible la catégorie selectionnée du controller
            this.client = clientSelected;
        }
    }

    //Méthode annuler
    @FXML
    void annuler(ActionEvent event) {
        txtNom.setText("");
        txtTelephone.setText("");
        cmbSexe.setValue("");
        cmbType.setValue("");

        btnSauvegarder.setVisible(false);
        btnAnnuler.setVisible(false);
        btnEnregistrer.setVisible(true);

    }

    //Sauvegarder la modificztion
    @FXML
    void sauvegarder(ActionEvent event) {
        String nom = txtNom.getText();
        if (nom.isEmpty()) {
            showArlertError("veuillez entrée le nom", "champs vide");
            return;
        }
        String telephone = txtTelephone.getText();
        if (telephone.isEmpty()) {
            showArlertError("veuillez entrée le numéro de téléphone", "champs vide");
            return;
        }

        String sexe = cmbSexe.getValue();
        if (sexe == null) {
           showArlertError("veuillez entrée le sexe", "champs vide");
            return;
        }

        String type = cmbType.getValue();
        if (type == null) {
            showArlertError("veuillez entrée le type", "champs vide");
            return;
        }
        int idClient = client.getIdClient();
        Client client = new Client(idClient, nom, telephone, sexe, type);
        boolean clientModifier = client.modificationClient();
        if (clientModifier) {
            showArlertInformation("La modification a réussi!", "Modification");
            
            refresh();
            
            txtNom.setText("");
            txtTelephone.setText("");
            cmbSexe.setValue("");
            cmbType.setValue("");

            btnSauvegarder.setVisible(false);
            btnAnnuler.setVisible(false);
            btnEnregistrer.setVisible(true);

        } else {
            showArlertError("La modification a échouée", "Modification");
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

        ObservableList<Client> ListClient = Client.getClients();

        //Nous placons la liste des categories dans notre TableView
        tabClient.setItems(ListClient);
        //La valeur à passer dans la méthode setVellValuesFactory() 
        //doit correspondre avec le nom de la proprieté au niveau de la classe
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colSexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));

        ObservableList<String> ListSexe = FXCollections.observableArrayList();
        ListSexe.add("M");
        ListSexe.add("F");
        cmbSexe.setItems(ListSexe);

        ObservableList<String> ListType = FXCollections.observableArrayList();
        ListType.add("STANDARD");
        ListType.add("VIP");
        cmbType.setItems(ListType);
    }

    //Méthode pour actualiser notre TableView Categorie
    public void refresh() {
        ObservableList<Client> ListcaClients = Client.getClients();
        tabClient.setItems(ListcaClients);
        tabClient.refresh();
    }

}
