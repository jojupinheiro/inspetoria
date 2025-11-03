package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.db.DB;
import model.classes.Programa;

/**
 *
 * @author João Juliano Pinheiro
 * joaojulianopinheiro@hotmail.com
 */
public class ProgramaDAO {
    private Connection con;

    public ProgramaDAO(Connection con) {
        this.con = con;
    }
    
    public List<Programa> getAll() {
        List<Programa> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        
        try {
            String sql = "SELECT * FROM programa "
                    + "ORDER BY sigla_programa;";
            
            stmt = con.prepareStatement(sql);
            
            res = stmt.executeQuery();
            
            while (res.next()) {
                
                int idPrograma = res.getInt("pk_id_programa");
                String nome = res.getString("nome_programa");
                String sigla = res.getString("sigla_programa");
                String observacoes = res.getString("observacoes_programa");

                Programa programa = new Programa(idPrograma, sigla, nome, observacoes);
                                
                list.add(programa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
        
    public boolean inserir(Programa programa) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "INSERT INTO programa (nome_programa, "
                    + "sigla_programa, observacoes_programa) VALUES (?, ?, ?);";
            
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, programa.getNome());
            stmt.setString(2, programa.getSigla());
            stmt.setString(3, programa.getObservacao());
                
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    programa.setId(id);
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
    
    public boolean excluir(Programa programa) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from programa where pk_id_programa = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, programa.getId());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean editar(Programa programa) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE programa SET nome_programa = ?, "
                    + "sigla_programa = ?, observacoes_programa = ? "
                    + "WHERE pk_id_programa = ?;";
            stmt = con.prepareStatement(sql);

            stmt.setString(1, programa.getNome());
            stmt.setString(2, programa.getSigla());
            stmt.setString(3, programa.getObservacao());
            stmt.setInt(4, programa.getId());

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
