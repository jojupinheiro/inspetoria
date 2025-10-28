package model.classes;

/**
 *
 * @author Juliano
 */
public class Endereco {
    private int id;
    private String tipoLogradouro,
            logradouro,
            numero;

    public Endereco(int id, String tipoLogradouro, String logradouro, String numero) {
        this.id = id;
        this.tipoLogradouro = tipoLogradouro;
        this.logradouro = logradouro;
        this.numero = numero;
    }

    public Endereco(String tipoLogradouro, String logradouro, String numero) {
        this.tipoLogradouro = tipoLogradouro;
        this.logradouro = logradouro;
        this.numero = numero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoLogradouro() {
        return tipoLogradouro;
    }

    public void setTipoLogradouro(String tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Endereco{" + "id=" + id + ", tipoLogradouro=" + tipoLogradouro + ", logradouro=" + logradouro + ", numero=" + numero + '}';
    }
    
}
