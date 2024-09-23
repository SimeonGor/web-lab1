package com.simeon;

import com.simeon.dto.Request;
import com.simeon.dto.ResponseEntity;

public class HandlerAdapter {
    public MessageConverter messageConverter;

    public HandlerAdapter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    public ResponseEntity<?> handle(Controller controller, String httpRequestBody) {
        Request request = messageConverter.deserialize(httpRequestBody, Request.class);
        return controller.handle(request);
    }
}
