package model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Alert;
import telas.Statics;

/**
 *
 * @author Juliano
 */
public class DB {
    
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://192.168.1.233:3306/inspetoria";
        String user = "juliano";
        String senha = "1010XURIpug@";
        Connection con = DriverManager.getConnection(url, user, senha);
        return con;
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
