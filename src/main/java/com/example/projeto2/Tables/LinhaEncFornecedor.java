package com.example.projeto2.Tables;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "linhaencfornecedor")
public class LinhaEncFornecedor {

    @EmbeddedId
    private LinhaEncFornecedorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idEncFornecedor") // referencia o campo idEncFornecedor no ID composto
    @JoinColumn(name = "idencfornecedor", nullable = false)
    private EncomendaFornecedor encomendaFornecedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPeca") // referencia o campo idPeca no ID composto
    @JoinColumn(name = "idpeca", nullable = false)
    private Peca peca;

    @Column(name = "qtd")
    private BigDecimal qtd;

    @Column(name = "valortotal")
    private BigDecimal valorTotal;

    @Column(name = "qtdrecebida")
    private BigDecimal qtdRecebida;

    // Getters e setters

    public LinhaEncFornecedorId getId() {
        return id;
    }

    public void setId(LinhaEncFornecedorId id) {
        this.id = id;
    }

    public EncomendaFornecedor getEncomendaFornecedor() {
        return encomendaFornecedor;
    }

    public void setEncomendaFornecedor(EncomendaFornecedor encomendaFornecedor) {
        this.encomendaFornecedor = encomendaFornecedor;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
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

    public BigDecimal getQtdRecebida() {
        return qtdRecebida;
    }

    public void setQtdRecebida(BigDecimal qtdRecebida) {
        this.qtdRecebida = qtdRecebida;
    }

    @Override
    public String toString() {
        return "LinhaEncFornecedor{" +
                "id=" + id +
                ", qtd=" + qtd +
                ", valorTotal=" + valorTotal +
                ", qtdRecebida=" + qtdRecebida +
                '}';
    }
}
