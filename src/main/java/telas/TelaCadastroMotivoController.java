package telas;

import java.net.URL;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import model.classes.MotivoInfracao;
import model.exceptions.ValidacaoException;
import model.services.MotivoInfracaoService;
import utils.MascarasFX;

/**
 * FXML Controller class
 *
 * @author Juliano
 */
public class TelaCadastroMotivoController implements Initializable {
    
    @FXML    private Button btnCancelar;
    @FXML    private Button btnExcluir;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnSalvar;
    @FXML    private CheckBox ckbAdvertencia;
    @FXML    private ListView<MotivoInfracao> listView;
    @FXML    private RadioButton rbDecretoOutro;
    @FXML    private RadioButton rbDecretoPrincipal;
    @FXML    private RadioButton rbLeiOutra;
    @FXML    private RadioButton rbLeiPrincipal;
    @FXML    private RadioButton rbPenalidadeOutra;
    @FXML    private RadioButton rbPenalidadePrincipal;
    @FXML    private TextField txtAdicionalAnimal;
    @FXML    private TextField txtArtigoDecreto;
    @FXML    private TextField txtArtigoLei;
    @FXML    private TextField txtArtigoPenalidade;
    @FXML    private TextField txtDecretoOutro;
    @FXML    private TextArea txtDescricao;
    @FXML    private TextField txtLeiOutra;
    @FXML    private TextField txtMultaInicial;
    @FXML    private TextField txtPenalidadeOutra;
    @FXML    private TextArea txtResumo;
    
    private MotivoInfracao motivoInfracao;
    private List<MotivoInfracao> listaMotivos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraNumero(txtMultaInicial);
        MascarasFX.mascaraNumero(txtAdicionalAnimal);
        btnExcluir.setVisible(false);
        rbLeiPrincipal.setSelected(true);
        rbDecretoPrincipal.setSelected(true);
        rbPenalidadePrincipal.setSelected(true);
        
        txtLeiOutra.setDisable(true);
        txtDecretoOutro.setDisable(true);
        txtPenalidadeOutra.setDisable(true);
        
        listView.setCellFactory(lv -> new ListCell<MotivoInfracao>() {
            @Override
            protected void updateItem(MotivoInfracao motivoInfracao, boolean empty) {
                super.updateItem(motivoInfracao, empty);
                if (empty || motivoInfracao == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(motivoInfracao.getResumoDescricao());
                }
            }
        });
        
        listView.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                btnExcluir.setVisible(true);
            }
        });
        
        btnExcluir.setOnAction((t) -> {
            if(listView.getSelectionModel().getSelectedIndex() >= 0){
                new MotivoInfracaoService().excluir(listView.getSelectionModel().getSelectedItem());
            }
            listarMotivos();
            btnExcluir.setVisible(false);
        });
                
        rbLeiOutra.selectedProperty().addListener((t, ov, nv) -> txtLeiOutra.setDisable(ov));
        rbDecretoOutro.selectedProperty().addListener((t, ov, nv) -> txtDecretoOutro.setDisable(ov));
        rbPenalidadeOutra.selectedProperty().addListener((t, ov, nv) -> txtPenalidadeOutra.setDisable(ov));
        
        btnSalvar.setOnAction((t) -> salvar() );
        btnCancelar.setOnAction((t) -> ((Stage) btnCancelar.getScene().getWindow()).close());
        btnLimpar.setOnAction((t) -> limparCampos());
        
        listarMotivos();
    }    
    
    private void salvar(){
        ValidacaoException exc = new ValidacaoException("Erro validando!!");
        
        try {
            String lei = rbLeiPrincipal.isSelected() ? "Lei Estadual nº 13.467/2010" : txtLeiOutra.getText().trim();
            String decreto = rbDecretoPrincipal.isSelected() ? "Decreto Estadual nº 52.434/2015" : txtDecretoOutro.getText().trim();
            String penalidade = rbPenalidadePrincipal.isSelected() ? "Decreto Estadual nº 52.434/2015" : txtPenalidadeOutra.getText().trim();
            String artigoLei = txtArtigoLei.getText().trim();
            String artigoDecreto = txtArtigoDecreto.getText().trim();
            String artigoPenalidade = txtArtigoPenalidade.getText().trim();
            String descricao = txtDescricao.getText().trim();
            String resumoDescricao = txtResumo.getText().trim();
            boolean preveAdv = ckbAdvertencia.isSelected();
            float multaInicial = Float.parseFloat(txtMultaInicial.getText());
            float adicionalAnimal = Float.parseFloat(txtAdicionalAnimal.getText());
            
            if (motivoInfracao == null) motivoInfracao = new MotivoInfracao(preveAdv, lei, artigoLei, decreto, 
                    artigoDecreto, penalidade, artigoPenalidade, multaInicial, adicionalAnimal, descricao, resumoDescricao);
            
            if (!exc.getErrors().isEmpty()) {
                throw exc;
            }
            
            if (new MotivoInfracaoService().salvarOuAtualizar(motivoInfracao)) {
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
    
    private void limparCampos(){
        ckbAdvertencia.setSelected(false);
        txtAdicionalAnimal.setText("");
        txtArtigoDecreto.setText("");
        txtArtigoLei.setText("");
        txtArtigoPenalidade.setText("");
        txtDecretoOutro.setText("");
        txtDescricao.setText("");
        txtLeiOutra.setText("");
        txtMultaInicial.setText("");
        txtPenalidadeOutra.setText("");
        txtResumo.setText("");
        rbDecretoPrincipal.setSelected(true);
        rbLeiPrincipal.setSelected(true);
        rbPenalidadePrincipal.setSelected(true);
    }
    
     private void setErrorMessages(Map<String, String> errors) {
        // Pegar todos os campos de erro
        Set<String> campos = errors.keySet();

        // Mostrar o erro no label que definimos
//        lblErroAutuado.setText(campos.contains("autuado") ? errors.get("autuado") : "");

    }
     
     private void listarMotivos() {                                                                                                                                               
        this.listaMotivos = new MotivoInfracaoService().getAll();                                                                                                             
        ObservableList<MotivoInfracao> listaObsMotivos = FXCollections.observableArrayList(listaMotivos);                                                                         
        listView.setItems(listaObsMotivos);                                                                                                                                
    }
}
