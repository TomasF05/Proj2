package com.example.projeto2.Tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "Funcionario")
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IdFuncionario", nullable = false)
    private BigDecimal idFuncionario;

    @Column(name = "Nome")
    private String nome;

    @Column(name = "Tipo")
    private BigDecimal tipo;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    // Getters and Setters
    public BigDecimal getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(BigDecimal idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getTipo() {
        return tipo;
    }

    public void setTipo(BigDecimal tipo) {
        this.tipo = tipo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString method
    @Override
    public String toString() {
        return "Funcionario{" +
                "idFuncionario=" + idFuncionario +
                ", nome='" + nome + '\'' +
                ", tipo=" + tipo +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
