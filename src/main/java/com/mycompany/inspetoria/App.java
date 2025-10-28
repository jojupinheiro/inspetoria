package com.mycompany.inspetoria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {


    @Override
    public void start(Stage stage) throws IOException {
//        scene = new Scene(loadFXML("TelaListas"), 1080, 780);
//        stage.setScene(scene);
//        stage.show();

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("TelaListas.fxml"));
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("IDA Venâncio Aires");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

}