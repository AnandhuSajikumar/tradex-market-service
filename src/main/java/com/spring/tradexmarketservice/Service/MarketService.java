package com.spring.tradexmarketservice.Service;

import com.spring.tradexmarketservice.DTO.MarketMapper;
import com.spring.tradexmarketservice.DTO.PriceResponse;
import com.spring.tradexmarketservice.Repository.StockRepository;
import com.spring.tradexmarketservice.kafka.MarketPriceEvent;
import com.spring.tradexmarketservice.kafka.PriceEngine;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.query.ParameterBinding;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final StockRepository stockRepository;
    private final KafkaTemplate<String, MarketPriceEvent> kafkaTemplate;

    private final Map<String, BigDecimal> latestPrices = new ConcurrentHashMap<>();
    private final PriceEngine priceEngine;

    @PostConstruct
    public void initPrices() {
        stockRepository.findAll().forEach(stock ->
                latestPrices.put(stock.getSymbol(), stock.getBasePrice())
        );
    }

    @Scheduled(fixedRate = 2000)
    public void publishPriceUpdates() {

        for (Map.Entry<String, BigDecimal> entry : latestPrices.entrySet()) {

            String symbol = entry.getKey();
            BigDecimal current = entry.getValue();

            double drift = (Math.random() - 0.5) * 0.02;

            BigDecimal newPrice =
                    current.multiply(BigDecimal.valueOf(1 + drift))
                            .setScale(2, RoundingMode.HALF_UP);

            latestPrices.put(symbol, newPrice);

            kafkaTemplate.send(
                    "market-price-topic",
                    symbol,
                    new MarketPriceEvent(
                            symbol,
                            newPrice,
                            Instant.now()
                    )
            );
        }
    }

    public BigDecimal getCurrentPrice(String symbol) {
        return priceEngine.getCurrentPrice(symbol);

    }
}
