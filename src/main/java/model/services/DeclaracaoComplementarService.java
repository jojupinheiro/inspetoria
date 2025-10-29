package model.services;

import java.util.List;
import java.util.Map;
import model.classes.DeclaracaoComplementar;
import model.classes.MotivoInfracao;
import model.classes.Produtor;
import model.dao.DeclaracaoComplementarDAO;
import model.db.DB;

/**
 *
 * @author joaojuliano
 */
public class DeclaracaoComplementarService {
    private DeclaracaoComplementarDAO dao;

    public DeclaracaoComplementarService() {
        dao = new DeclaracaoComplementarDAO(DB.getConnection());
    }

    public List<DeclaracaoComplementar> getAll() {
        return dao.getAll();
    }
    
    public int getProximoNumeroDC(int idMunicipio){
        return dao.getProximoNumeroDC(idMunicipio);
    }
    
    public boolean testarNumeroDC(int idMunicipio, int numeroDc) {
        return dao.testarNumeroDC(idMunicipio, numeroDc);
    }

    public boolean salvarOuAtualizar(DeclaracaoComplementar declaracaoComplementar) {
        //Tenho que testar se é uma inclusão ou alteração
        if (declaracaoComplementar.getId() <= 0) {
            //É inclusão
            return dao.inserir(declaracaoComplementar);
        } else {
            //È alteração
            return dao.editar(declaracaoComplementar);
        }
    }

    public boolean excluir(DeclaracaoComplementar declaracaoComplementar) {
        return dao.excluir(declaracaoComplementar);
    }
    
    
}
