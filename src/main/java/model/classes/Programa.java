package model.classes;

/**
 *
 * @author João Juliano Pinheiro
 * joaojulianopinheiro@hotmail.com
 */
public class Programa {
    int id;
    String sigla,
            nome,
            observacao;

    public Programa(int id, String sigla, String nome, String observacao) {
        this.id = id;
        this.sigla = sigla;
        this.nome = nome;
        this.observacao = observacao;
    }

    public Programa(String sigla, String nome, String observacao) {
        this.sigla = sigla;
        this.nome = nome;
        this.observacao = observacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return sigla;
    }
    
}
