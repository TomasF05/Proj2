package com.example.projeto2.Tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "FaturaCliente")
public class FaturaCliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "nFatura", nullable = false)
    private BigDecimal nFatura;

    @Column(name = "Data")
    private Date data;

    @Column(name = "ValorTotal")
    private BigDecimal valorTotal;

    @Column(name = "IdCliente")
    private BigDecimal idCliente;

    // Getters and Setters
    public BigDecimal getnFatura() {
        return nFatura;
    }

    public void setnFatura(BigDecimal nFatura) {
        this.nFatura = nFatura;
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

    public BigDecimal getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(BigDecimal idCliente) {
        this.idCliente = idCliente;
    }

    // toString method
    @Override
    public String toString() {
        return "FaturaCliente{" +
                "nFatura=" + nFatura +
                ", data=" + data +
                ", valorTotal=" + valorTotal +
                ", idCliente=" + idCliente +
                '}';
    }
}
