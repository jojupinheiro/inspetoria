package model.classes;

/**
 *
 * @author Juliano
 */
public class Municipio {
    private int id;
    private String nome;
    private String codIbge;

    public Municipio(int id, String nome, String codIbge) {
        this.id = id;
        this.nome = nome;
        this.codIbge = codIbge;
    }

    public Municipio(String nome, String codIbge) {
        this.nome = nome;
        this.codIbge = codIbge;
    }

    public Municipio(String nome) {
        this.nome = nome;
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

    public String getCodIbge() {
        return codIbge;
    }

    public void setCodIbge(String codIbge) {
        this.codIbge = codIbge;
    }

    @Override
    public String toString() {
        return nome ;
    }
    
    
}
