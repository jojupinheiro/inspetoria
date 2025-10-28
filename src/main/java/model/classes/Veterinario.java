package model.classes;

/**
 *
 * @author Juliano
 */
public class Veterinario {
    private int id;
    private String nome,
            identidadeFuncional,
            crmv;

    public Veterinario(int id, String nome, String identidadeFuncional, String crmv) {
        this.id = id;
        this.nome = nome;
        this.identidadeFuncional = identidadeFuncional;
        this.crmv = crmv;
    }

    public Veterinario(String nome, String identidadeFuncional, String crmv) {
        this.nome = nome;
        this.identidadeFuncional = identidadeFuncional;
        this.crmv = crmv;
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

    public String getIdentidadeFuncional() {
        return identidadeFuncional;
    }

    public void setIdentidadeFuncional(String identidadeFuncional) {
        this.identidadeFuncional = identidadeFuncional;
    }

    public String getCrmv() {
        return crmv;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    @Override
    public String toString() {
        return nome ;
    }
    
}
