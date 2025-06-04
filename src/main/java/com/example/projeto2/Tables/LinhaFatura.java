package com.example.projeto2.Tables;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "LinhaFatura")
public class LinhaFatura implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId  // Usando a chave composta embutida
    private LinhaFaturaId id;  // A chave primária composta é agora um objeto de tipo LinhaFaturaId

    @Column(name = "Qtd")
    private BigDecimal qtd;

    @Column(name = "ValorTotal")
    private BigDecimal valorTotal;

    // Getters e Setters
    public LinhaFaturaId getId() {
        return id;
    }

    public void setId(LinhaFaturaId id) {
        this.id = id;
    }

    public BigDecimal getQtd() {
        return qtd;
    }

    public void setQtd(BigDecimal qtd) {
        this.qtd = qtd;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    // toString() method
    @Override
    public String toString() {
        return "LinhaFatura{" +
                "id=" + id +
                ", qtd=" + qtd +
                ", valorTotal=" + valorTotal +
                '}';
    }
}