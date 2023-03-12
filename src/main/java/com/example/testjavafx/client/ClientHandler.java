package com.example.testjavafx.client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlerArrayList = new ArrayList<>();
    private  Socket clientSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private String clientUsername;

    private ReentrantLock mutex = new ReentrantLock();
    public ClientHandler(Socket clientSocket) {
        try{


            this.clientSocket = clientSocket;
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            clientUsername = bufferedReader.readLine();
            clientHandlerArrayList.add(this);

            broadcastMessage("User-ul cu nume " + clientUsername + " s-a conectat");

        }catch(IOException ioException){
            System.out.println("Error creating the clientHandler");
            closeEverything(clientSocket,bufferedReader,bufferedWriter);
        }

    }

    public void removeClientHandler(){
        clientHandlerArrayList.remove(this);
        //broadcastMessage(this.clientUsername + " has disconnected.");
    }

    public void closeEverything(Socket _clientSocket,BufferedReader _bufferedReader, BufferedWriter _bufferedWriter){
        removeClientHandler();

        try{
            if( bufferedWriter != null)
                bufferedWriter.close();
            if(bufferedReader != null)
                bufferedReader.close();
            if(clientSocket != null)
                clientSocket.close();

        }catch (IOException ioException){
            System.out.println("Error closing everything");
        }
    }
    public void broadcastMessage(String message){
        clientHandlerArrayList.forEach(
                (clientHandler -> {
                    try{
                        if(!clientHandler.clientUsername.equals(this.clientUsername)){
                            clientHandler.bufferedWriter.write(message);
                            clientHandler.bufferedWriter.newLine();
                            clientHandler.bufferedWriter.flush();
                        }
                    }catch (IOException ioException){
                        closeEverything(clientSocket,bufferedReader,bufferedWriter);
                    }
                })
        );
    }
    @Override
    public void run() {
        String messageFromClient;

        while(clientSocket.isConnected()){
            try{
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            }catch(IOException ioException){
                closeEverything(clientSocket,bufferedReader,bufferedWriter);
                break;
            }
        }
    }
}
