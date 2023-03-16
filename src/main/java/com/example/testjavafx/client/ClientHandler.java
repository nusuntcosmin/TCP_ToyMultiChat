package com.example.testjavafx.client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clientHandlerArrayList = new ArrayList<>();
    private  Socket clientSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private String userEmail;

    public ClientHandler(Socket clientSocket) {
        try{
            this.clientSocket = clientSocket;
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            clientHandlerArrayList.add(this);
            userEmail = bufferedReader.readLine();
            sendMessageToAllClients("SERVER: The user with the email " + userEmail + " has just connected ");

        }catch(IOException ioException){
            System.out.println("Error creating the clientHandler");
            closeSockets(clientSocket,bufferedReader,bufferedWriter);
        }

    }
    public void removeClientHandler(){
        sendMessageToAllClients("SERVER: The user with the email " + userEmail + " has just disconnected.");
        clientHandlerArrayList.remove(this);

    }
    public void closeSockets(Socket _clientSocket,BufferedReader _bufferedReader, BufferedWriter _bufferedWriter){
        removeClientHandler();
        try{
            if( _bufferedWriter != null)
                _bufferedWriter.close();
            if(_bufferedReader != null)
                _bufferedReader.close();
            if(_clientSocket != null)
                _clientSocket.close();

        }catch (IOException ioException){
            System.out.println("Error occurred when trying to close the sockets");
        }
    }
    public void sendMessageToAllClients(String message){
        clientHandlerArrayList.forEach(
                (clientHandler -> {
                    try{
                        if(!clientHandler.userEmail.equals(this.userEmail)){
                            clientHandler.bufferedWriter.write(message);
                            clientHandler.bufferedWriter.newLine();
                            clientHandler.bufferedWriter.flush();
                        }
                    }catch (IOException ioException){
                        closeSockets(clientSocket,bufferedReader,bufferedWriter);
                        System.err.println("Error occurred when trying to broadcast a message");
                    }
                })
        );
    }
    @Override
    public void run() {
        while(clientSocket.isConnected()){
            try{
                String messageFromClient = bufferedReader.readLine();
                sendMessageToAllClients(messageFromClient);
            }catch(IOException ioException){
                closeSockets(clientSocket,bufferedReader,bufferedWriter);
                break;
            }
        }
    }
}
