package model.classes;

/**
 *
 * @author Juliano
 */
public class Propriedade {
    int id;
    Endereco endereco;
    Contato contato;
    String codigo;

    public Propriedade() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Endereco getEndereco() {
        return endereco;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Propriedade{" + "id=" + id + ", endereco=" + endereco + ", contato=" + contato + ", codigo=" + codigo + '}';
    }
    
}
