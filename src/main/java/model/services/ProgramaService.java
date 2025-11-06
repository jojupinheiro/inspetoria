package model.services;

import java.sql.SQLException;
import java.util.List;
import model.classes.Programa;
import model.dao.ProgramaDAO;
import model.db.DB;

/**
 *
 * @author João Juliano Pinheiro
 * joaojulianopinheiro@hotmail.com
 */
public class ProgramaService {
    private ProgramaDAO dao;
    
    public ProgramaService() {
        try {
            dao = new ProgramaDAO(DB.getConnection());
        } catch (SQLException ex) {
            System.getLogger(ProgramaService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    public List<Programa> getAll() {
        return dao.getAll();
    }
    
    public boolean salvarOuAtualizar(Programa programa) {
        //Tenho que testar se é uma inclusão ou alteração
        if (programa.getId() <= 0) {
            //É inclusão
            return dao.inserir(programa);
        } else {
            //È alteração
            return dao.editar(programa);
        }
    }
    
    public boolean excluir(Programa programa) {
        return dao.excluir(programa);
    }
}
