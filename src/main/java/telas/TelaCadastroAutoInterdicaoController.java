package telas;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.classes.AutoInterdicao;
import model.classes.MotivoInfracao;
import model.classes.Municipio;
import model.classes.Produtor;
import model.classes.Programa;
import model.classes.Veterinario;
import model.exceptions.ValidacaoException;
import model.services.AutoInfracaoService;
import model.services.AutoInterdicaoService;
import model.services.ProdutorService;
import model.services.ProgramaService;
import model.services.UtilitarioService;
import org.controlsfx.control.SearchableComboBox;
import utils.MascarasFX;
import utils.Utils;

/**
 * FXML Controller class
 *
 * @author Juliano
 */
public class TelaCadastroAutoInterdicaoController implements Initializable {

    @FXML    private AnchorPane anchorPane;
    @FXML    private Button btnAtualizarDataCiencia;
    @FXML    private Button btnAtualizarHorario;
    @FXML    private Button btnCancelar;
    @FXML    private Button btnEditarProdutor;
    @FXML    private Button btnInserirMotivo;
    @FXML    private Button btnInserirMunicipio;
    @FXML    private Button btnInserirProdutor;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnSalvar;
    @FXML    private DatePicker dpDtCiencia;
    @FXML    private DatePicker dpDtDesinterdicao;
    @FXML    private DatePicker dpDtLavratura;
    @FXML    private Label lblAno;
    @FXML    private Label lblErroAutuado;
    @FXML    private Label lblErroDtLavratura;
    @FXML    private Label lblErroHoraLavratura;
    @FXML    private Label lblErroMotivo;
    @FXML    private Label lblErroMunicipio;
    @FXML    private Label lblErroNumero;
    @FXML    private SearchableComboBox<Produtor> scmbAutuado;
    @FXML    private SearchableComboBox<Veterinario> scmbFEA;
    @FXML    private SearchableComboBox<Programa> scmbMotivo;
    @FXML    private SearchableComboBox<Municipio> scmbMunicipio;
    @FXML    private TextField txtHora;
    @FXML    private TextField txtNumeroAI;
    @FXML    private TextArea txtObservacao;
    
    AutoInterdicao ai;
    AutoInterdicao aiSalvo;
    private int idMunicipio;
    private int proximoNumeroAI;
    private int anoAuto;
    private List<Municipio> listaMunicipios;
    private List<Produtor> listaProdutores;
    private List<Programa> listaProgramas;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraData(dpDtCiencia);
        MascarasFX.mascaraData(dpDtDesinterdicao);
        MascarasFX.mascaraData(dpDtLavratura);
        MascarasFX.mascaraHorario(txtHora);
        Utils.atualizarHorario(txtHora);
        MascarasFX.mascaraNumeroInteiro(txtNumeroAI);
        btnEditarProdutor.setVisible(false);
        
        btnAtualizarDataCiencia.setOnAction((t) -> {
            if (dpDtLavratura.getValue() != null){
                dpDtCiencia.setValue(dpDtLavratura.getValue());
            }
        });
        
        listaMunicipios = new UtilitarioService().getMunicipios();
        ObservableList<Municipio> listaObsMunicipios = FXCollections.observableArrayList(listaMunicipios);
        scmbMunicipio.setItems(listaObsMunicipios);
        scmbMunicipio.setValue(Statics.municipioPadrao);
        
        listaProgramas = new ProgramaService().getAll();
        ObservableList<Programa> listaObsProgramas = FXCollections.observableArrayList(listaProgramas);
        scmbMotivo.setItems(listaObsProgramas);

        ObservableList<Veterinario> listaObsVeterinario = FXCollections.observableArrayList(Statics.listaVeterinarios);
        scmbFEA.setItems(listaObsVeterinario);
        
        listaProdutores = new ProdutorService().getNomesECpfs(0, "");
        ObservableList<Produtor> listaObsProdutor = FXCollections.observableArrayList(listaProdutores);
        scmbAutuado.setItems(listaObsProdutor);
        
        dpDtLavratura.setValue(LocalDate.now());
        idMunicipio = Statics.municipioPadrao.getId();
        int anoAtual = LocalDate.now().getYear();
        lblAno.setText("/" + anoAtual);
        proximoNumeroAI = new AutoInterdicaoService().getProximoNumeroAI(idMunicipio, anoAtual);
        txtNumeroAI.setText(String.valueOf(proximoNumeroAI));
        
        btnInserirProdutor.setOnAction((t) -> {
            Produtor novoProdutor = Telas.cadastrarProdutor(btnSalvar.getScene().getWindow());
            
            // Verificar se o usuário realmente salvou um produtor (e não cancelou)
            if (novoProdutor != null) {
                // Adicionar o novo produtor à lista que alimenta o ComboBox
                scmbAutuado.getItems().add(novoProdutor);
                scmbAutuado.setValue(novoProdutor);
            }
        });
        
        scmbAutuado.setOnAction((t) -> { 
            if (scmbAutuado.getValue() != null){
                btnEditarProdutor.setVisible(true);
            }
        });
        
