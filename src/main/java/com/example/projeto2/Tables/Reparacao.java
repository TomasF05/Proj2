package com.example.projeto2.Tables;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "reparacao")
public class Reparacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idreparacao", nullable = false)
    private BigDecimal idReparacao;

    @Column(name = "datainicio")
    private Date dataInicio;

    @Column(name = "datafim")
    private Date dataFim;

    @Column(name = "estado")
    private String estado;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "valortotal")
    private BigDecimal valorTotal;

    @Column(name = "nfatura")
    private BigDecimal nFatura;

    @Column(name = "idveiculo")
    private BigDecimal idVeiculo;

    @Column(name = "idfuncionario")
    private BigDecimal idFuncionario;

    @Column(name = "idservico")
    private BigDecimal idServico;

    public BigDecimal getIdReparacao() {
        return idReparacao;
    }

    public void setIdReparacao(BigDecimal idReparacao) {
        this.idReparacao = idReparacao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getnFatura() {
        return nFatura;
    }

    public void setnFatura(BigDecimal nFatura) {
        this.nFatura = nFatura;
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

    public BigDecimal getIdServico() {
        return idServico;
    }

    public void setIdServico(BigDecimal idServico) {
        this.idServico = idServico;
    }

    // Método toString() (opcional)
    @Override
    public String toString() {
        return "Reparacao{" +
                "idReparacao=" + idReparacao +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", estado='" + estado + '\'' +
                ", descricao='" + descricao + '\'' +
                ", valorTotal=" + valorTotal +
                ", nFatura=" + nFatura +
                ", idVeiculo=" + idVeiculo +
                ", idFuncionario=" + idFuncionario +
                ", idServico=" + idServico +
                '}';
    }
}