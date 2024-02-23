/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author MEGA
 */
public class historiqueStock {
    
    Categorie categorie;
    

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

    public boolean approvisionner() {

        try {

            Connection connection = Database.getConnection();

            //Début de la tansaction
            //On désactive l'auto Enregistrement
            connection.setAutoCommit(false);

            String Sql = "INSERT INTO `historique_stock`(`type`, `quantiteModifie`, `idArticle`) "
                    + "VALUES (?,?,?)";
            PreparedStatement sqlPrepare = connection.prepareStatement(Sql);
            sqlPrepare.setString(1, this.type);
            sqlPrepare.setInt(2, this.quantiteModifie);
            sqlPrepare.setInt(3, this.article.getIdArticle());

            System.out.println(article.getIdArticle());

            int nombreLigne = sqlPrepare.executeUpdate();

            if (nombreLigne > 0) {

                String sqlModification = "UPDATE article"
                        + " SET quantite = quantite + ? "
                        + "WHERE idArticle  = ?";
                PreparedStatement sqlPrepereModifiecation = connection.prepareStatement(sqlModification);
                sqlPrepereModifiecation.setInt(1, this.quantiteModifie);
                sqlPrepereModifiecation.setInt(2, this.article.getIdArticle());

                int nombreLigneModifier = sqlPrepereModifiecation.executeUpdate();

                //Validation de la transaction
                connection.commit();

                //On réactive l'auto enregistrement
                connection.setAutoCommit(true);

                return nombreLigneModifier > 0;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static ObservableList<historiqueStock> getHistorique() {
        try {
            ObservableList<historiqueStock> listHistorique = FXCollections.observableArrayList();
            Connection connection = Database.getConnection();

            String sql = "SELECT article.*,"
                    + " historique_stock.*,"
                    + " historique_stock.quantiteModifie,"
                    + " historique_stock.date\n"
                    + "FROM article\n"
                    + "INNER JOIN historique_stock\n"
                    + "ON article.idArticle = historique_stock.idArticle\n"
                    + "ORDER BY historique_stock.date DESC";
            
            PreparedStatement sqlPrepare = connection.prepareStatement(sql);
            ResultSet resultat = sqlPrepare.executeQuery();
            
            while (resultat.next()) {                
                int IdArticle = resultat.getInt("idArticle");
                String nom = resultat.getString("Nom");
                String type = resultat.getString("type");
                int quantite = resultat.getInt("quantiteModifie");
                Date datehistorique = resultat.getDate("date");
                
                Article article = new Article(IdArticle, nom);
                listHistorique.add(new historiqueStock(type, datehistorique, quantite, article));
            }
            return listHistorique;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
