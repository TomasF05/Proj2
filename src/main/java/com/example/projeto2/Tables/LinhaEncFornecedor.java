package com.example.projeto2.Tables;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "LinhaEncFornecedor")
public class LinhaEncFornecedor {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private LinhaEncFornecedorId id;

    @Column(name = "Qtd")
    private BigDecimal qtd;

    @Column(name = "ValorTotal")
    private BigDecimal valorTotal;

    // Getters and Setters
    public LinhaEncFornecedorId getId() {
        return id;
    }

    public void setId(LinhaEncFornecedorId id) {
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

    // toString method
    @Override
    public String toString() {
        return "LinhaEncFornecedor{" +
                "id=" + id +
                ", qtd=" + qtd +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
