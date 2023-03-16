package com.example.testjavafx.client;

import com.example.testjavafx.gui_controllers.ChatViewController;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;



    public void closeSockets(Socket _clientSocket, BufferedReader _bufferedReader, BufferedWriter _bufferedWriter){
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
    public Client(Socket _clientSocket) {

        this.clientSocket = _clientSocket;


        try{
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        }catch (IOException ioException){
            closeSockets(clientSocket,bufferedReader,bufferedWriter);
        }
    }

    public void sendMessage(String messageToSend){
        try{
            bufferedWriter.write(messageToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        }catch(IOException ioException){
            closeSockets(clientSocket,bufferedReader,bufferedWriter);
        }
    }

    public void listenForMessages(VBox vBox){
        Thread threadForListening = new Thread(new Runnable() {
            @Override
            public void run() {
                while (clientSocket.isConnected()){
                    try{
                        String msgFromGroupChat = bufferedReader.readLine();
                        ChatViewController.displayReceivedMessage(msgFromGroupChat,vBox);

                    }catch (IOException ioException){
                        System.out.println("Error receiving message from the Server");
                        closeSockets(clientSocket,bufferedReader,bufferedWriter);
                        break;
                    }
                }
            }
        });

        threadForListening.start();
        /*
        new Thread (new Runnable() {
            @Override
            public void run() {
                while (clientSocket.isConnected()){
                    try{
                        String msgFromGroupChat = bufferedReader.readLine();
                        ChatViewController.addLabel(msgFromGroupChat,vBox);

                    }catch (IOException ioException){
                        System.out.println("Error receiving message from the Server");
                        closeSockets(clientSocket,bufferedReader,bufferedWriter);
                        break;
                    }
                }
            }
        }).start();

         */
    }



}
