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
import javafx.scene.control.RadioButton;
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
import model.classes.Endereco;
import model.classes.Municipio;
import model.classes.Produtor;
import model.exceptions.ValidacaoException;
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
public class TelaCadastroProdutorController implements Initializable {
    
    @FXML    AnchorPane anchorPane;
    @FXML    private Button btnCancelar;
    @FXML    private Button btnCopiarCpf;
    @FXML    private Button btnCopiarNome;
    @FXML    private Button btnInserirMunicipio;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnSalvar;
    @FXML    private Label lblErroCPF;
    @FXML    private Label lblErroMunicipio;
    @FXML    private Label lblErroNome;
    @FXML    private Label lblErroTipoPessoa;
    @FXML    private RadioButton rbPF;
    @FXML    private RadioButton rbPJ;
    @FXML    private SearchableComboBox<Municipio> sComboBoxMunicipio;
    @FXML    private SearchableComboBox<String> sComboBoxTipoLogradouro;
    @FXML    private TextField txtCpf;
    @FXML    private TextField txtEmail;
    @FXML    private TextField txtLogradouro;
    @FXML    private TextField txtNome;
    @FXML    private TextField txtNumero;
    @FXML    private TextArea txtObservacao;
    @FXML    private TextField txtRg;
    @FXML    private TextField txtTelefone1;
    @FXML    private TextField txtTelefone2;
    
    List<Municipio> listaMunicipios;
    private Produtor produtor;
    private Produtor produtorSalvo = null;
    
