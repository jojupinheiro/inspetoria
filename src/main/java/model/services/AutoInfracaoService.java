package model.services;

import java.util.List;
import java.util.Map;
import model.dao.AutoInfracaoDAO;
import model.db.DB;
import model.classes.AutoInfracao;
import model.classes.Produtor;
import model.classes.MotivoInfracao;

/**
 *
 * @author Juliano
 */
public class AutoInfracaoService {
    
    private AutoInfracaoDAO dao;

    public AutoInfracaoService() {
        dao = new AutoInfracaoDAO(DB.getConnection());
    }

    public List<AutoInfracao> getAll(int filtroSelecionado, String txtFiltro) {
        return dao.getAll(filtroSelecionado, txtFiltro);
    }
    
    public int getProximoNumeroAI(int idMunicipio){
        return dao.getProximoNumeroAI(idMunicipio);
    }
    
    public List<String> getMunicipiosComAI(){
        return dao.getMunicipiosComAI();
    }

    public boolean salvarOuAtualizar(AutoInfracao ai, Map<String, Integer> animaisEnvolvidos) {
        //Tenho que testar se é uma inclusão ou alteração
        if (ai.getId() <= 0) {
            //É inclusão
            return dao.inserir(ai, animaisEnvolvidos);
        } else {
            //È alteração
            return dao.editar(ai, animaisEnvolvidos);
        }
    }

    public boolean excluir(AutoInfracao ai) {
        return dao.excluir(ai);
    }
    
    public List<AutoInfracao> verificarReincidencia(Produtor produtor, MotivoInfracao motivoInfracao){
        return dao.verificarReincidencia(produtor, motivoInfracao);
    }
    
    public Map<String, Integer> getAnimaisEnvolvidos (AutoInfracao ai){
        return dao.getAnimaisEnvolvidos(ai);
    }
}
