package telas;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import model.classes.Municipio;
import model.classes.Veterinario;
import model.services.UtilitarioService;
import model.services.VeterinarioService;


public class TelaConfiguracoesController implements Initializable {

    @FXML    private ComboBox<Municipio> cmbMunicipio;
    @FXML    private Button btnInserirMunicipio;
    @FXML    private Button btnExcluir;
    @FXML    private Button btnExcluirMunicipio;
    @FXML    private Button btnExcluirRedator;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnLimparMunicipio;
    @FXML    private Button btnLimparRedator;
    @FXML    private Button btnInserir;
    @FXML    private Button btnInserirRedator;
    @FXML    private Button btnSalvarMunicipio;
    @FXML    private ListView<Veterinario> listView;
    @FXML    private ListView<Municipio> listViewMunicipio;
    @FXML    private ListView<String> listViewRedator;
    @FXML    private TextField txtCodIBGE;
    @FXML    private TextField txtCrmv;
    @FXML    private TextField txtIf;
    @FXML    private TextField txtMunicipio;
    @FXML    private TextField txtNome;
    @FXML    private TextField txtNomeRedator;
    
    List<Municipio> listaMunicipios;
    Veterinario veterinario;
    Municipio municipio;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // INÍCIO VALORES PADRÃO ------------------------------------------------------------------------------------------
        listaMunicipios = new UtilitarioService().getMunicipios();
        ObservableList<Municipio> listaObsMunicipios = FXCollections.observableArrayList(listaMunicipios);
        cmbMunicipio.setItems(listaObsMunicipios);
        cmbMunicipio.setValue(Statics.municipioPadrao);
      
        cmbMunicipio.setOnAction((t) -> {
            if (new UtilitarioService().editarMunicipioPadrao(cmbMunicipio.getValue())){
                Statics.municipioPadrao = cmbMunicipio.getValue();
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Sucesso");
                al.setContentText("Município padrão alterado para " + cmbMunicipio.getValue().getNome());
                al.showAndWait();
            }
        });
        
        btnInserirMunicipio.setOnAction((t) -> Telas.inserirMunicipio(btnInserirMunicipio.getScene().getWindow() ) );
        // FIM VALORES PADRÃO -----------------------------------------------------------------------------------------
        
        
        //INÍCIO VETERINÁRIOS -----------------------------------------------------------------------------------------
        listView.setCellFactory(lv -> new ListCell<Veterinario>() {
            @Override
            protected void updateItem(Veterinario veterinario, boolean empty) {
                super.updateItem(veterinario, empty);
                if (empty || veterinario == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(veterinario.getNome() );
                }
            }
        });
        
