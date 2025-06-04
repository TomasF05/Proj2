package com.example.projeto2.Tables;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "LinhaReparacao")
public class LinhaReparacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private LinhaReparacaoId id;  // A chave composta é agora um objeto

    @Column(name = "Qtd")
    private BigDecimal qtd;

    // Getters e Setters
    public LinhaReparacaoId getId() {
        return id;
    }

    public void setId(LinhaReparacaoId id) {
        this.id = id;
    }

    public BigDecimal getQtd() {
        return qtd;
    }

    public void setQtd(BigDecimal qtd) {
        this.qtd = qtd;
    }

    // toString() method
    @Override
    public String toString() {
        return "LinhaReparacao{" +
                "id=" + id +
                ", qtd=" + qtd +
                '}';
    }
}