package com.mycompany.inspetoria;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.stage.Window;
import model.classes.MotivoInfracao;
import model.classes.Produtor;
import model.services.ProdutorService;
import utils.Utils;

/**
 * FXML Controller class
 *
 * @author Juliano
 */
public class TelaListaProdutoresController implements Initializable {

    @FXML    private Button btnAtualizar;
    @FXML    private Button btnFiltrar;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnNovo;
    @FXML    private CheckBox ckbEmail;
    @FXML    private CheckBox ckbEndereco;
    @FXML    private CheckBox ckbRg;
    @FXML    private CheckBox ckbTelefone2;
    @FXML    private ComboBox<String> cmbFiltro;
    @FXML    private TableColumn<Produtor, String> tCCpfCnpj;
    @FXML    private TableColumn<Produtor, String> tCEmail;
    @FXML    private TableColumn<Produtor, String> tCEndereco;
    @FXML    private TableColumn<Produtor, String> tCMunicipio;
    @FXML    private TableColumn<Produtor, String> tCNome;
    @FXML    private TableColumn<Produtor, String> tCObs;
    @FXML    private TableColumn<Produtor, String> tCRg;
    @FXML    private TableColumn<Produtor, String> tCTelefone1;
    @FXML    private TableColumn<Produtor, String> tCTelefone2;
    @FXML    private TableView<Produtor> tblProdutores;
    @FXML    private TextField txtBusca;
    
    List<Produtor> listaProdutores;
    // Esta lista principal observável conterá TODOS os produtores carregados do banco
    private ObservableList<Produtor> observableListaProdutores = FXCollections.observableArrayList();
    
    // Esta lista "envelopa" a lista observável e aplica os filtros
    private FilteredList<Produtor> filteredListaProdutores;
    
    private String txtFiltro;
    private int filtroSelecionado = -1;
    List<MotivoInfracao> listaMotivos;
    
