package model.classes;

/**
 *
 * @author João Juliano Pinheiro
 * joaojulianopinheiro@hotmail.com
 */
public class Empresa {
    private int id,
            numeroRegistro;
    private Endereco endereco;
    private Municipio municipio;
    private Contato contato;
    private Produtor representante;
    private String razaoSocial,
            cnpj,
            observacoes;

    public Empresa(int id, int numeroRegistro, Endereco endereco, Municipio municipio, Contato contato, Produtor representante, String razaoSocial, String cnpj, String observacoes) {
        this.id = id;
        this.numeroRegistro = numeroRegistro;
        this.endereco = endereco;
        this.municipio = municipio;
        this.contato = contato;
        this.representante = representante;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.observacoes = observacoes;
    }

    public Empresa(int numeroRegistro, Endereco endereco, Municipio municipio, Contato contato, Produtor representante, String razaoSocial, String cnpj, String observacoes) {
        this.numeroRegistro = numeroRegistro;
        this.endereco = endereco;
        this.municipio = municipio;
        this.contato = contato;
        this.representante = representante;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.observacoes = observacoes;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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

    public int getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(int numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public Endereco getEndereco() {
        return endereco;
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

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public Produtor getRepresentante() {
        return representante;
    }

    public void setRepresentante(Produtor representante) {
        this.representante = representante;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return "Empresa{" + "id=" + id + ", numeroRegistro=" + numeroRegistro + ", endereco=" + endereco + ", contato=" + contato + ", representante=" + representante + ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + '}';
    }
    
}
