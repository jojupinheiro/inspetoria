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
}
