package model.services;

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
        dao = new VeterinarioDAO(DB.getConnection());
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
