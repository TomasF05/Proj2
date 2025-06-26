package com.example.projeto2.Web;

import com.example.projeto2.DTOs.ClienteDTO;
import com.example.projeto2.DTOs.ReparacaoDTO;
import com.example.projeto2.DTOs.VeiculoDTO;
import com.example.projeto2.Services.*;
import com.example.projeto2.Tables.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DashboardRestController {

    @Autowired private VeiculoService veiculoService;
    @Autowired private ReparacaoService reparacaoService;
    @Autowired private AgendamentoService agendamentoService;

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardData(HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            throw new RuntimeException("Não autenticado");
        }

        BigDecimal idCliente = cliente.getIdCliente();

        System.out.println("=== DEBUG DASHBOARD ===");
        System.out.println("Cliente logado: " + cliente.getNome() + " (ID: " + idCliente + ")");

        // Verificar veículos na BD
        List<Veiculo> todosVeiculos = veiculoService.getAllVeiculos();
        for (Veiculo v : todosVeiculos) {
            System.out.println("Veículo BD - ID: " + v.getIdVeiculo() + ", ID Cliente: " + v.getIdCliente());
        }

        List<Veiculo> veiculos = todosVeiculos.stream()
                .filter(v -> v.getIdCliente() != null && v.getIdCliente().equals(idCliente))
                .collect(Collectors.toList());

        List<VeiculoDTO> veiculosDTO = veiculos.stream()
                .map(VeiculoDTO::new)
                .collect(Collectors.toList());

        System.out.println("Veículos filtrados: " + veiculosDTO.size());

        // Reparações ligadas aos veículos do cliente
        List<Reparacao> todasReparacoes = reparacaoService.getAllReparacoes();
        List<Reparacao> reparacoes = todasReparacoes.stream()
                .filter(r -> veiculos.stream()
                        .anyMatch(v -> v.getIdVeiculo().equals(r.getIdVeiculo())))
                .collect(Collectors.toList());

        // Atualizar estados dinamicamente
        LocalDate hoje = LocalDate.now();
        reparacoes.forEach(r -> {
            LocalDate dataInicio = r.getDataInicio().toLocalDate();
            LocalDate dataFim = r.getDataFim() != null ? r.getDataFim().toLocalDate() : dataInicio;
            if (hoje.isBefore(dataInicio)) {
                r.setEstado("Pendente");
            } else if ((hoje.isEqual(dataInicio) || hoje.isAfter(dataInicio)) && (hoje.isBefore(dataFim) || hoje.isEqual(dataFim))) {
                r.setEstado("Em execução");
            } else {
                r.setEstado("Concluída");
            }
        });

        List<ReparacaoDTO> reparacoesDTO = reparacoes.stream()
                .map(ReparacaoDTO::new)
                .collect(Collectors.toList());

        System.out.println("Reparações filtradas: " + reparacoesDTO.size());

        // Agendamentos do cliente
        List<Agendamento> agendamentos = agendamentoService.getAllAgendamentos().stream()
                .filter(a -> a.getCliente() != null && idCliente.equals(a.getCliente().getIdCliente()))
                .collect(Collectors.toList());

        agendamentos.forEach(a -> a.setCliente(null));

        System.out.println("Agendamentos filtrados: " + agendamentos.size());

        // Montar resposta
        Map<String, Object> response = new HashMap<>();
        response.put("cliente", new ClienteDTO(cliente)); // <- Aqui está a mudança!
        response.put("veiculos", veiculosDTO);
        response.put("reparacoes", reparacoesDTO);
        response.put("agendamentos", agendamentos);

        return response;
    }
}