        listView.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                btnExcluir.setVisible(true);
            }
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) && listView.getSelectionModel().getSelectedItem() != null) {
                veterinario = listView.getSelectionModel().getSelectedItem();
                txtNome.setText(veterinario.getNome());
                txtCrmv.setText(veterinario.getCrmv());
                txtIf.setText(veterinario.getIdentidadeFuncional());
                btnInserir.setText("Alterar");
            }
        });
        
        btnExcluir.setOnAction((t) -> {
            if(listView.getSelectionModel().getSelectedIndex() >= 0){
                new VeterinarioService().excluir(listView.getSelectionModel().getSelectedItem());
            }
            listarVeterinarios();
            btnExcluir.setVisible(false);
            limparCamposVeterinario();
        });
        
        btnInserir.setOnAction((t) -> {
            String nome = txtNome.getText().trim();
            String crmv = txtCrmv.getText().trim();
            String identFunc = txtIf.getText().trim();
            if (veterinario == null){
                veterinario = new Veterinario(nome, identFunc, crmv);
            }else{
                veterinario.setNome(nome);
                veterinario.setCrmv(crmv);
                veterinario.setIdentidadeFuncional(identFunc);
            }
            
            if(!txtNome.getText().equals("") && !txtIf.getText().equals("")){
                new VeterinarioService().salvarOuAtualizar(veterinario);
            }
            limparCamposVeterinario();
            listarVeterinarios();
        });
        
        btnLimpar.setOnAction((t) -> limparCamposVeterinario());
        
        listarVeterinarios();
        //FIM VETERINÁRIOS --------------------------------------------------------------------------------------------
        
        
        //INÍCIO REDATORES --------------------------------------------------------------------------------------------
        listViewRedator.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                btnExcluirRedator.setVisible(true);
            }
         });
        
        btnInserirRedator.setOnAction((t) -> inserirRedator());
        txtNomeRedator.setOnAction((t) -> inserirRedator());
        
        btnExcluirRedator.setOnAction((t) -> {
            if(listViewRedator.getSelectionModel().getSelectedIndex() >= 0){
                new UtilitarioService().excluirRedator(listViewRedator.getSelectionModel().getSelectedItem());
            }
            listarRedatores();
            btnExcluirRedator.setVisible(false);
        });
        
        btnLimparRedator.setOnAction((t) -> txtNomeRedator.setText(""));
        
        listarRedatores();
        //FIM REDATORES -----------------------------------------------------------------------------------------------
        
        //INÍCIO MUNICÍPIO --------------------------------------------------------------------------------------------
        listViewMunicipio.setCellFactory(lv -> new ListCell<Municipio>() {
            @Override
            protected void updateItem(Municipio municipio, boolean empty) {
                super.updateItem(municipio, empty);
                if (empty || municipio == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(municipio.getNome() );
                }
            }
        });
        
        listViewMunicipio.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                btnExcluirMunicipio.setVisible(true);
            }
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) && listViewMunicipio.getSelectionModel().getSelectedItem() != null) {
                municipio = listViewMunicipio.getSelectionModel().getSelectedItem();
                txtMunicipio.setText(municipio.getNome());
                txtCodIBGE.setText(municipio.getCodIbge());
                btnSalvarMunicipio.setText("Alterar");
            }
        });
        
        btnExcluirMunicipio.setOnAction((t) -> {
            if(listViewMunicipio.getSelectionModel().getSelectedIndex() >= 0){
                new UtilitarioService().excluirMunicipio(listViewMunicipio.getSelectionModel().getSelectedItem());
            }
            listarMunicipios();
            btnExcluirMunicipio.setVisible(false);
            limparCamposMunicipio();
        });
        
        btnSalvarMunicipio.setOnAction((t) -> {
            String nomeMunicipio = txtMunicipio.getText().trim();
            String codIBGE = txtCodIBGE.getText().trim();
            if (municipio == null){
                municipio = new Municipio(nomeMunicipio, codIBGE);
            }else{
                municipio.setNome(nomeMunicipio);
                municipio.setCodIbge(codIBGE);
            }
            
            if(!txtMunicipio.getText().equals("") && !txtCodIBGE.getText().equals("")){
                new UtilitarioService().salvarOuAtualizar(municipio);
            }
            limparCamposMunicipio();
            listarMunicipios();
        });
        
        btnLimpar.setOnAction((t) -> limparCamposMunicipio());
        
        listarMunicipios();
        //FIM MUNICÍPIO -----------------------------------------------------------------------------------------------
    }    
    
    private void inserirRedator(){
        String nomeRedator = txtNomeRedator.getText().trim();
            if(!txtNomeRedator.getText().equals("")){
                new UtilitarioService().salvarRedator(nomeRedator);
            }
            txtNomeRedator.setText("");
            listarRedatores();
            txtNomeRedator.requestFocus();
    }
    
    private void listarVeterinarios() {
        Statics.listaVeterinarios = new VeterinarioService().getAll();
        ObservableList<Veterinario> listaObsTipoVacina = FXCollections.observableArrayList(Statics.listaVeterinarios);                                                                         
        listView.setItems(listaObsTipoVacina);
    }
    
    private void listarMunicipios() {
        listaMunicipios = new UtilitarioService().getMunicipios();
        ObservableList<Municipio> listaObsMunicipio = FXCollections.observableArrayList(listaMunicipios);                                                                         
        listViewMunicipio.setItems(listaObsMunicipio);
    }
    
    private void listarRedatores() {
        Statics.listaRedatores = new UtilitarioService().getRedatores();
        ObservableList<String> listaObsRedatores = FXCollections.observableArrayList(Statics.listaRedatores);                                                                         
        listViewRedator.setItems(listaObsRedatores);
    }
    
    private void limparCamposVeterinario(){
        txtCrmv.setText("");
        txtIf.setText("");
        txtNome.setText("");
        veterinario = null;
        listView.getSelectionModel().select(null);
        btnExcluir.setVisible(false);
        btnInserir.setText("Inserir");
    }
    
    private void limparCamposMunicipio(){
        txtMunicipio.setText("");
        txtCodIBGE.setText("");
        municipio = null;
        listViewMunicipio.getSelectionModel().select(null);
        btnExcluirMunicipio.setVisible(false);
        btnSalvarMunicipio.setText("Inserir");
    }
}
