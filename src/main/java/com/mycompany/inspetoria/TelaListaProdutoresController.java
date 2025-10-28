package com.mycompany.inspetoria;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

    @FXML    private Button btnFiltrar;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnNovo;
    @FXML    private CheckBox ckbEmail;
    @FXML    private CheckBox ckbEndereco;
    @FXML    private CheckBox ckbRg;
    @FXML    private CheckBox ckbTelefone2;
    @FXML    private ComboBox<String> cmbFiltro;
    @FXML    private ComboBox<MotivoInfracao> cmbMotivos;
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
    private String txtFiltro;
    private int filtroSelecionado = -1;
    List<MotivoInfracao> listaMotivos;
    
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
        
        ObservableList<String> listaObs = FXCollections.observableArrayList("CPF/CNPJ", "Município", "Nome", "Pessoa física", "Pessoa Jurídica");
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
                atualizaTabela(filtroSelecionado, txtFiltro);
            }
        });
        
        atualizaTabela(filtroSelecionado, txtFiltro);
    }    
    
    public void atualizaTabela(int filtroSelecionado, String txtFiltro) {
        // Buscar os dados no banco de dados na tabela pet
        listaProdutores = new ProdutorService().getAll(filtroSelecionado, txtFiltro);
        // ObservableList
        ObservableList<Produtor> listaObs = FXCollections.observableArrayList(listaProdutores);
        //Vinculando a lista observável com a TableView
        tblProdutores.setItems(listaObs);
        Utils.formatTableColumnCpfOuCnpj(tCCpfCnpj);
    }
}
