package com.example.projeto2.Web;

import com.example.projeto2.Services.AgendamentoService;
import com.example.projeto2.Tables.Agendamento;
import com.example.projeto2.Tables.Cliente;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentsRestController {

    @Autowired
    private AgendamentoService agendamentoService;

    @GetMapping
    public List<Agendamento> getAppointments(HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            throw new RuntimeException("Não autenticado");
        }

        return agendamentoService.getAllAgendamentos().stream()
                .filter(a -> a.getCliente() != null && a.getCliente().getIdCliente().equals(cliente.getIdCliente()))
                .collect(Collectors.toList());
    }
}
