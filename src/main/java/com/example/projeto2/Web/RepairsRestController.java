package com.example.projeto2.Web;

import com.example.projeto2.DTOs.VeiculoDTO;
import com.example.projeto2.DTOs.ReparacaoDTO;
import com.example.projeto2.Services.ReparacaoService;
import com.example.projeto2.Services.VeiculoService;
import com.example.projeto2.Tables.Cliente;
import com.example.projeto2.Tables.Reparacao;
import com.example.projeto2.Tables.Veiculo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/repairs")
public class RepairsRestController {

    @Autowired private ReparacaoService reparacaoService;
    @Autowired private VeiculoService veiculoService;

    @GetMapping
    public ResponseEntity<?> getReparacoes(HttpSession session,
                                           @RequestParam(required = false) BigDecimal veiculo) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não autenticado");
        }

        List<Veiculo> veiculosEnt = veiculoService.getAllVeiculos().stream()
                .filter(v -> v.getIdCliente().equals(cliente.getIdCliente()))
                .collect(Collectors.toList());

        List<Reparacao> reparacoesEnt = reparacaoService.getAllReparacoes().stream()
                .filter(r -> veiculosEnt.stream().anyMatch(v -> v.getIdVeiculo().equals(r.getIdVeiculo())))
                .collect(Collectors.toList());

        if (veiculo != null) {
            reparacoesEnt = reparacoesEnt.stream()
                    .filter(r -> r.getIdVeiculo().equals(veiculo))
                    .collect(Collectors.toList());
        }

        // Atualiza o estado baseado nas datas atuais
        LocalDate hoje = LocalDate.now();
        reparacoesEnt.forEach(r -> {
            LocalDate dataInicio = r.getDataInicio().toLocalDate();
            LocalDate dataFim = r.getDataFim() != null ? r.getDataFim().toLocalDate() : dataInicio;
            if (hoje.isBefore(dataInicio)) {
                r.setEstado("Pendente");
            } else if ((hoje.isEqual(dataInicio) || hoje.isAfter(dataInicio)) && (hoje.isBefore(dataFim) || hoje.isEqual(dataFim))) {
                r.setEstado("Em execução");
            } else if (hoje.isAfter(dataFim)) {
                r.setEstado("Concluída");
            }
        });

        List<VeiculoDTO> veiculos = veiculosEnt.stream().map(VeiculoDTO::new).collect(Collectors.toList());
        List<ReparacaoDTO> reparacoes = reparacoesEnt.stream().map(ReparacaoDTO::new).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("veiculos", veiculos);
        response.put("reparacoes", reparacoes);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/agendar")
    public ResponseEntity<?> agendarReparacao(@RequestBody Map<String, String> dados, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não autenticado");
        }

        try {
            String descricao = dados.get("descricao");
            String dataInicioStr = dados.get("dataInicio");
            BigDecimal idVeiculo = new BigDecimal(dados.get("idVeiculo"));

            LocalDateTime localDateTimeInicio = LocalDateTime.parse(dataInicioStr);

            // Verifica se a dataInicio é anterior ao momento atual
            if (localDateTimeInicio.isBefore(LocalDateTime.now())) {
                String msg = "Não pode agendar reparações para datas anteriores a hoje.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
            }

            java.sql.Date dataInicio = java.sql.Date.valueOf(localDateTimeInicio.toLocalDate());
            java.sql.Date dataFim = java.sql.Date.valueOf(localDateTimeInicio.toLocalDate().plusDays(1));

            LocalDate hoje = LocalDate.now();
            String estado;
            if (hoje.isBefore(localDateTimeInicio.toLocalDate())) {
                estado = "Pendente";
            } else if (hoje.isEqual(localDateTimeInicio.toLocalDate())) {
                estado = "Em execução";
            } else {
                estado = "Concluída";
            }

            Reparacao nova = new Reparacao();
            nova.setDescricao(descricao);
            nova.setDataInicio(dataInicio);
            nova.setDataFim(dataFim);
            nova.setIdVeiculo(idVeiculo);
            nova.setEstado(estado);
            nova.setIdFuncionario(null);
            nova.setValorTotal(null);
            nova.setIdServico(null);
            nova.setnFatura(null);

            reparacaoService.saveReparacao(nova);
            return ResponseEntity.ok("Agendado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao agendar reparação.");
        }
    }
}