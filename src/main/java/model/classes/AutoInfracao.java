package model.classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Juliano
 */
public class AutoInfracao {
    private int id;
    private int numeroAi;
    private LocalDate dataLavratura,
            dataCiencia;
    private LocalTime hora;
    private Municipio municipioLavratura;
    private MotivoInfracao motivo;
    private String enquadramentoAdicional,
            historico,
            observacoes,
            processo,
            redator;
    private boolean advertencia, 
            reincidencia, 
            desconto;
    private Produtor autuado;
    private Veterinario fea;

    public AutoInfracao(int numeroAi, int id, LocalDate dataLavratura, Municipio municipioLavratura, MotivoInfracao motivo, String enquadramentoAdicional, boolean advertencia, boolean reincidencia, boolean desconto, Produtor autuado) {
        this.numeroAi = numeroAi;
        this.id = id;
        this.dataLavratura = dataLavratura;
        this.municipioLavratura = municipioLavratura;
        this.motivo = motivo;
        this.enquadramentoAdicional = enquadramentoAdicional;
        this.advertencia = advertencia;
        this.reincidencia = reincidencia;
        this.desconto = desconto;
        this.autuado = autuado;
    }

    public AutoInfracao(int numeroAi, LocalDate dataLavratura, Municipio municipioLavratura, MotivoInfracao motivo, String enquadramentoAdicional, boolean advertencia, boolean reincidencia, boolean desconto, Produtor autuado) {
        this.numeroAi = numeroAi;
        this.dataLavratura = dataLavratura;
        this.municipioLavratura = municipioLavratura;
        this.motivo = motivo;
        this.enquadramentoAdicional = enquadramentoAdicional;
        this.advertencia = advertencia;
        this.reincidencia = reincidencia;
        this.desconto = desconto;
        this.autuado = autuado;
    }

    public AutoInfracao(int id, int numeroAi, LocalDate dataLavratura, Municipio municipioLavratura, MotivoInfracao motivo, boolean advertencia, boolean reincidencia, boolean desconto, Produtor autuado) {
        this.id = id;
        this.numeroAi = numeroAi;
        this.dataLavratura = dataLavratura;
        this.municipioLavratura = municipioLavratura;
        this.motivo = motivo;
        this.advertencia = advertencia;
        this.reincidencia = reincidencia;
        this.desconto = desconto;
        this.autuado = autuado;
    }

    public AutoInfracao(int id, LocalDate dataLavratura, int numeroAi) {
        this.id = id;
        this.dataLavratura = dataLavratura;
        this.numeroAi = numeroAi;
    }

    public LocalDate getDataCiencia() {
        return dataCiencia;
    }

    public void setDataCiencia(LocalDate dataCiencia) {
        this.dataCiencia = dataCiencia;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroAi() {
        return numeroAi;
    }

    public void setNumeroAi(int numeroAi) {
        this.numeroAi = numeroAi;
    }

    public LocalDate getDataLavratura() {
        return dataLavratura;
    }

    public void setDataLavratura(LocalDate dataLavratura) {
        this.dataLavratura = dataLavratura;
    }

    public Municipio getMunicipioLavratura() {
        return municipioLavratura;
    }

    public void setMunicipioLavratura(Municipio municipioLavratura) {
        this.municipioLavratura = municipioLavratura;
    }

    public MotivoInfracao getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivoInfracao motivo) {
        this.motivo = motivo;
    }

    public String getEnquadramentoAdicional() {
        return enquadramentoAdicional;
    }

    public void setEnquadramentoAdicional(String enquadramentoAdicional) {
        this.enquadramentoAdicional = enquadramentoAdicional;
    }

    public boolean isAdvertencia() {
        return advertencia;
    }

    public void setAdvertencia(boolean advertencia) {
        this.advertencia = advertencia;
    }

    public boolean isReincidencia() {
        return reincidencia;
    }

    public void setReincidencia(boolean reincidencia) {
        this.reincidencia = reincidencia;
    }

    public boolean isDesconto() {
        return desconto;
    }

    public void setDesconto(boolean desconto) {
        this.desconto = desconto;
    }

    public Produtor getAutuado() {
        return autuado;
    }

    public void setAutuado(Produtor autuado) {
        this.autuado = autuado;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public Veterinario getFea() {
        return fea;
    }

    public void setFea(Veterinario fea) {
        this.fea = fea;
    }
    
    public String getCpf() {
        return autuado.getCpf();
    }
    
    public LocalDate getDataLimiteDefesa() {
        return dataCiencia != null ?  dataCiencia.plusDays(15) : null;
    }
    
    public String getNomeFea() {
        if (fea == null) {
            return null;
        }
        return fea.getNome();
    }
    
    public String getReincidente() {
        return reincidencia ? "Sim" : "Não";
    }
    
    public String getPossuiDesconto() {
        return desconto ? "Sim" : "Não";
    }
    
    public String getEAdvertencia() {
        return advertencia ? "Sim" : "Não";
    }
    
    public String getNomeAutuado() {
        return autuado.getNome();
    }
    
    public String getHora(){
        if (hora == null) {
            return null;
        }
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm");
        return hora.format(formatador);
    }
    
    public LocalTime getHoraLocalTime(){
        return hora;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }
    
    public String getResumoMotivo() {
        return motivo.getResumoDescricao();
    }
    
    public String getNomeMunicipio() {
        return municipioLavratura.getNome();
    }
    
    public String getNomeMunicipioAutuado() {
        return autuado.getMunicipioString();
    }

    public String getRedator() {
        return redator;
    }

    public void setRedator(String redator) {
        this.redator = redator;
    }
    
}
