package model.classes;

import java.time.LocalDate;

/**
 *
 * @author Juliano
 */
public class AutoInterdicao {
    int id,
            numero;
    Municipio municipio;
    Programa programa;
    Produtor produtor;
    Veterinario veterinario;
    LocalDate dataLavratura,
            dataCiencia;
    String observacoes;

    public AutoInterdicao(int id, int numero, Municipio municipio, Programa programa, Produtor produtor, Veterinario veterinario, LocalDate dataLavratura, LocalDate dataCiencia, String observacoes) {
        this.id = id;
        this.numero = numero;
        this.municipio = municipio;
        this.programa = programa;
        this.produtor = produtor;
        this.veterinario = veterinario;
        this.dataLavratura = dataLavratura;
        this.dataCiencia = dataCiencia;
        this.observacoes = observacoes;
    }

    public AutoInterdicao(int numero, Municipio municipio, Programa programa, Produtor produtor, Veterinario veterinario, LocalDate dataLavratura, LocalDate dataCiencia, String observacoes) {
        this.numero = numero;
        this.municipio = municipio;
        this.programa = programa;
        this.produtor = produtor;
        this.veterinario = veterinario;
        this.dataLavratura = dataLavratura;
        this.dataCiencia = dataCiencia;
        this.observacoes = observacoes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Municipio getMunicipio() {
        return municipio;
    }
    
    public Municipio getMunicipioAutuado() {
        return produtor.getMunicipio();
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public Produtor getProdutor() {
        return produtor;
    }
    
    public String getCpf(){
        return produtor.getCpf();
    }

    public void setProdutor(Produtor produtor) {
        this.produtor = produtor;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public LocalDate getDataLavratura() {
        return dataLavratura;
    }

    public void setDataLavratura(LocalDate dataLavratura) {
        this.dataLavratura = dataLavratura;
    }

    public LocalDate getDataCiencia() {
        return dataCiencia;
    }

    public void setDataCiencia(LocalDate dataCiencia) {
        this.dataCiencia = dataCiencia;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public String toString() {
        return "AutoInterdicao{" + "id=" + id + ", numero=" + numero + ", municipio=" + municipio + ", programa=" + programa + ", produtor=" + produtor + ", veterinario=" + veterinario + ", dataLavratura=" + dataLavratura + ", dataCiencia=" + dataCiencia + ", observacoes=" + observacoes + '}';
    }
    
}
