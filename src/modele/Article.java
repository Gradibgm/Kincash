/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
    
    Categorie categories;
    int idCategorie = categorie.getIdCategorie();

    public Article() {
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
            sqlPrepare.setInt(5, this.idCategorie);
            int nombreLigne = sqlPrepare.executeUpdate();
            return nombreLigne > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

