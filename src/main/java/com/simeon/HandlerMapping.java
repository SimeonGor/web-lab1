package com.simeon;

public class HandlerMapping {
    private Controller controller;

    public void addController(Controller controller) {
        this.controller = controller;
    }

    public Controller map(String method, String uri) {
        return controller;
    }
}
