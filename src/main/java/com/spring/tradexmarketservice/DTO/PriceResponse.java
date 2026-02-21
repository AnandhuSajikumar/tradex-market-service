package com.spring.tradexmarketservice.DTO;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class PriceResponse {
    private String symbol;
    private BigDecimal price;

    public PriceResponse(String symbol, BigDecimal price) {
        this.symbol = symbol;
        this.price = price;
    }
}
