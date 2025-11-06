package telas;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Window;
import javafx.util.Callback;
import model.classes.AutoInfracao;
import model.classes.MotivoInfracao;
import model.classes.Produtor;
import model.services.AutoInfracaoService;
import model.services.MotivoInfracaoService;
import model.services.ProdutorService;
import utils.Utils;

/**
 * FXML Controller class
 *
 * @author Juliano
 */
public class TelaListaAIController implements Initializable {

    @FXML    private Button btnAtualizar;
    @FXML    private Button btnFiltrar;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnNovo;
    @FXML    private CheckBox ckbDesconto;
    @FXML    private CheckBox ckbFea;
    @FXML    private CheckBox ckbHistorico;
    @FXML    private CheckBox ckbLimiteDefesa;
    @FXML    private CheckBox ckbObservacoes;
    @FXML    private CheckBox ckbProcesso;
    @FXML    private CheckBox ckbRedator;
    @FXML    private CheckBox ckbReincidente;
    @FXML    private ComboBox<String> cmbFiltro;
    @FXML    private ComboBox<Object> cmbFiltroGenerico;
    @FXML    private DatePicker dpDataFim;
    @FXML    private DatePicker dpDataInicio;
    @FXML    private HBox boxDatas;
    @FXML    private ProgressIndicator spinnerCarregamento;
    @FXML    private TableColumn<AutoInfracao, String> tCAdvertencia;
    @FXML    private TableColumn<AutoInfracao, Produtor> tCAutuado;
    @FXML    private TableColumn<AutoInfracao, String> tCCpf;
    @FXML    private TableColumn<AutoInfracao, LocalDate> tCData;
    @FXML    private TableColumn<AutoInfracao, String> tCDesconto;
    @FXML    private TableColumn<AutoInfracao, LocalDate> tCDtCiencia;
    @FXML    private TableColumn<AutoInfracao, LocalDate> tCDtLimiteDefesa;
    @FXML    private TableColumn<AutoInfracao, String> tCFEA;
    @FXML    private TableColumn<AutoInfracao, String> tCHistorico;
    @FXML    private TableColumn<AutoInfracao, String> tCHora;
    @FXML    private TableColumn<AutoInfracao, String> tCMotivo;
    @FXML    private TableColumn<AutoInfracao, String> tCMunicipioLavratura;
    @FXML    private TableColumn<AutoInfracao, String> tCMunicipioAutuado;
    @FXML    private TableColumn<AutoInfracao, String> tCNumero;
    @FXML    private TableColumn<AutoInfracao, String> tCObs;
    @FXML    private TableColumn<AutoInfracao, String> tCProcesso;
    @FXML    private TableColumn<AutoInfracao, String> tCRedator;
    @FXML    private TableColumn<AutoInfracao, String> tCReincidente;
    @FXML    private TableView<AutoInfracao> tableViewAutosInfracao;
    @FXML    private TextField txtBusca;
    
    List<AutoInfracao> listaAI;
    private String txtFiltro;
    private int filtroSelecionado = -1;
    List<MotivoInfracao> listaMotivos;
    List<String> listaMunicipios;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tCAdvertencia.setCellValueFactory(new PropertyValueFactory<>("EAdvertencia"));
        tCAutuado.setCellValueFactory(new PropertyValueFactory<>("nomeAutuado"));
        tCCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tCData.setCellValueFactory(new PropertyValueFactory<>("dataLavratura"));
        tCDesconto.setCellValueFactory(new PropertyValueFactory<>("possuiDesconto"));
        tCDtCiencia.setCellValueFactory(new PropertyValueFactory<>("dataCiencia"));
        tCDtLimiteDefesa.setCellValueFactory(new PropertyValueFactory<>("dataLimiteDefesa"));
        tCFEA.setCellValueFactory(new PropertyValueFactory<>("nomeFea"));
        tCRedator.setCellValueFactory(new PropertyValueFactory<>("redator"));
        tCHistorico.setCellValueFactory(new PropertyValueFactory<>("historico"));
        tCHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        tCMotivo.setCellValueFactory(new PropertyValueFactory<>("resumoMotivo"));
        tCMunicipioAutuado.setCellValueFactory(new PropertyValueFactory<>("nomeMunicipioAutuado"));
        tCMunicipioLavratura.setCellValueFactory(new PropertyValueFactory<>("nomeMunicipio"));
        tCNumero.setCellValueFactory(new PropertyValueFactory<>("numeroAiCompleto"));
        tCObs.setCellValueFactory(new PropertyValueFactory<>("observacoes"));
        tCProcesso.setCellValueFactory(new PropertyValueFactory<>("processo"));
        tCRedator.setCellValueFactory(new PropertyValueFactory<>("redator"));
        tCReincidente.setCellValueFactory(new PropertyValueFactory<>("reincidente"));
        
