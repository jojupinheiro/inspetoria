package model.services;

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
        dao = new MotivoInfracaoDAO(DB.getConnection());
    }
    
    public List<MotivoInfracao> getAll() {
        return dao.getAll();
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
}