    private Image iconObservacao;
    {
        try {
            iconObservacao = new Image(getClass().getResourceAsStream("/images/icon_obs.png")); 
        } catch (Exception e) {
            System.err.println("Erro ao carregar ícone de observação: " + e.getMessage());
            iconObservacao = null; // Garante que o app não quebre se o ícone faltar
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tCCpfCnpj.setCellValueFactory(new PropertyValueFactory<>("Cpf"));
        tCEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        tCEndereco.setCellValueFactory(new PropertyValueFactory<>("EnderecoCompleto"));
        tCMunicipio.setCellValueFactory(new PropertyValueFactory<>("MunicipioString"));
        tCNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        tCObs.setCellValueFactory(new PropertyValueFactory<>("Observacao"));
        tCRg.setCellValueFactory(new PropertyValueFactory<>("Rg"));
        tCTelefone1.setCellValueFactory(new PropertyValueFactory<>("Telefone1"));
        tCTelefone2.setCellValueFactory(new PropertyValueFactory<>("Telefone2"));
        
        ckbEmail.setSelected(true);
        ckbEndereco.setSelected(true);
        ckbRg.setSelected(true);
        ckbTelefone2.setSelected(true);
        
        tCNome.setCellFactory(col -> new TableCell<Produtor, String>() {
            // Cria um ImageView para o ícone
            private final ImageView iconView = new ImageView();
            {
                if (iconObservacao != null) {
                    iconView.setImage(iconObservacao);
                    iconView.setFitHeight(16); // Define um tamanho padrão
                    iconView.setFitWidth(16);
                }
            }

            @Override
            protected void updateItem(String nome, boolean empty) {
                super.updateItem(nome, empty);

                if (empty || nome == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Define o nome do produtor como texto
                    setText(nome); 
                    
                    // Pega o objeto Produtor completo da linha atual
                    Produtor produtor = (Produtor) getTableRow().getItem();

                    // Verifica se o produtor existe e se tem observação
                    if (produtor != null && produtor.getObservacao() != null && !produtor.getObservacao().trim().isEmpty()) {
                        setGraphic(iconView); // Mostra o ícone
                    } else {
                        setGraphic(null); // Esconde o ícone
                    }
                }
            }
        });
        
        tblProdutores.setRowFactory(tv -> {
            // 1. Criamos a TableRow personalizada (com sua lógica de Tooltip)
            final TableRow<Produtor> row = new TableRow<Produtor>() {
                private Tooltip tooltip = new Tooltip();
                {
                    tooltip.setWrapText(true);
                    tooltip.setMaxWidth(300);
                }
                
                @Override
                public void updateItem(Produtor produtor, boolean empty) {
                    super.updateItem(produtor, empty);
                    
                    if (empty || produtor == null) {
                        setTooltip(null);
                    } else {
                        String obs = produtor.getObservacao();
                        if (obs != null && !obs.trim().isEmpty()) {
                            tooltip.setText(obs);
                            setTooltip(tooltip);
                        } else {
                            setTooltip(null);
                        }
                    }
                }
            }; // Fim da definição da TableRow

            // 2. Criamos o ContextMenu (lógica adaptada do seu exemplo)
            final ContextMenu rowMenu = new ContextMenu();
            
            // --- Item: Novo Produtor ---
            MenuItem novo = new MenuItem("Novo Produtor");
            novo.setOnAction(event -> {
                // Chama a tela de cadastro (que retorna o produtor, como fizemos antes)
                Produtor novoProdutor = Telas.cadastrarProdutor(btnNovo.getScene().getWindow());
                if (novoProdutor != null) {
                    // Adiciona na lista principal. O filtro e a tabela se atualizam sozinhos.
                    observableListaProdutores.add(novoProdutor);
                }
            });

            // --- Item: Editar Produtor ---
            MenuItem editItem = new MenuItem("Editar Produtor");
            editItem.setOnAction(event -> {
                // Pega o produtor da linha e chama a edição
                Produtor produtorParaEditar = row.getItem();
                if(produtorParaEditar == null) return;
                
                Produtor produtorAtualizado = Telas.editarProdutor(produtorParaEditar, btnFiltrar.getScene().getWindow());
                
                if (produtorAtualizado != null) {
                    // Atualiza o item na lista principal para forçar a UI a redesenhar
                    int index = observableListaProdutores.indexOf(produtorParaEditar);
                    if (index != -1) {
                        observableListaProdutores.set(index, produtorAtualizado);
                    }
                }
            });

            // --- Item: Excluir Produtor ---
            MenuItem removeItem = new MenuItem("Excluir Produtor");
            removeItem.setOnAction(event -> {
                Produtor produtorParaExcluir = row.getItem();
                if(produtorParaExcluir == null) return;
                
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmação");
                al.setContentText("O produtor '" + produtorParaExcluir.getNome() + "' será excluído! Tem certeza?");
                
                if (al.showAndWait().get() == ButtonType.OK) {
                    // NOTA: Isso assume que você tem um método 'excluir' no seu ProdutorService
                    if (new ProdutorService().excluir(produtorParaExcluir)) {
                        Alert mens = new Alert(Alert.AlertType.INFORMATION);
                        mens.initOwner(btnFiltrar.getScene().getWindow());
                        mens.setTitle("Excluído");
                        mens.setContentText("Registro excluído com sucesso!");
                        mens.showAndWait();
                        
                        // Remove da lista principal (a tabela atualizará sozinha)
                        observableListaProdutores.remove(produtorParaExcluir);
                    } else {
                        Alert erroAlert = new Alert(Alert.AlertType.ERROR);
                        erroAlert.setTitle("Erro");
                        erroAlert.setContentText("Falha ao excluir o produtor. Verifique se ele está sendo usado em outro local.");
                        erroAlert.showAndWait();
                    }
                }
            });

            // Adiciona os itens ao menu
            rowMenu.getItems().addAll(novo, editItem, removeItem);

            // 3. Vincula o menu à linha (lógica de 'empty' do seu exemplo)
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                    .then((ContextMenu) null)
                    .otherwise(rowMenu)
            );
            
            // 4. Retorna a linha configurada
            return row;
        });
        
        btnNovo.setOnAction((t) -> Telas.cadastrarProdutor(btnNovo.getScene().getWindow()));
        
        ObservableList<String> listaObs = FXCollections.observableArrayList("CPF/CNPJ", "Município", "Nome", "Pessoa física", "Pessoa Jurídica", "Possui observação");
        cmbFiltro.setItems(listaObs);
        
        ckbEmail.selectedProperty().addListener((t, ov, nv) -> tCEmail.setVisible(nv));
        ckbEndereco.selectedProperty().addListener((t, ov, nv) -> tCEndereco.setVisible(nv));
        ckbRg.selectedProperty().addListener((t, ov, nv) -> tCRg.setVisible(nv));
        ckbTelefone2.selectedProperty().addListener((t, ov, nv) -> tCTelefone2.setVisible(nv));
        
        tblProdutores.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) && tblProdutores.getSelectionModel().getSelectedItem() != null) {
                Produtor produtor = tblProdutores.getSelectionModel().getSelectedItem();
                Window janela = btnFiltrar.getScene().getWindow();
                Telas.editarProdutor(produtor, janela);
                atualizaTabela();
            }
        });
        
        btnAtualizar.setOnAction((t) -> carregarDadosEConfigurarFiltros());
        
        carregarDadosEConfigurarFiltros();
    }    
    
    public void atualizaTabela() {
        // 1. Buscar os dados atualizados no banco de dados
        listaProdutores = new ProdutorService().getAll(-1, null); // -1 e null para buscar tudo
        
        // 2. Atualizar a lista observável principal.
        //    O FilteredList e o SortedList reagirão automaticamente
        //    e manterão os filtros atuais.
        observableListaProdutores.setAll(listaProdutores);

        // 3. Formatar a coluna (se necessário)
        Utils.formatTableColumnCpfOuCnpj(tCCpfCnpj);
    }
    
    private void carregarDadosEConfigurarFiltros() {
        // 1. Carrega TODOS os produtores do banco
        listaProdutores = new ProdutorService().getAll(-1, null); // -1 e null para buscar tudo
        observableListaProdutores.setAll(listaProdutores);

        // 2. Envelopa a lista principal em uma FilteredList
        filteredListaProdutores = new FilteredList<>(observableListaProdutores, p -> true);

        // 3. Adiciona listeners para o ComboBox e o TextField
        //    Qualquer alteração neles chamará o método aplicarFiltro()
        cmbFiltro.valueProperty().addListener((obs, oldV, newV) -> aplicarFiltro());
        txtBusca.textProperty().addListener((obs, oldV, newV) -> aplicarFiltro());

        // 4. Envelopa a FilteredList em uma SortedList
        //    Isso permite que o usuário clique nos cabeçalhos das colunas para ordenar
        SortedList<Produtor> sortedData = new SortedList<>(filteredListaProdutores);

        // 5. Vincula o comparador da SortedList com o da TableView
        sortedData.comparatorProperty().bind(tblProdutores.comparatorProperty());

        // 6. Define a SortedList como os itens da tabela
        tblProdutores.setItems(sortedData);
        Utils.formatTableColumnCpfOuCnpj(tCCpfCnpj);
    }
    
    private void aplicarFiltro() {
        String filtroSelecionado = cmbFiltro.getValue();
        String textoBusca = txtBusca.getText();

        filteredListaProdutores.setPredicate(produtor -> {
            
            // Caso 1: Nenhum filtro selecionado
            if (filtroSelecionado == null) {
                return true; // Mostra todos
            }

            // Caso 2: Filtros que NÃO usam o texto de busca
            if (filtroSelecionado.equals("Pessoa física")) {
                // (Assumindo que seu boolean é 'false' para Física e 'true' para Jurídica)
                return !produtor.isTipoProdutor(); 
            }
            if (filtroSelecionado.equals("Pessoa Jurídica")) {
                return produtor.isTipoProdutor();
            }
            if (filtroSelecionado.equals("Possui observação")){
                return produtor.getObservacao() != null && !produtor.getObservacao().equals("");
            }

            // Caso 3: Filtros que USAM o texto de busca
            
            // Se o texto estiver vazio, mostra todos (para os filtros de texto)
            if (textoBusca == null || textoBusca.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = textoBusca.toLowerCase().trim();

            switch (filtroSelecionado) {
                case "CPF/CNPJ":
                    return produtor.getCpf() != null && produtor.getCpf().contains(lowerCaseFilter);
                case "Município":
                    // Assumindo que você tem um getMunicipioString() ou similar
                    return produtor.getMunicipioString() != null && produtor.getMunicipioString().toLowerCase().contains(lowerCaseFilter);
                case "Nome":
                    return produtor.getNome() != null && produtor.getNome().toLowerCase().contains(lowerCaseFilter);
                default:
                    // Se o filtro for "Pessoa Física/Jurídica" mas tiver texto, não mostra
                    return false; 
            }
        });
    }
    
    /**
     * Ação para o botão "Limpar" (btnLimpar).
     * Você precisa vincular este método ao seu botão no FXML:
     * No SceneBuilder, selecione o btnLimpar e em "On Action" digite "onLimparFiltros"
     */
    @FXML
    private void onLimparFiltros() {
        cmbFiltro.getSelectionModel().clearSelection();
        txtBusca.clear();
        // O filtro será aplicado automaticamente pelos listeners
    }
}
