package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.classes.DeclaracaoComplementar;
import model.classes.Municipio;
import model.classes.Produtor;
import model.db.DB;

/**
 *
 * @author joaojuliano
 */
public class DeclaracaoComplementarDAO {
    private Connection con;

    public DeclaracaoComplementarDAO(Connection con) {
        this.con = con;
    }

    public List<DeclaracaoComplementar> getAll() {
        List<DeclaracaoComplementar> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT dc.*, p.*, mun.*, munProd.* FROM dc "
                    + "LEFT JOIN produtor p ON (p.pk_id_produtor = dc.fk_produtor_dc) "
                    + "LEFT JOIN municipio mun ON (dc.fk_municipio_dc = mun.pk_id_municipio) "
                    + "LEFT JOIN municipio munProd ON (p.fk_id_municipio = munProd.pk_id_municipio) "
                    + "ORDER BY numero_dc DESC";

            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();

            while (res.next()) {

                int idProdutor = res.getInt("pk_id_produtor");
                int idMunicipioProdutor = res.getInt("munProd.pk_id_municipio");
                String nomeMunicipioProdutor = res.getString("munProd.nome_municipio");
                String codIbgeMunicipioProdutor = res.getString("munProd.cod_ibge_municipio");
                Municipio municipioProdutor = new Municipio(idMunicipioProdutor, nomeMunicipioProdutor, codIbgeMunicipioProdutor);
                String nomeProdutor = res.getString("nome_produtor");
                String cpfProdutor = res.getString("cpf_cnpj_produtor");
                String rgProdutor = res.getString("rg_produtor");
                Produtor produtor = new Produtor(idProdutor, municipioProdutor, nomeProdutor, cpfProdutor, rgProdutor);

                int id = res.getInt("pk_id_dc");
                int numeroDc = res.getInt("numero_dc");
                LocalDate dataDc = res.getDate("data_dc").toLocalDate();
                String nomeMunicipio = res.getString("mun.nome_municipio");
                int idMunicipio = res.getInt("mun.pk_id_municipio");
                String codIbge = res.getString("mun.cod_ibge_municipio");
                Municipio municipioDc = new Municipio(idMunicipio, nomeMunicipio, codIbge);
                String observacoesDc = res.getString("observacoes_dc");

                DeclaracaoComplementar declaracaoComplementar = new DeclaracaoComplementar(numeroDc, id, dataDc, produtor, observacoesDc, municipioDc);

                list.add(declaracaoComplementar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int getProximoNumeroDC(int idMunicipio) {
        PreparedStatement stmt = null;
        ResultSet res = null;
        int proximoNumero = 0;
        try {
            String sql = "SELECT MAX(numero_dc) AS valor_maximo FROM dc WHERE fk_municipio_dc = ?;";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idMunicipio);
            res = stmt.executeQuery();

            while (res.next()) {
                proximoNumero = res.getInt("valor_maximo") + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return proximoNumero;
    }
    
    public boolean testarNumeroDC(int idMunicipio, int numeroDc) {
        PreparedStatement stmt = null;
        ResultSet res = null;
        boolean result = false;
        int proximoNumero = 0;
        try {
            String sql = "SELECT numero_dc FROM dc WHERE fk_municipio_dc = ? AND numero_dc = ?;";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idMunicipio);
            stmt.setInt(2, numeroDc);
            res = stmt.executeQuery();

            while (res.next()) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean inserir(DeclaracaoComplementar dc) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "INSERT INTO dc (fk_municipio_dc, fk_produtor_dc, numero_dc, data_dc, observacoes_dc) VALUES (?,?,?,?,?)";

            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            LocalDate dataDc = dc.getData();

            stmt.setInt(1, dc.getMunicipioPropriedade().getId());
            stmt.setInt(2, dc.getProdutor().getId());
            stmt.setInt(3, dc.getNumero());
            stmt.setDate(4, Date.valueOf(dataDc));
            stmt.setString(5, dc.getObservacoes());

            int rowsAffected = 0;

            try {
                rowsAffected = stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (rowsAffected > 0) {
                // Deu certo
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // getInt(1) pega o código que foi gerado e que está no primeiro campo do resultSet
                    int id = rs.getInt(1);
                    dc.setId(id);
                    result = true;
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

    public boolean excluir(DeclaracaoComplementar dc) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from dc where pk_id_dc = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, dc.getId());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    public boolean editar(DeclaracaoComplementar dc) {
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            String sql = "UPDATE dc SET fk_municipio_dc = ?, fk_produtor_dc = ?, numero_dc = ?, data_dc = ?, observacoes_dc = ? WHERE pk_id_dc = ?";
            stmt = con.prepareStatement(sql);

            //troca os parâmetros
            stmt.setInt(1, dc.getMunicipioPropriedade().getId());
            stmt.setInt(2, dc.getProdutor().getId());
            stmt.setInt(3, dc.getNumero());
            stmt.setDate(4, Date.valueOf(dc.getData()));
            stmt.setString(5, dc.getObservacoes());
            stmt.setInt(6, dc.getId());
            
            stmt.executeUpdate();

            DB.closeStatement(stmt);

            result = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

}
