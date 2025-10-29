package model.services;

import java.util.List;
import model.classes.Municipio;
import model.dao.UtilitarioDAO;
import model.db.DB;

/**
 *
 * @author João Juliano Pinheiro
 * joaojulianopinheiro@hotmail.com
 */
public class UtilitarioService {
    private UtilitarioDAO dao;
    
    public UtilitarioService() {
        dao = new UtilitarioDAO(DB.getConnection());
    }
    
    public List<Municipio> getMunicipios() {
        return dao.getMunicipios();
    }
    
    public Municipio getMunicipioPadrao(){
        return dao.getMunicipioPadrao();
    }
    
    public boolean editarMunicipioPadrao(Municipio municipio){
        return dao.editarMunicipioPadrao(municipio);
    }
    
    public boolean inserirMunicipio(Municipio municipio) {
        return dao.inserirMunicipio(municipio);
    }
    
    public boolean excluirMunicipio(Municipio municipio) {
        return dao.excluirMunicipio(municipio);
    }
    
    public boolean salvarOuAtualizar(Municipio municipio) {
        //Tenho que testar se é uma inclusão ou alteração
        if (municipio.getId() <= 0) {
            //É inclusão
            return dao.inserirMunicipio(municipio);
        } else {
            //È alteração
            return dao.editarMunicipio(municipio);
        }
    }
    
    public List<String> getRedatores(){
        return dao.getRedatores();
    }
    
    public boolean salvarRedator(String redator){
        return dao.salvarRedator(redator);
    }
    
    public boolean excluirRedator (String redator){
        return dao.excluirRedator(redator);
    }
}
