package com.spring.tradexmarketservice.kafka;

import com.spring.tradexmarketservice.Model.Stock;
import com.spring.tradexmarketservice.Repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class PriceEngine {

    private final StockRepository stockRepository;
    private final KafkaTemplate<String, MarketPriceEvent> kafkaTemplate;

    private final Map<String, BigDecimal> latestPrices = new ConcurrentHashMap<>();

    @Scheduled(fixedRate = 2000)
    public void updatePrices() {

        List<Stock> stocks = stockRepository.findAll();

        for (Stock stock : stocks) {

            BigDecimal current =
                    latestPrices.getOrDefault(
                            stock.getSymbol(),
                            stock.getBasePrice()
                    );

            double drift = (Math.random() - 0.5) * 0.02;

            BigDecimal newPrice =
                    current.multiply(BigDecimal.valueOf(1 + drift))
                            .setScale(2, RoundingMode.HALF_UP);

            latestPrices.put(stock.getSymbol(), newPrice);

            kafkaTemplate.send(
                    "market-price-topic",
                    stock.getSymbol(),
                    new MarketPriceEvent(
                            stock.getSymbol(),
                            newPrice,
                            Instant.now()
                    )
            );
        }
    }

    public BigDecimal getCurrentPrice(String symbol) {
        return latestPrices.get(symbol);
    }
}