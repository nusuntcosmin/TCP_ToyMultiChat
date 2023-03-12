package com.example.testjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Stage appStage;
    @Override
    public void start(Stage stage) throws IOException {
        appStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("enter-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 380);
        stage.setTitle("First");
        stage.setScene(scene);
        stage.show();

    }

    public static Object getParameterFromScene(){
        Object parameter =appStage.getUserData();
        appStage.setUserData(null);
        return parameter;
    }

    public static void changeScene(String fxml) throws IOException{
        Parent pane = FXMLLoader.load(HelloApplication.class.getResource(fxml));
        appStage.getScene().setRoot(pane);
    }
    public static void sendParameterToScene(Object argument){
        appStage.setUserData(argument);

    }
    public static void main(String[] args) {
        launch();
    }
}