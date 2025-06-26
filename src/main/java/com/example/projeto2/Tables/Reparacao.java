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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreparacao", nullable = false)
    private Long idReparacao;

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

    public Long getIdReparacao() {
        return idReparacao;
    }

    public void setIdReparacao(Long idReparacao) {
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
                ", veiculo=" + (veiculo != null ? veiculo.getModelo() : "null") + // Include vehicle model if available
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reparacao reparacao = (Reparacao) o;
        return idReparacao != null && idReparacao.equals(reparacao.idReparacao);
    }

    @Override
    public int hashCode() {
        return idReparacao != null ? idReparacao.hashCode() : 0;
    }

    @ManyToOne
    @JoinColumn(name = "idveiculo", insertable = false, updatable = false)
    private Veiculo veiculo;

    public Cliente getCliente() {
        if (veiculo != null) {
            return veiculo.getCliente();
        }
        return null;
    }
}
