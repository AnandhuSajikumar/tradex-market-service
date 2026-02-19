package com.spring.tradexmarketservice.Model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "stocks")

public class Stock {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long stockId;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal currentPrice;
}
