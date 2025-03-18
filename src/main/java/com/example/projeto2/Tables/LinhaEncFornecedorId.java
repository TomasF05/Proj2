package com.example.projeto2.Tables;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
public class LinhaEncFornecedorId implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal idPeca;
    private BigDecimal idEncFornecedor;

    // Construtor padrão
    public LinhaEncFornecedorId() {}

    // Construtor com parâmetros
    public LinhaEncFornecedorId(BigDecimal idPeca, BigDecimal idEncFornecedor) {
        this.idPeca = idPeca;
        this.idEncFornecedor = idEncFornecedor;
    }

    // Getters e Setters
    public BigDecimal getIdPeca() {
        return idPeca;
    }

    public void setIdPeca(BigDecimal idPeca) {
        this.idPeca = idPeca;
    }

    public BigDecimal getIdEncFornecedor() {
        return idEncFornecedor;
    }

    public void setIdEncFornecedor(BigDecimal idEncFornecedor) {
        this.idEncFornecedor = idEncFornecedor;
    }

    @Override
    public String toString() {
        return "LinhaEncFornecedorId{" +
                "idPeca=" + idPeca +
                ", idEncFornecedor=" + idEncFornecedor +
                '}';
    }
}

