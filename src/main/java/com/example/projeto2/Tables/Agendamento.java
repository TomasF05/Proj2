package com.example.projeto2.Tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "Agendamento")
public class Agendamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idagendamento", nullable = false)
    private BigDecimal idAgendamento;

    @Column(name = "datahora")
    private Date dataHora;

    @Column(name = "estadopagamento")
    private String estadoPagamento;

    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "idveiculo")
    private BigDecimal idVeiculo;

    @Column(name = "idfuncionario")
    private BigDecimal idFuncionario;

    @ManyToOne
    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente")
    private Cliente cliente;

    public BigDecimal getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(BigDecimal idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getEstadoPagamento() {
        return estadoPagamento;
    }

    public void setEstadoPagamento(String estadoPagamento) {
        this.estadoPagamento = estadoPagamento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public BigDecimal getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(BigDecimal idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public BigDecimal getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(BigDecimal idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Convert Date to LocalTime
    public LocalTime getLocalTime() {
        return dataHora.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    @Override
    public String toString() {
        return "Agendamento{" +
                "idAgendamento=" + idAgendamento +
                ", dataHora=" + dataHora +
                ", estadoPagamento='" + estadoPagamento + '\'' +
                ", observacoes='" + observacoes + '\'' +
                ", idVeiculo=" + idVeiculo +
                ", idFuncionario=" + idFuncionario +
                ", cliente=" + cliente +
                '}';
    }
}