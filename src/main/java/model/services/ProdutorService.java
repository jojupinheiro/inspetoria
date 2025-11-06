package model.services;

import java.sql.SQLException;
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
        try {
            dao = new ProdutorDAO(DB.getConnection());
        } catch (SQLException ex) {
            System.getLogger(ProdutorService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
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
