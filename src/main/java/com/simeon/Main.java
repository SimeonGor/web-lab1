package com.simeon;

import java.io.IOException;
import java.util.logging.LogManager;

public class Main {
    public static void main(String[] args) {
        try {
            LogManager.getLogManager().readConfiguration(Main.class.getResourceAsStream("/logging.properties"));
        } catch (IOException ignored) {
            System.out.println("config file was not found");
        }

        Application application = new Application();
        application.start();
    }
}
