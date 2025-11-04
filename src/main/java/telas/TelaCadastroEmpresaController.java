package telas;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import model.classes.Contato;
import model.classes.Empresa;
import model.classes.Endereco;
import model.classes.Municipio;
import model.classes.Produtor;
import model.exceptions.ValidacaoException;
import model.services.EmpresaService;
import model.services.ProdutorService;
import model.services.UtilitarioService;
import org.controlsfx.control.SearchableComboBox;
import utils.MascarasFX;
import utils.Utils;

/**
 * FXML Controller class
 *
 * @author Juliano
 */
public class TelaCadastroEmpresaController implements Initializable {

    @FXML    private AnchorPane anchorPane;
    @FXML    private Button btnCancelar;
    @FXML    private Button btnCopiarCnpj;
    @FXML    private Button btnCopiarRazaoSocial;
    @FXML    private Button btnCopiarRegistro;
    @FXML    private Button btnEditarProdutor;
    @FXML    private Button btnInserirMunicipio;
    @FXML    private Button btnInserirProdutor;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnSalvar;
    @FXML    private Label lblErroCnpj;
    @FXML    private Label lblErroMunicipio;
    @FXML    private Label lblErroRazaoSocial;
    @FXML    private Label lblErroRegistro;
    @FXML    private Label lblErroRepresentante;
    @FXML    private SearchableComboBox<String> scmbTipoLogradouro;
    @FXML    private SearchableComboBox<Municipio> scmbMunicipio;
    @FXML    private SearchableComboBox<Produtor> scmbRepresentante;
    @FXML    private TextField txtCnpj;
    @FXML    private TextField txtEmail;
    @FXML    private TextField txtLogradouro;
    @FXML    private TextField txtNumero;
    @FXML    private TextArea txtObservacao;
    @FXML    private TextField txtRazaoSocial;
    @FXML    private TextField txtRegistro;
    @FXML    private TextField txtTelefone1;
    @FXML    private TextField txtTelefone2;
    
    private List<Municipio> listaMunicipios;
    private List<Produtor> listaProdutores;
    private Empresa empresa;
    private Empresa empresaSalva;
    
