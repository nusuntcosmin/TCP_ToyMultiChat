package com.example.testjavafx.gui_controllers;

import com.example.testjavafx.MainClientApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController extends AbstractController{

        @FXML
        private Button connectButton;

        @FXML
        private TextField username;

        @FXML
        private PasswordField passwordTextField;

        @FXML
        private Button registerButton;

        @FXML
        public void initialize(){
            initializeButtonsBehaviour();

        }
        private void initializeButtonsBehaviour(){
            connectButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try{
                        if(username.getText().isEmpty() || passwordTextField.getText().isEmpty()){
                            throw new Exception("Please complete all textfileds");
                        }
                        if(!passwordTextField.getText().equals(service.findOne(username.getText()).getUserPassword())){
                            throw new Exception("Wrong email or password");
                        }
                        changeScene("chat-view.fxml",username.getText());
                    }catch (Exception ex){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(ex.getMessage());
                        alert.setContentText("Complete all fields and try again");
                        alert.show();
                    }
                }
            });

            registerButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        changeScene("register-view.fxml");
                    } catch (Exception e) {
                         int a = 3;
                    }
                }
            });
        }
        @Override
        protected void changeScene(String fxml, String... param) throws Exception {
            if(param.length !=0){
                MainClientApp.sendParameterToScene(param[0]);
            }
        MainClientApp.changeScene(fxml);
    }
}
