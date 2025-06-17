package com.example.projeto2.Tables;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "linhaencfornecedor")
public class LinhaEncFornecedor {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private LinhaEncFornecedorId id;

    @Column(name = "qtd")
    private BigDecimal qtd;

    @Column(name = "valortotal")
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

    public EncomendaFornecedor getEncomendaFornecedor() {
        return this.getId().getEncomendaFornecedor();
    }

    public Peca getPeca() {
        return this.getId().getPeca();
    }

    @Column(name = "qtdrecebida")
    private BigDecimal qtdRecebida;

    public BigDecimal getQtdRecebida() {
        return this.qtdRecebida;
    }

    public void setQtdRecebida(BigDecimal qtdRecebida) {
        this.qtdRecebida = qtdRecebida;
    }
}
