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
import model.classes.Empresa;
import model.classes.Produtor;
import model.db.DB;

/**
 *
 * @author João Juliano Pinheiro
 * joaojulianopinheiro@hotmail.com
 */
public class EmpresaDAO {
    private Connection con;

    public EmpresaDAO(Connection con) {
        this.con = con;
    }
    
    public List<Empresa> getAll() {
        List<Empresa> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        
        try {
            String sql = "SELECT e.*, m.*, end.*, c.*, p.*, munProd.* FROM empresa e "
                    + "LEFT JOIN municipio m ON (m.pk_id_municipio = e.fk_id_municipio) "
                    + "LEFT JOIN endereco end ON (end.pk_id_endereco = e.fk_id_endereco) "
                    + "LEFT JOIN contato c ON (c.pk_id_contato = e.fk_id_contato) "
                    + "LEFT JOIN produtor p ON (p.pk_id_produtor = e.fk_id_representante) "
                    + "LEFT JOIN municipio munProd ON (munProd.pk_id_municipio = p.fk_id_municipio) "
                    + "ORDER BY razao_social ;";
            
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            
            while (res.next()) {
                
                //Municipio empresa
                int idMunicipio = res.getInt("m.pk_id_municipio");
                String nomeMunicipio = res.getString("m.nome_municipio");
                String codIbgeMunicipio = res.getString("m.cod_ibge_municipio");
                Municipio municipio = new Municipio(idMunicipio, nomeMunicipio, codIbgeMunicipio);
                
                //Endereço empresa
                int idEndereco = res.getInt("end.pk_id_endereco");
                String tipoLogradouro = res.getString("end.tipo_logradouro_endereco");
                String logradouro = res.getString("end.logradouro_endereco");
                String numeroEndereco = res.getString("end.numero_endereco");
                Endereco endereco = new Endereco(idEndereco, tipoLogradouro, logradouro, numeroEndereco);
                
                //Contato empresa
                int idContato = res.getInt("c.pk_id_contato");
                String telefone1 = res.getString("c.telefone1_contato");
                String telefone2 = res.getString("c.telefone2_contato");
                String email = res.getString("c.email_contato");
                Contato contato = new Contato(idContato, telefone1, telefone2, email);
                
                //Municipio Produtor
                int idMunicipioProdutor = res.getInt("munProd.pk_id_municipio");
                String nomeMunicipioProdutor = res.getString("munProd.nome_municipio");
                String codIbgeMunicipioProdutor = res.getString("munProd.cod_ibge_municipio");
                Municipio municipioProdutor = new Municipio(idMunicipioProdutor, nomeMunicipioProdutor, codIbgeMunicipioProdutor);
                
                //Produtor
                int idProdutor = res.getInt("p.pk_id_produtor");
                String nomeProdutor = res.getString("p.nome_produtor");
                String cpfProdutor = res.getString("p.cpf_cnpj_produtor");
                String rgProdutor = res.getString("p.rg_produtor");
                Produtor produtor = new Produtor(idProdutor, municipioProdutor, nomeProdutor, cpfProdutor, rgProdutor);
                
                //Empresa
                int idEmpresa = res.getInt("e.pk_id_empresa");
                String razaoSocial = res.getString("e.razao_social");
                String cnpjEmpresa = res.getString("e.cnpj_empresa");
                String observacoesEmpresa = res.getString("e.observacoes_empresa");
                int registro = res.getInt("e.numero_registro");
                Empresa empresa = new Empresa(idEmpresa, registro, endereco, municipio, contato, produtor, razaoSocial, cnpjEmpresa, observacoesEmpresa);
                                
                list.add(empresa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    
    
    public Empresa getEmpresa(Empresa empresa) {
        ResultSet res = null;
        PreparedStatement stmt = null;
        
        try {
            String sql = "SELECT e.*, m.*, end.*, c.*, p.*, munProd.* FROM empresa e "
                    + "LEFT JOIN municipio m ON (m.pk_id_municipio = e.fk_id_municipio) "
                    + "LEFT JOIN endereco end ON (end.pk_id_endereco = e.fk_id_endereco) "
                    + "LEFT JOIN contato c ON (c.pk_id_contato = e.fk_id_contato) "
                    + "LEFT JOIN produtor p ON (p.pk_id_produtor = e.fk_id_representante) "
                    + "LEFT JOIN municipio munProd ON (munProd.pk_id_municipio = p.fk_id_municipio) "
                    + "WHERE pk_id_empresa = ? ;";
            
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, empresa.getId() );
            res = stmt.executeQuery();
            
            while (res.next()) {
                
                //Municipio empresa
                int idMunicipio = res.getInt("m.pk_id_municipio");
                String nomeMunicipio = res.getString("m.nome_municipio");
                String codIbgeMunicipio = res.getString("m.cod_ibge_municipio");
                Municipio municipio = new Municipio(idMunicipio, nomeMunicipio, codIbgeMunicipio);
                
                //Endereço empresa
                int idEndereco = res.getInt("end.pk_id_endereco");
                String tipoLogradouro = res.getString("end.tipo_logradouro_endereco");
                String logradouro = res.getString("end.logradouro_endereco");
                String numeroEndereco = res.getString("end.numero_endereco");
                Endereco endereco = new Endereco(idEndereco, tipoLogradouro, logradouro, numeroEndereco);
                
                //Contato empresa
                int idContato = res.getInt("c.pk_id_contato");
                String telefone1 = res.getString("c.telefone1_contato");
                String telefone2 = res.getString("c.telefone2_contato");
                String email = res.getString("c.email_contato");
                Contato contato = new Contato(idContato, telefone1, telefone2, email);
                
                //Municipio Produtor
                int idMunicipioProdutor = res.getInt("munProd.pk_id_municipio");
                String nomeMunicipioProdutor = res.getString("munProd.nome_municipio");
                String codIbgeMunicipioProdutor = res.getString("munProd.cod_ibge_municipio");
                Municipio municipioProdutor = new Municipio(idMunicipioProdutor, nomeMunicipioProdutor, codIbgeMunicipioProdutor);
                
                //Produtor
                int idProdutor = res.getInt("p.pk_id_produtor");
                String nomeProdutor = res.getString("p.nome_produtor");
                String cpfProdutor = res.getString("p.cpf_cnpj_produtor");
                String rgProdutor = res.getString("p.rg_produtor");
                Produtor produtor = new Produtor(idProdutor, municipioProdutor, nomeProdutor, cpfProdutor, rgProdutor);
                
                //Empresa
                int idEmpresa = res.getInt("e.pk_id_empresa");
                String razaoSocial = res.getString("e.razao_social");
                String cnpjEmpresa = res.getString("e.cnpj_empresa");
                String observacoesEmpresa = res.getString("e.observacoes_empresa");
                int registro = res.getInt("e.numero_registro");
                empresa = new Empresa(idEmpresa, registro, endereco, municipio, contato, produtor, razaoSocial, cnpjEmpresa, observacoesEmpresa);

                                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return empresa;
    }
    
    public boolean inserir(Empresa empresa) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            //Inserindo Endereço
            String sql = "INSERT INTO endereco (tipo_logradouro_endereco, logradouro_endereco, numero_endereco) VALUES (?, ?, ?);";
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, empresa.getEndereco().getTipoLogradouro() );
            stmt.setString(2, empresa.getEndereco().getLogradouro() );
            stmt.setString(3, empresa.getEndereco().getNumero() );
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    empresa.getEndereco().setId(id);
                }
            }
            
            //Inserindo Contato
            sql = "INSERT INTO contato (telefone1_contato, telefone2_contato, email_contato) VALUES (?, ?, ?);";
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, empresa.getContato().getTelefone1() );
            stmt.setString(2, empresa.getContato().getTelefone2() );
            stmt.setString(3, empresa.getContato().getEmail() );
            
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    empresa.getContato().setId(id);
                }
            }
            
            //Inserindo Empresa
            sql = "INSERT INTO empresa (fk_id_endereco, fk_id_contato, fk_id_representante, "
                    + "fk_id_municipio, razao_social, numero_registro, cnpj_empresa) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?);";
            
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, empresa.getEndereco().getId() );
            stmt.setInt(2, empresa.getContato().getId() );
            stmt.setInt(3, empresa.getRepresentante().getId() );
            stmt.setInt(4, empresa.getMunicipio().getId() );
            stmt.setString(5, empresa.getRazaoSocial() );
            stmt.setInt(6, empresa.getNumeroRegistro() );
            stmt.setString(7, empresa.getCnpj() );
            
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Deu certo
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // getInt(1) pega o código que foi gerado e que está no primeiro campo do resultSet
                    int id = rs.getInt(1);
                    //Atualiza o ID do tutor no parâmetro que foi recebido pelo método
                    empresa.setId(id);
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
    
    public boolean excluir(Empresa empresa) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from empresa where pk_id_empresa = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, empresa.getId());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean editar(Empresa empresa) {
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
            String sqlGetOldIds = "SELECT fk_id_endereco_empresa, fk_id_contato_empresa FROM empresa WHERE pk_id_empresa = ?";
            stmt = con.prepareStatement(sqlGetOldIds);
            stmt.setInt(1, empresa.getId());
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Usamos getObject para lidar com possíveis valores NULL
                oldEnderecoId = (Integer) rs.getObject("fk_id_endereco_empresa"); 
                oldContatoId = (Integer) rs.getObject("fk_id_contato_empresa");
            } else {
                // Se o empresa não existe, não podemos editar.
                throw new SQLException("Empresa com ID " + empresa.getId() + " não encontrado para edição.");
            }
            
            DB.closeResultSet(rs);
            DB.closeStatement(stmt);

            // 3. Inserir o NOVO Endereco (se fornecido) e obter seu ID
            Endereco endereco = empresa.getEndereco();
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
            Contato contato = empresa.getContato();
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

            // 5. Atualizar a tabela principal 'empresa' com os NOVOS IDs
            String sqlUpdateEmpresa = "UPDATE empresa SET fk_id_endereco = ?, fk_id_contato = ?, "
                    + "fk_id_representante = ?, fk_id_municipio = ?, razao_social = ?, numero_registro = ?, "
                    + "cnpj_empresa = ? WHERE pk_id_empresa = ?;";
            
            stmt = con.prepareStatement(sqlUpdateEmpresa);
            
            // Define os parâmetros do empresa
            if (newEnderecoId != null)  stmt.setInt(1, newEnderecoId); else  stmt.setNull(1, Types.INTEGER);
            if (newContatoId != null)  stmt.setInt(2, newContatoId);  else  stmt.setNull(2, Types.INTEGER);
            stmt.setInt(3, empresa.getRepresentante().getId() );
            stmt.setInt(4, empresa.getMunicipio().getId() );
            stmt.setString(5, empresa.getRazaoSocial() );
            stmt.setInt(6, empresa.getNumeroRegistro() );
            stmt.setString(7, empresa.getCnpj() );
            stmt.setInt(8, empresa.getId());
            
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
