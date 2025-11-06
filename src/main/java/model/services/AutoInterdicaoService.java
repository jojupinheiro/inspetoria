package model.services;

import java.sql.SQLException;
import java.util.List;
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
        try {
            dao = new AutoInterdicaoDAO(DB.getConnection());
        } catch (SQLException ex) {
            System.getLogger(AutoInterdicaoService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public List<AutoInterdicao> getAll(int filtroSelecionado, String txtFiltro) {
        return dao.getAll();
    }
    
    public int getProximoNumeroAI(int idMunicipio, int ano){
        return dao.getProximoNumeroAI(idMunicipio, ano);
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
