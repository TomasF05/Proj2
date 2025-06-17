package com.example.projeto2.Services;

import com.example.projeto2.Repo.LinhaReparacaoRepository; // Import LinhaReparacaoRepository
import com.example.projeto2.Repo.ReparacaoRepository;
import com.example.projeto2.Repo.LinhaReparacaoRepository; // Import LinhaReparacaoRepository
import com.example.projeto2.Repo.ReparacaoRepository;
import com.example.projeto2.Tables.LinhaReparacao; // Import LinhaReparacao
import com.example.projeto2.Tables.LinhaReparacaoId; // Import LinhaReparacaoId
import com.example.projeto2.Tables.Peca; // Import Peca
import com.example.projeto2.Tables.Reparacao;
import com.example.projeto2.Desktop.mechanic.PartQuantity; // Import PartQuantity
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.sql.Date; // Import Date
import java.time.LocalDate; // Import LocalDate
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReparacaoService {

    @Autowired
    private ReparacaoRepository reparacaoRepository;

    @Autowired
    private LinhaReparacaoRepository linhaReparacaoRepository; // Inject LinhaReparacaoRepository

    public int countTodayRepairs() {
        LocalDate today = LocalDate.now();
        return reparacaoRepository.countByDataInicioLessThanEqualAndDataFimGreaterThanEqual(today, today);
    }

    public int countCompletedRepairsToday() {
        LocalDate today = LocalDate.now();
        return reparacaoRepository.countByDataFimLessThanEqualAndEstado(today, "Concluída");
    }
    public List<Reparacao> getTodayRepairs() {
        LocalDate today = LocalDate.now();
        return reparacaoRepository.findByDataInicioLessThanEqualAndDataFimGreaterThanEqual(today, today);
    }

    public List<Reparacao> getActiveRepairs() {
        return reparacaoRepository.findByEstadoNot("Concluída");
    }

    // Salva ou atualiza uma reparação
    @Transactional
    public BigDecimal saveReparacao(Reparacao reparacao) {
        Reparacao savedReparacao = reparacaoRepository.save(reparacao);
        return savedReparacao.getIdReparacao();  // Retorna o ID da reparação salva
    }

    // Deleta uma reparação pelo ID
    @Transactional
    public void deleteReparacao(BigDecimal id) {
        reparacaoRepository.deleteById(id);
    }

    // Atualiza os dados de uma reparação existente
    @Transactional
    public void updateReparacao(BigDecimal id, Reparacao reparacao) {
        Reparacao existingReparacao = getReparacaoById(id);
        existingReparacao.setDataInicio(reparacao.getDataInicio());
        existingReparacao.setDataFim(reparacao.getDataFim());
        existingReparacao.setEstado(reparacao.getEstado());
        existingReparacao.setDescricao(reparacao.getDescricao());
        existingReparacao.setValorTotal(reparacao.getValorTotal());
        existingReparacao.setnFatura(reparacao.getnFatura());
        existingReparacao.setIdVeiculo(reparacao.getIdVeiculo());
        existingReparacao.setIdFuncionario(reparacao.getIdFuncionario());
        existingReparacao.setIdServico(reparacao.getIdServico());
        reparacaoRepository.save(existingReparacao);
    }

    // Retorna uma reparação pelo ID
    @Transactional
    public Reparacao getReparacaoById(BigDecimal id) {
        return reparacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reparação não encontrada para o id: " + id));
    }

    // Retorna todas as reparações
    @Transactional
    public List<Reparacao> getAllReparacoes() {
        return reparacaoRepository.findAll();
    }

    // Method to start a repair
    @Transactional
    public void startRepair(BigDecimal repairId) {
        Reparacao repair = getReparacaoById(repairId);
        if (repair != null && "Pendente".equals(repair.getEstado())) {
            repair.setEstado("Em andamento");
            reparacaoRepository.save(repair);
        }
    }

    // Method to finish a repair
    @Transactional
    public void finishRepair(BigDecimal repairId) {
        Reparacao repair = getReparacaoById(repairId);
        if (repair != null && "Em andamento".equals(repair.getEstado())) {
            repair.setEstado("Concluída");
            repair.setDataFim(Date.valueOf(LocalDate.now()));
            reparacaoRepository.save(repair);
        }
    }

    // Add method to add parts to a repair, accepting a list of PartQuantity
    @Autowired
    private PecaService pecaService;

    // Add method to add parts to a repair, accepting a list of PartQuantity
    @Transactional
    public void addPartsToRepair(BigDecimal repairId, List<PartQuantity> partsWithQuantity) {
        if (repairId == null || partsWithQuantity == null || partsWithQuantity.isEmpty()) {
            return;
        }

        Reparacao repair = getReparacaoById(repairId); // Fetch the repair entity
        if (repair == null) {
            throw new NoSuchElementException("Reparação não encontrada para o id: " + repairId);
        }

        BigDecimal currentTotal = repair.getValorTotal() != null ? repair.getValorTotal() : BigDecimal.ZERO;

        for (PartQuantity partQuantity : partsWithQuantity) {
            Peca part = partQuantity.getPart();
            BigDecimal quantity = partQuantity.getQuantity();

            if (part != null && part.getIdPeca() != null && quantity != null && quantity.compareTo(BigDecimal.ZERO) > 0) {
                 // Create a new LinhaReparacao for each part with the specified quantity
                LinhaReparacao linhaReparacao = new LinhaReparacao();
                LinhaReparacaoId linhaReparacaoId = new LinhaReparacaoId(repair.getIdReparacao(), part.getIdPeca());
                linhaReparacao.setId(linhaReparacaoId);
                linhaReparacao.setQtd(quantity);

                // Save the LinhaReparacao
                linhaReparacaoRepository.save(linhaReparacao);

                // Update the total value of the repair
                BigDecimal partPrice = part.getPreco() != null ? part.getPreco() : BigDecimal.ZERO;
                BigDecimal lineItemTotal = partPrice.multiply(quantity);
                currentTotal = currentTotal.add(lineItemTotal);

                // Decrease the stock quantity of the part
                BigDecimal currentStock = part.getQtd() != null ? part.getQtd() : BigDecimal.ZERO;
                part.setQtd(currentStock.subtract(quantity));
                pecaService.savePeca(part); // Save the updated part quantity

            } else {
                System.err.println("Skipping part with invalid data: " + (part != null ? part.getNome() : "null") + ", Quantity: " + quantity);
            }
        }

        repair.setValorTotal(currentTotal); // Set the updated total value
        reparacaoRepository.save(repair); // Save the updated repair
    }
}
