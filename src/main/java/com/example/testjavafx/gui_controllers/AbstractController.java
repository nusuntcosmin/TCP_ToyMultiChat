package com.example.testjavafx.gui_controllers;

import com.example.testjavafx.server.Server;
import com.example.testjavafx.service.Service;

import java.io.IOException;

public abstract class AbstractController {
    protected abstract void changeScene(String fxml,String... param) throws Exception;
    protected static Service service = new Service();

}
