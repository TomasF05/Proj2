package com.example.projeto2.Tables;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "\"Fornecedor\"", schema = "public")
public class Fornecedor {

    @Id
    @Column(name = "\"IdFornecedor\"", nullable = false, precision = 8)
    private BigDecimal id;

    @Column(name = "\"Nome\"", length = 50)
    private String nome;

    @Column(name = "\"NIF\"", precision = 9)
    private BigDecimal nif;

    @Column(name = "\"Contacto\"", precision = 9)
    private BigDecimal contacto;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "\"CodPostal\"")
    private CodPostal codPostal;

    @OneToMany(mappedBy = "idFornecedor")
    private Set<EncomendaFornecedor> encomendaFornecedors = new LinkedHashSet<>();

    // Getters and Setters
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getNif() {
        return nif;
    }

    public void setNif(BigDecimal nif) {
        this.nif = nif;
    }

    public BigDecimal getContacto() {
        return contacto;
    }

    public void setContacto(BigDecimal contacto) {
        this.contacto = contacto;
    }

    public CodPostal getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(CodPostal codPostal) {
        this.codPostal = codPostal;
    }

    public Set<EncomendaFornecedor> getEncomendaFornecedors() {
        return encomendaFornecedors;
    }

    public void setEncomendaFornecedors(Set<EncomendaFornecedor> encomendaFornecedors) {
        this.encomendaFornecedors = encomendaFornecedors;
    }

    // toString method
    @Override
    public String toString() {
        return "Fornecedor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", nif=" + nif +
                ", contacto=" + contacto +
                ", codPostal=" + codPostal +
                '}';
    }
}
