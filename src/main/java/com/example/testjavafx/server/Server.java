package com.example.testjavafx.server;


import com.example.testjavafx.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer(){
        try{
            while(!serverSocket.isClosed()){
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }catch (IOException ioException){
            closeServer();
        }
    }

    public void closeServer(){
        try{
            if(serverSocket != null)
                serverSocket.close();
        }catch (IOException ioException){
            System.out.println("Error occured when trying to close the server");
        }
    }

    public static void main(String[] args) throws IOException{
        ServerSocket serverrSocket = new ServerSocket(1235);
        Server server = new Server(serverrSocket);
        server.startServer();


    }
}
