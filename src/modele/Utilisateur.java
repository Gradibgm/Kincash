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
public class Utilisateur {
    private int idUtilisateur;
    private String username;
    private String password;
    private String role;

    public Utilisateur() {
    }

    public Utilisateur(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Utilisateur(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Utilisateur(int idUtilisateur, String username, String password, String role) {
        this.idUtilisateur = idUtilisateur;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

   
    
    public boolean insertionUtilisateur(){
        
        
        try {
            Connection connection = Database.getConnection();
            String sql ="INSERT INTO `utilisateur`( `username`, `password`, `role`) "
                    + "VALUES(?,?,?)";
            
            PreparedStatement sqlPrepare = connection.prepareStatement(sql);
            sqlPrepare.setString(1, this.username);
            sqlPrepare.setString(2, this.password);
            sqlPrepare.setString(3, this.role);
            int nombreLigne = sqlPrepare.executeUpdate();
            return nombreLigne > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    // méthode pour la connexion d'un utilisateur à l'application
    // méthode pour vérifié si un utilisateur existe dans la bdd
    public boolean seConnecter() {
        try {
            Connection connection = Database.getConnection();
            String sql = "SELECT * FROM `utilisateur` WHERE username = ? AND password = ?";
            PreparedStatement sqlPrepare = connection.prepareStatement(sql);
            sqlPrepare.setString(1, this.username);
            sqlPrepare.setString(2, this.password);
            
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
}
