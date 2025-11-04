package model.classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

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
            dataCiencia,
            dataDesinterdicao;
    LocalTime horaLavratura;
    String observacoes;

    public AutoInterdicao(int id, int numero, Municipio municipio, Programa programa, Produtor produtor, Veterinario veterinario, LocalDate dataLavratura, LocalDate dataCiencia, LocalDate dataDesinterdicao, LocalTime horaLavratura, String observacoes) {
        this.id = id;
        this.numero = numero;
        this.municipio = municipio;
        this.programa = programa;
        this.produtor = produtor;
        this.veterinario = veterinario;
        this.dataLavratura = dataLavratura;
        this.dataCiencia = dataCiencia;
        this.dataDesinterdicao = dataDesinterdicao;
        this.horaLavratura = horaLavratura;
        this.observacoes = observacoes;
    }

    public AutoInterdicao(int numero, Municipio municipio, Programa programa, Produtor produtor, Veterinario veterinario, LocalDate dataLavratura, LocalDate dataCiencia, LocalDate dataDesinterdicao, LocalTime horaLavratura, String observacoes) {
        this.numero = numero;
        this.municipio = municipio;
        this.programa = programa;
        this.produtor = produtor;
        this.veterinario = veterinario;
        this.dataLavratura = dataLavratura;
        this.dataCiencia = dataCiencia;
        this.dataDesinterdicao = dataDesinterdicao;
        this.horaLavratura = horaLavratura;
        this.observacoes = observacoes;
    }

    public LocalDate getDataDesinterdicao() {
        return dataDesinterdicao;
    }

    public void setDataDesinterdicao(LocalDate dataDesinterdicao) {
        this.dataDesinterdicao = dataDesinterdicao;
    }

    public LocalTime getHoraLavratura() {
        return horaLavratura;
    }

    public void setHoraLavratura(LocalTime horaLavratura) {
        this.horaLavratura = horaLavratura;
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
    
    public String getNumeroCompleto() {
        if (numero > 1000) {
            return String.valueOf(numero);
        }
        
        // %03d significa:
        // % = Inicia o formatador
        // 0 = Preencha com zeros à esquerda
        // 3 = Tenha uma largura total de 3 caracteres
        // d = O valor é um inteiro decimal (int)
        String numeroFormatado = String.format("%03d", numero);

        return numeroFormatado + "/" + dataLavratura.getYear();
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AutoInterdicao other = (AutoInterdicao) obj;
        return this.id == other.id;
    }

}
