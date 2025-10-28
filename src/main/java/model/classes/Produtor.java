package model.classes;

import utils.Utils;

/**
 *
 * @author Juliano
 */
public class Produtor {
    private int id;
    private Municipio municipio;
    private String nome;
    private String cpf;
    private String rg;
    private String observacao;
    private Contato contato;
    private Endereco endereco;
    private boolean tipoProdutor; //0 para pessoa física e 1 para pessoa jurídica

    public Produtor(int id, Municipio municipio, String nome, String cpf, String rg, Endereco endereco, boolean tipoProdutor) {
        this.id = id;
        this.municipio = municipio;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.endereco = endereco;
        this.tipoProdutor = tipoProdutor;
    }

    public Produtor(Municipio municipio, String nome, String cpf, String rg, String observacao, Contato contato, Endereco endereco, boolean tipoProdutor) {
        this.municipio = municipio;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.observacao = observacao;
        this.contato = contato;
        this.endereco = endereco;
        this.tipoProdutor = tipoProdutor;
    }

    public Produtor(int id, Municipio municipio, String nome, String cpf, String rg) {
        this.id = id;
        this.municipio = municipio;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
    }

    public Produtor(int id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public boolean isTipoProdutor() {
        return tipoProdutor;
    }

    public void setTipoProdutor(boolean tipoProdutor) {
        this.tipoProdutor = tipoProdutor;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }
    
    public String getEmail() {
        return contato != null ? contato.getEmail() : "";
    }
    
    public String getEnderecoCompleto() {
        if(endereco.getNumero() == null) endereco.setNumero("");
        if(endereco.getTipoLogradouro() == null) endereco.setTipoLogradouro("");
        if(endereco.getLogradouro() == null) endereco.setLogradouro("");
        
        if(!endereco.getNumero().equals("")){
            return endereco.getTipoLogradouro() + " " + endereco.getLogradouro() + ", " + endereco.getNumero();
        }else{
            return endereco.getTipoLogradouro() + " " + endereco.getLogradouro();
        }
    }
    
    public String getMunicipioString() {
        return municipio.getNome();
    }
    
    public String getTelefone1() {
        return contato != null ? contato.getTelefone1() : "";
    }
    
    public String getTelefone2() {
        return contato != null ? contato.getTelefone2() : "";
    }

    @Override
    public String toString() {
        return nome + " (" + Utils.imprimeCPF(cpf) + ")";
        
    }
    
}
