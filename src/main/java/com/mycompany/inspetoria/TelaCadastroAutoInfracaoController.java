package com.mycompany.inspetoria;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.classes.MotivoInfracao;
import model.classes.Municipio;
import model.classes.Produtor;
import model.classes.Veterinario;
import model.exceptions.ValidacaoException;
import model.services.AutoInfracaoService;
import model.services.ProdutorService;
import model.services.UtilitarioService;
import org.controlsfx.control.SearchableComboBox;
import utils.MascarasFX;
import utils.Utils;
import model.classes.AutoInfracao;
import model.services.MotivoInfracaoService;
import utils.Alertas;

/**
 * FXML Controller class
 *
 * @author Juliano
 */
public class TelaCadastroAutoInfracaoController implements Initializable {

    @FXML    private Button btnAtualizarHorario;
    @FXML    private Button btnCancelar;
    @FXML    private Button btnEditarProdutor;
    @FXML    private Button btnInserirMotivo;
    @FXML    private Button btnInserirMunicipio;
    @FXML    private Button btnInserirProdutor;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnSalvar;
    @FXML    private CheckBox ckbAdvertencia;
    @FXML    private CheckBox ckbDesconto;
    @FXML    private CheckBox ckbReincidente;
    @FXML    private DatePicker dpDtCiencia;
    @FXML    private DatePicker dpDtLavratura;
    @FXML    private DatePicker dpDtLimiteDefesa;
    @FXML    private Label lblErroDtLavratura;
    @FXML    private Label lblErroAutuado;
    @FXML    private Label lblErroHoraLavratura;
    @FXML    private Label lblErroMotivo;
    @FXML    private Label lblErroMunicipio;
    @FXML    private Label lblErroNumero;
    @FXML    private Label lblLoteAbelha;
    @FXML    private Label lblLoteAves;
    @FXML    private Label lblLoteCaprinos;
    @FXML    private Label lblLoteOvinos;
    @FXML    private Label lblLotePeixes;
    @FXML    private Label lblLoteSuinos;
    @FXML    private Label lblMulta;
    @FXML    private SearchableComboBox<Produtor> sComboBoxAutuado;
    @FXML    private SearchableComboBox<Veterinario> sComboBoxFEA;
    @FXML    private SearchableComboBox<MotivoInfracao> sComboBoxMotivo;
    @FXML    private SearchableComboBox<Municipio> sComboBoxMunicipio;
    @FXML    private SearchableComboBox<String> sComboBoxRedator;
    @FXML    private TextField txtAbelhas;
    @FXML    private TextField txtAves;
    @FXML    private TextField txtBovinos;
    @FXML    private TextField txtBubalinos;
    @FXML    private TextField txtCaprinos;
    @FXML    private TextField txtEnquadramentoAdicional;
    @FXML    private TextField txtEquideos;
    @FXML    private TextArea txtHistorico;
    @FXML    private TextField txtHora;
    @FXML    private TextField txtNumeroAI;
    @FXML    private TextArea txtObservacao;
    @FXML    private TextField txtOutra;
    @FXML    private TextField txtOvinos;
    @FXML    private TextField txtPeixes;
    @FXML    private TextField txtProcesso;
    @FXML    private TextField txtSuinos;
    
    private AutoInfracao autoInfracao;
    private Map<String, Integer> animaisEnvolvidos = new TreeMap<>();
    private int loteBovinos = 0;
    private int loteBubalinos = 0;
    private int loteOvinos = 0;
    private int loteCaprinos = 0;
    private int loteSuinos = 0;
    private int loteEquideos = 0;
    private int lotePeixes = 0;
    private int loteAves = 0;
    private int loteAbelhas = 0;
    List<Municipio> listaMunicipios;
    List<Produtor> listaProdutores;
    List<MotivoInfracao> listaMotivos;
    
