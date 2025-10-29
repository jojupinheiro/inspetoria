/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package telas;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import model.classes.Municipio;
import model.services.UtilitarioService;

/**
 * FXML Controller class
 *
 * @author juliano
 */
public class TelaInserirMunicipioController implements Initializable {

    @FXML    private Button btnExcluir;
    @FXML    private Button btnInserir;
    @FXML    private ListView<Municipio> listViewMunicipio;
    @FXML    private TextField txtMunicipio;
    @FXML    private TextField txtCodIBGE;
    
    private List<Municipio> listaMunicipios = new ArrayList<>();
    
    Municipio municipio;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        listViewMunicipio.setCellFactory(lv -> new ListCell<Municipio>() {
            @Override
            protected void updateItem(Municipio municipio, boolean empty) {
                super.updateItem(municipio, empty);
                if (empty || municipio == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(municipio.getNome());
                }
            }
        });
        
        listViewMunicipio.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                btnExcluir.setVisible(true);
            }
        });
        
        btnExcluir.setOnAction((t) -> {
            if(listViewMunicipio.getSelectionModel().getSelectedIndex() >= 0){
                new UtilitarioService().excluirMunicipio(listViewMunicipio.getSelectionModel().getSelectedItem());
            }
            listarMunicipios();
            btnExcluir.setVisible(false);
        });
            
            
        btnInserir.setOnAction((t) -> {
            String nomeMunicipio = txtMunicipio.getText().trim();
            String codIbge = txtCodIBGE.getText().trim();
            if(municipio == null){
                municipio = new Municipio(nomeMunicipio, codIbge);
            }
            
            if(!txtMunicipio.getText().equals("") && !codIbge.equals("")){
                new UtilitarioService().salvarOuAtualizar(municipio);
                txtMunicipio.setText("");
                txtCodIBGE.setText("");
                municipio = null;
            }
            
            listarMunicipios();
        });
        
        listarMunicipios();
    }    
    
    private void listarMunicipios() {                                                                                                                                               
        this.listaMunicipios = new UtilitarioService().getMunicipios();                                                                                                             
        ObservableList<Municipio> listaObsMunicipio = FXCollections.observableArrayList(listaMunicipios);                                                                         
        listViewMunicipio.setItems(listaObsMunicipio);                                                                                                                                
    }
    
}