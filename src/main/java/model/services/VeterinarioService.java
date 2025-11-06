package model.services;

import java.sql.SQLException;
import java.util.List;
import model.dao.VeterinarioDAO;
import model.db.DB;
import model.classes.Veterinario;

/**
 *
 * @author João Juliano Pinheiro
 * joaojulianopinheiro@hotmail.com
 */
public class VeterinarioService {
    private VeterinarioDAO dao;
    
    public VeterinarioService() {
        try {
            dao = new VeterinarioDAO(DB.getConnection());
        } catch (SQLException ex) {
            System.getLogger(VeterinarioService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    public List<Veterinario> getAll() {
        return dao.getAll();
    }
    
    public boolean excluir(Veterinario veterinario) {
        return dao.excluir(veterinario);
    }
    
    public boolean salvarOuAtualizar(Veterinario veterinario) {
        //Tenho que testar se é uma inclusão ou alteração
        if (veterinario.getId() <= 0) {
            //É inclusão
            return dao.inserir(veterinario);
        } else {
            //È alteração
            return dao.editar(veterinario);
        }
    }
}
