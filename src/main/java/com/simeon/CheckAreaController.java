package com.simeon;

import com.simeon.dto.Request;
import com.simeon.dto.Response;
import com.simeon.dto.ResponseEntity;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CheckAreaController {

    public ResponseEntity<?> handle(Request request) {
        long startTime = System.nanoTime();
        boolean isHit = checkFirstQuarter(request.getX(), request.getY(), request.getR())
                || checkSecondQuarter(request.getX(), request.getY(), request.getR())
                || checkThirdQuarter(request.getX(), request.getY(), request.getR())
                || checkForthQuarter(request.getX(), request.getY(), request.getR());
        Response response = new Response();
        response.setX(request.getX());
        response.setY(request.getY());
        response.setR(request.getR());
        response.setHit(isHit);
        response.setCreated_at(LocalDateTime.now());
        long endTime = System.nanoTime();
        response.setWorking_time(endTime - startTime);

        return ResponseEntity.builder()
                .body(response)
                .build();
    }

    private boolean checkFirstQuarter(BigDecimal x, BigDecimal y, BigDecimal r) {
        if (x.compareTo(new BigDecimal("0")) < 0 || y.compareTo(new BigDecimal("0")) < 0) return false;
        return (x.multiply(x).add(y.multiply(y))).compareTo(r.multiply(r)) <= 0;
    }

    private boolean checkSecondQuarter(BigDecimal x, BigDecimal y, BigDecimal r) {
        return false;
    }

    private boolean checkThirdQuarter(BigDecimal x, BigDecimal y, BigDecimal r) {
        if (x.compareTo(new BigDecimal("0")) > 0 || y.compareTo(new BigDecimal("0")) > 0) return false;
        return y.add(x).compareTo(r.negate()) >= 0;
    }

    private boolean checkForthQuarter(BigDecimal x, BigDecimal y, BigDecimal r) {
        if (x.compareTo(new BigDecimal("0")) < 0 || y.compareTo(new BigDecimal("0")) > 0) return false;
        return x.compareTo(r) <= 0 && y.compareTo(r.negate()) >= 0;
    }

}