    public void setEmpresa(Empresa empresa){
        this.empresa = empresa;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraNumeroInteiro(txtRegistro);
        MascarasFX.mascaraCNPJ(txtCnpj);
        MascarasFX.mascaraTelefone(txtTelefone1);
        MascarasFX.mascaraTelefone(txtTelefone2);
        
        anchorPane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                salvar();
            }
        });
        
        btnInserirProdutor.setOnAction((t) -> {
            Produtor novoProdutor = Telas.cadastrarProdutor(btnSalvar.getScene().getWindow());
            if (novoProdutor != null) {
                scmbRepresentante.getItems().add(novoProdutor);
                scmbRepresentante.setValue(novoProdutor);
            }
        });
        
        btnEditarProdutor.setOnAction((t) -> {
            Produtor produtorAtualizado = Telas.editarProdutor(scmbRepresentante.getValue(), btnSalvar.getScene().getWindow());
            if (produtorAtualizado != null) {
                int index = scmbRepresentante.getItems().indexOf(produtorAtualizado);
                if (index != -1) {
                    scmbRepresentante.getItems().set(index, produtorAtualizado); // Força a atualização
                }
                scmbRepresentante.setValue(produtorAtualizado);
            }
        });
        
        scmbRepresentante.setOnAction((t) -> { 
            if (scmbRepresentante.getValue() != null){
                btnEditarProdutor.setVisible(true);
            }
        });
        
        btnCopiarCnpj.setOnAction((t) -> {
            String textoParaCopiar = txtCnpj.getText();
            if (textoParaCopiar != null && !textoParaCopiar.isEmpty()) {
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(textoParaCopiar);
                clipboard.setContent(content);
                lblErroCnpj.setText("Copiado para a área de transferência!");
                PauseTransition pause = new PauseTransition(Duration.seconds(5));
                pause.setOnFinished(e -> {
                    lblErroCnpj.setText(""); 
                });
                pause.play();
            }
        });
        
        btnCopiarRazaoSocial.setOnAction((t) -> {
            String textoParaCopiar = txtRazaoSocial.getText();
            if (textoParaCopiar != null && !textoParaCopiar.isEmpty()) {
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(textoParaCopiar);
                clipboard.setContent(content);
                lblErroRazaoSocial.setText("Copiado para a área de transferência!");
                PauseTransition pause = new PauseTransition(Duration.seconds(5));
                pause.setOnFinished(e -> {
                    lblErroRazaoSocial.setText(""); 
                });
                pause.play();
            }
        });
        
        btnCopiarRegistro.setOnAction((t) -> {
            String textoParaCopiar = txtRegistro.getText();
            if (textoParaCopiar != null && !textoParaCopiar.isEmpty()) {
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(textoParaCopiar);
                clipboard.setContent(content);
                lblErroRegistro.setText("Copiado para a área de transferência!");
                PauseTransition pause = new PauseTransition(Duration.seconds(5));
                pause.setOnFinished(e -> {
                    lblErroRegistro.setText(""); 
                });
                pause.play();
            }
        });
        
        ObservableList<String> listaObsTipoLogradouro = FXCollections.observableArrayList("Avenida", "Estrada", "Linha", "Rua","Travessa", "Vila");
        scmbTipoLogradouro.setItems(listaObsTipoLogradouro);
        
        scmbTipoLogradouro.setOnAction((t) -> {
            if(scmbTipoLogradouro.getValue() != null){
                switch(scmbTipoLogradouro.getValue()){
                    case "Estrada":
                    case "Linha":
                    case "Travessa":
                    case "Vila":
                        txtNumero.setText("s/ Nº");
                        break;
                    default:
                        txtNumero.setText("");
                }
            }
        });
        
        listaProdutores = new ProdutorService().getNomesECpfs(0, "");
        ObservableList<Produtor> listaObsProdutor = FXCollections.observableArrayList(listaProdutores);
        scmbRepresentante.setItems(listaObsProdutor);
        
        btnSalvar.setOnAction((t) -> salvar() );
        btnCancelar.setOnAction((t) -> ((Stage) btnCancelar.getScene().getWindow()).close() );
        btnLimpar.setOnAction((t) -> limparCampos());
        btnInserirMunicipio.setOnAction((t) -> inserirMunicipio(btnCancelar.getScene().getWindow()));
        
        listaMunicipios = new UtilitarioService().getMunicipios();
        ObservableList<Municipio> listaObsMunicipios = FXCollections.observableArrayList(listaMunicipios);
        scmbMunicipio.setItems(listaObsMunicipios);
    }    
    
    private void salvar() {
        ValidacaoException exc = new ValidacaoException("Erro validando!!");

        try {
            Produtor produtor = null;
            if (scmbRepresentante.getValue() == null){
                exc.adicionarErro("representante", "Selecione um representante!");
            }else{
                produtor = new ProdutorService().getProdutor(scmbRepresentante.getValue());
            }
            
            String razaoSocial = txtRazaoSocial.getText();
            if (razaoSocial.equals("")) exc.adicionarErro("razaoSocial", "Insira a razão social da empresa!");
            int registro = 0;
            try {
                registro = Integer.parseInt(txtRegistro.getText());
            } catch (Exception e) {
                exc.adicionarErro("registro", "Insira o n");
            }

            String cnpj = Utils.formataDados(txtCnpj.getText());
            if (cnpj.equals("")) exc.adicionarErro("cnpj", "Insira o cnpj da empresa!");
            Municipio municipio = scmbMunicipio.getValue();
            if (municipio == null) exc.adicionarErro("municipio", "Selecione o município da empresa!");
            String observacao = txtObservacao.getText();
            
            String tipoLogradouro = scmbTipoLogradouro.getValue();
            String logradouro = txtLogradouro.getText();
            String numero = txtNumero.getText();
            Endereco endereco = new Endereco(tipoLogradouro, logradouro, numero);
            
            String telefone1 = txtTelefone1.getText();
            String telefone2 = txtTelefone2.getText();
            String email = txtEmail.getText();
            Contato contato = new Contato(telefone1, telefone2, email);
            
            if (empresa == null){
                empresa = new Empresa(registro, endereco, municipio, contato, produtor, razaoSocial, cnpj, observacao);
            }else{
                empresa = new Empresa(empresa.getId(), registro, endereco, municipio, contato, produtor, razaoSocial, cnpj, observacao);
            }
            
            
            if (!exc.getErrors().isEmpty()) {
                throw exc;
            }

            if (new EmpresaService().salvarOuAtualizar(empresa)) {
                this.empresaSalva = this.empresa;
                ((Stage) btnCancelar.getScene().getWindow()).close();
            } else {
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
    
    private void inserirMunicipio(Window janela){
        Telas.inserirMunicipio(janela);
        listaMunicipios = new UtilitarioService().getMunicipios();
        ObservableList<Municipio> listaObsMunicipios = FXCollections.observableArrayList(listaMunicipios);
        scmbMunicipio.setItems(listaObsMunicipios);
    }
    
    private void limparCampos(){
        txtRazaoSocial.setText("");
        txtRegistro.setText("");
        txtCnpj.setText("");
        scmbRepresentante.setValue(null);
        scmbTipoLogradouro.setValue(null);
        txtLogradouro.setText("");
        txtNumero.setText("");
        scmbMunicipio.setValue(Statics.municipioPadrao);
        txtTelefone1.setText("");
        txtTelefone2.setText("");
        txtEmail.setText("");
        txtObservacao.setText("");
        empresa = null;
        btnEditarProdutor.setVisible(false);
    }
    
    public Empresa getEmpresaSalva(){
        return this.empresaSalva;
    }
    
    private void setErrorMessages(Map<String, String> errors) {
        // Pegar todos os campos de erro
        Set<String> campos = errors.keySet();

        // Mostrar o erro no label que definimos
        lblErroCnpj.setText(campos.contains("vnpj") ? errors.get("vnpj") : "");
        lblErroRazaoSocial.setText(campos.contains("razaoSocial") ? errors.get("razaoSocial") : "");
        lblErroRegistro.setText(campos.contains("registro") ? errors.get("registro") : "");
        lblErroMunicipio.setText(campos.contains("municipio") ? errors.get("municipio") : "");
        lblErroRepresentante.setText(campos.contains("representante") ? errors.get("representante") : "");
    }

}