    public void setProdutor(Produtor produtor) {
        this.produtor = produtor;
        produtor = new ProdutorService().getProdutor(produtor);
        txtNome.setText(produtor.getNome());
        txtRg.setText(produtor.getRg());
        if (String.valueOf(produtor.getCpf()).length() == 11) {
            rbPF.setSelected(true);
        } else {
            rbPJ.setSelected(true);
        }
        if (String.valueOf(produtor.getCpf()).length() == 11) {
            txtCpf.setText(Utils.imprimeCPF(String.valueOf(produtor.getCpf())));    
        } else {
            txtCpf.setText(Utils.imprimeCNPJ(String.valueOf(produtor.getCpf())));
        }
        txtLogradouro.setText(produtor.getEndereco() != null ? produtor.getEndereco().getLogradouro() : "");
        txtNumero.setText(produtor.getEndereco() != null ? produtor.getEndereco().getNumero() : ""); 
        sComboBoxMunicipio.getSelectionModel().select(produtor.getMunicipio());
        txtObservacao.setText(produtor.getObservacao()); 
        txtTelefone1.setText(produtor.getContato() != null ? produtor.getContato().getTelefone1() : "");
        txtTelefone2.setText(produtor.getContato() != null ? produtor.getContato().getTelefone2() : "");
        txtEmail.setText(produtor.getContato() != null ? produtor.getContato().getEmail(): "");
        sComboBoxTipoLogradouro.getSelectionModel().select(produtor.getEndereco() != null ? produtor.getEndereco().getTipoLogradouro() : null);
        txtCpf.setDisable(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraTelefone(txtTelefone1);
        MascarasFX.mascaraTelefone(txtTelefone2);
        MascarasFX.mascaraCPF(txtCpf);
        
        anchorPane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                salvar();
            }
        });
        
        txtRg.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) sComboBoxTipoLogradouro.requestFocus();
        });
        sComboBoxTipoLogradouro.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) txtLogradouro.requestFocus();
        });
        txtNumero.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) sComboBoxMunicipio.requestFocus();
        });
        sComboBoxMunicipio.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) txtTelefone1.requestFocus();
        });
        
        rbPF.setOnAction((t) -> {
            txtCpf.setEditable(true);
            txtCpf.setDisable(false);
            MascarasFX.mascaraCPF(txtCpf);
        });

        rbPJ.setOnAction((t) -> {
            txtCpf.setEditable(true);
            txtCpf.setDisable(false);
            MascarasFX.mascaraCNPJ(txtCpf);
        });
        
        btnCopiarCpf.setOnAction((t) -> {
            String textoParaCopiar = txtCpf.getText();

            if (textoParaCopiar != null && !textoParaCopiar.isEmpty()) {

                // Pega a área de transferência do sistema
                final Clipboard clipboard = Clipboard.getSystemClipboard();

                // Cria um objeto para guardar o conteúdo
                final ClipboardContent content = new ClipboardContent();

                // Adiciona o texto ao conteúdo
                content.putString(textoParaCopiar);

                // Define o conteúdo na área de transferência
                clipboard.setContent(content);

                lblErroCPF.setText("Copiado para a área de transferência!");

                // Cria uma pausa de 5 segundos
                PauseTransition pause = new PauseTransition(Duration.seconds(5));

                // Define o que fazer quando a pausa terminar
                pause.setOnFinished(e -> {
                    // Limpa o texto do label
                    lblErroCPF.setText(""); 
                });

                // Inicia a contagem
                pause.play();
            }
        });
        
        btnCopiarNome.setOnAction((t) -> {
            String textoParaCopiar = txtNome.getText();

            if (textoParaCopiar != null && !textoParaCopiar.isEmpty()) {
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(textoParaCopiar);
                clipboard.setContent(content);
                lblErroNome.setText("Copiado para a área de transferência!");
                PauseTransition pause = new PauseTransition(Duration.seconds(5));
                pause.setOnFinished(e -> {
                    lblErroNome.setText(""); 
                });
                pause.play();
            }
        });
        
        ObservableList<String> listaObsTipoLogradouro = FXCollections.observableArrayList("Avenida", "Estrada", "Linha", "Rua","Travessa", "Vila");
        sComboBoxTipoLogradouro.setItems(listaObsTipoLogradouro);
        
        sComboBoxTipoLogradouro.setOnAction((t) -> {
            if(sComboBoxTipoLogradouro.getValue() != null){
                switch(sComboBoxTipoLogradouro.getValue()){
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
        
        btnInserirMunicipio.setOnAction((t) -> inserirMunicipio(btnCancelar.getScene().getWindow()));
        List<Municipio> listaMunicipios = new UtilitarioService().getMunicipios();
        ObservableList<Municipio> listaObsMunicipios = FXCollections.observableArrayList(listaMunicipios);
        sComboBoxMunicipio.setItems(listaObsMunicipios);
        
        btnCancelar.setOnAction((t) -> ((Stage) btnCancelar.getScene().getWindow()).close() );
        btnLimpar.setOnAction((t) -> limparCampos() );
        btnSalvar.setOnAction((t) -> salvar() );
        
        rbPF.setSelected(true);
    }    
    
    private void inserirMunicipio(Window janela){
        Telas.inserirMunicipio(janela);
        listaMunicipios = new UtilitarioService().getMunicipios();
        ObservableList<Municipio> listaObsMunicipios = FXCollections.observableArrayList(listaMunicipios);
        sComboBoxMunicipio.setItems(listaObsMunicipios);
    }
    
    private void limparCampos(){
        txtNome.setText("");
        txtCpf.setText("");
        txtRg.setText("");
        rbPF.setSelected(true);
        rbPJ.setSelected(false);
        sComboBoxTipoLogradouro.getSelectionModel().select("");
        sComboBoxMunicipio.getSelectionModel().select(null);
        txtLogradouro.setText("");
        txtNumero.setText("");
        txtTelefone1.setText("");
        txtTelefone2.setText("");
        txtEmail.setText("");
        txtObservacao.setText("");
    }
    
    private void salvar(){
        ValidacaoException exc = new ValidacaoException("Erro validando!!");
        
        try {
            String nomeProdutor = txtNome.getText().trim();
            String cpfProdutor = Utils.formataDados(txtCpf.getText());
            String rgProdutor = Utils.formataDados(txtRg.getText());
            String observacao = txtObservacao.getText() != null ? txtObservacao.getText().trim() : "";
            
            String tipoLogradouro = sComboBoxTipoLogradouro.getValue();
            String numero = txtNumero.getText() != null ? txtNumero.getText().trim() : "";
            String logradouro = txtLogradouro.getText() != null ? txtLogradouro.getText().trim() : "";
            Endereco endereco = new Endereco(tipoLogradouro, logradouro, numero);
            
            String telefone1 = Utils.formataDados(txtTelefone1.getText());
            String telefone2 = Utils.formataDados(txtTelefone2.getText());
            String email = txtEmail.getText() != null ? txtEmail.getText().trim() : "";
            Contato contato = new Contato(telefone1, telefone2, email);
            
            if (nomeProdutor.equals("")) exc.adicionarErro("Nome", "Insira um nome!");
            if (cpfProdutor.equals("")) exc.adicionarErro("cpf", "Insira um CPF ou CNPJ válido!");
            if (rbPF.isSelected() == false && rbPJ.isSelected() == false) exc.adicionarErro("tipoPessoa", "Selecione se é PF ou PJ");
            if (sComboBoxMunicipio.getValue() == null) exc.adicionarErro("municipio", "Selecione o município do produtor!");
                        
            Municipio municipio = sComboBoxMunicipio.getValue();
            boolean tipoProdutor;  //true para pessoa jurídica, false para pessoa física
            tipoProdutor = !rbPF.isSelected();
            
            if (produtor == null) {
                produtor = new Produtor(municipio, nomeProdutor, cpfProdutor, rgProdutor, observacao, contato, endereco, tipoProdutor);
            }else{
                produtor.setNome(nomeProdutor);
                produtor.setCpf(cpfProdutor);
                produtor.setRg(rgProdutor);
                produtor.setObservacao(observacao);
                produtor.setEndereco(endereco);
                produtor.setContato(contato);
                produtor.setMunicipio(municipio);
                produtor.setTipoProdutor(tipoProdutor);
            }
            if (!exc.getErrors().isEmpty()) {
                throw exc;
            }
            
            if (new ProdutorService().salvarOuAtualizar(produtor)) {
                // Deu certo
                // Posso fechar a janela
                this.produtorSalvo = this.produtor;
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
    
    public Produtor getProdutorSalvo() {
        return this.produtorSalvo;
    }
    
    private void setErrorMessages(Map<String, String> errors) {
        // Pegar todos os campos de erro
        Set<String> campos = errors.keySet();

        // Mostrar o erro no label que definimos
        lblErroNome.setText(campos.contains("Nome") ? errors.get("Nome") : "");
        lblErroCPF.setText(campos.contains("cpf") ? errors.get("cpf") : "");
        lblErroTipoPessoa.setText(campos.contains("tipoPessoa") ? errors.get("tipoPessoa") : "");
        lblErroMunicipio.setText(campos.contains("municipio") ? errors.get("municipio") : "");
    }
}
