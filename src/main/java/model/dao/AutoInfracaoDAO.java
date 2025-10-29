package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.scene.control.Alert;
import model.classes.AutoInfracao;
import model.classes.MotivoInfracao;
import model.classes.Municipio;
import model.classes.Produtor;
import model.classes.Veterinario;
import model.db.DB;

/**
 *
 * @author Juliano
 */
public class AutoInfracaoDAO {

    private Connection con;

    public AutoInfracaoDAO(Connection con) {
        this.con = con;
    }

    public List<AutoInfracao> getAll(int filtroSelecionado, String txtFiltro) {
        List<AutoInfracao> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT ai.*,mun.pk_id_municipio, mun.cod_ibge_municipio, mun.nome_municipio, munProd.pk_id_municipio, munProd.cod_ibge_municipio, munProd.nome_municipio, mot.pk_id_motivo_ai, mot.resumo_descricao_motivo_ai, "
                    + "mot.multa_inicial_motivo_ai, mot.adicional_animal_motivo_ai, mot.preve_adv_motivo_ai, p.pk_id_produtor, p.nome_produtor, p.cpf_cnpj_produtor, p.rg_produtor, p.fk_id_municipio, "
                    + "v.* FROM ai "
                    + "LEFT JOIN produtor p ON (p.pk_id_produtor = ai.fk_produtor_ai) "
                    + "LEFT JOIN veterinario v ON (v.pk_id_veterinario = ai.fk_id_veterinario_ai) "
                    + "LEFT JOIN municipio mun ON (ai.fk_municipio_lavratura_ai = mun.pk_id_municipio) "
                    + "LEFT JOIN municipio munProd ON (p.fk_id_municipio = munProd.pk_id_municipio) "
                    + "LEFT JOIN motivo_ai mot ON (ai.fk_id_motivo_ai = mot.pk_id_motivo_ai) ";

            String filtroSql = "";

            switch (filtroSelecionado) {
                case -1:
                    filtroSql = "";
                    break;
                case 0:
                    filtroSql = "WHERE p.nome_produtor LIKE ? ";
                    break;
                case 1:
                    filtroSql = "WHERE p.cpf_cnpj_produtor LIKE ? ";
                    break;
                case 2:
                    filtroSql = "WHERE ai.data_ai BETWEEN ? AND ? ";
                    break;
                case 3:
                    filtroSql = "WHERE mot.resumo_descricao_motivo_ai LIKE ? ";
                    break;
                case 4:
                    filtroSql = "WHERE ai.fk_municipio_lavratura_ai = ? ";
                    break;
                case 5:
                    filtroSql = "WHERE ai.numero_ai LIKE ? ";
                    break;
                default:
                    break;
            }

            //preparando a String sql para execução
            sql += filtroSql;
            sql += "ORDER BY numero_ai DESC";
            stmt = con.prepareStatement(sql);
            if (txtFiltro == null) {
                txtFiltro = "";
            }

            if (filtroSelecionado == 0 || filtroSelecionado == 1 || filtroSelecionado == 3 || filtroSelecionado == 4) {
                stmt.setString(1, "%" + txtFiltro + "%");

            } else if (filtroSelecionado == 2) {
                String[] datas = txtFiltro.split(" ");
                stmt.setString(1, datas[0]);
                stmt.setString(2, datas[1]);
            }

            res = stmt.executeQuery();

            while (res.next()) {

                int idVeterinario = res.getInt("pk_id_veterinario");
                String nomeVeterinario = res.getString("nome_veterinario");
                String ifVeterinario = res.getString("if_veterinario");
                String crmvVeterinario = res.getString("crmv_veterinario");
                Veterinario veterinario = new Veterinario(idVeterinario, nomeVeterinario, ifVeterinario, crmvVeterinario);

                int idProdutor = res.getInt("pk_id_produtor");
                int idMunicipioProdutor = res.getInt("munProd.pk_id_municipio");
                String nomeMunicipioProdutor = res.getString("munProd.nome_municipio");
                String codIbgeMunicipioProdutor = res.getString("munProd.cod_ibge_municipio");
                Municipio municipioProdutor = new Municipio(idMunicipioProdutor, nomeMunicipioProdutor, codIbgeMunicipioProdutor);
                String nomeProdutor = res.getString("nome_produtor");
                String cpfProdutor = res.getString("cpf_cnpj_produtor");
                String rgProdutor = res.getString("rg_produtor");
                Produtor produtor = new Produtor(idProdutor, municipioProdutor, nomeProdutor, cpfProdutor, rgProdutor);

                int idMotivo = res.getInt("pk_id_motivo_ai");
                String resumoDescricao = res.getString("resumo_descricao_motivo_ai");
                float multaInicial = res.getFloat("multa_inicial_motivo_ai");
                float adicionalAnimal = res.getFloat("adicional_animal_motivo_ai");
                boolean preveAdv = res.getBoolean("preve_adv_motivo_ai");
                MotivoInfracao motivo = new MotivoInfracao(idMotivo, multaInicial, adicionalAnimal, resumoDescricao, preveAdv);

                int id = res.getInt("pk_id_ai");
                int numeroAi = res.getInt("numero_ai");
                LocalDate dataLavratura = res.getDate("data_ai").toLocalDate();
                LocalTime horaLavratura = res.getTime("data_ai").toLocalTime();
                LocalDate dataCiencia = null;
                if (res.getDate("data_ciencia_ai") != null) {
                    dataCiencia = res.getDate("data_ciencia_ai").toLocalDate();
                }
                String nomeMunicipio = res.getString("mun.nome_municipio");
                int idMunicipio = res.getInt("mun.pk_id_municipio");
                String codIbge = res.getString("mun.cod_ibge_municipio");
                Municipio municipioLavratura = new Municipio(idMunicipio, nomeMunicipio, codIbge);
                boolean advertencia = res.getBoolean("advertencia_ai");
                boolean reincidente = res.getBoolean("reincidente_ai");
                boolean desconto = res.getBoolean("desconto_ai");
                String historico = res.getString("historico_ai");
                String observacoesAI = res.getString("observacoes_ai");
                String processo = res.getString("processo_ai");
                String redator = res.getString("redator_ai");

                AutoInfracao autoInfracao = new AutoInfracao(id, numeroAi, dataLavratura, municipioLavratura, motivo, advertencia, reincidente, desconto, produtor);
                autoInfracao.setHistorico(historico);
                autoInfracao.setObservacoes(observacoesAI);
                autoInfracao.setProcesso(processo);
                autoInfracao.setDataCiencia(dataCiencia);
                autoInfracao.setHora(horaLavratura);
                autoInfracao.setFea(veterinario);
                autoInfracao.setRedator(redator);

                list.add(autoInfracao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<String> getMunicipiosComAI(){
        List<String> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        
        try {
            String sql = "SELECT DISTINCT m.nome_municipio FROM municipio m JOIN ai ON m.pk_id_municipio = ai.fk_municipio_lavratura_ai ORDER BY m.nome_municipio ";
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            
            while (res.next()) {
                String nomeMunicipio = res.getString("m.nome_municipio");
                list.add(nomeMunicipio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getProximoNumeroAI(int idMunicipio) {
        PreparedStatement stmt = null;
        ResultSet res = null;
        int proximoNumero = 0;
        try {
            String sql = "SELECT MAX(numero_ai) AS valor_maximo FROM ai WHERE fk_municipio_lavratura_ai = ?;";
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

    public boolean inserir(AutoInfracao ai, Map<String, Integer> animaisEnvolvidos) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "INSERT INTO ai (fk_municipio_lavratura_ai, fk_id_motivo_ai, fk_produtor_ai, fk_id_veterinario_ai, "
                    + "enquadramento_adicional_ai, advertencia_ai, reincidente_ai, desconto_ai, numero_ai, data_ai, "
                    + "data_ciencia_ai, historico_ai, observacoes_ai, processo_ai, redator_ai) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            LocalDate dataLavratura = ai.getDataLavratura();
            LocalTime horaLavratura = ai.getHoraLocalTime();
            LocalDateTime dataHoraCompleta = dataLavratura.atTime(horaLavratura);

            stmt.setInt(1, ai.getMunicipioLavratura().getId());
            stmt.setInt(2, ai.getMotivo().getId());
            stmt.setInt(3, ai.getAutuado().getId());
            if (ai.getFea() != null) {
                stmt.setInt(4, ai.getFea().getId());
            }
            stmt.setString(5, ai.getEnquadramentoAdicional());
            stmt.setBoolean(6, ai.isAdvertencia());
            stmt.setBoolean(7, ai.isReincidencia());
            stmt.setBoolean(8, ai.isDesconto());
            stmt.setInt(9, ai.getNumeroAi());
            stmt.setObject(10, dataHoraCompleta);
            stmt.setDate(11, ai.getDataCiencia() == null ? null : Date.valueOf(ai.getDataCiencia()));
            stmt.setString(12, ai.getHistorico());
            stmt.setString(13, ai.getObservacoes());
            stmt.setString(14, ai.getProcesso());
            stmt.setString(15, ai.getRedator());

            int rowsAffected = 0;

            try {
                rowsAffected = stmt.executeUpdate();
            } catch (SQLException e) {
                sql = "INSERT INTO ai (fk_municipio_lavratura_ai, fk_id_motivo_ai, fk_produtor_ai, fk_id_veterinario_ai, "
                        + "enquadramento_adicional_ai, advertencia_ai, reincidente_ai, desconto_ai, numero_ai, data_ai, "
                        + "data_ciencia_ai, historico_ai, observacoes_ai, processo_ai, redator_ai) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

                stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                int proximoNumero = getProximoNumeroAI(ai.getMunicipioLavratura().getId());

                dataLavratura = ai.getDataLavratura();
                horaLavratura = ai.getHoraLocalTime();
                dataHoraCompleta = dataLavratura.atTime(horaLavratura);
                stmt.setInt(1, ai.getMunicipioLavratura().getId());
                stmt.setInt(2, ai.getMotivo().getId());
                stmt.setInt(3, ai.getAutuado().getId());
                stmt.setInt(4, ai.getFea().getId());
                stmt.setString(5, ai.getEnquadramentoAdicional());
                stmt.setBoolean(6, ai.isAdvertencia());
                stmt.setBoolean(7, ai.isReincidencia());
                stmt.setBoolean(8, ai.isDesconto());
                stmt.setInt(9, proximoNumero);
                stmt.setObject(10, dataHoraCompleta);
                stmt.setDate(11, ai.getDataCiencia() == null ? null : Date.valueOf(ai.getDataCiencia()));
                stmt.setString(12, ai.getHistorico());
                stmt.setString(13, ai.getObservacoes());
                stmt.setString(14, ai.getProcesso());
                stmt.setString(15, ai.getRedator());

                rowsAffected = stmt.executeUpdate();

                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("ATENÇÃO");
                al.setContentText("O número do Auto informado já estava em uso. Foi utilizado o próximo número possível: " + proximoNumero);
                al.showAndWait();
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

                    if (!animaisEnvolvidos.isEmpty()) {
                        for (String especie : animaisEnvolvidos.keySet()) {
                            System.out.println(especie + " - " + animaisEnvolvidos.get(especie));
                            sql = "INSERT INTO ai_especie (fk_id_ai, nome_especie_ai_especie, qtd_especie) VALUES (?, ?, ?);";

                            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                            stmt.setInt(1, ai.getId());
                            stmt.setString(2, especie);
                            stmt.setInt(3, animaisEnvolvidos.get(especie));

                            rowsAffected = stmt.executeUpdate();
//                            if (rowsAffected > 0) {
//                                stmt.getGeneratedKeys();
//                                result = true;
//                            }
                        }
                    }
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

    public boolean excluir(AutoInfracao ai) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from ai where pk_id_ai = ?";
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
    
    public Map<String, Integer> getAnimaisEnvolvidos (AutoInfracao ai){
        PreparedStatement stmt = null;
        ResultSet res = null;
        Map<String, Integer> list = new TreeMap<>();

        try {
            String sql = "SELECT * FROM ai_especie WHERE fk_id_ai = ?;";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, ai.getId());

            res = stmt.executeQuery();

            while (res.next()) {
                String especie = res.getString("nome_especie_ai_especie");
                int qtdAnimais = res.getInt("qtd_especie");

                list.put(especie, qtdAnimais);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean editar(AutoInfracao ai, Map<String, Integer> animaisEnvolvidos) {
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            // 2. Iniciar a transação
            // Desliga o auto-commit para agrupar todas as queries
            con.setAutoCommit(false);

            // 3. Atualizar a tabela principal 'ai' (seu código original)
            String sql = "UPDATE ai SET fk_municipio_lavratura_ai = ?, fk_id_motivo_ai = ?, fk_produtor_ai = ?, "
                    + "fk_id_veterinario_ai = ?, enquadramento_adicional_ai = ?, advertencia_ai = ?, "
                    + "reincidente_ai = ?, desconto_ai = ?, numero_ai = ?, data_ai = ?, data_ciencia_ai = ?, "
                    + "historico_ai = ?, observacoes_ai = ?, processo_ai = ?, redator_ai = ? WHERE pk_id_ai = ?;";
            stmt = con.prepareStatement(sql);

            LocalDate dataLavratura = ai.getDataLavratura();
            LocalTime horaLavratura = ai.getHoraLocalTime();
            LocalDateTime dataHoraCompleta = dataLavratura.atTime(horaLavratura);

            //troca os parâmetros
            stmt.setInt(1, ai.getMunicipioLavratura().getId());
            stmt.setInt(2, ai.getMotivo().getId());
            stmt.setInt(3, ai.getAutuado().getId());
            stmt.setInt(4, ai.getFea().getId());
            stmt.setString(5, ai.getEnquadramentoAdicional());
            stmt.setBoolean(6, ai.isAdvertencia());
            stmt.setBoolean(7, ai.isReincidencia());
            stmt.setBoolean(8, ai.isDesconto());
            stmt.setInt(9, ai.getNumeroAi());
            stmt.setObject(10, dataHoraCompleta);
            if (ai.getDataCiencia() == null) {
                stmt.setDate(11, null);
            } else {
                stmt.setDate(11, Date.valueOf(ai.getDataCiencia()));
            }
            stmt.setString(12, ai.getHistorico());
            stmt.setString(13, ai.getObservacoes());
            stmt.setString(14, ai.getProcesso());
            stmt.setString(15, ai.getRedator());
            stmt.setInt(16, ai.getId());

            stmt.executeUpdate();

            // Limpar o statement anterior
            DB.closeStatement(stmt);

            // 4. Excluir TODOS os registros antigos de 'ai_especie' ligados a este AI
            // Isso cuida dos animais removidos ou alterados
            String sqlDeleteEspecies = "DELETE FROM ai_especie WHERE fk_id_ai = ?";
            stmt = con.prepareStatement(sqlDeleteEspecies);
            stmt.setInt(1, ai.getId());
            stmt.executeUpdate();

            // Limpar o statement anterior
            DB.closeStatement(stmt);

            // 5. Inserir os novos registros de 'ai_especie'
            // Isso cuida dos animais novos ou alterados
            if (!animaisEnvolvidos.isEmpty()) {
                String sqlInsertEspecie = "INSERT INTO ai_especie (fk_id_ai, nome_especie_ai_especie, qtd_especie) VALUES (?, ?, ?);";

                for (String especie : animaisEnvolvidos.keySet()) {
                    // O seu Controller já garante que só estão no Map animais com qtd > 0
                    stmt = con.prepareStatement(sqlInsertEspecie);
                    stmt.setInt(1, ai.getId());
                    stmt.setString(2, especie);
                    stmt.setInt(3, animaisEnvolvidos.get(especie));

                    stmt.executeUpdate();

                    // Fechar o statement dentro do loop
                    DB.closeStatement(stmt);
                }
            }

            // 6. Se tudo deu certo, comitar a transação
            con.commit();
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
            // 7. Se algo deu errado, reverter (fazer rollback)
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        } finally {
            // 8. Ligar o auto-commit novamente e fechar o último statement (se houver)
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException e3) {
                e3.printStackTrace();
            }
            // DB.closeStatement(stmt); // O stmt já é fechado nos passos anteriores ou será null
            return result;
        }
    }

    public List<AutoInfracao> verificarReincidencia(Produtor produtor, MotivoInfracao motivoInfracao) {
        PreparedStatement stmt = null;
        ResultSet res = null;
        List<AutoInfracao> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM ai WHERE fk_produtor_ai = ? AND fk_id_motivo_ai = ? AND data_ai BETWEEN ? AND ?;";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, produtor.getId());
            stmt.setInt(2, motivoInfracao.getId());
            stmt.setObject(3, LocalDate.now().minusYears(5));
            stmt.setObject(4, LocalDate.now().plusDays(1));

            res = stmt.executeQuery();

            while (res.next()) {
                int id = res.getInt("pk_id_ai");
                int numero = res.getInt("numero_ai");
                LocalDate dataAI = res.getDate("data_ai").toLocalDate();
                AutoInfracao autoInfracao = new AutoInfracao(id, dataAI, numero);

                list.add(autoInfracao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
