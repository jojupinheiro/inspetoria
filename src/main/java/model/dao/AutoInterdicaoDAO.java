package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.scene.control.Alert;
import model.classes.AutoInterdicao;
import model.classes.MotivoInfracao;
import model.classes.Municipio;
import model.classes.Produtor;
import model.classes.Programa;
import model.classes.Veterinario;
import model.db.DB;

/**
 *
 * @author joaojuliano
 */
public class AutoInterdicaoDAO {
    private Connection con;

    public AutoInterdicaoDAO(Connection con) {
        this.con = con;
    }

    public List<AutoInterdicao> getAll() {
        List<AutoInterdicao> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT ai.*, ml.*, prog.*, p.*, mp.*, v.* FROM auto_interdicao ai "
                    + "LEFT JOIN municipio ml ON (ai.fk_municipio_lavratura_ai = ml.pk_id_municipio) "
                    + "LEFT JOIN programa prog ON (ai.fk_id_programa_ai = prog.pk_id_programa) "
                    + "LEFT JOIN produtor p ON (ai.fk_produtor_ai = p.pk_id_produtor) "
                    + "LEFT JOIN municipio mp ON (p.fk_id_municipio = mp.pk_id_municipio) "
                    + "LEFT JOIN veterinario v ON (ai.fk_id_veterinario_ai = v.pk_id_veterinario) "
                    + "ORDER BY data_ai DESC;";

            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();

            while (res.next()) {

                // Veterinário
                int idVeterinario = res.getInt("v.pk_id_veterinario");
                String nomeVeterinario = res.getString("v.nome_veterinario");
                String ifVeterinario = res.getString("v.if_veterinario");
                String crmvVeterinario = res.getString("v.crmv_veterinario");
                Veterinario veterinario = new Veterinario(idVeterinario, nomeVeterinario, ifVeterinario, crmvVeterinario);

                // Produtor e seu município
                int idProdutor = res.getInt("p.pk_id_produtor");
                int idMunicipioProdutor = res.getInt("mp.pk_id_municipio");
                String nomeMunicipioProdutor = res.getString("mp.nome_municipio");
                String codIbgeMunicipioProdutor = res.getString("mp.cod_ibge_municipio");
                Municipio municipioProdutor = new Municipio(idMunicipioProdutor, nomeMunicipioProdutor, codIbgeMunicipioProdutor);
                String nomeProdutor = res.getString("p.nome_produtor");
                String cpfProdutor = res.getString("p.cpf_cnpj_produtor");
                String rgProdutor = res.getString("p.rg_produtor");
                Produtor produtor = new Produtor(idProdutor, municipioProdutor, nomeProdutor, cpfProdutor, rgProdutor);

                // Programa
                int idPrograma = res.getInt("prog.pk_id_programa");
                String nomePrograma = res.getString("prog.nome_programa");
                String siglaPrograma = res.getString("prog.sigla_programa");
                String observacoesPrograma = res.getString("prog.observacoes_programa");
                Programa programa = new Programa(idPrograma, siglaPrograma, nomePrograma, observacoesPrograma);
                
                // Auto de interdição
                int id = res.getInt("ai.pk_id_ai");
                int numeroAi = res.getInt("ai.numero_ai");
                LocalDate dataLavratura = res.getDate("data_ai").toLocalDate();
                LocalTime horaLavratura = res.getTime("hora_ai").toLocalTime();
                LocalDate dataCiencia = null;
                if (res.getDate("data_ciencia_ai") != null) {
                    dataCiencia = res.getDate("data_ciencia_ai").toLocalDate();
                }
                LocalDate dataDesinterdicao = null;
                if (res.getDate("data_desinterdicao") != null) {
                    dataDesinterdicao = res.getDate("data_desinterdicao").toLocalDate();
                }
                String nomeMunicipio = res.getString("ml.nome_municipio");
                int idMunicipio = res.getInt("ml.pk_id_municipio");
                String codIbge = res.getString("ml.cod_ibge_municipio");
                Municipio municipioLavratura = new Municipio(idMunicipio, nomeMunicipio, codIbge);
                String observacoesAI = res.getString("observacoes_ai");

                AutoInterdicao autoInterdicao = new AutoInterdicao(id, numeroAi, municipioLavratura, programa, produtor, veterinario, dataLavratura, dataCiencia, dataDesinterdicao, horaLavratura, observacoesAI);

                list.add(autoInterdicao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    

    public int getProximoNumeroAI(int idMunicipio, int ano) {
    PreparedStatement stmt = null;
    ResultSet res = null;
    
    // Inicia em 1. Se o SELECT não retornar nada (ou MAX for NULL), 
    // o primeiro número será 1.
    int proximoNumero = 1; 
    
        try {

            // 2. SQL alterada para filtrar pelo município E pelo ano atual
            String sql = "SELECT MAX(numero_ai) AS valor_maximo "
                    + "FROM auto_interdicao "
                    + "WHERE fk_municipio_lavratura_ai = ? AND YEAR(data_ai) = ?;";

            stmt = con.prepareStatement(sql);

            // 3. Definir os DOIS parâmetros
            stmt.setInt(1, idMunicipio);
            stmt.setInt(2, ano);

            res = stmt.executeQuery();

            // 4. Usar 'if' é mais adequado que 'while' para MAX(), que sempre retorna 1 linha
            if (res.next()) {
                // res.getInt() retorna 0 se o valor no banco for NULL (ex: nenhum registro encontrado)
                int valorMaximo = res.getInt("valor_maximo");
                proximoNumero = valorMaximo + 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Em caso de erro no banco, retorna 1 (ou pode-se optar por lançar a exceção)
        } finally {
            try {
                res.close();
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return proximoNumero;
    }

    public boolean inserir(AutoInterdicao ai) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "INSERT INTO auto_interdicao (fk_municipio_lavratura_ai, fk_id_programa_ai, "
                    + "fk_produtor_ai, fk_id_veterinario_ai, numero_ai, data_ai, hora_ai, data_ciencia_ai, "
                    + "data_desinterdicao, observacoes_ai) VALUES (?,?,?,?,?,?,?,?,?,?);";

            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            LocalDate dataLavratura = ai.getDataLavratura();
            LocalTime horaLavratura = ai.getHoraLavratura();

            stmt.setInt(1, ai.getMunicipio().getId());
            stmt.setInt(2, ai.getPrograma().getId());
            stmt.setInt(3, ai.getProdutor().getId());
            if (ai.getVeterinario()!= null) {
                stmt.setInt(4, ai.getVeterinario().getId());
            }
            stmt.setInt(5, ai.getNumero());
            stmt.setDate(6, Date.valueOf(dataLavratura));
            stmt.setTime(7, Time.valueOf(horaLavratura));
            if (ai.getDataCiencia() != null){
                stmt.setDate(8, Date.valueOf(ai.getDataCiencia()));
            }else{
                stmt.setNull(8, java.sql.Types.DATE);
            }
            
            if (ai.getDataDesinterdicao()!= null){
                stmt.setDate(9, Date.valueOf(ai.getDataDesinterdicao()));
            }else{
                stmt.setNull(9, java.sql.Types.DATE);
            }
            stmt.setString(10, ai.getObservacoes());

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
                    //Atualiza o ID do tutor no parâmetro que foi recebido pelo método
                    ai.setId(id);
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

    public boolean excluir(AutoInterdicao ai) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from auto_interdicao where pk_id_ai = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, ai.getId());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    

    public boolean editar(AutoInterdicao ai) {
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            String sql = "UPDATE auto_interdicao SET fk_municipio_lavratura_ai = ?, fk_id_programa_ai = ?, "
                    + "fk_produtor_ai = ?, fk_id_veterinario_ai = ?, numero_ai = ?, data_ai = ?, hora_ai = ?, "
                    + "data_ciencia_ai = ?, data_desinterdicao = ?, observacoes_ai = ? WHERE pk_id_ai = ?;";
            stmt = con.prepareStatement(sql);

            LocalDate dataLavratura = ai.getDataLavratura();
            LocalTime horaLavratura = ai.getHoraLavratura();

            stmt.setInt(1, ai.getMunicipio().getId());
            stmt.setInt(2, ai.getPrograma().getId());
            stmt.setInt(3, ai.getProdutor().getId());
            if (ai.getVeterinario()!= null) {
                stmt.setInt(4, ai.getVeterinario().getId());
            }
            stmt.setInt(5, ai.getNumero());
            stmt.setDate(6, Date.valueOf(dataLavratura));
            stmt.setTime(7, Time.valueOf(horaLavratura));
            if (ai.getDataCiencia() != null){
                stmt.setDate(8, Date.valueOf(ai.getDataCiencia()));
            }
            if (ai.getDataDesinterdicao()!= null){
                stmt.setDate(9, Date.valueOf(ai.getDataDesinterdicao()));
            }
            stmt.setString(10, ai.getObservacoes());
            stmt.setInt(11, ai.getId());

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
