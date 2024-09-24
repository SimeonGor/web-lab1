package com.simeon;

import com.simeon.exceptions.MethodNotAllowedException;
import com.simeon.exceptions.ResourceNotFound;

public class HandlerMapping {
    private CheckAreaController controller;

    public void addController(CheckAreaController controller) {
        this.controller = controller;
    }

    public CheckAreaController map(String method, String uri) {
        if (uri.equals("")) {
            if (method.equals("POST")) {
                return controller;
            } else {
                throw new MethodNotAllowedException("Method are not allowed %s %s".formatted(method, uri));
            }
        } else {
            throw new ResourceNotFound("Resource not found %s".formatted(uri));
        }
    }
}
