
package eksamenhtxbankz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * Til at connecte til db samt trække forskellige data
 * @author Victor B. Pedersen
 */
public class databaseConnection {
    
    
    
    /**
     * Opretter et sammenspil mellem database og kode
     * @return - connection til db
     */
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
    
    /**
     * Kode der laver og kører diverse sql statements såsom f.eks. drop table og create table
     * 
     * @param sql selve sql koden der skal køres 
     */
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
    
    
    /**
     * Metoden er til at få antallet af kolonner fra database tabellen "Udgifter"
     * @return - antal kolonner
     */
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
    
    /**
     * Metoden er til at få udgifter fra udgiftstabel som resultset
     * @return - ResultSet for udgifter
     */
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
    
    

    /**
     * Metoden er til få metadataen fra et resultset
     * @param rs resulsetSet for et Sql statement
     * @return - ResultSetMetaData
     */
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
    
    
    /**
     * Til at finde kolonne navnet i tabelen "Udgifter", ved kolonne nummeret i 
     * @param i kolonne nummer
     * @return - kolonne navn
     */
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
    
    /**
     * Til at finde kolonne værdien i tabelen "Udgifter", ved kolonne nummeret i 
     * @param i kolonne nummer
     * @return - kolonne værdi
     */        
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
