package model.classes;

import java.time.LocalDate;
import utils.Utils;

/**
 *
 * @author Juliano
 */
public class DeclaracaoComplementar {
    int numero,
            id;
    LocalDate data;
    Produtor produtor;
    String observacoes;
    Municipio municipioPropriedade;

    public DeclaracaoComplementar(int numero, int id, LocalDate data, Produtor produtor, String observacoes, Municipio municipioPropriedade) {
        this.numero = numero;
        this.id = id;
        this.data = data;
        this.produtor = produtor;
        this.observacoes = observacoes;
        this.municipioPropriedade = municipioPropriedade;
    }

    public DeclaracaoComplementar(int numero, LocalDate data, Produtor produtor, String observacoes, Municipio municipioPropriedade) {
        this.numero = numero;
        this.data = data;
        this.produtor = produtor;
        this.observacoes = observacoes;
        this.municipioPropriedade = municipioPropriedade;
    }

    public Municipio getMunicipioPropriedade() {
        return municipioPropriedade;
    }

    public void setMunicipioPropriedade(Municipio municipioPropriedade) {
        this.municipioPropriedade = municipioPropriedade;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Produtor getProdutor() {
        return produtor;
    }

    public String getProdutorString() {
        return produtor.getNome() + " (" + Utils.imprimeCPFouCNPJ(produtor.getCpf()) + ")";
    }
    
    public void setProdutor(Produtor produtor) {
        this.produtor = produtor;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public String toString() {
        return "DeclaracaoComplementar{" + "numero=" + numero + ", id=" + id + ", data=" + data + ", produtor=" + produtor + ", observacoes=" + observacoes + ", municipioPropriedade=" + municipioPropriedade + '}';
    }

}
