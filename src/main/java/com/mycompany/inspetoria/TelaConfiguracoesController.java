package com.mycompany.inspetoria;

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
import model.classes.Municipio;
import model.services.UtilitarioService;
import org.controlsfx.control.SearchableComboBox;


public class TelaConfiguracoesController implements Initializable {

    @FXML ComboBox<Municipio> cmbMunicipio;
    @FXML Button btnInserirMunicipio;
    
    List<Municipio> listaMunicipios;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    }    
    
}
