package com.example.projeto2.Services;

import com.example.projeto2.Repo.LinhaReparacaoRepository;
import com.example.projeto2.Repo.ReparacaoRepository;
import com.example.projeto2.Tables.LinhaReparacao;
import com.example.projeto2.Tables.LinhaReparacaoId;
import com.example.projeto2.Tables.Peca;
import com.example.projeto2.Tables.Reparacao;
import com.example.projeto2.Desktop.mechanic.PartQuantity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReparacaoService {

    @Autowired private ReparacaoRepository reparacaoRepository;
    @Autowired private LinhaReparacaoRepository linhaReparacaoRepository;
    @Autowired private PecaService pecaService;

    // ---------- Contagens e consultas ----------
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

    // ---------- CRUD ----------
    @Transactional
    public Long saveReparacao(Reparacao reparacao) {
        Reparacao saved = reparacaoRepository.save(reparacao);
        return saved.getIdReparacao();
    }

    @Transactional
    public void deleteReparacao(Long id) {
        reparacaoRepository.deleteById(id);
    }

    @Transactional
    public void updateReparacao(Long id, Reparacao r) {
        Reparacao existing = getReparacaoById(id);
        existing.setDataInicio(r.getDataInicio());
        existing.setDataFim(r.getDataFim());
        existing.setEstado(r.getEstado());
        existing.setDescricao(r.getDescricao());
        existing.setValorTotal(r.getValorTotal());
        existing.setnFatura(r.getnFatura());
        existing.setIdVeiculo(r.getIdVeiculo());
        existing.setIdFuncionario(r.getIdFuncionario());
        existing.setIdServico(r.getIdServico());
        reparacaoRepository.save(existing);
    }

    @Transactional
    public Reparacao getReparacaoById(Long id) {
        return reparacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reparação não encontrada: " + id));
    }

    @Transactional
    public List<Reparacao> getAllReparacoes() {
        return reparacaoRepository.findAll();
    }

    // ---------- Estado ----------
    @Transactional
    public void startRepair(Long id) {
        Reparacao r = getReparacaoById(id);
        if (r != null && "Pendente".equals(r.getEstado())) {
            r.setEstado("Em andamento");
            reparacaoRepository.save(r);
        }
    }

    @Transactional
    public void finishRepair(Long id) {
        Reparacao r = getReparacaoById(id);
        if (r != null && "Em andamento".equals(r.getEstado())) {
            r.setEstado("Concluída");
            r.setDataFim(Date.valueOf(LocalDate.now()));
            reparacaoRepository.save(r);
        }
    }

    // ---------- Peças ----------
    @Transactional
    public void addPartsToRepair(Long repairId, List<PartQuantity> parts) {
        if (repairId == null || parts == null || parts.isEmpty()) return;
        Reparacao repair = getReparacaoById(repairId);
        if (repair == null) throw new NoSuchElementException("Reparação não encontrada: " + repairId);

        BigDecimal total = repair.getValorTotal() != null ? repair.getValorTotal() : BigDecimal.ZERO;

        for (PartQuantity pq : parts) {
            Peca part = pq.getPart();
            BigDecimal qty = pq.getQuantity();
            if (part == null || part.getIdPeca() == null || qty == null || qty.compareTo(BigDecimal.ZERO) <= 0) continue;

            LinhaReparacao lr = new LinhaReparacao();
            lr.setId(new LinhaReparacaoId(BigDecimal.valueOf(repairId), part.getIdPeca()));
            lr.setQtd(qty);
            linhaReparacaoRepository.save(lr);

            BigDecimal price = part.getPreco() != null ? part.getPreco() : BigDecimal.ZERO;
            total = total.add(price.multiply(qty));

            part.setQtd(part.getQtd().subtract(qty));
            pecaService.savePeca(part);
        }

        repair.setValorTotal(total);
        reparacaoRepository.save(repair);
    }
}