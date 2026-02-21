package com.spring.tradexmarketservice.Controller;

import com.spring.tradexmarketservice.DTO.MarketMapper;
import com.spring.tradexmarketservice.DTO.PriceResponse;
import com.spring.tradexmarketservice.Service.MarketService;
import com.spring.tradexmarketservice.kafka.PriceEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/api/market")
@RequiredArgsConstructor
public class MarketController {

    private final MarketService marketService;


    @GetMapping("/{symbol}")
    public PriceResponse getPrice(@PathVariable String symbol) {
        BigDecimal price = marketService.getCurrentPrice(symbol);
        return MarketMapper.toResponse(symbol,price);
    }
}
