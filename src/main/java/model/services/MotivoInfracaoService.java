package model.services;

import java.sql.SQLException;
import java.util.List;
import model.dao.MotivoInfracaoDAO;
import model.classes.MotivoInfracao;
import model.db.DB;

/**
 *
 * @author João Juliano Pinheiro
 * joaojulianopinheiro@hotmail.com
 */
public class MotivoInfracaoService {
    private MotivoInfracaoDAO dao;
    
    public MotivoInfracaoService() {
        try {
            dao = new MotivoInfracaoDAO(DB.getConnection());
        } catch (SQLException ex) {
            System.getLogger(MotivoInfracaoService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    public List<MotivoInfracao> getAll() {
        return dao.getAll();
    }
    
    public List<MotivoInfracao> getInformacoesPrincipais() {
        return dao.getInformacoesPrincipais();
    }
    
    public boolean salvarOuAtualizar(MotivoInfracao motivoInfracao) {
        //Tenho que testar se é uma inclusão ou alteração
        if (motivoInfracao.getId() <= 0) {
            //É inclusão
            return dao.inserir(motivoInfracao);
        } else {
            //È alteração
            return dao.editar(motivoInfracao);
        }
    }
    
    public boolean excluir(MotivoInfracao motivoInfracao) {
        return dao.excluir(motivoInfracao);
    }
}
