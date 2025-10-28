package com.mycompany.inspetoria;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import model.classes.AutoInfracao;
import model.classes.MotivoInfracao;
import model.classes.Produtor;
import model.services.AutoInfracaoService;
import model.services.MotivoInfracaoService;
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
    @FXML    private ComboBox<MotivoInfracao> cmbMotivos;
    @FXML    private DatePicker dpDataFim;
    @FXML    private DatePicker dpDataInicio;
    @FXML    private HBox boxDatas;
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
        
        cmbMotivos.setVisible(false);
        cmbMotivos.setManaged(false);
       
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
                rowMenu.getItems().addAll(novo, editItem, removeItem, verAutuado);

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
        
        listaMotivos = new MotivoInfracaoService().getAll();
        ObservableList<MotivoInfracao> listaObsMotivos = FXCollections.observableArrayList(listaMotivos);
        cmbMotivos.setItems(listaObsMotivos);
        
        cmbMotivos.setOnAction((t) -> atualizaTabela(filtroSelecionado, cmbMotivos.getValue() != null ? cmbMotivos.getValue().toString() : "") );
        
        ObservableList<String> listaObs = FXCollections.observableArrayList("Autuado", "CPF do autuado", "Data de lavratura", "Motivo da infração", "Número do AI");
        cmbFiltro.setItems(listaObs);
        
        btnLimpar.setOnAction((t) -> {
            cmbFiltro.getSelectionModel().select(-1);
            txtBusca.setText("");
            dpDataFim.setValue(null);
            dpDataInicio.setValue(null);
            cmbMotivos.setValue(null);
        });

        btnFiltrar.setOnAction((t) -> {
            if (filtroSelecionado == 2){
                String dtInicial = dpDataInicio.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String dtFinal = dpDataFim.getValue().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                txtFiltro = dtInicial + " " + dtFinal;
            }else{
                txtFiltro = txtBusca.getText();
            }
            atualizaTabela(filtroSelecionado, txtFiltro);
        });

        txtBusca.setOnAction((t) -> {
            if (filtroSelecionado != 2 && filtroSelecionado != -1){
                txtFiltro = txtBusca.getText();
                atualizaTabela(filtroSelecionado, txtFiltro);
            }
        });
        
        cmbFiltro.setOnAction((t) -> {
            try {
                filtroSelecionado = cmbFiltro.getSelectionModel().getSelectedIndex();
                if (filtroSelecionado == 2) {
                    txtBusca.setVisible(false);
                    txtBusca.setManaged(false);
                    boxDatas.setVisible(true);
                    boxDatas.setManaged(true);
                    cmbMotivos.setVisible(false);
                    cmbMotivos.setManaged(false);
                } else if(filtroSelecionado == 3){
                    txtBusca.setVisible(false);
                    txtBusca.setManaged(false);
                    boxDatas.setVisible(false);
                    boxDatas.setManaged(false);
                    cmbMotivos.setVisible(true);
                    cmbMotivos.setManaged(true);
                }else {
                    txtBusca.setVisible(true);
                    txtBusca.setManaged(true);
                    boxDatas.setVisible(false);
                    boxDatas.setManaged(false);
                    cmbMotivos.setVisible(false);
                    cmbMotivos.setManaged(false);
                    atualizaTabela(filtroSelecionado, txtFiltro);
                }

            } catch (Exception e) {
                filtroSelecionado = 0;
                e.printStackTrace();
            }
        });
        boxDatas.setVisible(false);
        boxDatas.setManaged(false);
        
        btnNovo.setOnAction((t) ->  {
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
        
        atualizaTabela(filtroSelecionado, txtFiltro);
    }    
    
    public void atualizaTabela(int filtroSelecionado, String txtFiltro) {
        // Buscar os dados no banco de dados na tabela pet
        listaAI = new AutoInfracaoService().getAll(filtroSelecionado, txtFiltro);
        // ObservableList
        ObservableList<AutoInfracao> listaObs = FXCollections.observableArrayList(listaAI);
        //Vinculando a lista observável com a TableView
        tableViewAutosInfracao.setItems(listaObs);
        Utils.formatTableColumnDate(tCData);
        Utils.formatTableColumnDate(tCDtCiencia);
        Utils.formatTableColumnDate(tCDtLimiteDefesa);
        Utils.formatTableColumnCpfOuCnpj(tCCpf);
//        Utils.formatTableColumnPeso(tableColumnPeso);
    }
    
}