    public void setAI (AutoInfracao ai, Map<String, Integer> animaisEnvolvidos){
        this.autoInfracao = ai;
        this.animaisEnvolvidos = animaisEnvolvidos;
        txtNumeroAI.setText(String.valueOf(ai.getNumeroAi()));
        dpDtLavratura.setValue(ai.getDataLavratura());
        sComboBoxAutuado.setValue(ai.getAutuado());
        sComboBoxMunicipio.setValue(ai.getMunicipioLavratura());
        sComboBoxMotivo.setValue(ai.getMotivo());
        dpDtCiencia.setValue(ai.getDataCiencia());
        dpDtLimiteDefesa.setValue(ai.getDataCiencia() != null ? ai.getDataCiencia().plusDays(15) : null);
        sComboBoxRedator.setValue(ai.getRedator());
        sComboBoxFEA.setValue(ai.getFea());
        txtProcesso.setText(ai.getProcesso());
        txtEnquadramentoAdicional.setText(ai.getEnquadramentoAdicional());
        txtHistorico.setText(ai.getHistorico());
        txtObservacao.setText(ai.getObservacoes());
        txtHora.setText(String.valueOf(ai.getHoraLocalTime()));
        ckbAdvertencia.setSelected(ai.isAdvertencia());
        ckbDesconto.setSelected(ai.isDesconto());
        ckbReincidente.setSelected(ai.isReincidencia());
        sComboBoxMotivo.setDisable(false);
        
        setAnimaisEnvolvidos(animaisEnvolvidos);
    }
    
    public void ajustarTela (){
        
    }
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraHorario(txtHora);
        MascarasFX.mascaraNumeroInteiro(txtBovinos);
        MascarasFX.mascaraNumeroInteiro(txtBubalinos);
        MascarasFX.mascaraNumeroInteiro(txtOvinos);
        MascarasFX.mascaraNumeroInteiro(txtCaprinos);
        MascarasFX.mascaraNumeroInteiro(txtSuinos);
        MascarasFX.mascaraNumeroInteiro(txtEquideos);
        MascarasFX.mascaraNumeroInteiro(txtPeixes);
        MascarasFX.mascaraNumeroInteiro(txtAves);
        MascarasFX.mascaraNumeroInteiro(txtAbelhas);
        MascarasFX.mascaraNumeroInteiro(txtOutra);
        MascarasFX.mascaraNumeroInteiro(txtNumeroAI);
        sComboBoxMotivo.setDisable(true);
        btnEditarProdutor.setVisible(false);
//        atualizarHorario();
        Utils.atualizarHorario(txtHora);
        dpDtLavratura.setValue(LocalDate.now());
        
        listaMunicipios = new UtilitarioService().getMunicipios();
        ObservableList<Municipio> listaObsMunicipios = FXCollections.observableArrayList(listaMunicipios);
        sComboBoxMunicipio.setItems(listaObsMunicipios);
        
        listaProdutores = new ProdutorService().getNomesECpfs(0, "");
        ObservableList<Produtor> listaObsProdutor = FXCollections.observableArrayList(listaProdutores);
        sComboBoxAutuado.setItems(listaObsProdutor);
        
        sComboBoxAutuado.setOnAction((t) -> { 
            if (sComboBoxAutuado.getValue() != null){
                sComboBoxMotivo.setDisable(false);
                btnEditarProdutor.setVisible(true);
                verificarReincidencia();
            }
        });
        
        btnEditarProdutor.setOnAction((t) -> {
            Produtor produtorAtualizado = Telas.editarProdutor(sComboBoxAutuado.getValue(), btnSalvar.getScene().getWindow());
            if (produtorAtualizado != null) {
                // O objeto foi ATUALIZADO (mutado). 
                // Para forçar o ComboBox a redesenhar o nome (caso tenha mudado),
                // removemos e readicionamos o item.
                int index = sComboBoxAutuado.getItems().indexOf(produtorAtualizado);
                if (index != -1) {
                    sComboBoxAutuado.getItems().set(index, produtorAtualizado); // Força a atualização
                }

                // Garante que ele continue selecionado
                sComboBoxAutuado.setValue(produtorAtualizado);
            }
        });
        
