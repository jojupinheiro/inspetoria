package com.mycompany.inspetoria;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import java.util.logging.Logger;
import java.util.logging.Level;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Juliano
 */
public class TelaListasController implements Initializable {
    
    @FXML    private BorderPane borderPane;
    @FXML    private Button btnAutosInfracao;
    @FXML    private Button btnAutosInterdicao;
    @FXML    private Button btnDeclaracoesComplementares;
    @FXML    private Button btnDeclaracoesNC;
    @FXML    private MenuItem miAutoInfracao;
    @FXML    private MenuItem miMotivosInfracao;
    @FXML    private MenuItem miCadastrarProdutor;
    @FXML    private MenuItem miVerProdutores;
    @FXML    private MenuItem miVeterinarios;
    @FXML    private VBox vBoxLegendas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vBoxLegendas.setVisible(false);
        

        btnAutosInfracao.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaListaAI.fxml"));
                borderPane.setCenter(view);
            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
            vBoxLegendas.setVisible(true);
        });
        
        miVerProdutores.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaListaProdutores.fxml"));
                borderPane.setCenter(view);
            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
            vBoxLegendas.setVisible(false);
        });
        
        miAutoInfracao.setOnAction((t) -> Telas.cadastrarAutoInfracao(btnAutosInfracao.getScene().getWindow()));
        miCadastrarProdutor.setOnAction((t) -> Telas.cadastrarProdutor(btnAutosInfracao.getScene().getWindow()));
    }    
    
}
