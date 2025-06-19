package com.example.projeto2.Web;

import com.example.projeto2.Services.FaturaClienteService;
import com.example.projeto2.Tables.Cliente;
import com.example.projeto2.Tables.FaturaCliente;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesRestController {

    @Autowired
    private FaturaClienteService faturaClienteService;

    @GetMapping
    public List<FaturaCliente> getFaturas(HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            throw new RuntimeException("Não autenticado");
        }

        return faturaClienteService.getAllFaturasCliente().stream()
                .filter(f -> f.getIdCliente().equals(cliente.getIdCliente()))
                .collect(Collectors.toList());
    }
}
