package com.simeon;

import com.simeon.dto.ErrorMassage;
import com.simeon.dto.ResponseEntity;
import com.simeon.exceptions.HttpFormatException;
import com.simeon.exceptions.SerializationException;
import com.simeon.view.ViewResolver;

public class Dispatcher {
    private final ViewResolver viewResolver;
    private final HandlerAdapter handlerAdapter;
    private final HandlerMapping handlerMapping;

    public Dispatcher(ViewResolver viewResolver, HandlerAdapter handlerAdapter, HandlerMapping handlerMapping) {
        this.handlerAdapter = handlerAdapter;
        this.viewResolver = viewResolver;
        this.handlerMapping = handlerMapping;
    }

    public String handle(HttpRequest httpRequest) {
        ResponseEntity<?> response;
        try {
            Controller controller = handlerMapping.map(httpRequest.getMethod(), httpRequest.getUri());
            response = handlerAdapter.handle(controller, httpRequest.getBody());
        } catch (SerializationException e) {
            response = ResponseEntity.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorMassage(e.getMessage())).build();
        }
        return viewResolver.getHttpResponse(response);
    }
}
