/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eksamenhtxbankz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Victor B. Pedersen
 */
public class databaseConnection {
    
    
    
    //Opretter et sammenspil mellem database og kode
    public static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:Bankz.db";
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
    
    public int getCountOfCollumns(){
        
       String sql = "SELECT * FROM Udgifter;";
       
       try {
           Statement stmt = connect().createStatement();
           ResultSet rs = stmt.executeQuery(sql);
           ResultSetMetaData rsmd = rs.getMetaData();
           
           int collumnCount = rsmd.getColumnCount();
           stmt.close();
           //int numberOfRows = ;
           return collumnCount;//numberOfRows;
       }
       catch (SQLException e) {
           System.out.println(e.getMessage());
       }   
       return 0;
    }
    
    public ResultSet getUdgifter(){
        String sql = "SELECT * FROM Udgifter;";
        ResultSet rs = null;
        try {
            Statement stmt = connect().createStatement();
            rs = stmt.executeQuery(sql);
            stmt.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }   
        return rs;
    }
    
    public ResultSetMetaData getMetaRS(ResultSet rs){
        ResultSetMetaData rsmd = null;
        try {
            rsmd = rs.getMetaData();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }   
        return rsmd;
    }
    
    public ExpensesChart getUdgifterListe(ExpensesChart expenseChart){
        String sql = "SELECT * FROM Udgifter;";
        ResultSet rs = null;
        JLabel[] list = new JLabel[getCountOfCollumns()];
        try {
            Statement stmt = connect().createStatement();
            rs = stmt.executeQuery(sql);
            stmt.close();
            
            ResultSetMetaData rsmd = getMetaRS(rs);
            
            int i = 0;
            while(rs.next()){
                JLabel udgiftTemp = expenseChart.addExpense(rsmd.getColumnName(i), rs.getFloat(i));
                list[i] = udgiftTemp;
                i++;
            }
            
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }   
        return expenseChart;
    }
    
    public String getCollumnNameForI(int i){
        
        String sql = "SELECT * FROM Udgifter;";
       
        try {
           Statement stmt = connect().createStatement();
           ResultSet rs = stmt.executeQuery(sql);
           ResultSetMetaData rsmd = rs.getMetaData();
           
           String collumnName = rsmd.getColumnName(i);
           stmt.close();
           //int numberOfRows = ;
           return collumnName;//numberOfRows;
        }
        catch (SQLException e) {
           System.out.println(e.getMessage());
        }   
        
          
        return null;
    }
    
            
    public float getCollumnValueForI(int i){
        String sql = "SELECT * FROM Udgifter;";
       
        try {
           Statement stmt = connect().createStatement();
           ResultSet rs = stmt.executeQuery(sql);
           
           
           float collumnValue = rs.getFloat(i);
           stmt.close();
           //int numberOfRows = ;
           return collumnValue;//numberOfRows;
        }
        catch (SQLException e) {
           System.out.println(e.getMessage());
        }   
        
          
        return 0;
    }
}
