package com.example.projeto2.Tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "EncomendaFornecedor")
public class EncomendaFornecedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IdEncFornecedor", nullable = false)
    private BigDecimal idEncFornecedor;

    @Column(name = "Data")
    private Date data;

    @Column(name = "ValorTotal")
    private BigDecimal valorTotal;

    @Column(name = "IdFornecedor")
    private BigDecimal idFornecedor;

    // Getters and Setters
    public BigDecimal getIdEncFornecedor() {
        return idEncFornecedor;
    }

    public void setIdEncFornecedor(BigDecimal idEncFornecedor) {
        this.idEncFornecedor = idEncFornecedor;
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

    public BigDecimal getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(BigDecimal idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    // toString method
    @Override
    public String toString() {
        return "EncomendaFornecedor{" +
                "idEncFornecedor=" + idEncFornecedor +
                ", data=" + data +
                ", valorTotal=" + valorTotal +
                ", idFornecedor=" + idFornecedor +
                '}';
    }
}
