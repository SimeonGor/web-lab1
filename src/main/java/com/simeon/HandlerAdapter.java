package com.simeon;

import com.simeon.dto.Request;
import com.simeon.dto.ResponseEntity;
import com.simeon.exceptions.SerializationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class HandlerAdapter {
    private static final Validator validator;
    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }
    public MessageConverter messageConverter;

    public HandlerAdapter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    public ResponseEntity<?> handle(CheckAreaController controller, String httpRequestBody) {
        Request request = messageConverter.deserialize(httpRequestBody, Request.class);
        Set<ConstraintViolation<Request>> validates =
                validator.validate(request);

        if (!validates.isEmpty()) {
            throw new SerializationException("invalid constraints");
        }

        return controller.handle(request);
    }
}
