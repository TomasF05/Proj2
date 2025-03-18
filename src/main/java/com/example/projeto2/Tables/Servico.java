package com.example.projeto2.Tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "Servico")
public class Servico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IdServico", nullable = false)
    private BigDecimal idServico;

    @Column(name = "Nome")
    private String nome;

    @Column(name = "Descricao")
    private String descricao;

    // Getters e Setters

    public BigDecimal getIdServico() {
        return idServico;
    }

    public void setIdServico(BigDecimal idServico) {
        this.idServico = idServico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // Método toString() (opcional)
    @Override
    public String toString() {
        return "Servico{" +
                "idServico=" + idServico +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}