package com.example.projeto2.Services;

import com.example.projeto2.Repo.LinhaReparacaoRepository;
import com.example.projeto2.Tables.LinhaReparacao;
import com.example.projeto2.Tables.LinhaReparacaoId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LinhaReparacaoService {

    @Autowired
    private LinhaReparacaoRepository linhaReparacaoRepository;

    @Transactional
    public LinhaReparacao saveLinhaReparacao(LinhaReparacao linhaReparacao) {
        return linhaReparacaoRepository.save(linhaReparacao);
    }

    @Transactional
    public List<LinhaReparacao> getAllLinhaReparacoes() {
        return linhaReparacaoRepository.findAll();
    }

    @Transactional
    public LinhaReparacao getLinhaReparacaoById(LinhaReparacaoId id) {
        return linhaReparacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Linha de reparação não encontrada para o id: " + id));
    }

    @Transactional
    public void deleteLinhaReparacao(LinhaReparacaoId id) {
        linhaReparacaoRepository.deleteById(id);
    }

    @Transactional
    public void updateLinhaReparacao(LinhaReparacaoId id, LinhaReparacao linhaReparacao) {
        LinhaReparacao existingLinhaReparacao = getLinhaReparacaoById(id);
        existingLinhaReparacao.setQtd(linhaReparacao.getQtd());  // Atualizando a quantidade
        linhaReparacaoRepository.save(existingLinhaReparacao);
    }
}
