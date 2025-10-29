package telas;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.classes.AutoInterdicao;
import model.classes.Municipio;
import model.classes.Produtor;
import model.classes.Programa;
import model.classes.Veterinario;
import model.services.UtilitarioService;

/**
 * FXML Controller class
 *
 * @author Juliano
 */
public class TelaListaAutoInterdicaoController implements Initializable {
    
    @FXML    private Button btnAtualizar;
    @FXML    private Button btnFiltrar;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnNovo;
    @FXML    private ComboBox<String> cmbFiltro;
    @FXML    private ComboBox<Programa> cmbMotivos;
    @FXML    private TableColumn<AutoInterdicao, String> tCCpf;
    @FXML    private TableColumn<AutoInterdicao, LocalDate> tCData;
    @FXML    private TableColumn<AutoInterdicao, LocalDate> tCDtCiencia;
    @FXML    private TableColumn<AutoInterdicao, Veterinario> tCFEA;
    @FXML    private TableColumn<AutoInterdicao, Programa> tCMotivo;
    @FXML    private TableColumn<AutoInterdicao, Municipio> tCMunicipioAutuado;
    @FXML    private TableColumn<AutoInterdicao, Municipio> tCMunicipioLavratura;
    @FXML    private TableColumn<AutoInterdicao, String> tCNumero;
    @FXML    private TableColumn<AutoInterdicao, String> tCObs;
    @FXML    private TableColumn<AutoInterdicao, Produtor> tCProdutor;
    @FXML    private TableView<AutoInterdicao> tblAutosInterdicao;
    @FXML    private TextField txtBusca;
    
    private String txtFiltro;
    private int filtroSelecionado = -1;
    List<AutoInterdicao> listaAutosInterdicao;
    private ObservableList<AutoInterdicao> observableListaAutosInterdicao = FXCollections.observableArrayList();
    private FilteredList<AutoInterdicao> filteredListaAutosInterdicao;
    
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tCCpf.setCellValueFactory(new PropertyValueFactory<>("Cpf"));
        tCData.setCellValueFactory(new PropertyValueFactory<>("DataLavratura"));
        tCDtCiencia.setCellValueFactory(new PropertyValueFactory<>("DataCiencia"));
        tCFEA.setCellValueFactory(new PropertyValueFactory<>("Veterinario"));
        tCMotivo.setCellValueFactory(new PropertyValueFactory<>("Programa"));
        tCMunicipioAutuado.setCellValueFactory(new PropertyValueFactory<>("MunicipioAutuado"));
        tCMunicipioLavratura.setCellValueFactory(new PropertyValueFactory<>("Municipio"));
        tCNumero.setCellValueFactory(new PropertyValueFactory<>("Numero"));
        tCObs.setCellValueFactory(new PropertyValueFactory<>("Observacoes"));
        tCProdutor.setCellValueFactory(new PropertyValueFactory<>("Produtor"));
        
        cmbMotivos.setVisible(false);
        cmbMotivos.setManaged(false);
        
        btnNovo.setOnAction((t) -> cadastrarAutoInterdicao() );
        
        cmbFiltro.setOnAction((t) -> {
            try {
                filtroSelecionado = cmbFiltro.getSelectionModel().getSelectedIndex();
                if(filtroSelecionado == 3){
                    txtBusca.setVisible(false);
                    txtBusca.setManaged(false);
                    cmbMotivos.setVisible(true);
                    cmbMotivos.setManaged(true);
                }else {
                    txtBusca.setVisible(true);
                    txtBusca.setManaged(true);
                    cmbMotivos.setVisible(false);
                    cmbMotivos.setManaged(false);
                }

            } catch (Exception e) {
                filtroSelecionado = 0;
                e.printStackTrace();
            }
        });
        
        ObservableList<String> listaObs = FXCollections.observableArrayList("CPF/CNPJ", "Município do Produtor", "Nome", "Programa");
        cmbFiltro.setItems(listaObs);
        
        ObservableList<Programa> listaObsProgramas = FXCollections.observableArrayList(Statics.listaProgramas);
        cmbMotivos.setItems(listaObsProgramas);
    }    
    
    private void cadastrarAutoInterdicao(){
        AutoInterdicao novoAI = Telas.cadastrarAutoInterdicao(btnNovo.getScene().getWindow());
        if (novoAI != null){
            tblAutosInterdicao.getItems().add(novoAI);
        }
    }
    
}
