package com.example.projeto2.Web;

import com.example.projeto2.Services.VeiculoService;
import com.example.projeto2.Tables.Cliente;
import com.example.projeto2.Tables.Veiculo;
import com.example.projeto2.DTOs.VeiculoDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicles")
public class VehiclesRestController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public List<VeiculoDTO> getVeiculos(HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            throw new RuntimeException("Não autenticado");
        }

        return veiculoService.getAllVeiculos().stream()
                .filter(v -> v.getIdCliente().equals(cliente.getIdCliente()))
                .map(VeiculoDTO::new) //converte cada veiculo em VeiculoDTO
                .collect(Collectors.toList());
    }
}
