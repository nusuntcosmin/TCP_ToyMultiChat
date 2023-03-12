package com.example.testjavafx.client;

import com.example.testjavafx.HelloApplication;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController  implements Initializable{

    private Client client;
    @FXML
    private ScrollPane chatScrollPane;

    @FXML
    private VBox messagesVBox;

    @FXML
    private TextField messageTextField;

    @FXML
    private Button sendButton;

    private void setButtonBehaviour(){
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String messageToSend =messageTextField.getText();
                if(!messageToSend.isEmpty() && !messageToSend.isBlank()){

                    HBox messageHBox = new HBox();
                    messageHBox.setAlignment(Pos.CENTER_RIGHT);
                    messageHBox.setPadding(new Insets(5,5,5,10));

                    Text textToSend = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(textToSend);
                    textFlow.setStyle("-fx-color: rgb(239,242,255); " +
                            "-fx-background-color: rgb(15,34,242); " +
                            "-fx-background-radius: 20px");
                    textFlow.setPadding(new Insets(5,5,5,10));
                    textToSend.setFill(Color.color(0.934,0.945,0.996));

                    messageHBox.getChildren().add(textFlow);
                    messagesVBox.getChildren().add(messageHBox);


                    client.sendMessage(messageToSend);
                    messageTextField.clear();
                }

            }
        });
    }
    private void initializeChat(){
        messagesVBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                chatScrollPane.setVvalue((Double) newValue);
            }
        });

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            Socket socket = new Socket("localhost",1235);
            String username = (String) HelloApplication.getParameterFromScene();
            client = new Client(socket,username);
            client.sendMessage(username);
        }catch (IOException ioException){

        }
        initializeChat();
        client.listenForMessages(messagesVBox);
        setButtonBehaviour();
    }

    public static void addLabel(String msgFromGroupChat, VBox vBox){
        HBox messageHBox = new HBox();
        messageHBox.setAlignment(Pos.CENTER_LEFT);
        messageHBox.setPadding(new Insets(5,5,5,10));

        Text textToSend = new Text(msgFromGroupChat);
        TextFlow textFlow = new TextFlow(textToSend);
        textFlow.setStyle("-fx-color: rgb(255,255,255); " +
                "-fx-background-color: rgb(255,0,0); " +
                "-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5,5,5,10));
        textToSend.setFill(Color.color(0.934,0.945,0.996));

        messageHBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(messageHBox);
            }
        });

    }

}
