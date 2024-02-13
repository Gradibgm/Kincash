/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author MEGA
 */
public class Article {
    private int idArticle;
    private String nom;
    private String prix;
    private String code;
    private String quantite;
    private Categorie categorie;

    public Article() {
    }

    public Article(String nom, String prix, String code, String quantite, Categorie categorie) {
        this.nom = nom;
        this.prix = prix;
        this.code = code;
        this.quantite = quantite;
        this.categorie = categorie;
    }

    public Article(int idArticle, String nom, String prix, String code, String quantite, Categorie categorie) {
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

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQuantite() {
        return quantite;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    
}

