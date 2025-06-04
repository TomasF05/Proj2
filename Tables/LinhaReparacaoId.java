package com.example.projeto2.Tables;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable  // Marca a classe como uma chave composta que pode ser embutida
public class LinhaReparacaoId implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal idReparacao;
    private BigDecimal idPeca;

    // Construtor padrão
    public LinhaReparacaoId() {
    }

    // Construtor com parâmetros
    public LinhaReparacaoId(BigDecimal idReparacao, BigDecimal idPeca) {
        this.idReparacao = idReparacao;
        this.idPeca = idPeca;
    }

    // Getters e Setters
    public BigDecimal getIdReparacao() {
        return idReparacao;
    }

    public void setIdReparacao(BigDecimal idReparacao) {
        this.idReparacao = idReparacao;
    }

    public BigDecimal getIdPeca() {
        return idPeca;
    }

    public void setIdPeca(BigDecimal idPeca) {
        this.idPeca = idPeca;
    }
}