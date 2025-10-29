package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.classes.Contato;
import model.classes.Endereco;
import model.classes.Municipio;
import model.classes.Produtor;
import model.db.DB;

/**
 *
 * @author João Juliano Pinheiro
 * joaojulianopinheiro@hotmail.com
 */
public class ProdutorDAO {
    private Connection con;

    public ProdutorDAO(Connection con) {
        this.con = con;
    }
    
    public List<Produtor> getAll(int filtroSelecionado, String txtFiltro) {
        List<Produtor> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        
        try {
            String sql = "SELECT p.*, m.*, e.*, c.* FROM produtor p "
                    + "LEFT JOIN municipio m ON (m.pk_id_municipio = p.fk_id_municipio) "
                    + "LEFT JOIN endereco e ON (e.pk_id_endereco = p.fk_id_endereco_produtor) "
                    + "LEFT JOIN contato c ON (c.pk_id_contato = p.fk_id_contato_produtor) "
                    + "ORDER BY nome_produtor ;";
            
            stmt = con.prepareStatement(sql);
            
            res = stmt.executeQuery();
            
            while (res.next()) {
                
                //Municipio
                int idMunicipio = res.getInt("pk_id_municipio");
                String nomeMunicipio = res.getString("nome_municipio");
                String codIbgeMunicipio = res.getString("cod_ibge_municipio");
                Municipio municipio = new Municipio(idMunicipio, nomeMunicipio, codIbgeMunicipio);
                
                //Endereço
                int idEndereco = res.getInt("pk_id_endereco");
                String tipoLogradouro = res.getString("tipo_logradouro_endereco");
                String logradouro = res.getString("logradouro_endereco");
                String numeroEndereco = res.getString("numero_endereco");
                Endereco endereco = new Endereco(idEndereco, tipoLogradouro, logradouro, numeroEndereco);
                
                //Contato
                int idContato = res.getInt("pk_id_contato");
                String telefone1 = res.getString("telefone1_contato");
                String telefone2 = res.getString("telefone2_contato");
                String email = res.getString("email_contato");
                Contato contato = new Contato(idContato, telefone1, telefone2, email);
                
                 
                int idProdutor = res.getInt("pk_id_produtor");
                String nomeProdutor = res.getString("nome_produtor");
                String cpfProdutor = res.getString("cpf_cnpj_produtor");
                String rg = res.getString("rg_produtor");
                String observacao = res.getString("observacao_produtor");
                boolean tipoPessoa = res.getBoolean("tipo_pessoa_produtor");
                Produtor produtor = new Produtor(idProdutor, municipio, nomeProdutor, cpfProdutor, rg, endereco, tipoPessoa);
                produtor.setContato(contato);
                produtor.setObservacao(observacao);
                                
                list.add(produtor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    public List<Produtor> getNomesECpfs(int filtroSelecionado, String txtFiltro) {
        List<Produtor> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        
        try {
            String sql = "SELECT p.pk_id_produtor, p.nome_produtor, p.cpf_cnpj_produtor, m.* FROM produtor p "
                    + "LEFT JOIN municipio m ON (p.fk_id_municipio = m.pk_id_municipio) "
                    + "ORDER BY nome_produtor ";
            
            stmt = con.prepareStatement(sql);
            
            res = stmt.executeQuery();
            
            while (res.next()) {
                //Município
                int idMunicipio = res.getInt("pk_id_municipio");
                String nomeMunicipio = res.getString("nome_municipio");
                String codIbgeMunicipio = res.getString("cod_ibge_municipio");
                Municipio municipio = new Municipio(idMunicipio, nomeMunicipio, codIbgeMunicipio);
                
                int idProdutor = res.getInt("pk_id_produtor");
                String nomeProdutor = res.getString("nome_produtor");
                String cpfProdutor = res.getString("cpf_cnpj_produtor");
                Produtor produtor = new Produtor(idProdutor, nomeProdutor, cpfProdutor);
                produtor.setMunicipio(municipio);
                                
                list.add(produtor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    public Produtor getProdutor(Produtor produtor) {
        ResultSet res = null;
        PreparedStatement stmt = null;
        
        try {
            String sql = "SELECT p.*, m.*, e.*, c.* FROM produtor p "
                    + "LEFT JOIN municipio m ON (m.pk_id_municipio = p.fk_id_municipio) "
                    + "LEFT JOIN endereco e ON (e.pk_id_endereco = p.fk_id_endereco_produtor) "
                    + "LEFT JOIN contato c ON (c.pk_id_contato = p.fk_id_contato_produtor) "
                    + "WHERE pk_id_produtor = ? ;";
            
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, produtor.getId() );
            res = stmt.executeQuery();
            
            while (res.next()) {
                
                //Municipio
                int idMunicipio = res.getInt("pk_id_municipio");
                String nomeMunicipio = res.getString("nome_municipio");
                String codIbgeMunicipio = res.getString("cod_ibge_municipio");
                Municipio municipio = new Municipio(idMunicipio, nomeMunicipio, codIbgeMunicipio);
                
                //Endereço
                int idEndereco = res.getInt("pk_id_endereco");
                String tipoLogradouro = res.getString("tipo_logradouro_endereco");
                String logradouro = res.getString("logradouro_endereco");
                String numeroEndereco = res.getString("numero_endereco");
                Endereco endereco = new Endereco(idEndereco, tipoLogradouro, logradouro, numeroEndereco);
                
                //Contato
                int idContato = res.getInt("pk_id_contato");
                String telefone1 = res.getString("telefone1_contato");
                String telefone2 = res.getString("telefone2_contato");
                String email = res.getString("email_contato");
                Contato contato = new Contato(idContato, telefone1, telefone2, email);
                
                 
                int idProdutor = res.getInt("pk_id_produtor");
                String nomeProdutor = res.getString("nome_produtor");
                String cpfProdutor = res.getString("cpf_cnpj_produtor");
                String rg = res.getString("rg_produtor");
                String observacao = res.getString("observacao_produtor");
                boolean tipoPessoa = res.getBoolean("tipo_pessoa_produtor");
                produtor = new Produtor(idProdutor, municipio, nomeProdutor, cpfProdutor, rg, endereco, tipoPessoa);
                produtor.setContato(contato);
                produtor.setObservacao(observacao);
                                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return produtor;
    }
    
    public boolean inserir(Produtor produtor) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            //Inserindo Endereço
            String sql = "INSERT INTO endereco (tipo_logradouro_endereco, logradouro_endereco, numero_endereco) VALUES (?, ?, ?);";
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, produtor.getEndereco().getTipoLogradouro() );
            stmt.setString(2, produtor.getEndereco().getLogradouro() );
            stmt.setString(3, produtor.getEndereco().getNumero() );
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    produtor.getEndereco().setId(id);
                }
            }
            
            
            //Inserindo Contato
            sql = "INSERT INTO contato (telefone1_contato, telefone2_contato, email_contato) VALUES (?, ?, ?);";
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, produtor.getContato().getTelefone1() );
            stmt.setString(2, produtor.getContato().getTelefone2() );
            stmt.setString(3, produtor.getContato().getEmail() );
            
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    produtor.getContato().setId(id);
                }
            }
            
            //Inserindo Produtor
            sql = "INSERT INTO produtor (fk_id_endereco_produtor, fk_id_municipio, "
                    + "fk_id_contato_produtor, nome_produtor, cpf_cnpj_produtor, rg_produtor, "
                    + "tipo_pessoa_produtor, observacao_produtor) VALUES (?,?,?,?,?,?,?,?);";
            
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, produtor.getEndereco().getId() );
            stmt.setInt(2, produtor.getMunicipio().getId() );
            stmt.setInt(3, produtor.getContato().getId() );
            stmt.setString(4, produtor.getNome() );
            stmt.setString(5, produtor.getCpf() );
            stmt.setString(6, produtor.getRg() );
            stmt.setBoolean(7, produtor.isTipoProdutor() );
            stmt.setString(8, produtor.getObservacao());
            
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Deu certo
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // getInt(1) pega o código que foi gerado e que está no primeiro campo do resultSet
                    int id = rs.getInt(1);
                    //Atualiza o ID do tutor no parâmetro que foi recebido pelo método
                    produtor.setId(id);
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
    
    public boolean excluir(Produtor produtor) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from produtor where pk_id_produtor = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, produtor.getId());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean editar(Produtor produtor) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean result = false;

        Integer oldEnderecoId = null;
        Integer oldContatoId = null;
        
        Integer newEnderecoId = null;
        Integer newContatoId = null;

        try {
            // 1. Iniciar a transação
            con.setAutoCommit(false);

            // 2. Buscar os IDs de FK antigos ANTES de qualquer alteração
            //    Precisamos deles para excluir os registros órfãos no final.
            String sqlGetOldIds = "SELECT fk_id_endereco_produtor, fk_id_contato_produtor FROM produtor WHERE pk_id_produtor = ?";
            stmt = con.prepareStatement(sqlGetOldIds);
            stmt.setInt(1, produtor.getId());
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Usamos getObject para lidar com possíveis valores NULL
                oldEnderecoId = (Integer) rs.getObject("fk_id_endereco_produtor"); 
                oldContatoId = (Integer) rs.getObject("fk_id_contato_produtor");
            } else {
                // Se o produtor não existe, não podemos editar.
                throw new SQLException("Produtor com ID " + produtor.getId() + " não encontrado para edição.");
            }
            
            DB.closeResultSet(rs);
            DB.closeStatement(stmt);

            // 3. Inserir o NOVO Endereco (se fornecido) e obter seu ID
            Endereco endereco = produtor.getEndereco();
            if (endereco != null) {
                String sqlInsertEndereco = "INSERT INTO endereco (tipo_logradouro_endereco, logradouro_endereco, numero_endereco) VALUES (?, ?, ?);";
                // Usamos RETURN_GENERATED_KEYS para pegar o novo ID
                stmt = con.prepareStatement(sqlInsertEndereco, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, endereco.getTipoLogradouro());
                stmt.setString(2, endereco.getLogradouro());
                stmt.setString(3, endereco.getNumero());
                stmt.executeUpdate();
                
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    newEnderecoId = rs.getInt(1); // Pega o pk_id_endereco gerado
                }
                DB.closeResultSet(rs);
                DB.closeStatement(stmt);
            }

