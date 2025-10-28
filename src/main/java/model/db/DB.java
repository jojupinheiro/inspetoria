package model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Juliano
 */
public class DB {
    
    public static Connection getConnection(){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/inspetoria","root","");
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void closeStatement(Statement st){
        try {
            if (st != null){
                st.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void closeResultSet(ResultSet rs){
        try {
            if (rs != null){
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
