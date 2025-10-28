package com.mycompany.inspetoria;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.classes.AutoInfracao;
import model.classes.Produtor;
import model.services.ProdutorService;

/**
 *
 * @author João Juliano Pinheiro joaojulianopinheiro@hotmail.com
 */
public class Telas {

    public static void cadastrarAutoInfracao(Window janela) {
        try {
            URL url = Telas.class.getResource("TelaCadastroAutoInfracao.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Auto de infração");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(690);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void cadastrarProdutor(Window janela) {
        try {
            URL url = Telas.class.getResource("TelaCadastroProdutor.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Produtor");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setMinWidth(800);
            stage.setMinHeight(690);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editarAI(AutoInfracao ai, Map<String, Integer> animaisEnvolvidos, Window janela) {
        try {
            URL url = Telas.class.getResource("TelaCadastroAutoInfracao.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Auto de Infração");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroAutoInfracaoController cont = loader.getController();
            cont.setAI(ai, animaisEnvolvidos);
            cont.ajustarTela();
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editarProdutor(Produtor produtor, Window janela) {
        try {
            URL url = Telas.class.getResource("TelaCadastroProdutor.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Produtor");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroProdutorController cont = loader.getController();
            cont.setProdutor(produtor);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
