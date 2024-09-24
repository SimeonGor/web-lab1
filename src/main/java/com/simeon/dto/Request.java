package com.simeon.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
public class Request {
    @DecimalMin("-2.0")
    @DecimalMax("2")
    private BigDecimal x;
    @DecimalMin("-3")
    @DecimalMax("5")
    private BigDecimal y;
    @DecimalMin("1")
    @DecimalMax("3")
    private BigDecimal r;
}
