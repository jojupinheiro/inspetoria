package model.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.classes.Municipio;
import model.db.DB;

/**
 *
 * @author João Juliano Pinheiro
 * joaojulianopinheiro@hotmail.com
 */
public class UtilitarioDAO {
    private Connection con;
    
    public UtilitarioDAO(Connection con) {
        this.con = con;
    }
    
    public List<Municipio> getMunicipios() {
        List<Municipio> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        
        try {
            String sql = "SELECT * FROM municipio ORDER BY nome_municipio ";
            
            stmt = con.prepareStatement(sql);
            
            res = stmt.executeQuery();
            
            while (res.next()) {
                
                int idmunicipio = res.getInt("pk_id_municipio");
                String nomeMunicipio = res.getString("nome_municipio");
                String codIbge = res.getString("cod_ibge_municipio");
                Municipio municipio = new Municipio(idmunicipio, nomeMunicipio, codIbge);
                                
                list.add(municipio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    public Municipio getMunicipioPadrao() {
        Municipio municipio = null;
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT mp.fk_id_municipio, m.* FROM municipioPadrao mp "
                    + "JOIN municipio m ON (m.pk_id_municipio = mp.fk_id_municipio) ";
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            
            while (res.next()) {
                int idmunicipio = res.getInt("m.pk_id_municipio");
                String nomeMunicipio = res.getString("nome_municipio");
                String codIbge = res.getString("cod_ibge_municipio");
                municipio = new Municipio(idmunicipio, nomeMunicipio, codIbge);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return municipio;
    }
    
    public boolean editarMunicipioPadrao(Municipio municipio){
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE municipiopadrao SET fk_id_municipio = ? WHERE pk_id_municipio = 1;";
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, municipio.getId());
            
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean inserirMunicipio(Municipio municipio) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "INSERT INTO municipio (nome_municipio, cod_ibge_municipio) VALUES (?, ?)";
            //o RETURN_GENERATE_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, municipio.getNome());
            stmt.setString(2, municipio.getCodIbge());

            //executar o script
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                //deu certo
                //pegando o código gerado no INSERT
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    //getInt 1 pega o código que foi gerado e está no primeiro campo do resultSet
                    int id = rs.getInt(1);
                    result = true;
                    //depois daqui vai para o finally
                }
            } else {
                //falhou e vamos gerar uma exception para que o código 
                //caia automaticamente dentro do catch e depois no finally
                throw new SQLException("Não foi possível inserir");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean excluirMunicipio (Municipio municipio){
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "DELETE FROM municipio WHERE pk_id_municipio = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, municipio.getId());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean editarMunicipio (Municipio municipio){
        return true;
    }
    
    public List<String> getRedatores(){
        List<String> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        
        try {
            String sql = "SELECT * FROM redator ORDER BY nome_redator ";
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            
            while (res.next()) {
                String nomeRedator = res.getString("nome_redator");
                list.add(nomeRedator);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean salvarRedator(String redator){
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "INSERT INTO redator (nome_redator) VALUES (?)";
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, redator);

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
    
    public boolean excluirRedator (String redator){
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "DELETE FROM redator WHERE nome_redator = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, redator);
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
