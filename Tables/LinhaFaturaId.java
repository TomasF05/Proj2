package com.example.projeto2.Tables;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
public class LinhaFaturaId implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal idFatura;
    private BigDecimal idPeca;

    // Construtor padrão
    public LinhaFaturaId() {
    }

    // Construtor com parâmetros
    public LinhaFaturaId(BigDecimal idFatura, BigDecimal idPeca) {
        this.idFatura = idFatura;
        this.idPeca = idPeca;
    }

    // Getters e Setters
    public BigDecimal getIdFatura() {
        return idFatura;
    }

    public void setIdFatura(BigDecimal idFatura) {
        this.idFatura = idFatura;
    }

    public BigDecimal getIdPeca() {
        return idPeca;
    }

    public void setIdPeca(BigDecimal idPeca) {
        this.idPeca = idPeca;
    }
}
