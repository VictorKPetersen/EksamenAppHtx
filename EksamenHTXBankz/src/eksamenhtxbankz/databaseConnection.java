/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eksamenhtxbankz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Victor B. Pedersen
 */
public class databaseConnection {
    
    
    
    //Opretter et sammenspil mellem database og kode
    public static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:";
            conn = DriverManager.getConnection(url);
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
        return conn;
    }
    //Kode der kører diverse sql statements såsom drop table og create table
    public void createSQLStatement(String sql){
        try {
            Statement stmt = connect().createStatement(); //Connecter  til DB og laver et statement
            stmt.execute(sql); //Executer statetment
            stmt.close(); //Lukker connection
        } 
        catch (SQLException e) { //sender fejlkode i tilfælde af fejl
            System.out.println(e.getMessage());
        }
    }
    
    
}
