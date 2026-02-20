package com.spring.tradexmarketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TradexMarketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradexMarketServiceApplication.class, args);
    }

}