        listaMotivos = new MotivoInfracaoService().getAll();
        ObservableList<MotivoInfracao> listaObsMotivos = FXCollections.observableArrayList(listaMotivos);
        sComboBoxMotivo.setItems(listaObsMotivos);
        
        ObservableList<Veterinario> listaObsVeterinario = FXCollections.observableArrayList(Statics.listaVeterinarios);
        sComboBoxFEA.setItems(listaObsVeterinario);
        
        ObservableList<String> listaObsRedator = FXCollections.observableArrayList(Statics.listaRedatores);
        sComboBoxRedator.setItems(listaObsRedator);
        
        sComboBoxMunicipio.setValue(Statics.municipioPadrao);
        txtNumeroAI.setText(String.valueOf(new AutoInfracaoService().getProximoNumeroAI(sComboBoxMunicipio.getValue().getId())));
        
        txtOvinos.setOnAction((t) -> lblLoteOvinos.setText(calcularLote("ovinos", txtOvinos.getText())  + " (lote de 5)" ) );
        txtCaprinos.setOnAction((t) -> lblLoteCaprinos.setText(calcularLote("caprinos", txtCaprinos.getText()) + " (lote de 5)"));
        txtSuinos.setOnAction((t) -> lblLoteSuinos.setText(calcularLote("suinos", txtSuinos.getText()) + " (lote de 5)"));
        txtPeixes.setOnAction((t) -> lblLotePeixes.setText(calcularLote("peixes", txtPeixes.getText()) + " (lote de 1000)"));
        txtAves.setOnAction((t) -> lblLoteAves.setText(calcularLote("aves", txtAves.getText()) + " (lote de 1000)"));
        txtAbelhas.setOnAction((t) -> lblLoteAbelha.setText(calcularLote("abelhas", txtAbelhas.getText()) + " (lote de 10)"));
        ckbReincidente.setOnAction((t) -> {
            setValorMulta();
            if (ckbReincidente.isSelected()) {
                ckbAdvertencia.setDisable(true);
            } else if (!ckbReincidente.isSelected() && sComboBoxMotivo.getValue().isPreveAdv()) {
                ckbAdvertencia.setDisable(false);
            }
        });
        
