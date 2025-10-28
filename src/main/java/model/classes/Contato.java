package model.classes;

/**
 *
 * @author João Juliano Pinheiro
 * joaojulianopinheiro@hotmail.com
 */
public class Contato {
    private int id;
    private String telefone1,
            telefone2,
            email;

    public Contato() {
    }

    public Contato(int id, String telefone1, String telefone2, String email) {
        this.id = id;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.email = email;
    }

    public Contato(String telefone1, String telefone2, String email) {
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contato{" + "id=" + id + ", telefone1=" + telefone1 + ", telefone2=" + telefone2 + ", email=" + email + '}';
    }
    
    
}
