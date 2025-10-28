package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.classes.MotivoInfracao;
import model.db.DB;

/**
 *
 * @author João Juliano Pinheiro
 * joaojulianopinheiro@hotmail.com
 */
public class MotivoInfracaoDAO {
    private Connection con;

    public MotivoInfracaoDAO(Connection con) {
        this.con = con;
    }
    
    public List<MotivoInfracao> getAll() {
        List<MotivoInfracao> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        
        try {
            String sql = "SELECT pk_id_motivo_ai, resumo_descricao_motivo_ai, multa_inicial_motivo_ai, adicional_animal_motivo_ai, preve_adv_motivo_ai FROM motivo_ai "
                    + "ORDER BY resumo_descricao_motivo_ai ";
            
            stmt = con.prepareStatement(sql);
            
            res = stmt.executeQuery();
            
            while (res.next()) {
                
                int idMotivo = res.getInt("pk_id_motivo_ai");
                String resumoDescricao = res.getString("resumo_descricao_motivo_ai");
                float multaInicial = res.getFloat("multa_inicial_motivo_ai");
                float adicionalAnimal = res.getFloat("adicional_animal_motivo_ai");
                boolean preveAdv = res.getBoolean("preve_adv_motivo_ai");
                MotivoInfracao motivoInfracao = new MotivoInfracao(idMotivo, multaInicial, adicionalAnimal, resumoDescricao, preveAdv);
                                
                list.add(motivoInfracao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    public boolean inserir(MotivoInfracao motivoInfracao) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "INSERT INTO motivo_ai (preve_adv_motivo_ai, lei_motivo_ai, art_lei_motivo_ai, "
                    + "decreto_motivo_ai, art_decreto_motivo_ai, penalidade_decreto_motivo_ai, "
                    + "art_penalidade_decreto_motivo_ai, multa_inicial_motivo_ai, adicional_animal_motivo_ai, "
                    + "descricao_motivo_ai, resumo_descricao_motivo_ai) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
            
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setBoolean(1, motivoInfracao.isPreveAdv() );
            stmt.setString(2, motivoInfracao.getLei() );
            stmt.setString(3, motivoInfracao.getArtLei() );
            stmt.setString(4, motivoInfracao.getDecreto() );
            stmt.setString(5, motivoInfracao.getArtDecreto() );
            stmt.setString(6, motivoInfracao.getPenalidade() );
            stmt.setString(7, motivoInfracao.getArtPenalidade() );
            stmt.setFloat(8, motivoInfracao.getMultaInicial() );
            stmt.setFloat(9, motivoInfracao.getAdicionalAnimal() );
            stmt.setString(10, motivoInfracao.getDescricao() );
            stmt.setString(11, motivoInfracao.getResumoDescricao() );
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Deu certo
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // getInt(1) pega o código que foi gerado e que está no primeiro campo do resultSet
                    int id = rs.getInt(1);
                    //Atualiza o ID do tutor no parâmetro que foi recebido pelo método
                    motivoInfracao.setId(id);
                    result = true;
                    //Depois daqui vai para o finally
                }
            } else {
                //falhou e vamos gerar uma exception para que o código caia automaticamente dentro do catch e depois no finally
                throw new SQLException("Não foi possível inserir");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean excluir(MotivoInfracao motivoInfracao) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from motivo_ai where pk_id_motivo_ai = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, motivoInfracao.getId());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean editar(MotivoInfracao motivoInfracao) {
        return true;
    }
}
