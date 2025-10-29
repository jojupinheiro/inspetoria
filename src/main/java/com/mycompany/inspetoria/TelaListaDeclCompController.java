package com.mycompany.inspetoria;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.classes.DeclaracaoComplementar;
import model.classes.Municipio;
import model.classes.Produtor;
import model.services.DeclaracaoComplementarService;
import model.services.ProdutorService;
import model.services.UtilitarioService;
import org.controlsfx.control.SearchableComboBox;
import utils.Utils;

/**
 * FXML Controller class
 *
 * @author joaojuliano
 */
public class TelaListaDeclCompController implements Initializable {

    @FXML    private Button btnAtualizar;
    @FXML    private Button btnEditarProdutor;
    @FXML    private Button btnFiltrar;
    @FXML    private Button btnInserirDC;
    @FXML    private Button btnInserirProdutor;
    @FXML    private Button btnLimpar;
    @FXML    private ComboBox<String> cmbFiltro;
    @FXML    private ComboBox<Municipio> cmbMunicipio;
    @FXML    private DatePicker dpData;
    @FXML    private SearchableComboBox<Produtor> scmbProdutor;
    @FXML    private TableView<DeclaracaoComplementar> tblComplementar;
    @FXML    private TableColumn<DeclaracaoComplementar, LocalDate> tcData;
    @FXML    private TableColumn<DeclaracaoComplementar, String> tcNumero;
    @FXML    private TableColumn<DeclaracaoComplementar, String> tcObservacoes;
    @FXML    private TableColumn<DeclaracaoComplementar, String> tcProdutor;
    @FXML    private TextField txtBusca;
    @FXML    private TextField txtNumero;
    @FXML    private TextField txtObservacoes;
    
    List<Municipio> listaMunicipios;
    List<Produtor> listaProdutores;
    List<DeclaracaoComplementar> listaDc;
    private ObservableList<DeclaracaoComplementar> observableListaDc = FXCollections.observableArrayList();
    private FilteredList<DeclaracaoComplementar> filteredListaDc;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcData.setCellValueFactory(new PropertyValueFactory<>("Data"));
        tcNumero.setCellValueFactory(new PropertyValueFactory<>("Numero"));
        tcObservacoes.setCellValueFactory(new PropertyValueFactory<>("Observacoes"));
        tcProdutor.setCellValueFactory(new PropertyValueFactory<>("ProdutorString"));
        
        btnEditarProdutor.setVisible(false);
        dpData.setValue(LocalDate.now());
        
        ObservableList<String> listaObs = FXCollections.observableArrayList("CPF/CNPJ", "Município da propriedade", "Nome", "Possui observação");
        cmbFiltro.setItems(listaObs);
        
        listaProdutores = new ProdutorService().getNomesECpfs(0, "");
        ObservableList<Produtor> listaObsProdutor = FXCollections.observableArrayList(listaProdutores);
        scmbProdutor.setItems(listaObsProdutor);
        
        scmbProdutor.setOnAction((t) -> {
            if (scmbProdutor.getValue() != null){
                btnEditarProdutor.setVisible(true);
            }
        });
        
        btnInserirProdutor.setOnAction((t) -> {
            Produtor novoProdutor = Telas.cadastrarProdutor(btnFiltrar.getScene().getWindow());
            
            // Verificar se o usuário realmente salvou um produtor (e não cancelou)
            if (novoProdutor != null) {
                // Adicionar o novo produtor à lista que alimenta o ComboBox
                scmbProdutor.getItems().add(novoProdutor);
                scmbProdutor.setValue(novoProdutor);
            }
        });
        
        btnEditarProdutor.setOnAction((t) -> {
            Produtor produtorAtualizado = Telas.editarProdutor(scmbProdutor.getValue(), btnFiltrar.getScene().getWindow());
            if (produtorAtualizado != null) {
                // O objeto foi ATUALIZADO (mutado). 
                // Para forçar o ComboBox a redesenhar o nome (caso tenha mudado),
                // removemos e readicionamos o item.
                int index = scmbProdutor.getItems().indexOf(produtorAtualizado);
                if (index != -1) {
                    scmbProdutor.getItems().set(index, produtorAtualizado); // Força a atualização
                }

                // Garante que ele continue selecionado
                scmbProdutor.setValue(produtorAtualizado);
            }
        });
        btnInserirDC.setOnAction((t) -> inserirDC());
        
