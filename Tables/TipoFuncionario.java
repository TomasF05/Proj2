package com.example.projeto2.Tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "tipofuncionario")
public class TipoFuncionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idtipo", nullable = false)
    private BigDecimal idTipo;

    @Column(name = "tipofuncionario")
    private String tipoFuncionario;

    // Getters e Setters

    public BigDecimal getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(BigDecimal idTipo) {
        this.idTipo = idTipo;
    }

    public String getTipoFuncionario() {
        return tipoFuncionario;
    }

    public void setTipoFuncionario(String tipoFuncionario) {
        this.tipoFuncionario = tipoFuncionario;
    }

    // Método toString() (opcional)
    @Override
    public String toString() {
        return "TipoFuncionario{" +
                "idTipo=" + idTipo +
                ", tipoFuncionario='" + tipoFuncionario + '\'' +
                '}';
    }
}