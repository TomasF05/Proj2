package com.example.projeto2.Web;

import com.example.projeto2.Services.ClienteService;
import com.example.projeto2.Tables.Cliente;
import com.example.projeto2.DTOs.ClienteDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginRestController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/login")
    public Cliente login(@RequestBody Cliente dados, HttpSession session) {
        List<Cliente> clientes = clienteService.getAllClientes();
        Optional<Cliente> clienteOpt = clientes.stream()
                .filter(c -> dados.getUsername().equals(c.getUsername()) &&
                        dados.getPassword().equals(c.getPassword()))
                .findFirst();

        if (clienteOpt.isPresent()) {
            session.setAttribute("cliente", clienteOpt.get());
            return clienteOpt.get();
        } else {
            throw new RuntimeException("Credenciais inválidas");
        }
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @GetMapping("/session")
    public ClienteDTO getSession(HttpSession session) {
        Object cliente = session.getAttribute("cliente");
        if (cliente instanceof Cliente) {
            return new ClienteDTO((Cliente) cliente);
        } else {
            throw new RuntimeException("Não autenticado");
        }
    }
}