        listaMunicipios = new UtilitarioService().getMunicipios();
        ObservableList<Municipio> listaObsMunicipios = FXCollections.observableArrayList(listaMunicipios);
        cmbMunicipio.setItems(listaObsMunicipios);
        cmbMunicipio.setValue(Statics.municipioPadrao);
        txtNumero.setText(String.valueOf(new DeclaracaoComplementarService().getProximoNumeroDC(cmbMunicipio.getValue().getId())));
        btnAtualizar.setOnAction((t) -> carregarDadosEConfigurarFiltros());
        carregarDadosEConfigurarFiltros();
    }    
    
    private void inserirDC(){
                
        LocalDate data = dpData.getValue();
        Produtor produtor = scmbProdutor.getValue();
        Municipio municipio = cmbMunicipio.getValue();
        String observacoes = txtObservacoes.getText() != null ? txtObservacoes.getText().trim() : "";
        
        //Teste para saber se já não foi inserido uma DC com esse número
        int numeroDc = Integer.parseInt(txtNumero.getText());
        int proximoNumero = new DeclaracaoComplementarService().getProximoNumeroDC(cmbMunicipio.getValue().getId());
        boolean numeroJaUtilizado = new DeclaracaoComplementarService().testarNumeroDC(municipio.getId(), numeroDc);
        
        if (numeroJaUtilizado){
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("ATENÇÃO");
            al.setContentText("O número da Declaração Complementar informado já estava em uso. Foi utilizado o próximo número possível: " + proximoNumero);
            al.showAndWait();
            numeroDc = proximoNumero;
        }
        
        DeclaracaoComplementar dc = new DeclaracaoComplementar(numeroDc, data, produtor, observacoes, municipio);
        
        if (dpData.getValue() != null && scmbProdutor.getValue() != null && cmbMunicipio.getValue() != null){
            if(new DeclaracaoComplementarService().salvarOuAtualizar(dc)) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Sucesso");
                al.setContentText("A declaração complementar foi inserida com sucesso");
                al.showAndWait();
            }else{
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Erro");
                al.setContentText("Ocorreu um erro ao inserir");
                al.showAndWait();
            }
        }
        
        txtObservacoes.setText("");
        dpData.setValue(LocalDate.now());
        scmbProdutor.setValue(null);
        cmbMunicipio.setValue(Statics.municipioPadrao);
        btnEditarProdutor.setVisible(false);
        txtNumero.setText(String.valueOf(new DeclaracaoComplementarService().getProximoNumeroDC(cmbMunicipio.getValue().getId())));
        
        carregarDadosEConfigurarFiltros();
    }
    
    private void carregarDadosEConfigurarFiltros() {
        // 1. Carrega TODOS os produtores do banco
        listaDc = new DeclaracaoComplementarService().getAll();
        observableListaDc.setAll(listaDc);

        // 2. Envelopa a lista principal em uma FilteredList
        filteredListaDc = new FilteredList<>(observableListaDc, p -> true);

        // 3. Adiciona listeners para o ComboBox e o TextField
        //    Qualquer alteração neles chamará o método aplicarFiltro()
        cmbFiltro.valueProperty().addListener((obs, oldV, newV) -> aplicarFiltro());
        txtBusca.textProperty().addListener((obs, oldV, newV) -> aplicarFiltro());

        // 4. Envelopa a FilteredList em uma SortedList
        //    Isso permite que o usuário clique nos cabeçalhos das colunas para ordenar
        SortedList<DeclaracaoComplementar> sortedData = new SortedList<>(filteredListaDc);

        // 5. Vincula o comparador da SortedList com o da TableView
        sortedData.comparatorProperty().bind(tblComplementar.comparatorProperty());

        // 6. Define a SortedList como os itens da tabela
        tblComplementar.setItems(sortedData);
        Utils.formatTableColumnDate(tcData);
    }
    
    private void aplicarFiltro() {
        String filtroSelecionado = cmbFiltro.getValue();
        String textoBusca = txtBusca.getText();

        filteredListaDc.setPredicate(dc -> {
            
            // Nenhum filtro selecionado
            if (filtroSelecionado == null) {
                return true; // Mostra todos
            }

            //Mostra somente DCs com observações
            if (filtroSelecionado.equals("Possui observação")){
                return dc.getObservacoes()!= null && !dc.getObservacoes().equals("");
            }

            // Filtros que USAM o texto de busca
            // Se o texto estiver vazio, mostra todos (para os filtros de texto)
            if (textoBusca == null || textoBusca.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = textoBusca.toLowerCase().trim();

            switch (filtroSelecionado) {
                case "CPF/CNPJ":
                    return dc.getProdutor().getCpf()!= null && dc.getProdutor().getCpf().contains(lowerCaseFilter);
                case "Município da propriedade":
                    // Assumindo que você tem um getMunicipioString() ou similar
                    return dc.getMunicipioPropriedade().getNome() != null && dc.getMunicipioPropriedade().getNome().toLowerCase().contains(lowerCaseFilter);
                case "Nome":
                    return dc.getProdutor().getNome() != null && dc.getProdutor().getNome().toLowerCase().contains(lowerCaseFilter);
                default:
                    // Se o filtro for "Pessoa Física/Jurídica" mas tiver texto, não mostra
                    return false; 
            }
        });
    }
    
    @FXML
    private void onLimparFiltros() {
        cmbFiltro.getSelectionModel().clearSelection();
        txtBusca.clear();
        // O filtro será aplicado automaticamente pelos listeners
    }
}
