package telas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.classes.AutoInterdicao;
import org.controlsfx.control.SearchableComboBox;

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
    @FXML    private Label lblErroAutuado;
    @FXML    private Label lblErroDtLavratura;
    @FXML    private Label lblErroHoraLavratura;
    @FXML    private Label lblErroMotivo;
    @FXML    private Label lblErroMunicipio;
    @FXML    private Label lblErroNumero;
    @FXML    private SearchableComboBox<?> scmbAutuado;
    @FXML    private SearchableComboBox<?> scmbFEA;
    @FXML    private SearchableComboBox<?> scmbMotivo;
    @FXML    private SearchableComboBox<?> scmbMunicipio;
    @FXML    private TextField txtHora;
    @FXML    private TextField txtNumeroAI;
    @FXML    private TextArea txtObservacao;
    
    AutoInterdicao aiSalvo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public AutoInterdicao getAISalvo(){
        return aiSalvo;
    }
}
