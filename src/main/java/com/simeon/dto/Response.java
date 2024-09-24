package com.simeon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Response {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private boolean isHit;
    private long working_time;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime created_at;
}
