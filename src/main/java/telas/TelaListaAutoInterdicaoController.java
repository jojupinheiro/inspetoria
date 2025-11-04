package telas;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Window;
import model.classes.AutoInterdicao;
import model.classes.Municipio;
import model.classes.Produtor;
import model.classes.Programa;
import model.classes.Veterinario;
import model.services.AutoInterdicaoService;
import model.services.ProdutorService;
import model.services.UtilitarioService;
import utils.Utils;

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
    @FXML    private TableColumn<AutoInterdicao, LocalDate> tCDesinterdicao;
    @FXML    private TableColumn<AutoInterdicao, Veterinario> tCFEA;
    @FXML    private TableColumn<AutoInterdicao, Programa> tCMotivo;
    @FXML    private TableColumn<AutoInterdicao, Municipio> tCMunicipioAutuado;
    @FXML    private TableColumn<AutoInterdicao, Municipio> tCMunicipioLavratura;
    @FXML    private TableColumn<AutoInterdicao, String> tCNumero;
    @FXML    private TableColumn<AutoInterdicao, String> tCObs;
    @FXML    private TableColumn<AutoInterdicao, Produtor> tCProdutor;
    @FXML    private TableView<AutoInterdicao> tblAutosInterdicao;
    @FXML    private TextField txtBusca;
    
    List<AutoInterdicao> listaAIs;
    private ObservableList<AutoInterdicao> observableListaAIs = FXCollections.observableArrayList();
    private FilteredList<AutoInterdicao> filteredListaAIs;
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
        tCDesinterdicao.setCellValueFactory(new PropertyValueFactory<>("DataDesinterdicao"));
        tCFEA.setCellValueFactory(new PropertyValueFactory<>("Veterinario"));
        tCMotivo.setCellValueFactory(new PropertyValueFactory<>("Programa"));
        tCMunicipioAutuado.setCellValueFactory(new PropertyValueFactory<>("MunicipioAutuado"));
        tCMunicipioLavratura.setCellValueFactory(new PropertyValueFactory<>("Municipio"));
        tCNumero.setCellValueFactory(new PropertyValueFactory<>("NumeroCompleto"));
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
        

        
        tblAutosInterdicao.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) && tblAutosInterdicao.getSelectionModel().getSelectedItem() != null) {
                AutoInterdicao ai = tblAutosInterdicao.getSelectionModel().getSelectedItem();
                Window janela = btnFiltrar.getScene().getWindow();
                Telas.cadastrarAutoInterdicao(ai, janela);
                atualizaTabela();
            }
        });
        
        ObservableList<String> listaObs = FXCollections.observableArrayList("CPF/CNPJ", "Município do Produtor", "Nome", "Programa");
        cmbFiltro.setItems(listaObs);
        
        ObservableList<Programa> listaObsProgramas = FXCollections.observableArrayList(Statics.listaProgramas);
        cmbMotivos.setItems(listaObsProgramas);
        
        carregarDadosEConfigurarFiltros();
    }    
    
    private void cadastrarAutoInterdicao(){
        AutoInterdicao novoAI = Telas.cadastrarAutoInterdicao(null, btnNovo.getScene().getWindow());
        if (novoAI != null){
            tblAutosInterdicao.getItems().add(novoAI);
        }
        atualizaTabela();
    }
    
    public void atualizaTabela() {
        listaAIs = new AutoInterdicaoService().getAll(-1, null); // -1 e null para buscar tudo
        observableListaAIs.setAll(listaAIs);
        Utils.formatTableColumnDate(tCData);
        Utils.formatTableColumnDate(tCDtCiencia);
        Utils.formatTableColumnDate(tCDesinterdicao);
        Utils.formatTableColumnCpfOuCnpj(tCCpf);
    }
    
    private void carregarDadosEConfigurarFiltros() {
        // 1. Carrega TODOS os produtores do banco
        listaAIs = new AutoInterdicaoService().getAll(-1, null); // -1 e null para buscar tudo
        observableListaAIs.setAll(listaAIs);

        // 2. Envelopa a lista principal em uma FilteredList
        filteredListaAIs = new FilteredList<>(observableListaAIs, p -> true);

        // 3. Adiciona listeners para o ComboBox e o TextField
        //    Qualquer alteração neles chamará o método aplicarFiltro()
        cmbFiltro.valueProperty().addListener((obs, oldV, newV) -> aplicarFiltro());
        txtBusca.textProperty().addListener((obs, oldV, newV) -> aplicarFiltro());
        cmbMotivos.valueProperty().addListener((obs, oldV, newV) -> aplicarFiltro());

        // 4. Envelopa a FilteredList em uma SortedList
        //    Isso permite que o usuário clique nos cabeçalhos das colunas para ordenar
        SortedList<AutoInterdicao> sortedData = new SortedList<>(filteredListaAIs);

        // 5. Vincula o comparador da SortedList com o da TableView
        sortedData.comparatorProperty().bind(tblAutosInterdicao.comparatorProperty());

        // 6. Define a SortedList como os itens da tabela
        tblAutosInterdicao.setItems(sortedData);
        Utils.formatTableColumnDate(tCData);
        Utils.formatTableColumnDate(tCDtCiencia);
        Utils.formatTableColumnDate(tCDesinterdicao);
        Utils.formatTableColumnCpfOuCnpj(tCCpf);
    }
    
    private void aplicarFiltro() {
        String filtroSelecionado = cmbFiltro.getValue();
        String textoBusca = txtBusca.getText();
        Programa programaSelecionado = cmbMotivos.getValue(); // Pega o valor 1 vez

        filteredListaAIs.setPredicate(auto -> {
            
            // Caso 1: Nenhum filtro selecionado
            if (filtroSelecionado == null) {
                return true; // Mostra todos
            }

            // Caso 2: Filtro por "Programa"
            if (filtroSelecionado.equals("Programa")) {
                // Se nenhum programa foi selecionado no ComboBox, mostra tudo
                if (programaSelecionado == null) {
                    return true;
                }
                // Se o auto não tiver programa, não mostra
                if (auto.getPrograma() == null) {
                    return false;
                }
                // Compara usando .equals() (ou por ID, ex: auto.getPrograma().getId().equals(programaSelecionado.getId()))
                return auto.getPrograma().equals(programaSelecionado);
            }

            // Caso 3: Filtros que USAM o texto de busca
            
            // Se o texto estiver vazio, mostra todos (para os filtros de texto)
            if (textoBusca == null || textoBusca.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = textoBusca.toLowerCase().trim();

            switch (filtroSelecionado) {
                case "CPF/CNPJ":
                    return auto.getCpf() != null && auto.getCpf().contains(lowerCaseFilter);
                case "Município do Produtor":
                    // Assumindo que você tem um getMunicipioString() ou similar
                    return auto.getProdutor().getMunicipioString() != null && auto.getProdutor().getMunicipioString().toLowerCase().contains(lowerCaseFilter);
                case "Nome":
                    return auto.getProdutor().getNome() != null && auto.getProdutor().getNome().toLowerCase().contains(lowerCaseFilter);
                default:
                    return false; 
            }
        });
    }
    
}