        //Listeners para calcular os valores dos lotes de animais
        txtBovinos.focusedProperty().addListener((t, ov, nv) ->  { 
            if (ov && !txtBovinos.getText().equals("")) {
                loteBovinos = Integer.parseInt(txtBovinos.getText());
                animaisEnvolvidos.put("bovinos", loteBovinos);
            } else if(txtBovinos.getText().equals("")){
                loteBovinos = 0;
                animaisEnvolvidos.remove("bovinos");
            }
            setValorMulta();
        });
        txtBubalinos.focusedProperty().addListener((t, ov, nv) ->  { 
            if (ov && !txtBubalinos.getText().equals("")) {
                loteBubalinos = Integer.parseInt(txtBubalinos.getText());
                animaisEnvolvidos.put("bubalinos", loteBubalinos);
            } else if(txtBubalinos.getText().equals("")){
                loteBubalinos = 0;
                animaisEnvolvidos.remove("bubalinos");
            }
        });
        txtOvinos.focusedProperty().addListener((t, ov, nv) ->  { 
            if (ov && !txtOvinos.getText().equals("")) {
                loteOvinos = Integer.parseInt(txtOvinos.getText());
                lblLoteOvinos.setText(calcularLote("ovinos", txtOvinos.getText()) + " (lote de 5)");
                animaisEnvolvidos.put("ovinos", loteOvinos);
            } else if(txtOvinos.getText().equals("")){
                lblLoteOvinos.setText("");
                loteOvinos = 0;
                animaisEnvolvidos.remove("ovinos");
            }
            setValorMulta();
        });
        txtCaprinos.focusedProperty().addListener((t, ov, nv) ->  {
            if (ov && !txtCaprinos.getText().equals("")) {
                loteCaprinos = Integer.parseInt(txtCaprinos.getText());
                lblLoteCaprinos.setText(calcularLote("caprinos", txtCaprinos.getText()) + " (lote de 5)");
                animaisEnvolvidos.put("caprinos", loteCaprinos);
            } else if(txtCaprinos.getText().equals("")){
                lblLoteCaprinos.setText("");
                loteCaprinos = 0;
                animaisEnvolvidos.remove("caprinos");
            }
            setValorMulta();
        });
        txtSuinos.focusedProperty().addListener((t, ov, nv) ->  {
            if (ov && !txtSuinos.getText().equals("")) {
                loteSuinos = Integer.parseInt(txtSuinos.getText());
                lblLoteSuinos.setText(calcularLote("suinos", txtSuinos.getText()) + " (lote de 5)");
                animaisEnvolvidos.put("suinos", loteSuinos);
            } else if(txtSuinos.getText().equals("")){
                lblLoteSuinos.setText("");
                loteSuinos = 0;
                animaisEnvolvidos.remove("suinos");
            }
            setValorMulta();
        });
        txtEquideos.focusedProperty().addListener((t, ov, nv) ->  { 
            if (ov && !txtEquideos.getText().equals("")) {
                loteEquideos = Integer.parseInt(txtEquideos.getText());
                animaisEnvolvidos.put("equideos", loteEquideos);
            } else if(txtEquideos.getText().equals("")){
                loteEquideos = 0;
                animaisEnvolvidos.remove("equideos");
            }
            setValorMulta();
        });
        txtPeixes.focusedProperty().addListener((t, ov, nv) ->  {
            if (ov && !txtPeixes.getText().equals("")) {
                lotePeixes = Integer.parseInt(txtPeixes.getText());
                lblLotePeixes.setText(calcularLote("peixes", txtPeixes.getText()) + " (lote de 1000)");
                animaisEnvolvidos.put("peixes", lotePeixes);
            } else if(txtPeixes.getText().equals("")){
                lblLotePeixes.setText("");
                lotePeixes = 0;
                animaisEnvolvidos.remove("peixes");
            }
            setValorMulta();
        });
        txtAves.focusedProperty().addListener((t, ov, nv) ->  {
            if (ov && !txtAves.getText().equals("")) {
                loteAves = Integer.parseInt(txtAves.getText());
                lblLoteAves.setText(calcularLote("aves", txtAves.getText()) + " (lote de 1000)");
                animaisEnvolvidos.put("aves", loteAves);
            } else if(txtAves.getText().equals("")){
                lblLoteAves.setText("");
                loteAves = 0;
                animaisEnvolvidos.remove("aves");
            }
            setValorMulta();
        });
        txtAbelhas.focusedProperty().addListener((t, ov, nv) ->  {
            if (ov && !txtAbelhas.getText().equals("")) {
                loteAbelhas = Integer.parseInt(txtAbelhas.getText());
                lblLoteAbelha.setText(calcularLote("abelhas", txtAbelhas.getText()) + " (lote de 1000)");
                animaisEnvolvidos.put("abelhas", loteAbelhas);
            } else if(txtAbelhas.getText().equals("")){
                lblLoteAbelha.setText("");
                loteAbelhas = 0;
                animaisEnvolvidos.remove("abelhas");
            }
            setValorMulta();
        });
        
        sComboBoxMotivo.setOnAction((t) -> {
            setValorMulta();
            if (sComboBoxMotivo.getValue() != null) {
                ckbAdvertencia.setDisable(!sComboBoxMotivo.getValue().isPreveAdv());
                verificarReincidencia();
            }
        });
        
        ckbAdvertencia.setOnAction((t) -> setValorMulta());
        dpDtCiencia.setOnAction((t) -> {
            if(dpDtCiencia.getValue() != null){
                dpDtLimiteDefesa.setValue(dpDtCiencia.getValue().plusDays(15));
            }else{
                dpDtLimiteDefesa.setValue(null);
            }
        });
        btnAtualizarHorario.setOnAction((t) -> Utils.atualizarHorario(txtHora));
        