        btnEditarProdutor.setOnAction((t) -> {
            Produtor produtorAtualizado = Telas.editarProdutor(scmbAutuado.getValue(), btnSalvar.getScene().getWindow());
            if (produtorAtualizado != null) {
                // O objeto foi ATUALIZADO (mutado). 
                // Para forçar o ComboBox a redesenhar o nome (caso tenha mudado),
                // removemos e readicionamos o item.
                int index = scmbAutuado.getItems().indexOf(produtorAtualizado);
                if (index != -1) {
                    scmbAutuado.getItems().set(index, produtorAtualizado); // Força a atualização
                }

                // Garante que ele continue selecionado
                scmbAutuado.setValue(produtorAtualizado);
            }
        });
        
        btnInserirMunicipio.setOnAction((t) -> inserirMunicipio(btnCancelar.getScene().getWindow()));
        btnSalvar.setOnAction((t) -> salvar());
        btnCancelar.setOnAction((t) -> ((Stage) btnCancelar.getScene().getWindow()).close());
        btnLimpar.setOnAction((t) -> limpaCampos());
        scmbMunicipio.setOnAction((t) -> calcularNumeroDoAuto());
        dpDtLavratura.setOnAction((t) -> calcularNumeroDoAuto());
        btnAtualizarHorario.setOnAction((t) -> Utils.atualizarHorario(txtHora));
    }    
    
    public AutoInterdicao getAISalvo(){
        return aiSalvo;
    }
    
    private void calcularNumeroDoAuto() {
        if (scmbMunicipio.getValue() != null && dpDtLavratura.getValue() != null) {
            idMunicipio = scmbMunicipio.getValue().getId();
            anoAuto = dpDtLavratura.getValue().getYear();
            proximoNumeroAI = new AutoInterdicaoService().getProximoNumeroAI(idMunicipio, anoAuto);
            txtNumeroAI.setText(String.valueOf(proximoNumeroAI));
            lblAno.setText("/" + anoAuto);
        }
    }
    
    private void inserirMunicipio(Window janela){
        Telas.inserirMunicipio(janela);
        listaMunicipios = new UtilitarioService().getMunicipios();
        ObservableList<Municipio> listaObsMunicipios = FXCollections.observableArrayList(listaMunicipios);
        scmbMunicipio.setItems(listaObsMunicipios);
    }
    
    private void limpaCampos(){
        scmbMunicipio.setValue(Statics.municipioPadrao);
        dpDtLavratura.setValue(LocalDate.now());
        calcularNumeroDoAuto();
        scmbAutuado.setValue(null);
        btnEditarProdutor.setVisible(false);
        Utils.atualizarHorario(txtHora);
        scmbMunicipio.setValue(null);
        dpDtDesinterdicao.setValue(null);
        dpDtCiencia.setValue(null);
        scmbMotivo.setValue(null);
        scmbFEA.setValue(null);
        txtObservacao.setText("");
        ai = null;
    }
    
    private void salvar(){
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm");
        ValidacaoException exc = new ValidacaoException("Erro validando!!");
        
        try {
            int numeroAI = Integer.parseInt(txtNumeroAI.getText());
            LocalDate dtLavratura = dpDtLavratura.getValue();
            LocalDate dtCiencia = dpDtCiencia.getValue();
            LocalDate dtDesinterdicao = dpDtDesinterdicao.getValue();
            Produtor produtor = scmbAutuado.getValue();
            LocalTime horaLavratura = LocalTime.parse(txtHora.getText(), formatador);
            Municipio municipioLavratura = scmbMunicipio.getValue();
            Programa programa = scmbMotivo.getValue();
            Veterinario fea = scmbFEA.getValue();
            String observacao = txtObservacao.getText();
            
            if (txtNumeroAI.getText().equals("")) exc.adicionarErro("numero", "Insira um número válido para o AI!");
            if (scmbMunicipio.getValue() == null) exc.adicionarErro("municipio", "Selecione o município de lavratura!");
            if (scmbAutuado.getValue() == null) exc.adicionarErro("autuado", "Selecione o produtor autuado!");
            if (scmbMotivo.getValue() == null) exc.adicionarErro("motivo", "Selecione o motivo da infração!");
            if (dpDtLavratura.getValue() == null) exc.adicionarErro("dtLavratura", "Insira a data de lavratura!");
            if (txtHora.getText().equals("")) exc.adicionarErro("horaLavratura", "Insira a hora da lavratura!");
            
            if (ai == null){
                ai = new AutoInterdicao(numeroAI, municipioLavratura, programa, produtor, fea, dtLavratura, dtCiencia, dtDesinterdicao, horaLavratura, observacao);
            }else{
                int id = ai.getId();
                ai = new AutoInterdicao(id, numeroAI, municipioLavratura, programa, produtor, fea, dtLavratura, dtCiencia, dtDesinterdicao, horaLavratura, observacao);
            }
            
            if (!exc.getErrors().isEmpty()) {
                throw exc;
            }

            if (new AutoInterdicaoService().salvarOuAtualizar(ai)) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Sucesso");
                al.setContentText("Auto de Interdição inserido com sucesso!");
                al.showAndWait();
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
}
