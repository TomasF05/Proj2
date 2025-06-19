package com.example.projeto2.Tables;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "encomendafornecedor")
public class EncomendaFornecedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idencfornecedor", nullable = false)
    private BigDecimal idEncFornecedor;

    @Column(name = "data")
    private Date data;

    @Column(name = "valortotal")
    private BigDecimal valorTotal;

    @Column(name = "idfornecedor")
    private BigDecimal idFornecedor;

    @OneToMany(mappedBy = "encomendaFornecedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LinhaEncFornecedor> linhasEncFornecedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idfornecedor", insertable = false, updatable = false)
    private Fornecedor fornecedor;

    // Getters e setters

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

    public List<LinhaEncFornecedor> getLinhasEncFornecedor() {
        return linhasEncFornecedor;
    }

    public void setLinhasEncFornecedor(List<LinhaEncFornecedor> linhasEncFornecedor) {
        this.linhasEncFornecedor = linhasEncFornecedor;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

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
