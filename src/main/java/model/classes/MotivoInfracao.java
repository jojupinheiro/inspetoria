package model.classes;

/**
 *
 * @author João Juliano Pinheiro
 * joaojulianopinheiro@hotmail.com
 */
public class MotivoInfracao {
    int id;
    boolean preveAdv;
    String lei, 
            artLei, 
            decreto, 
            artDecreto, 
            penalidade, 
            artPenalidade;
    float multaInicial;
    float adicionalAnimal;
    String descricao,
            resumoDescricao;

    public MotivoInfracao(int id, boolean preveAdv, String lei, String artLei, String decreto, String artDecreto, String penalidade, String artPenalidade, float multaInicial, float adicionalAnimal, String descricao, String resumoDescricao) {
        this.id = id;
        this.preveAdv = preveAdv;
        this.lei = lei;
        this.artLei = artLei;
        this.decreto = decreto;
        this.artDecreto = artDecreto;
        this.penalidade = penalidade;
        this.artPenalidade = artPenalidade;
        this.multaInicial = multaInicial;
        this.adicionalAnimal = adicionalAnimal;
        this.descricao = descricao;
        this.resumoDescricao = resumoDescricao;
    }

    public MotivoInfracao(boolean preveAdv, String lei, String artLei, String decreto, String artDecreto, String penalidade, String artPenalidade, float multaInicial, float adicionalAnimal, String descricao, String resumoDescricao) {
        this.preveAdv = preveAdv;
        this.lei = lei;
        this.artLei = artLei;
        this.decreto = decreto;
        this.artDecreto = artDecreto;
        this.penalidade = penalidade;
        this.artPenalidade = artPenalidade;
        this.multaInicial = multaInicial;
        this.adicionalAnimal = adicionalAnimal;
        this.descricao = descricao;
        this.resumoDescricao = resumoDescricao;
    }

    public MotivoInfracao(int id, float multaInicial, float adicionalAnimal, String resumoDescricao, boolean preveAdv) {
        this.id = id;
        this.multaInicial = multaInicial;
        this.adicionalAnimal = adicionalAnimal;
        this.resumoDescricao = resumoDescricao;
        this.preveAdv = preveAdv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPreveAdv() {
        return preveAdv;
    }

    public void setPreveAdv(boolean preveAdv) {
        this.preveAdv = preveAdv;
    }

    public String getLei() {
        return lei;
    }

    public void setLei(String lei) {
        this.lei = lei;
    }

    public String getArtLei() {
        return artLei;
    }

    public void setArtLei(String artLei) {
        this.artLei = artLei;
    }

    public String getDecreto() {
        return decreto;
    }

    public void setDecreto(String decreto) {
        this.decreto = decreto;
    }

    public String getArtDecreto() {
        return artDecreto;
    }

    public void setArtDecreto(String artDecreto) {
        this.artDecreto = artDecreto;
    }

    public String getPenalidade() {
        return penalidade;
    }

    public void setPenalidade(String penalidade) {
        this.penalidade = penalidade;
    }

    public String getArtPenalidade() {
        return artPenalidade;
    }

    public void setArtPenalidade(String artPenalidade) {
        this.artPenalidade = artPenalidade;
    }

    public float getMultaInicial() {
        return multaInicial;
    }

    public void setMultaInicial(float multaInicial) {
        this.multaInicial = multaInicial;
    }

    public float getAdicionalAnimal() {
        return adicionalAnimal;
    }

    public void setAdicionalAnimal(float adicionalAnimal) {
        this.adicionalAnimal = adicionalAnimal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getResumoDescricao() {
        return resumoDescricao;
    }

    public void setResumoDescricao(String resumoDescricao) {
        this.resumoDescricao = resumoDescricao;
    }

    @Override
    public String toString() {
        return resumoDescricao;
    }
    
}
