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
public class Detailvente {
    private int idDetail;
    private int quantiteVendu;
    private double prixvendu;
    private Article article;
    private Vente  vente;

    public Detailvente() {
    }

    public Detailvente(int quantiteVendu, double prixvendu, Article article, Vente vente) {
        this.quantiteVendu = quantiteVendu;
        this.prixvendu = prixvendu;
        this.article = article;
        this.vente = vente;
    }

    public Detailvente(int idDetail, int quantiteVendu, double prixvendu, Article article, Vente vente) {
        this.idDetail = idDetail;
        this.quantiteVendu = quantiteVendu;
        this.prixvendu = prixvendu;
        this.article = article;
        this.vente = vente;
    }

    public int getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(int idDetail) {
        this.idDetail = idDetail;
    }

    public int getQuantiteVendu() {
        return quantiteVendu;
    }

    public void setQuantiteVendu(int quantiteVendu) {
        this.quantiteVendu = quantiteVendu;
    }

    public double getPrixvendu() {
        return prixvendu;
    }

    public void setPrixvendu(double prixvendu) {
        this.prixvendu = prixvendu;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
    }
    
}