        cmbFiltroGenerico.setVisible(false);
        cmbFiltroGenerico.setManaged(false);
       
        tableViewAutosInfracao.setRowFactory(
                new Callback<TableView<AutoInfracao>, TableRow<AutoInfracao>>() {
            @Override
            public TableRow<AutoInfracao> call(TableView<AutoInfracao> tableView) {

                // 1. Criamos uma TableRow personalizada que atualiza o estilo
                final TableRow<AutoInfracao> row = new TableRow<AutoInfracao>() {
                    // Guarda os nomes das classes CSS para limpar facilmente
                    private final String[] styleClasses = {"row-white", "row-yellow", "row-red", "row-green"};

                    @Override
                    protected void updateItem(AutoInfracao item, boolean empty) {
                        super.updateItem(item, empty);

                        // Remove todos os estilos de cor anteriores
                        getStyleClass().removeAll(styleClasses);

                        // Se a linha estiver vazia ou o item for nulo, não faz nada
                        if (item == null || empty) {
                            // (Já removemos os estilos, então ela fica padrão/vazia)
                        } else {
                            // --- AQUI COMEÇA A LÓGICA DE FORMATAÇÃO ---
                            LocalDate hoje = LocalDate.now();
                            LocalDate dataCiencia = item.getDataCiencia();
                            LocalDate dataLimite = item.getDataLimiteDefesa();
                            String processo = item.getProcesso();

                            // Regra 1 (Prioritária): Processo preenchido
                            if (processo != null && !processo.trim().isEmpty()) {
                                getStyleClass().add("row-green");
                            } // Regra 2: Ciência vazia
                            else if (dataCiencia == null) {
                                getStyleClass().add("row-white");
                            } // Regra 3: Venceu (Data atual > Limite Defesa)
                            // (Verifica dataLimite != null para evitar NullPointerException)
                            else if (dataLimite != null && hoje.isAfter(dataLimite)) {
                                getStyleClass().add("row-red");
                            } // Regra 4: No prazo (Ciência preenchida e Data atual <= Limite Defesa)
                            else {
                                getStyleClass().add("row-yellow");
                            }
                        }
                    }
                };

                // 2. --- SEU CÓDIGO ORIGINAL (MENU DE CONTEXTO) ---
                // (Exatamente como estava antes)
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem novo = new MenuItem("Novo Auto de Infração");
                novo.setOnAction((t) -> {
                    Telas.cadastrarAutoInfracao(btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem editItem = new MenuItem("Editar Auto de Infração");
                editItem.setOnAction((t) -> {
                    AutoInfracao ai = row.getItem();
                    Map<String, Integer> animaisEnvolvidos = new AutoInfracaoService().getAnimaisEnvolvidos(ai);
                    Telas.editarAI(ai, animaisEnvolvidos, btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem verAutuado = new MenuItem("Ver Autuado");
                verAutuado.setOnAction((t) -> {
                    Produtor autuado = row.getItem().getAutuado();
                    Telas.editarProdutor(autuado, btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem removeItem = new MenuItem("Excluir");
                removeItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                        al.setTitle("Confirmação");
                        al.setContentText("O AI nº " + row.getItem().getNumeroAi() + " será excluído! Tem certeza?");
                        if (al.showAndWait().get() == ButtonType.OK) {
                            if (new AutoInfracaoService().excluir(row.getItem())) {
                                Alert mens = new Alert(Alert.AlertType.INFORMATION);
                                mens.initOwner(btnFiltrar.getScene().getWindow());
                                mens.setTitle("Excluído");
                                mens.setContentText("Registro excluído com sucesso!");
                                mens.showAndWait();
                                atualizaTabela(filtroSelecionado, txtFiltro);
                            }
                        }
                    }
                });

                MenuItem copiarCpfAutuado = new MenuItem("Copiar CPF do Autuado");
                copiarCpfAutuado.setOnAction((t) -> {
                    AutoInfracao ai = row.getItem();

                    // Verifica se o item, o autuado e o CPF não são nulos
                    if (ai != null && ai.getAutuado() != null && ai.getAutuado().getCpf() != null) {
                        String cpf = Utils.imprimeCPFouCNPJ(ai.getAutuado().getCpf());

                        if (!cpf.isEmpty()) {
                            final Clipboard clipboard = Clipboard.getSystemClipboard();
                            final ClipboardContent content = new ClipboardContent();
                            content.putString(cpf);
                            clipboard.setContent(content);
                        }
                    }
                });

                MenuItem copiarNomeAutuado = new MenuItem("Copiar Nome do Autuado");
                copiarNomeAutuado.setOnAction((t) -> {
                    AutoInfracao ai = row.getItem();

                    // Verifica se o item, o autuado e o Nome não são nulos
                    if (ai != null && ai.getAutuado() != null && ai.getAutuado().getNome() != null) {
                        String nome = ai.getAutuado().getNome();

                        if (!nome.isEmpty()) {
                            final Clipboard clipboard = Clipboard.getSystemClipboard();
                            final ClipboardContent content = new ClipboardContent();
                            content.putString(nome);
                            clipboard.setContent(content);
                        }
                    }
                });

                rowMenu.getItems().addAll(novo, editItem, removeItem, verAutuado, copiarCpfAutuado, copiarNomeAutuado);

                // only display context menu for non-empty rows:
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu) null)
                                .otherwise(rowMenu));
                // --- FIM DO CÓDIGO DO MENU DE CONTEXTO ---

                return row;
            }
        });
        
        tableViewAutosInfracao.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) && tableViewAutosInfracao.getSelectionModel().getSelectedItem() != null) {
                AutoInfracao ai = tableViewAutosInfracao.getSelectionModel().getSelectedItem();
                Map<String, Integer> animaisEnvolvidos = new AutoInfracaoService().getAnimaisEnvolvidos(ai);
                Window janela = btnFiltrar.getScene().getWindow();
                Telas.editarAI(ai, animaisEnvolvidos, janela);
                atualizaTabela(filtroSelecionado, txtFiltro);
            }
        });
        
        listaMotivos = new MotivoInfracaoService().getInformacoesPrincipais();
        listaMunicipios = new AutoInfracaoService().getMunicipiosComAI();
        ObservableList<MotivoInfracao> listaObsMotivos = FXCollections.observableArrayList(listaMotivos);
      
        cmbFiltroGenerico.setOnAction((t) -> {
            Object valorSelecionado = cmbFiltroGenerico.getValue();
            // O toString() funcionará tanto para MotivoInfracao (se o método toString() estiver correto)
            // quanto para String (município)
            txtFiltro = (valorSelecionado != null) ? valorSelecionado.toString() : "";
            atualizaTabela(filtroSelecionado, txtFiltro);
        });
        
        ObservableList<String> listaObs = FXCollections.observableArrayList("Autuado", "CPF do autuado", "Data de lavratura", "Motivo da infração", "Município de lavratura", "Número do AI");
        cmbFiltro.setItems(listaObs);

        btnLimpar.setOnAction((t) -> {
            cmbFiltro.getSelectionModel().select(-1);
            txtBusca.setText("");
            dpDataFim.setValue(null);
            dpDataInicio.setValue(null);
            cmbFiltroGenerico.setValue(null);
            cmbFiltroGenerico.setItems(null); // Limpa as opções
            
            // A ação do cmbFiltro (linha 304) vai esconder os campos
            filtroSelecionado = -1;
            txtFiltro = null;
            atualizaTabela(filtroSelecionado, txtFiltro); // Mostra tabela sem filtros
        });

        // Este botão é necessário para o filtro de DATA e para filtros de TEXTO
        btnFiltrar.setOnAction((t) -> {
            if (filtroSelecionado == 2){ // Filtro de Data
                if(dpDataInicio.getValue() == null || dpDataFim.getValue() == null){
                    Alert al = new Alert(Alert.AlertType.WARNING, "É preciso preencher a data de início e fim.", ButtonType.OK);
                    al.showAndWait();
                    return;
                }
                String dtInicial = dpDataInicio.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String dtFinal = dpDataFim.getValue().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                txtFiltro = dtInicial + " " + dtFinal;
            }else{ // Filtros de Texto (Autuado, CPF, Numero AI)
                txtFiltro = txtBusca.getText();
            }
            atualizaTabela(filtroSelecionado, txtFiltro);
        });

        // Ação para o txtBusca (apertar Enter)
        txtBusca.setOnAction((t) -> {
            // Filtros 0, 1, 5 (Autuado, CPF, Numero AI)
            if (filtroSelecionado == 0 || filtroSelecionado == 1 || filtroSelecionado == 5){
                txtFiltro = txtBusca.getText();
                atualizaTabela(filtroSelecionado, txtFiltro);
            }
        });
        
        // ATUALIZADO: Lógica principal de seleção de filtro
        cmbFiltro.setOnAction((t) -> {
            try {
                filtroSelecionado = cmbFiltro.getSelectionModel().getSelectedIndex();
                
                // 1. Esconde todos os controles de filtro dinâmicos
                txtBusca.setVisible(false);
                txtBusca.setManaged(false);
                boxDatas.setVisible(false);
                boxDatas.setManaged(false);
                cmbFiltroGenerico.setVisible(false);
                cmbFiltroGenerico.setManaged(false);
                cmbFiltroGenerico.setItems(null); // Limpa os itens do ComboBox

                // 2. Mostra o controle correto baseado na seleção
                if (filtroSelecionado == 2) { // Data de lavratura
                    boxDatas.setVisible(true);
                    boxDatas.setManaged(true);
                } else if (filtroSelecionado == 3) { // Motivo da infração
                    ObservableList<Object> obsMotivos = FXCollections.observableArrayList(listaMotivos);
                    cmbFiltroGenerico.setItems(obsMotivos); // Popula com Motivos
                    cmbFiltroGenerico.setVisible(true);
                    cmbFiltroGenerico.setManaged(true);
                } else if (filtroSelecionado == 4) { // Município de lavratura
                    ObservableList<Object> obsMunicipios = FXCollections.observableArrayList(listaMunicipios);
                    cmbFiltroGenerico.setItems(obsMunicipios); // Popula com Municípios
                    cmbFiltroGenerico.setVisible(true);
                    cmbFiltroGenerico.setManaged(true);
                } else if (filtroSelecionado != -1) { // Filtros de texto (0, 1, 5)
                    txtBusca.setVisible(true);
                    txtBusca.setManaged(true);
                } else {
                    // Nenhum filtro selecionado (-1), apenas limpa a tabela
                    txtFiltro = null;
                    atualizaTabela(filtroSelecionado, txtFiltro);
                }

            } catch (Exception e) {
                filtroSelecionado = -1; // Reseta em caso de erro
                e.printStackTrace();
            }
        });
        
        boxDatas.setVisible(false);
        boxDatas.setManaged(false);
     
        btnNovo.setOnAction((t) -> {
            Telas.cadastrarAutoInfracao(btnNovo.getScene().getWindow());
            atualizaTabela(filtroSelecionado, txtFiltro);
        });
        ckbDesconto.selectedProperty().addListener((t, ov, nv) -> tCDesconto.setVisible(nv));
        ckbFea.selectedProperty().addListener((t, ov, nv) -> tCFEA.setVisible(nv));
        ckbHistorico.selectedProperty().addListener((t, ov, nv) -> tCHistorico.setVisible(nv));
        ckbLimiteDefesa.selectedProperty().addListener((t, ov, nv) -> tCDtLimiteDefesa.setVisible(nv));
        ckbObservacoes.selectedProperty().addListener((t, ov, nv) -> tCObs.setVisible(nv));
        ckbProcesso.selectedProperty().addListener((t, ov, nv) -> tCProcesso.setVisible(nv));
        ckbRedator.selectedProperty().addListener((t, ov, nv) -> tCRedator.setVisible(nv));
        ckbReincidente.selectedProperty().addListener((t, ov, nv) -> tCReincidente.setVisible(nv));
        
        btnAtualizar.setOnAction((t) -> atualizaTabela(filtroSelecionado, txtFiltro));
        
