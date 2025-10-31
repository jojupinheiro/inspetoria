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
        String usuarioWindows = "root";
        String usuarioLinux = "juliano";
        String senhaWindows = "";
        String senhaLinux = "5423gfe";
        String so = System.getProperty("os.name").toLowerCase();

        String usuarioUtilizado;
        String senhaUtilizada;
        
        if (so.contains("win")){
            usuarioUtilizado = usuarioWindows;
            senhaUtilizada = senhaWindows;
        }else{
            usuarioUtilizado = usuarioLinux;
            senhaUtilizada = senhaLinux;
        }
        
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/inspetoria",usuarioUtilizado,senhaUtilizada);
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
