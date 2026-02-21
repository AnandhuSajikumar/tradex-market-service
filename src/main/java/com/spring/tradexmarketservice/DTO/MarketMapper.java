package com.spring.tradexmarketservice.DTO;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MarketMapper {

    public static PriceResponse toResponse(String symbol, BigDecimal price){
        return new PriceResponse(symbol, price);
    }
}
