package com.simeon.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class Request {
    @Min(-2)
    @Max(2)
    private double x;
    @Min(-3)
    @Max(5)
    private double y;
    @Min(1)
    @Max(3)
    private double r;
}
