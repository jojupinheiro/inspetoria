package model.services;

import java.util.List;
import java.util.Map;
import model.classes.AutoInterdicao;
import model.dao.AutoInterdicaoDAO;
import model.db.DB;

/**
 *
 * @author joaojuliano
 */
public class AutoInterdicaoService {
    private AutoInterdicaoDAO dao;

    public AutoInterdicaoService() {
        dao = new AutoInterdicaoDAO(DB.getConnection());
    }

    public List<AutoInterdicao> getAll(int filtroSelecionado, String txtFiltro) {
        return dao.getAll();
    }
    
    public int getProximoNumeroAI(int idMunicipio){
        return dao.getProximoNumeroAI(idMunicipio);
    }


    public boolean salvarOuAtualizar(AutoInterdicao ai) {
        //Tenho que testar se é uma inclusão ou alteração
        if (ai.getId() <= 0) {
            //É inclusão
            return dao.inserir(ai);
        } else {
            //È alteração
            return dao.editar(ai);
        }
    }

    public boolean excluir(AutoInterdicao ai) {
        return dao.excluir(ai);
    }
}
