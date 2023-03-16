package com.example.testjavafx.gui_controllers;

import com.example.testjavafx.MainClientApp;
import com.example.testjavafx.exceptions.RegisterException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class RegisterViewController extends AbstractController{
    @FXML
    private PasswordField confirmPasswordTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Text registerNowText;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameTextField;

    @Override
    public void changeScene(String fxml,String... param) throws RegisterException {
        try{
            MainClientApp.changeScene(fxml);
        }catch (IOException ioException){
            throw new RegisterException("Error when trying to switch to login page");
        }
    }

    @FXML
    private void initialize(){
        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    if(usernameTextField.getText().isEmpty() || emailTextField.getText().isEmpty() || passwordTextField.getText().isEmpty() || confirmPasswordTextField.getText().isEmpty())
                        throw new RegisterException("All fields should be completed");

                    if(!passwordTextField.getText().equals(confirmPasswordTextField.getText()))
                        throw new RegisterException("Passwords do not match");

                    if(service.userExists(emailTextField.getText()))
                        throw new RegisterException("Email is already in use");
                    service.addUser(emailTextField.getText(),usernameTextField.getText(),passwordTextField.getText());
                    changeScene("login-view.fxml");

                }catch (Exception exception){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(exception.getMessage());
                    alert.setContentText("Complete all fields and try again");
                    alert.show();
                }

            }
        });
    }


}
