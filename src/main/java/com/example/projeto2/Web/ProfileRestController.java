package com.example.projeto2.Web;

import com.example.projeto2.DTOs.ClienteDTO;
import com.example.projeto2.Services.ClienteService;
import com.example.projeto2.Tables.Cliente;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProfileRestController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/profile")
    public ClienteDTO getProfile(HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            throw new RuntimeException("Não autenticado");
        }
        return new ClienteDTO(cliente);
    }

    @PostMapping("/profile/update")
    public ClienteDTO updateProfile(@RequestBody ClienteDTO clienteDTO, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            throw new RuntimeException("Não autenticado");
        }

        cliente.setNome(clienteDTO.nome);
        cliente.setContacto(clienteDTO.contacto);
        if (clienteDTO.password != null && !clienteDTO.password.isEmpty()) {
            cliente.setPassword(clienteDTO.password);
        }

        clienteService.saveCliente(cliente);
        session.setAttribute("cliente", cliente);

        return new ClienteDTO(cliente);
    }
}
