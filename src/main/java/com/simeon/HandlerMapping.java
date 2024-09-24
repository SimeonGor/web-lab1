package com.simeon;

import com.simeon.exceptions.ResourceNotFound;

import java.util.Objects;

public class HandlerMapping {
    private Controller controller;

    public void addController(Controller controller) {
        this.controller = controller;
    }

    public Controller map(String method, String uri) {
        if (Objects.equals(method, "POST")) {
            return controller;
        }
        else {
            throw new ResourceNotFound("Resource not found %s %s".formatted(method, uri));
        }
    }
}
