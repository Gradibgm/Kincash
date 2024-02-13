/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author omombo
 */
public class Client {

    private int idClient;
    private String nom;
    private String telephone;
    private String sexe;
    private String type;

    public Client() {
    }

    public Client(String nom, String telephone, String sexe, String type) {
        this.nom = nom;
        this.telephone = telephone;
        this.sexe = sexe;
        this.type = type;
    }

    public Client(int idClient, String nom, String telephone, String sexe, String type) {
        this.idClient = idClient;
        this.nom = nom;
        this.telephone = telephone;
        this.sexe = sexe;
        this.type = type;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean insertionClient() {
        try {
            // 1. Recupération de l'objet connection
            Connection connection = Database.getConnection();
            // création de la requette
            String sql = "INSERT INTO client(noms, telephone, sexe, type) "
                    + " VALUES (?,?,?,?)";
            //3. Préparation de la requette sql

            PreparedStatement sqlPrepare = connection.prepareStatement(sql);

            //3.1 Remplaement des valeurs pour chaque point d'interrogation
            sqlPrepare.setString(1, this.nom);
            sqlPrepare.setString(2, this.telephone);
            sqlPrepare.setString(3, this.sexe);
            sqlPrepare.setString(4, this.type);

            //4. Execution de la requete 
            int nombreLigne = sqlPrepare.executeUpdate();
            return nombreLigne > 0;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    public static ObservableList<Client> getClients() {

        try {
            //création de la liste Observable des categories
            ObservableList<Client> ListClient = FXCollections.observableArrayList();
            Connection connection = Database.getConnection();
            String sql = "SELECT * FROM client ORDER BY noms ASC";
            PreparedStatement sqlPrepare = connection.prepareStatement(sql);
            ResultSet resultat = sqlPrepare.executeQuery();

            while (resultat.next()) {
                int idCategorie = resultat.getInt("idClient");
                String nom = resultat.getString("noms");
                String telephone = resultat.getString("telephone");
                String sexe = resultat.getString("sexe");
                String type = resultat.getString("type");

                ListClient.add(new Client(idCategorie, nom, telephone, sexe, type));
            }
            return ListClient;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    
    public boolean suppressionClient() {
        try {
            Connection connection = Database.getConnection();
            String sql = "DELETE FROM client WHERE idClient=?";
            PreparedStatement sqlPrepare = connection.prepareStatement(sql);
            sqlPrepare.setInt(1, this.idClient);
            int nombreLigne = sqlPrepare.executeUpdate();
            return nombreLigne > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     public boolean modificationClient() {
        try {
            Connection connection = Database.getConnection();
            String sql ="UPDATE client SET noms = ?, telephone = ?, sexe = ?, type = ? WHERE idClient = ?";
            PreparedStatement sqlPrepare = connection.prepareStatement(sql);
            sqlPrepare.setString(1, this.nom);
            sqlPrepare.setString(2, this.telephone);
            sqlPrepare.setString(3, this.sexe);
             sqlPrepare.setString(4, this.type);
              sqlPrepare.setInt(5, this.idClient);
            int nombreLigne = sqlPrepare.executeUpdate();
            return nombreLigne > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
