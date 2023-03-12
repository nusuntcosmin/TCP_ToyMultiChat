package com.example.testjavafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EnterSceneController{

        @FXML
        private Button connectButton;

        @FXML
        private TextField username;

        @FXML
        public void initialize(){
            connectButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(!username.getText().isEmpty() && !username.getText().isBlank()){
                        try {
                            HelloApplication.sendParameterToScene(username.getText());
                            HelloApplication.changeScene("chat-view.fxml");
                        }catch (Exception ex){
                            int a = 3;
                        }

                        int a = 3;
                    }
                }
            });
        }
}
