package com.example.projeto2.Services;

import com.example.projeto2.Repo.VeiculoRepository;
import com.example.projeto2.Tables.Veiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    // Salva ou atualiza um veículo
    @Transactional
    public BigDecimal saveVeiculo(Veiculo veiculo) {
        Veiculo savedVeiculo = veiculoRepository.save(veiculo);
        return savedVeiculo.getIdVeiculo();  // Retorna o ID do veículo salvo
    }

    // Deleta um veículo pelo ID
    @Transactional
    public void deleteVeiculo(BigDecimal id) {
        veiculoRepository.deleteById(id);
    }

    // Atualiza os dados de um veículo existente
    @Transactional
    public void updateVeiculo(BigDecimal id, Veiculo veiculo) {
        Veiculo existingVeiculo = getVeiculoById(id);
        existingVeiculo.setMatricula(veiculo.getMatricula());
        existingVeiculo.setMarca(veiculo.getMarca());
        existingVeiculo.setModelo(veiculo.getModelo());
        existingVeiculo.setAno(veiculo.getAno());
        existingVeiculo.setIdCliente(veiculo.getIdCliente());
        veiculoRepository.save(existingVeiculo);
    }

    // Retorna um veículo pelo ID
    @Transactional
    public Veiculo getVeiculoById(BigDecimal id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Veículo não encontrado para o id: " + id));
    }

    // Retorna todos os veículos
    @Transactional
    public List<Veiculo> getAllVeiculos() {
        return veiculoRepository.findAll();
    }
}
