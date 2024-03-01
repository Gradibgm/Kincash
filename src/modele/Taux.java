/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Taux {

    private int idTaux;
    private double montant;

    public Taux() {
    }

    public Taux(double montant) {
        this.montant = montant;
    }

    public Taux(int idTaux, double montant) {
        this.idTaux = idTaux;
        this.montant = montant;
    }

    public int getIdTaux() {
        return idTaux;
    }

    public void setIdTaux(int idTaux) {
        this.idTaux = idTaux;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public boolean insertionTaux() {
        try {
            String sql = "INSERT INTO `taux`( `montant`) "
                    + "VALUES(?)";

            Connection connection = Database.getConnection();
            PreparedStatement sqlPrepare = connection.prepareStatement(sql);
            sqlPrepare.setDouble(1, this.montant);
            int nombreLigne = sqlPrepare.executeUpdate();

            return nombreLigne > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public static Taux recuperationTauxActuel() {
        try {
            Connection connection = Database.getConnection();
            String sql = "SELECT * FROM taux ORDER BY idTaux DESC LIMIT 1";
            PreparedStatement sqlPrepare = connection.prepareStatement(sql);
            
            ResultSet resultat = sqlPrepare.executeQuery();
            
            if (resultat.next()) {
                int idTaux = resultat.getInt("idTaux");
                double tauxActuel = resultat.getDouble("montant");
                return new Taux(idTaux, tauxActuel);
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}