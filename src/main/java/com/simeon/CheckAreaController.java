package com.simeon;

import com.simeon.dto.Request;
import com.simeon.dto.Response;
import com.simeon.dto.ResponseEntity;
import lombok.SneakyThrows;

import java.time.LocalDateTime;

public class CheckAreaController implements Controller {

    @SneakyThrows
    @Override
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

    private boolean checkFirstQuarter(double x, double y, double r) {
        if (x < 0 || y < 0) return false;
        return (x * x + y * y) <= r * r;
    }

    private boolean checkSecondQuarter(double x, double y, double r) {
        return false;
    }

    private boolean checkThirdQuarter(double x, double y, double r) {
        if (x > 0 || y > 0) return false;
        return y + x >= -r;
    }

    private boolean checkForthQuarter(double x, double y, double r) {
        if (x < 0 || y > 0) return false;
        return x <= r && y >= -r;
    }

}