        btnInserirProdutor.setOnAction((t) -> {
            Produtor novoProdutor = Telas.cadastrarProdutor(btnSalvar.getScene().getWindow());
            
            // Verificar se o usuário realmente salvou um produtor (e não cancelou)
            if (novoProdutor != null) {
                // Adicionar o novo produtor à lista que alimenta o ComboBox
                sComboBoxAutuado.getItems().add(novoProdutor);
                sComboBoxAutuado.setValue(novoProdutor);
            }
        });
        
        btnCancelar.setOnAction((t) -> ((Stage) btnCancelar.getScene().getWindow()).close());
        btnLimpar.setOnAction((t) -> limparCampos());
        btnSalvar.setOnAction((t) -> salvarAI());
        btnInserirMunicipio.setOnAction((t) -> inserirMunicipio(btnCancelar.getScene().getWindow()));
        btnInserirMotivo.setOnAction((t) -> inserirMotivo(btnCancelar.getScene().getWindow()));
    }    
    
    public void inserirProdutor(Window janela) {
        Telas.cadastrarProdutor(janela);
        listaProdutores = new ProdutorService().getNomesECpfs(0, "");
        ObservableList<Produtor> listaObsProdutor = FXCollections.observableArrayList(listaProdutores);
        sComboBoxAutuado.setItems(listaObsProdutor);
    }
    
    private void limparCampos(){
        animaisEnvolvidos.clear();
        dpDtLavratura.setValue(LocalDate.now());
        txtHora.setText("");
        sComboBoxAutuado.setValue(null);
        txtProcesso.setText("");
        sComboBoxMunicipio.setValue(Statics.municipioPadrao);
        txtNumeroAI.setText(String.valueOf(new AutoInfracaoService().getProximoNumeroAI(sComboBoxMunicipio.getValue().getId())));
        sComboBoxMotivo.setValue(null);
        dpDtCiencia.setValue(null);
        dpDtLimiteDefesa.setValue(null);
        sComboBoxRedator.setValue(null);
        sComboBoxFEA.setValue(null);
        txtHistorico.setText("");
        txtObservacao.setText("");
        ckbAdvertencia.setSelected(false);
        ckbDesconto.setSelected(false);
        ckbReincidente.setSelected(false);
        txtBovinos.setText("");
        txtBubalinos.setText("");
        txtOvinos.setText("");
        txtCaprinos.setText("");
        txtSuinos.setText("");
        txtEquideos.setText("");
        txtPeixes.setText("");
        txtAves.setText("");
        txtAbelhas.setText("");
        txtOutra.setText("");
        loteOvinos = 0;
        loteCaprinos = 0;
        loteSuinos = 0;
        lotePeixes = 0;
        loteAves = 0;
        loteAbelhas = 0;
    }
    
    private void inserirMunicipio(Window janela){
        Telas.inserirMunicipio(janela);
        listaMunicipios = new UtilitarioService().getMunicipios();
        ObservableList<Municipio> listaObsMunicipios = FXCollections.observableArrayList(listaMunicipios);
        sComboBoxMunicipio.setItems(listaObsMunicipios);
    }
    
    private void inserirMotivo(Window janela) {
        Telas.inserirMotivo(janela);
        listaMotivos = new MotivoInfracaoService().getAll();
        ObservableList<MotivoInfracao> listaObsMotivos = FXCollections.observableArrayList(listaMotivos);
        sComboBoxMotivo.setItems(listaObsMotivos);
    }
    
    private void salvarAI(){
        ValidacaoException exc = new ValidacaoException("Erro validando!!");
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm");
        
        try {
            int numeroAI = Integer.parseInt(txtNumeroAI.getText().trim());
            String processo = Utils.formataDados(txtProcesso.getText());
            String observacao = txtObservacao.getText() != null ? txtObservacao.getText().trim() : "";
            String historico = txtHistorico.getText() != null ? txtHistorico.getText().trim() : "";
            String enqAdicional = txtEnquadramentoAdicional.getText() != null ? txtEnquadramentoAdicional.getText().trim() : "";
            LocalDate dtLavratura = dpDtLavratura.getValue();
            LocalTime horaLavratura = LocalTime.parse(txtHora.getText(), formatador);
            LocalDate dtCiencia = dpDtCiencia.getValue();
            String redator = sComboBoxRedator.getValue();
            Veterinario fea = sComboBoxFEA.getValue();
            boolean reincidente = ckbReincidente.isSelected();
            boolean advertencia = ckbAdvertencia.isSelected();
            boolean desconto = ckbDesconto.isSelected();
            Municipio municipio = sComboBoxMunicipio.getValue();
            Produtor autuado = sComboBoxAutuado.getValue();
            MotivoInfracao motivo = sComboBoxMotivo.getValue();
            
            if (txtNumeroAI.getText().equals("")) exc.adicionarErro("numero", "Insira um número válido para o AI!");
            if (sComboBoxMunicipio.getValue() == null) exc.adicionarErro("municipio", "Selecione o município de lavratura!");
            if (sComboBoxAutuado.getValue() == null) exc.adicionarErro("autuado", "Selecione o produtor autuado!");
            if (sComboBoxMotivo.getValue() == null) exc.adicionarErro("motivo", "Selecione o motivo da infração!");
            if (dpDtLavratura.getValue() == null) exc.adicionarErro("dtLavratura", "Insira a data de lavratura!");
            if (txtHora.getText().equals("")) exc.adicionarErro("horaLavratura", "Insira a hora da lavratura!");
            
            if (autoInfracao == null) autoInfracao = new AutoInfracao(numeroAI, dtLavratura, municipio, 
                    motivo, enqAdicional, advertencia, reincidente, desconto, autuado);
            autoInfracao.setProcesso(processo);
            autoInfracao.setObservacoes(observacao);
            autoInfracao.setHistorico(historico);
            autoInfracao.setHora(horaLavratura);
            autoInfracao.setDataCiencia(dtCiencia);
            autoInfracao.setRedator(redator);
            autoInfracao.setFea(fea);
            
            if (!exc.getErrors().isEmpty()) {
                throw exc;
            }
            
            if (new AutoInfracaoService().salvarOuAtualizar(autoInfracao, animaisEnvolvidos)) {
                // Deu certo
                // Posso fechar a janela
                ((Stage) btnCancelar.getScene().getWindow()).close();
            } else {
                // Deu erro. O retorno do boolean veio false
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Ocorreu um erro ao inserir!");
                al.showAndWait();
            }
        } catch (ValidacaoException e) {
            e.printStackTrace();
            setErrorMessages(e.getErrors());
        }
    }
    
    private void setErrorMessages(Map<String, String> errors) {
        // Pegar todos os campos de erro
        Set<String> campos = errors.keySet();

        // Mostrar o erro no label que definimos
        lblErroAutuado.setText(campos.contains("autuado") ? errors.get("autuado") : "");
        lblErroDtLavratura.setText(campos.contains("dtLavratura") ? errors.get("dtLavratura") : "");
        lblErroHoraLavratura.setText(campos.contains("horaLavratura") ? errors.get("horaLavratura") : "");
        lblErroMotivo.setText(campos.contains("motivo") ? errors.get("motivo") : "");
        lblErroMunicipio.setText(campos.contains("municipio") ? errors.get("municipio") : "");
        lblErroNumero.setText(campos.contains("numero") ? errors.get("numero") : "");
    }
    
    private void setValorMulta(){
        if (sComboBoxMotivo.getValue() != null && sComboBoxMotivo.getValue().getMultaInicial() > 0){
            float valorMulta = sComboBoxMotivo.getValue().getMultaInicial();
            float valorAdicional = 0;
            for (String especie : animaisEnvolvidos.keySet()) {
                int lotes = Integer.parseInt(calcularLote(especie, String.valueOf(animaisEnvolvidos.get(especie))));
                valorAdicional += lotes * sComboBoxMotivo.getValue().getAdicionalAnimal();
            }
            float multaTotal = Math.round(valorMulta + valorAdicional);
            if (ckbReincidente.isSelected()) multaTotal *= 2;
            lblMulta.setText(ckbAdvertencia.isSelected() ? "" : "Valor da multa: " + multaTotal + " UPF");
        }else{
            lblMulta.setText("");
        }
    }
    
    private String calcularLote(String especie, String animais){
        double animaisDouble = Double.parseDouble(animais);
        int lotes = 0;
        switch (especie){
            case "bovinos":
            case "bubalinos":
            case "equideos":
                lotes = (int) animaisDouble;
                return String.valueOf(lotes);
            case "ovinos":
            case "caprinos":
            case "suinos":
                lotes = (int) Math.ceil(animaisDouble / 5);
                return String.valueOf(lotes);            
            case "peixes":
            case "aves":
                lotes = (int) Math.ceil(animaisDouble / 1000);
                return String.valueOf(lotes);            
            case "abelhas":
                lotes = (int) Math.ceil(animaisDouble / 10);
                return String.valueOf(lotes);            
            default:
                return "";            
        }
    }
    
    private void verificarReincidencia() {
        if (sComboBoxAutuado.getValue() != null && sComboBoxMotivo.getValue() != null) {
            List<AutoInfracao> list = new AutoInfracaoService().verificarReincidencia(sComboBoxAutuado.getValue(), sComboBoxMotivo.getValue());
            if (!list.isEmpty()) {
                String titulo = "ATENÇÃO";
                String cabecalho = "Reincidência encontrada";
                String conteudo;
                String autos = "";
                if (list.size() > 1) {
                    for (AutoInfracao ai : list) {
                        autos += " " + ai.getNumeroAi() + ",";
                    }
                    conteudo = "O produtor " + sComboBoxAutuado.getValue().getNome() + " já foi autuado por esse motivo nos últimos 5 anos (Autos nº" + autos + ").";
                    Alertas.exibirInformacao(titulo, cabecalho, conteudo);
                } else {
                    autos = " " + list.get(0).getNumeroAi();
                    conteudo = "O produtor " + sComboBoxAutuado.getValue().getNome() + " já foi autuado por esse motivo nos últimos 5 anos (Auto nº" + autos + ").";
                    Alertas.exibirInformacao(titulo, cabecalho, conteudo);
                }
            }
        }
    }
    
    private void setAnimaisEnvolvidos(Map<String, Integer> animaisEnvolvidos) {
        for (String especie : animaisEnvolvidos.keySet()){
            String qtdAnimais = String.valueOf(animaisEnvolvidos.get(especie));
            
            switch (especie){
                case "bovinos":
                    txtBovinos.setText(qtdAnimais);
                    break;
                case "bubalinos":
                    txtBubalinos.setText(qtdAnimais);
                    break;
                case "equideos":
                    txtEquideos.setText(qtdAnimais);
                    break;
                case "ovinos":
                    txtOvinos.setText(qtdAnimais);
                    lblLoteOvinos.setText(calcularLote(especie, qtdAnimais) + " (lote de 5)");
                    break;
                case "caprinos":
                    txtCaprinos.setText(qtdAnimais);
                    lblLoteCaprinos.setText(calcularLote(especie, qtdAnimais) + " (lote de 5)");
                    break;
                case "suinos":
                    txtSuinos.setText(qtdAnimais);
                    lblLoteSuinos.setText(calcularLote(especie, qtdAnimais) + " (lote de 5)");
                    break;
                case "peixes":
                    txtPeixes.setText(qtdAnimais);
                    lblLotePeixes.setText(calcularLote(especie, qtdAnimais) + " (lote de 1000)");
                    break;
                case "aves":
                    txtAves.setText(qtdAnimais);
                    lblLoteAves.setText(calcularLote(especie, qtdAnimais) + " (lote de 1000)");
                    break;
                case "abelhas":
                    txtAbelhas.setText(qtdAnimais);
                    lblLoteAbelha.setText(calcularLote(especie, qtdAnimais) + " (lote de 10cx)");
                    break;
                default:
                    break;
            }
        }
        setValorMulta();
    }
}
