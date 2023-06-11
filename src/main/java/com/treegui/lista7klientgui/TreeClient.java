package com.treegui.lista7klientgui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TreeClient {

    private Socket clientSocket;
    public PrintWriter out;
    public BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }
    public String readAll() throws IOException {
        StringBuilder message = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            message.append(inputLine);
        }
        return message.toString();
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

}