//        atualizaTabela(filtroSelecionado, txtFiltro);
        carregarDadosIniciais();
    }
    
    /**
     * Inicia uma Task em segundo plano para carregar TODOS os dados
     * iniciais (Motivos, Municípios e Autos) sem travar a UI.
     */
    private void carregarDadosIniciais() {
        spinnerCarregamento.setVisible(true);
        tableViewAutosInfracao.setDisable(true);

        Task<InitialData> task = new Task<>() {
            @Override
            protected InitialData call() throws Exception {
                // ISSO RODA EM SEGUNDO PLANO (BACKGROUND THREAD)
                // É seguro chamar o banco daqui
                List<MotivoInfracao> motivos = new MotivoInfracaoService().getInformacoesPrincipais();
                List<String> municipios = new AutoInfracaoService().getMunicipiosComAI();
                // Usa os filtros padrão (-1, null) para a primeira carga
                List<AutoInfracao> autos = new AutoInfracaoService().getAll(-1, null);

                return new InitialData(motivos, municipios, autos);
            }
        };

        // O que fazer quando a Task TERMINAR COM SUCESSO
        task.setOnSucceeded(e -> {
            // ISSO RODA NA UI THREAD
            InitialData data = task.getValue();

            // 1. Popula as variáveis da classe
            listaMotivos = data.motivos;
            listaMunicipios = data.municipios;
            listaAI = data.autos;

            // 2. Popula a tabela
            ObservableList<AutoInfracao> listaObs = FXCollections.observableArrayList(listaAI);
            tableViewAutosInfracao.setItems(listaObs);
            formatarColunasTabela(); // Formata as colunas

            // 3. Esconde o spinner e re-abilita a tabela
            spinnerCarregamento.setVisible(false);
            tableViewAutosInfracao.setDisable(false);
        });

        // O que fazer quando a Task FALHAR
        task.setOnFailed(e -> {
            // ISSO RODA NA UI THREAD
            spinnerCarregamento.setVisible(false);
            tableViewAutosInfracao.setDisable(true); // Deixa desabilitada se falhar
            
            // Pega o erro real e mostra o alerta
            mostrarAlertaErro(task.getException());
        });

        // Inicia a Task
        new Thread(task).start();
    }
    
    /**
     * Atualiza a tabela de Autos de Infração com base nos filtros.
     * Roda a busca no banco em uma Task separada.
     */
    public void atualizaTabela(int filtroSelecionado, String txtFiltro) {
        spinnerCarregamento.setVisible(true);
        spinnerCarregamento.setManaged(true);
        tableViewAutosInfracao.setDisable(true);
        tableViewAutosInfracao.setVisible(false);
        tableViewAutosInfracao.setManaged(false);

        Task<List<AutoInfracao>> task = new Task<>() {
            @Override
            protected List<AutoInfracao> call() throws Exception {
                // ISSO RODA EM SEGUNDO PLANO (BACKGROUND THREAD)
                return new AutoInfracaoService().getAll(filtroSelecionado, txtFiltro);
            }
        };

        // O que fazer quando a Task TERMINAR COM SUCESSO
        task.setOnSucceeded(e -> {
            // ISSO RODA NA UI THREAD
            listaAI = task.getValue(); // Atualiza a lista da classe
            ObservableList<AutoInfracao> listaObs = FXCollections.observableArrayList(listaAI);
            tableViewAutosInfracao.setItems(listaObs);
            
            formatarColunasTabela(); // Formata as colunas

            spinnerCarregamento.setVisible(false);
            spinnerCarregamento.setManaged(false);
            tableViewAutosInfracao.setDisable(false);
            tableViewAutosInfracao.setVisible(true);
            tableViewAutosInfracao.setManaged(true);
        });

        // O que fazer quando a Task FALHAR
        task.setOnFailed(e -> {
            // ISSO RODA NA UI THREAD
            spinnerCarregamento.setVisible(false);
            spinnerCarregamento.setManaged(false);
            tableViewAutosInfracao.setDisable(false);
            tableViewAutosInfracao.setVisible(true);
            tableViewAutosInfracao.setManaged(true);
            mostrarAlertaErro(task.getException());
        });

        // Inicia a Task
        new Thread(task).start();
    }
    
    /**
     * Centraliza a lógica de formatação das colunas da tabela.
     */
    private void formatarColunasTabela() {
        Utils.formatTableColumnDate(tCData);
        Utils.formatTableColumnDate(tCDtCiencia);
        Utils.formatTableColumnDate(tCDtLimiteDefesa);
        Utils.formatTableColumnCpfOuCnpj(tCCpf);
        Utils.formatTableColumnProcesso(tCProcesso);
    }

    /**
     * Mostra um Alerta de Erro padrão na UI Thread.
     * @param ex A exceção que causou a falha.
     */
    private void mostrarAlertaErro(Throwable ex) {
        // Garante que a janela obtenha o foco correto
        Window owner = (spinnerCarregamento != null && spinnerCarregamento.getScene() != null) 
                ? spinnerCarregamento.getScene().getWindow() 
                : null;
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(owner);
        alert.setTitle("Erro de Conexão");
        alert.setHeaderText("Não foi possível carregar os dados do banco.");
        alert.setContentText("Ocorreu um erro: " + ex.getMessage());
        
        // Imprime o stack trace no console para depuração
        ex.printStackTrace(); 
        
        alert.showAndWait();
    }
    
}

// Classe auxiliar para guardar os dados carregados pela Task inicial.

class InitialData {
    final List<MotivoInfracao> motivos;
    final List<String> municipios;
    final List<AutoInfracao> autos;

    public InitialData(List<MotivoInfracao> motivos, List<String> municipios, List<AutoInfracao> autos) {
        this.motivos = motivos;
        this.municipios = municipios;
        this.autos = autos;
    }
}