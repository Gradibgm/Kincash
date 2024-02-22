/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Date;

/**
 *
 * @author MEGA
 */
public class historiqueStock {
    private int idStock;
    private String type;
    private Date date;
    private int quantiteModifie;
    private Article article;

    public historiqueStock() {
    }

    public historiqueStock(String type, Date date, int quantiteModifie, Article article) {
        this.type = type;
        this.date = date;
        this.quantiteModifie = quantiteModifie;
        this.article = article;
    }

    public historiqueStock(int idStock, String type, Date date, int quantiteModifie, Article article) {
        this.idStock = idStock;
        this.type = type;
        this.date = date;
        this.quantiteModifie = quantiteModifie;
        this.article = article;
    }

    public historiqueStock(String type, int quantiteModifie, Article article) {
        this.type = type;
        this.quantiteModifie = quantiteModifie;
        this.article = article;
    }
    
    

    public int getIdStock() {
        return idStock;
    }

    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuantiteModifie() {
        return quantiteModifie;
    }

    public void setQuantiteModifie(int quantiteModifie) {
        this.quantiteModifie = quantiteModifie;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    
}
