package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.classes.Veterinario;
import model.db.DB;

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
    
    public boolean inserir(Veterinario veterinario){
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "INSERT INTO veterinario (nome_veterinario, if_veterinario, crmv_veterinario) VALUES (?, ?, ?)";
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, veterinario.getNome());
            stmt.setString(2, veterinario.getIdentidadeFuncional());
            stmt.setString(3, veterinario.getCrmv());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    result = true;
                }
            } else {
                throw new SQLException("Não foi possível inserir");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean editar(Veterinario veterinario){
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE veterinario SET nome_veterinario = ?, if_veterinario = ?, crmv_veterinario = ? WHERE pk_id_veterinario = ?";
            stmt = con.prepareStatement(sql);
            //troca os parâmetros
            stmt.setString(1, veterinario.getNome());
            stmt.setString(2, veterinario.getIdentidadeFuncional());
            stmt.setString(3, veterinario.getCrmv());
            stmt.setInt(3, veterinario.getId());

            //executa
            stmt.executeUpdate();

            result = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean excluir(Veterinario veterinario) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from veterinario where pk_id_veterinario = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, veterinario.getId());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
}
