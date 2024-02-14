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
 * @author MEGA
 */
public class Categorie {

    private int idCategorie;
    private String nom;
    private String description;

    public Categorie() {
    }

    public Categorie(int idCategorie, String nom, String description) {
        this.idCategorie = idCategorie;
        this.nom = nom;
        this.description = description;
    }

    public Categorie(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean insertionCategorie() {
        try {
            Connection connection = Database.getConnection();
            String sql = "INSERT INTO `categorie`( `nom`, `description`) "
                    + "VALUES(?,?)";

            PreparedStatement sqlPrepare = connection.prepareStatement(sql);
            sqlPrepare.setString(1, this.nom);
            sqlPrepare.setString(2, this.description);
            int nombreLigne = sqlPrepare.executeUpdate();
            return nombreLigne > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ObservableList<Categorie> getCategories() {

        try {
            //création de la liste Observable des categories
            ObservableList<Categorie> ListCategorie = FXCollections.observableArrayList();
            Connection connection = Database.getConnection();
            String sql = "SELECT * FROM categorie ORDER BY nom ASC";
            PreparedStatement sqlPrepare = connection.prepareStatement(sql);
            ResultSet resultat = sqlPrepare.executeQuery();

            while (resultat.next()) {
                int idCategorie = resultat.getInt("idCategorie");
                String nom = resultat.getString("nom");
                String description = resultat.getString("description");

                ListCategorie.add(new Categorie(idCategorie, nom, description));
            }
            return ListCategorie;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean suppressionCategorie() {
        try {
            Connection connection = Database.getConnection();
            String sql = "DELETE FROM categorie WHERE idCategorie=?";
            PreparedStatement sqlPrepare = connection.prepareStatement(sql);
            sqlPrepare.setInt(1, this.idCategorie);
            int nombreLigne = sqlPrepare.executeUpdate();
            return nombreLigne > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modificationCategorie() {
        try {
            Connection connection = Database.getConnection();
            String sql = "UPDATE categorie SET nom = ?, description = ? WHERE idCategorie = ?";
            PreparedStatement sqlPrepare = connection.prepareStatement(sql);
            sqlPrepare.setString(1, this.nom);
            sqlPrepare.setString(2, this.description);
            sqlPrepare.setInt(3, this.idCategorie);
            int nombreLigne = sqlPrepare.executeUpdate();
            return nombreLigne > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verificationCategorie() {
        try {
            Connection connection = Database.getConnection();
            String sql = "SELECT * FROM categorie WHERE nom = ? ";
            PreparedStatement sqlPrepare = connection.prepareStatement(sql);
            sqlPrepare.setString(1, this.nom);
            ResultSet resultat = sqlPrepare.executeQuery();
            if (resultat.next()){
                return true;
            }else{
                return false;
            }
            // return resultat.next(); le moyen le plus court pour la condition 
            
        } catch (Exception e) {
            e.printStackTrace();

        }

        return false;

    }

    //Méthode pour renommer l'affiche de l'objet Catégorie parr le nom de la categorie
    @Override
    public String toString() {
        return this.nom;
    }
}
