package com.example.projeto2.Tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "FaturaFornecedor")
public class FaturaFornecedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IdFaturaFornecedor", nullable = false)
    private BigDecimal idFaturaFornecedor;

    @Column(name = "Data")
    private Date data;

    @Column(name = "ValorTotal")
    private BigDecimal valorTotal;

    @Column(name = "IdEncFornecedor")
    private BigDecimal idEncFornecedor;

    // Getters and Setters
    public BigDecimal getIdFaturaFornecedor() {
        return idFaturaFornecedor;
    }

    public void setIdFaturaFornecedor(BigDecimal idFaturaFornecedor) {
        this.idFaturaFornecedor = idFaturaFornecedor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getIdEncFornecedor() {
        return idEncFornecedor;
    }

    public void setIdEncFornecedor(BigDecimal idEncFornecedor) {
        this.idEncFornecedor = idEncFornecedor;
    }

    // toString method
    @Override
    public String toString() {
        return "FaturaFornecedor{" +
                "idFaturaFornecedor=" + idFaturaFornecedor +
                ", data=" + data +
                ", valorTotal=" + valorTotal +
                ", idEncFornecedor=" + idEncFornecedor +
                '}';
    }
}