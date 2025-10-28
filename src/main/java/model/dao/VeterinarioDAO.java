package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.classes.Veterinario;

/**
 *
 * @author João Juliano Pinheiro
 * joaojulianopinheiro@hotmail.com
 */
public class VeterinarioDAO {
    private Connection con;

    public VeterinarioDAO(Connection con) {
        this.con = con;
    }
    
    public List<Veterinario> getAll() {
        List<Veterinario> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        
        try {
            String sql = "SELECT * FROM veterinario ORDER BY nome_veterinario ";
            
            stmt = con.prepareStatement(sql);
            
            res = stmt.executeQuery();
            
            while (res.next()) {
                
                int idVeterinario = res.getInt("pk_id_veterinario");
                String nomeVeterinario = res.getString("nome_veterinario");
                String ifVeterinario = res.getString("if_veterinario");
                String crmvVeterinario = res.getString("crmv_veterinario");
                
                Veterinario veterinario = new Veterinario(idVeterinario, nomeVeterinario, ifVeterinario, crmvVeterinario);
                                
                list.add(veterinario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
}
