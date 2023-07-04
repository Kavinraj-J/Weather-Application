package org.example;

import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static WeatherApiRequest api;
    private static LayoutFormatter layout;
    public static void main(String[] args) {
        System.out.println(" ");
        System.out.println("Welcome to weather services");
        System.out.println("Please enter your location to begin:");
        String location = scanner.nextLine();
        System.out.println("\u000c");
        api = new WeatherApiRequest(location);
        layout = new LayoutFormatter(api);

        try {
            for (int i = 0; i < layout.getAllStrings().size(); i++) {
                System.out.println(layout.getAllStrings().get(i));
                Thread.sleep(500);
            }
        }catch(Exception e){
            System.out.println("We are experiencing some problems right now. Try again later.");
        }

    }
}