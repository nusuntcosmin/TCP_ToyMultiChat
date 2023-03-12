package com.example.testjavafx.client;

import com.example.testjavafx.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket clientSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public void closeEverything(Socket _clientSocket, BufferedReader _bufferedReader, BufferedWriter _bufferedWriter){
        try{
            if(_bufferedReader != null)
                _bufferedReader.close();

            if(_bufferedWriter != null)
                _bufferedWriter.close();

            if(_clientSocket != null)
                _clientSocket.close();
        }catch(IOException ioException){
            System.out.println("Error closing everything");
        }
    }
    public Client(Socket clientSocket, String clientUsername) {
        this.clientSocket = clientSocket;
        this.clientUsername = clientUsername;

        try{
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        }catch (IOException ioException){
            closeEverything(clientSocket,bufferedReader,bufferedWriter);

        }
    }

    public void sendMessage(String messageToSend){
        try{
            bufferedWriter.write(messageToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();


        }catch(IOException ioException){
            closeEverything(clientSocket,bufferedReader,bufferedWriter);
        }
    }

    public void listenForMessages(VBox vBox){
        new Thread (new Runnable() {
            @Override
            public void run() {
                while (clientSocket.isConnected()){
                    try{
                        String msgFromGroupChat = bufferedReader.readLine();
                        ClientController.addLabel(msgFromGroupChat,vBox);

                    }catch (IOException ioException){
                        System.out.println("Error receiving message from the com.example.test.server");
                        closeEverything(clientSocket,bufferedReader,bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }



}
