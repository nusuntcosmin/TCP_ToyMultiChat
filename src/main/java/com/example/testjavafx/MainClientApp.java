package com.example.testjavafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class MainClientApp extends Application {

    public static Stage appStage;
    @Override
    public void start(Stage stage) throws IOException {
        appStage = stage;
        appStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
        FXMLLoader fxmlLoader = new FXMLLoader(MainClientApp.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 380);
        stage.setTitle("Multichat");
        stage.setScene(scene);
        stage.show();

    }

    public static Object getParameterFromScene(){
        Object parameter =appStage.getUserData();
        appStage.setUserData(null);
        return parameter;
    }

    public static void changeScene(String fxml) throws IOException{
        Parent pane = FXMLLoader.load(MainClientApp.class.getResource(fxml));
        appStage.setWidth(600);
        appStage.setHeight(425);
        appStage.getScene().setRoot(pane);
    }
    public static void sendParameterToScene(Object argument){
        appStage.setUserData(argument);

    }
    public static void main(String[] args) {
        launch();
    }
}