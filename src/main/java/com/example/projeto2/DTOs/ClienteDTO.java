package com.example.projeto2.DTOs;

import com.example.projeto2.Tables.Cliente;
import java.math.BigDecimal;

public class ClienteDTO {
    public BigDecimal idCliente;
    public String nome;
    public String contacto;
    public String username;
    public String nif;
    public String password;

    public ClienteDTO() {}

    public ClienteDTO(Cliente cliente) {
        this.idCliente = cliente.getIdCliente();
        this.nome = cliente.getNome();
        this.contacto = cliente.getContacto();
        this.username = cliente.getUsername();
        this.nif = cliente.getNif();
    }
}
