package model.services;

import java.util.List;
import model.dao.ProdutorDAO;
import model.db.DB;
import model.classes.Produtor;

/**
 *
 * @author João Juliano Pinheiro
 * joaojulianopinheiro@hotmail.com
 */
public class ProdutorService {
    private ProdutorDAO dao;

    public ProdutorService() {
        dao = new ProdutorDAO(DB.getConnection());
    }

    public List<Produtor> getAll(int filtroSelecionado, String txtFiltro) {
        return dao.getAll(filtroSelecionado, txtFiltro);
    }

    public List<Produtor> getNomesECpfs(int filtroSelecionado, String txtFiltro) {
        return dao.getAll(filtroSelecionado, txtFiltro);
    }
    
    public Produtor getProdutor(Produtor produtor) {
        return dao.getProdutor(produtor);
    }
    
    public boolean salvarOuAtualizar(Produtor produtor) {
        //Tenho que testar se é uma inclusão ou alteração
        if (produtor.getId() <= 0) {
            //É inclusão
            return dao.inserir(produtor);
        } else {
            //È alteração
            return dao.editar(produtor);
        }
    }

    public boolean excluir(Produtor produtor) {
        return dao.excluir(produtor);
    }
}
