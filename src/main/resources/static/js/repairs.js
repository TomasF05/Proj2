document.addEventListener('DOMContentLoaded', async () => {
    const tabela = document.getElementById('reparacoes-table');
    const emptyMsg = document.getElementById('empty-message');
    const wrapper = document.getElementById('table-wrapper');

    //Lê o parâmetro veiculo da query string (URL).
    //Se existir, adiciona-o à URL da API.
    const params = new URLSearchParams(window.location.search);
    const veiculoId = params.get('veiculo');
    const url = veiculoId ? `/api/repairs?veiculo=${veiculoId}` : '/api/repairs';

    try {
        const response = await fetch(url);
        if (!response.ok) throw new Error('Não autenticado');

        const data = await response.json();
        const veiculos = data.veiculos;
        const reparacoes = data.reparacoes;

        // Filtrar apenas reparações cujo estado é "Concluída" (ignorar maiúsculas/minúsculas e espaços)
        const reparacoesConcluidas = reparacoes.filter(r =>
            r.estado?.trim().toLowerCase() === 'concluída'
        );

        if (reparacoesConcluidas.length === 0) {
            emptyMsg.classList.remove('d-none');
            return;
        }

        wrapper.classList.remove('d-none');
        reparacoesConcluidas.forEach(r => {
            const veiculo = veiculos.find(v => v.idVeiculo === r.idVeiculo);
            const linha = document.createElement('tr');

            linha.innerHTML = `
                <td>${veiculo ? `${veiculo.marca} ${veiculo.modelo} (${veiculo.matricula})` : '-'}</td>
                <td>${r.descricao || 'Sem descrição'}</td>
                <td>${formatarData(r.dataInicio)}</td>
                <td>${r.dataFim ? formatarData(r.dataFim) : '-'}</td>
                <td><span class="badge ${estadoClasse(r.estado)}">${r.estado}</span></td>
                <td>${r.valorTotal != null ? r.valorTotal.toFixed(2) + '€' : '-'}</td>
            `;

            tabela.appendChild(linha);
        });
    } catch (err) {
        alert(err.message);
        window.location.href = '/login.html';
    }
});

function formatarData(dataIso) {
    const d = new Date(dataIso);
    return d.toLocaleDateString('pt-PT');
}

function estadoClasse(estado) {
    const normalizado = estado?.trim().toLowerCase();
    switch (normalizado) {
        case 'concluída': return 'bg-success';
        case 'em andamento': return 'bg-warning text-dark';
        default: return 'bg-secondary';
    }
}
