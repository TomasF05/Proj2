package com.example.projeto2.DTOs;

import com.example.projeto2.Tables.Reparacao;
import java.math.BigDecimal;
import java.sql.Date;

public class ReparacaoDTO {
    private Long idReparacao;
    private BigDecimal idVeiculo;
    private String descricao;
    private Date dataInicio;
    private Date dataFim;
    private String estado;
    private Double valorTotal;

    public ReparacaoDTO() {}

    public ReparacaoDTO(Reparacao reparacao) {
        this.idReparacao = reparacao.getIdReparacao();
        this.idVeiculo = reparacao.getIdVeiculo();
        this.descricao = reparacao.getDescricao();
        this.dataInicio = reparacao.getDataInicio();
        this.dataFim = reparacao.getDataFim();
        this.estado = reparacao.getEstado();
        this.valorTotal = reparacao.getValorTotal() != null ? reparacao.getValorTotal().doubleValue() : null;
    }

    public Long getIdReparacao() {
        return idReparacao;
    }

    public BigDecimal getIdVeiculo() {
        return idVeiculo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public String getEstado() {
        return estado;
    }

    public Double getValorTotal() {
        return valorTotal;
    }
}
