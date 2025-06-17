package com.example.projeto2.Desktop.mechanic;

import com.example.projeto2.Tables.Peca;

import java.math.BigDecimal;

public class PartQuantity {
    private Peca part;
    private BigDecimal quantity;

    public PartQuantity(Peca part, BigDecimal quantity) {
        this.part = part;
        this.quantity = quantity;
    }

    public Peca getPart() {
        return part;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}