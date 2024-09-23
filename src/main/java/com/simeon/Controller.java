package com.simeon;

import com.simeon.dto.Request;
import com.simeon.dto.ResponseEntity;

public interface Controller {
    ResponseEntity<?> handle(Request request);
}