            // 4. Inserir o NOVO Contato (se fornecido) e obter seu ID
            Contato contato = produtor.getContato();
            if (contato != null) {
                String sqlInsertContato = "INSERT INTO contato (telefone1_contato, telefone2_contato, email_contato) VALUES (?, ?, ?);";
                stmt = con.prepareStatement(sqlInsertContato, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, contato.getTelefone1());
                stmt.setString(2, contato.getTelefone2());
                stmt.setString(3, contato.getEmail());
                stmt.executeUpdate();

                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    newContatoId = rs.getInt(1); // Pega o pk_id_contato gerado
                }
                DB.closeResultSet(rs);
                DB.closeStatement(stmt);
            }

            // 5. Atualizar a tabela principal 'produtor' com os NOVOS IDs
            //    (Note: A coluna 'observacao' da sua classe não existe na tabela 'produtor' do schema)
            String sqlUpdateProdutor = "UPDATE produtor SET fk_id_municipio = ?, nome_produtor = ?, "
                    + "cpf_cnpj_produtor = ?, rg_produtor = ?, tipo_pessoa_produtor = ?, "
                    + "fk_id_endereco_produtor = ?, fk_id_contato_produtor = ?, observacao_produtor = ? "
                    + "WHERE pk_id_produtor = ?;";
            
            stmt = con.prepareStatement(sqlUpdateProdutor);
            
            // Define os parâmetros do produtor
            stmt.setInt(1, produtor.getMunicipio().getId());
            stmt.setString(2, produtor.getNome());
            stmt.setString(3, produtor.getCpf());
            stmt.setString(4, produtor.getRg());
            stmt.setBoolean(5, produtor.isTipoProdutor()); // Assumindo getter isTipoProdutor()
            
            // Define a FK do novo endereço (ou null)
            if (newEnderecoId != null) {
                stmt.setInt(6, newEnderecoId);
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            // Define a FK do novo contato (ou null)
            if (newContatoId != null) {
                stmt.setInt(7, newContatoId);
            } else {
                stmt.setNull(7, Types.INTEGER);
            }
            
            stmt.setString(8, produtor.getObservacao() );
            
            // Define o ID do produtor no WHERE
            stmt.setInt(9, produtor.getId());
            
            stmt.executeUpdate();
            DB.closeStatement(stmt);

            // 6. Apagar o Endereco ANTIGO (se existia)
            //    (Se outra tabela usar este endereço, a transação falhará aqui, o que é o esperado)
            if (oldEnderecoId != null) {
                String sqlDeleteOldEndereco = "DELETE FROM endereco WHERE pk_id_endereco = ?";
                stmt = con.prepareStatement(sqlDeleteOldEndereco);
                stmt.setInt(1, oldEnderecoId);
                stmt.executeUpdate();
                DB.closeStatement(stmt);
            }

            // 7. Apagar o Contato ANTIGO (se existia)
            if (oldContatoId != null) {
                String sqlDeleteOldContato = "DELETE FROM contato WHERE pk_id_contato = ?";
                stmt = con.prepareStatement(sqlDeleteOldContato);
                stmt.setInt(1, oldContatoId);
                stmt.executeUpdate();
                DB.closeStatement(stmt);
            }

            // 8. Se tudo deu certo, comitar a transação
            con.commit();
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
            // 9. Se algo deu errado, reverter (fazer rollback)
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        } finally {
            // 10. Ligar o auto-commit novamente e fechar os últimos recursos
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException e3) {
                e3.printStackTrace();
            }
            // Garante que os últimos stmt e rs sejam fechados
            DB.closeStatement(stmt); 
            DB.closeResultSet(rs);
        }
        
        return result;
    }
}
