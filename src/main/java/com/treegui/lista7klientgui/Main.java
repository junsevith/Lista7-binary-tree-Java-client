package com.treegui.lista7klientgui;

import java.io.IOException;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        TreeClient client = new TreeClient();
        Scanner scanner = new Scanner(System.in);
        client.startConnection("127.0.0.1", 6666);
        client.out.println("start");
        System.out.println(client.in.readLine());
        while (true){
            String input = scanner.nextLine();
            client.out.println(input);
            System.out.print(client.in.readLine().replace('\0','\n'));
//            System.out.println(client.readAll());
            if (input.equals("exit")) break;

        }
    }
}