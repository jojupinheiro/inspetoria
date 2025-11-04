package model.services;

import java.util.List;
import model.classes.Empresa;
import model.dao.EmpresaDAO;
import model.db.DB;

/**
 *
 * @author João Juliano Pinheiro
 * joaojulianopinheiro@hotmail.com
 */
public class EmpresaService {
    private EmpresaDAO dao;

    public EmpresaService() {
        dao = new EmpresaDAO(DB.getConnection());
    }

    public List<Empresa> getAll(int filtroSelecionado, String txtFiltro) {
        return dao.getAll();
    }

    public Empresa getEmpresa(Empresa empresa) {
        return dao.getEmpresa(empresa);
    }
    
    public boolean salvarOuAtualizar(Empresa empresa) {
        //Tenho que testar se é uma inclusão ou alteração
        if (empresa.getId() <= 0) {
            //É inclusão
            return dao.inserir(empresa);
        } else {
            //È alteração
            return dao.editar(empresa);
        }
    }

    public boolean excluir(Empresa empresa) {
        return dao.excluir(empresa);
    }
}
