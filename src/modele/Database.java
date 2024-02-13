/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author MEGA
 */
public class Database {
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null) {

                String connectionString = "jdbc:mysql://127.0.0.1:3306/kinchas_db";

                Class.forName("com.mysql.jdbc.Driver");

                connection = DriverManager.getConnection(connectionString, "root", "");

                System.out.println("Connexion reussie");

            }
            return connection;
        } catch (Exception e) {

            e.printStackTrace();

        }
        return null;
    }
    
}
