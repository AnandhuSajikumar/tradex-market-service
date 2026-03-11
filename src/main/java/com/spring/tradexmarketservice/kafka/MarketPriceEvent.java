package com.spring.tradexmarketservice.kafka;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@NoArgsConstructor
public class MarketPriceEvent {
    Long stockId;
    String symbol;
    BigDecimal price;
    Instant timestamp;


    public MarketPriceEvent(Long stockId, String symbol, BigDecimal price, Instant timestamp) {
        this.stockId = stockId;
        this.symbol = symbol;
        this.price = price;
        this.timestamp = timestamp;
    }
}
