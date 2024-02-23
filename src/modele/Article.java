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
import javafx.fxml.FXML;

/**
 *
 * @author MEGA
 */
public class Article {

    private int idArticle;
    private String nom;
    private double prix;
    private String code;
    private int quantite;
    private Categorie categorie;

    public Article() {
    }

    public Article(int idArticle, String nom) {
        this.idArticle = idArticle;
        this.nom = nom;
    }

    
    
    

    public Article(String nom, double prix, String code, int quantite, Categorie categorie) {
        this.nom = nom;
        this.prix = prix;
        this.code = code;
        this.quantite = quantite;
        this.categorie = categorie;
        
    }

    public Article(int idArticle, String nom, double prix, String code, int quantite, Categorie categorie) {
        this.idArticle = idArticle;
        this.nom = nom;
        this.prix = prix;
        this.code = code;
        this.quantite = quantite;
        this.categorie = categorie;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public boolean verificationArticle() {
        try {
            Connection connection = Database.getConnection();
            String sql = "SELECT * FROM article WHERE nom = ? ";
            PreparedStatement sqlPrepare = connection.prepareStatement(sql);
            sqlPrepare.setString(1, this.nom);
            ResultSet resultat = sqlPrepare.executeQuery();
            if (resultat.next()) {
                return true;
            } else {
                return false;
            }
            // return resultat.next(); le moyen le plus court pour la condition 

        } catch (Exception e) {
            e.printStackTrace();

        }

        return false;

    }

    public boolean insertionArticle() {
        try {
            Connection connection = Database.getConnection();
            String sql = "INSERT INTO article ( nom, prix, code, quantite, idCategorie) "
                    + "VALUES(?,?,?,?,?)";

            PreparedStatement sqlPrepare = connection.prepareStatement(sql);
            sqlPrepare.setString(1, this.nom);
            sqlPrepare.setDouble(2, this.prix);
            sqlPrepare.setString(3, this.code);
            sqlPrepare.setInt(4, this.quantite);
            sqlPrepare.setInt(5, this.categorie.getIdCategorie());
            int nombreLigne = sqlPrepare.executeUpdate();
            return nombreLigne > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean suppressionArticle() {
        try {
            Connection connection = Database.getConnection();
            String sql = "DELETE FROM article WHERE idArticle = ?";
            PreparedStatement sqlPrepare = connection.prepareStatement(sql);
            sqlPrepare.setInt(1, this.idArticle);

            int nombreLigne = sqlPrepare.executeUpdate();
            return nombreLigne > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modificationArticle() {
        try {
            Connection connection = Database.getConnection();
            String sql = "UPDATE article SET"
                    + " nom = ?,"
                    + " prix = ?,"
                    + " code = ?,"
                    + " quantite = ?,"
                    + " idCategorie = ?"
                    + " WHERE idArticle = ?";
            PreparedStatement sqlPrepare = connection.prepareStatement(sql);
            sqlPrepare.setString(1, this.nom);
            sqlPrepare.setDouble(2, this.prix);
            sqlPrepare.setString(3, this.code);
            sqlPrepare.setInt(4, this.quantite);
            sqlPrepare.setInt(5, categorie.getIdCategorie());
            sqlPrepare.setInt(6, this.idArticle);

            int nombreLigne = sqlPrepare.executeUpdate();
            return nombreLigne > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ObservableList<Article> recuperationArticle() {
        try {
            // Création de la liste observable des Article qui sera retourner a la fin de l'execution 
            ObservableList<Article> listArticle = FXCollections.observableArrayList();

            // 1. Recupération de l'objet connection
            Connection connection = Database.getConnection();
            String sql = "SELECT article.*,\n"
                    + "categorie.idCategorie,\n"
                    + "categorie.nom AS \"nomCategorie\",\n"
                    + "categorie.description\n"
                    + "FROM article\n"
                    + "INNER JOIN categorie\n"
                    + "ON categorie.idCategorie = article.idCategorie";

            PreparedStatement sqlPrepare = connection.prepareStatement(sql);

            ResultSet resultat = sqlPrepare.executeQuery();
            while (resultat.next()) {

                int idArticle = resultat.getInt("idArticle");
                String nom = resultat.getString("nom");
                String code = resultat.getString("code");
                String description = resultat.getString("description");
                String nomCategorie = resultat.getString("nomCategorie");
                int idCategorie = resultat.getInt("idCategorie");
                double prix = resultat.getDouble("prix");
                int quantite = resultat.getInt("quantite");

                Categorie categorie = new Categorie(idCategorie, nomCategorie, description);

                listArticle.add(new Article(idArticle, nom, prix, code, quantite, categorie));
            }

            return listArticle;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }
    
    
}
