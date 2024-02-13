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
public class Vente {
    private int idVente;
    private double prixTotal;
    private Date dateVente;
    private Client client;
    private Utilisateur utilisateur;
    private Taux taux;

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
    
}
