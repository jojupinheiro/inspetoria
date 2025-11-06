package telas;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Window;
import model.classes.Empresa;
import model.classes.MotivoInfracao;
import model.classes.Municipio;
import model.classes.Empresa;
import model.classes.Produtor;
import model.services.EmpresaService;
import model.services.ProdutorService;
import utils.Utils;

/**
 * FXML Controller class
 *
 * @author Juliano
 */
public class TelaListaEmpresasController implements Initializable {

    @FXML    private Button btnAtualizar;
    @FXML    private Button btnFiltrar;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnNovo;
    @FXML    private ComboBox<String> cmbFiltro;
    @FXML    private TableView<Empresa> tblEmpresas;
    @FXML    private TableColumn<Empresa, String> tcCnpj;
    @FXML    private TableColumn<Empresa, String> tcEndereco;
    @FXML    private TableColumn<Empresa, Municipio> tcMunicipio;
    @FXML    private TableColumn<Empresa, String> tcObservacao;
    @FXML    private TableColumn<Empresa, String> tcRazaoSocial;
    @FXML    private TableColumn<Empresa, Integer> tcRegistro;
    @FXML    private TableColumn<Empresa, Produtor> tcRepresentante;
    @FXML    private TextField txtBusca;

    private List<Empresa> listaEmpresas;
    private ObservableList<Empresa> observableListaEmpresas = FXCollections.observableArrayList();
    private FilteredList<Empresa> filteredListaEmpresas;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcCnpj.setCellValueFactory(new PropertyValueFactory<>("Cnpj"));
        tcEndereco.setCellValueFactory(new PropertyValueFactory<>("EnderecoCompleto"));
        tcMunicipio.setCellValueFactory(new PropertyValueFactory<>("Municipio"));
        tcObservacao.setCellValueFactory(new PropertyValueFactory<>("Observacoes"));
        tcRazaoSocial.setCellValueFactory(new PropertyValueFactory<>("RazaoSocial"));
        tcRegistro.setCellValueFactory(new PropertyValueFactory<>("NumeroRegistro"));
        tcRepresentante.setCellValueFactory(new PropertyValueFactory<>("Representante"));
        
        tblEmpresas.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) && tblEmpresas.getSelectionModel().getSelectedItem() != null) {
                Empresa empresa = tblEmpresas.getSelectionModel().getSelectedItem();
                Window janela = btnFiltrar.getScene().getWindow();
                Telas.cadastrarEmpresa(empresa, janela);
                atualizaTabela();
            }
        });
        
        btnNovo.setOnAction((t) -> {
            Empresa novaEmpresa = Telas.cadastrarEmpresa(null, btnNovo.getScene().getWindow());
            if (novaEmpresa != null) {
                observableListaEmpresas.add(novaEmpresa);
            }
        });

        ObservableList<String> listaObs = FXCollections.observableArrayList("CNPJ", "Município", "Razão Social", "Registro");
        cmbFiltro.setItems(listaObs);
        
        btnAtualizar.setOnAction((t) -> carregarDadosEConfigurarFiltros());
        
        carregarDadosEConfigurarFiltros();
    }   
    
    public void atualizaTabela() {
        listaEmpresas = new EmpresaService().getAll();
        observableListaEmpresas.setAll(listaEmpresas);
        Utils.formatTableColumnCpfOuCnpj(tcCnpj);
    }
    
     private void carregarDadosEConfigurarFiltros() {
        // 1. Carrega TODOS os produtores do banco
        listaEmpresas = new EmpresaService().getAll();
        observableListaEmpresas.setAll(listaEmpresas);

        // 2. Envelopa a lista principal em uma FilteredList
        filteredListaEmpresas = new FilteredList<>(observableListaEmpresas, p -> true);

        // 3. Adiciona listeners para o ComboBox e o TextField
        //    Qualquer alteração neles chamará o método aplicarFiltro()
        cmbFiltro.valueProperty().addListener((obs, oldV, newV) -> aplicarFiltro());
        txtBusca.textProperty().addListener((obs, oldV, newV) -> aplicarFiltro());

        // 4. Envelopa a FilteredList em uma SortedList
        //    Isso permite que o usuário clique nos cabeçalhos das colunas para ordenar
        SortedList<Empresa> sortedData = new SortedList<>(filteredListaEmpresas);

        // 5. Vincula o comparador da SortedList com o da TableView
        sortedData.comparatorProperty().bind(tblEmpresas.comparatorProperty());

        // 6. Define a SortedList como os itens da tabela
        tblEmpresas.setItems(sortedData);
        Utils.formatTableColumnCpfOuCnpj(tcCnpj);
    }

    private void aplicarFiltro() {
        String filtroSelecionado = cmbFiltro.getValue();
        String textoBusca = txtBusca.getText();

        filteredListaEmpresas.setPredicate(empresa -> {

            // Caso 1: Nenhum filtro selecionado
            if (filtroSelecionado == null) {
                return true; // Mostra todos
            }

            // Caso 3: Filtros que USAM o texto de busca
            // Se o texto estiver vazio, mostra todos (para os filtros de texto)
            if (textoBusca == null || textoBusca.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = textoBusca.toLowerCase().trim();

            switch (filtroSelecionado) {
                case "CNPJ":
                    return empresa.getCnpj()!= null && empresa.getCnpj().contains(lowerCaseFilter);
                case "Município":
                    // Assumindo que você tem um getMunicipioString() ou similar
                    return empresa.getMunicipio().getNome()!= null && empresa.getMunicipio().getNome().toLowerCase().contains(lowerCaseFilter);
                case "Razão Social":
                    return empresa.getRazaoSocial()!= null && empresa.getRazaoSocial().toLowerCase().contains(lowerCaseFilter);
                case "Registro":
                    return empresa.getNumeroRegistro()!= 0 && String.valueOf(empresa.getNumeroRegistro()).toLowerCase().contains(lowerCaseFilter);
                default:
                    return false;
            }
        });
    }
    
    @FXML    void onLimparFiltros(ActionEvent event) {
        cmbFiltro.getSelectionModel().clearSelection();
        txtBusca.clear();
    }
}
