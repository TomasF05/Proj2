package com.example.projeto2.DTOs;

import com.example.projeto2.Tables.Veiculo;
import java.math.BigDecimal;

public class VeiculoDTO {
    private BigDecimal idVeiculo;
    private String marca;
    private String modelo;
    private String matricula;
    private BigDecimal ano;

    public VeiculoDTO() {}

    public VeiculoDTO(Veiculo veiculo) {
        this.idVeiculo = veiculo.getIdVeiculo();
        this.marca = veiculo.getMarca();
        this.modelo = veiculo.getModelo();
        this.matricula = veiculo.getMatricula();
        this.ano = veiculo.getAno();
    }

    public BigDecimal getIdVeiculo() {
        return idVeiculo;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public BigDecimal getAno() {
        return ano;
    }
}
