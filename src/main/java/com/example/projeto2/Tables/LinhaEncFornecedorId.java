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

    public EncomendaFornecedor getEncomendaFornecedor() {
        return null; // These should be retrieved from LinhaEncFornecedor using the ID
    }

    public Peca getPeca() {
        return null; // These should be retrieved from LinhaEncFornecedor using the ID
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinhaEncFornecedorId that = (LinhaEncFornecedorId) o;

        if (!idPeca.equals(that.idPeca)) return false;
        return idEncFornecedor.equals(that.idEncFornecedor);
    }

    @Override
    public int hashCode() {
        int result = idPeca.hashCode();
        result = 31 * result + idEncFornecedor.hashCode();
        return result;
    }
}
