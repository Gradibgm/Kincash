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
import javafx.collections.ObservableList;

/**
 *
 * @author MEGA
 */
public class Vente {

    private int idVente;
    private double prixTotal;
    private Date dateVente;
    private Client client;
    private Utilisateur utilisateur;
    private Taux taux;
    private Detailvente detailVente;
    private Article article;
    private historiqueStock historique;

    public Vente() {
    }

    public Vente(double prixTotal, Date dateVente, Client client, Utilisateur utilisateur, Taux taux) {
        this.prixTotal = prixTotal;
        this.dateVente = dateVente;
        this.client = client;
        this.utilisateur = utilisateur;
        this.taux = taux;
    }

    public Vente(int idVente, double prixTotal, Date dateVente, Client client, Utilisateur utilisateur, Taux taux) {
        this.idVente = idVente;
        this.prixTotal = prixTotal;
        this.dateVente = dateVente;
        this.client = client;
        this.utilisateur = utilisateur;
        this.taux = taux;
    }

    public Vente(double prixTotal, Utilisateur utilisateur, Taux taux) {
        this.prixTotal = prixTotal;
        this.utilisateur = utilisateur;
        this.taux = taux;
    }

    public int getIdVente() {
        return idVente;
    }

    public void setIdVente(int idVente) {
        this.idVente = idVente;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Date getDateVente() {
        return dateVente;
    }

    public void setDateVente(Date dateVente) {
        this.dateVente = dateVente;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Taux getTaux() {
        return taux;
    }

    public void setTaux(Taux taux) {
        this.taux = taux;
    }

    public boolean insertionvente(ObservableList<Detailvente> listCommande) {
        try {
            Connection connection = Database.getConnection();
            //Début de la transaction
            connection.setAutoCommit(false);

            String sql = "INSERT INTO vente(prixTotal, idUtilisateur, idTaux) VALUES (?,?,?)";
            String sqlDetailVente = "INSERT INTO `detail_vente`(`quantiteVendu`, `prixVendu`, `idArticle`, `idVente`) VALUES(?,?,?,?)";
            String sqlHistorique = "INSERT INTO `historique_stock`(`type`, `quantiteModifie`, `idArticle`) VALUES(?,?,?)";
            String sqlArticle = "UPDATE `article` SET `quantite`= quantite - ? WHERE idArticle = ?";

            PreparedStatement sqlPrepare = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement sqlDetailPrepare = connection.prepareStatement(sqlDetailVente);
            PreparedStatement sqlHistoriquePrepare = connection.prepareStatement(sqlHistorique);
            PreparedStatement sqlUpdateArticle = connection.prepareStatement(sqlArticle);

            sqlPrepare.setDouble(1, this.prixTotal);
            sqlPrepare.setInt(2, utilisateur.getIdUtilisateur());
            sqlPrepare.setInt(3, this.taux.getIdTaux());

            int nombreLigne = sqlPrepare.executeUpdate();

            //On récupere la clé générée
            ResultSet resultat = sqlPrepare.getGeneratedKeys();

            if (resultat.next()) {

                //Nous récupérons l'id de la vente
                int idVenteInsere = resultat.getInt(1);
                for (Detailvente commande : listCommande) {
                   // sqlDetailPrepare.executeUpdate();

                    sqlDetailPrepare.setInt(1, commande.getQuantiteVendu());
                    sqlDetailPrepare.setDouble(2, commande.getPrixvendu());
                    sqlDetailPrepare.setInt(3, commande.getArticle().getIdArticle());
                    sqlDetailPrepare.setInt(4, idVenteInsere);

                    //Insertion de l'historique
                    sqlHistoriquePrepare.setString(1, "Sortie");
                    sqlHistoriquePrepare.setInt(2, commande.getQuantiteVendu());
                    sqlHistoriquePrepare.setInt(3, commande.getArticle().getIdArticle());

                    //Mis à jour du Stock de l'article
                    sqlUpdateArticle.setInt(1, commande.getQuantiteVendu());
                    sqlUpdateArticle.setInt(2, commande.getArticle().getIdArticle());

                    sqlDetailPrepare.executeUpdate();
                    sqlHistoriquePrepare.executeUpdate();
                    sqlUpdateArticle.executeUpdate();

                }

                // Enregistrer la transaction
                connection.commit();
                connection.setAutoCommit(true);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }
}
