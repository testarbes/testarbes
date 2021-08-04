package com.phonecompany.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MinutePrice {

    private LocalDateTime from;
    private LocalDateTime to;
    private BigDecimal price;

    public MinutePrice(LocalDateTime from, LocalDateTime to, BigDecimal price) {
        this.from = from;
        this.to = to;
        this.price = price;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
