package com.simeon;

import com.fastcgi.FCGIInterface;
import com.fastcgi.FCGIRequest;
import com.simeon.dto.Request;
import com.simeon.dto.Response;
import com.simeon.dto.ResponseEntity;
import com.simeon.view.ViewResolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Application application = new Application();
        application.start();
    }
}
