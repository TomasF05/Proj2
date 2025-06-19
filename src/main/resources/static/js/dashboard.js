
document.addEventListener('DOMContentLoaded', async () => {
    try {
        const resp = await fetch('/api/dashboard', {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Accept': 'application/json'
            }
        });

        if (!resp.ok) throw new Error('Sessão expirada ou acesso negado.');

        const data = await resp.json();
        console.log("DEBUG: Reparações recebidas na dashboard:");
        console.log(data.reparacoes);

        console.log("DEBUG: Veículos disponíveis:");
        console.log(data.veiculos);
        console.log('Dados recebidos:', data);

        // === Nome do cliente ===
        const nomeEl = document.getElementById('cliente-nome');
        nomeEl.textContent = data.cliente?.nome || 'Cliente';

        // === Atualizar contadores ===
        const hoje = new Date();
        const reparacoesAgendadas = (data.reparacoes || []).filter(r => new Date(r.dataInicio) > hoje);
        document.getElementById('veiculos-count').textContent = data.veiculos?.length || 0;
        document.getElementById('reparacoes-count').textContent = data.reparacoes?.length || 0;
        document.getElementById('agendamentos-count').textContent = reparacoesAgendadas.length || 0;

        // === Em andamento: entre dataInicio e dataFim ===
        const emAndamento = (data.reparacoes || []).filter(r => {
            const inicio = new Date(r.dataInicio);
            const fim = r.dataFim ? new Date(r.dataFim) : inicio;
            return inicio <= hoje && fim >= hoje;
        }).length || 0;
        document.getElementById('em-andamento-count').textContent = emAndamento;

        // === Mapa de veículos ===
        const veiculoMap = {};
        (data.veiculos || []).forEach(v => {
            veiculoMap[v.idVeiculo] = `${v.marca} ${v.modelo} (${v.matricula})`;
        });

        // === Últimas Reparações (dataInicio <= hoje) ===
        const reparacoesList = document.getElementById('reparacoes-list');
        reparacoesList.innerHTML = '';

        const reparacoesRecentes = (data.reparacoes || [])
            .filter(r => new Date(r.dataInicio) <= hoje)
            .sort((a, b) => new Date(b.dataInicio) - new Date(a.dataInicio))
            .slice(0, 5);

        if (reparacoesRecentes.length > 0) {
            reparacoesRecentes.forEach(rep => {
                const veiculo = veiculoMap[rep.idVeiculo] || 'Veículo desconhecido';
                const li = document.createElement('li');
                li.className = 'list-group-item bg-dark text-white border-secondary';
                li.innerHTML = `
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <strong>${rep.descricao || 'Sem descrição'}</strong><br>
                            <small class="text-muted">${veiculo}</small>
                        </div>
                        <small>${formatarData(rep.dataInicio)}</small>
                    </div>
                `;
                reparacoesList.appendChild(li);
            });
        } else {
            reparacoesList.innerHTML = '<li class="list-group-item bg-dark text-white border-secondary">Nenhuma reparação encontrada</li>';
        }

        // === Próximas Reparações como Agendamentos (dataInicio > hoje) ===
        const agendamentosList = document.getElementById('agendamentos-list');
        agendamentosList.innerHTML = '';

        const agendamentosFuturos = reparacoesAgendadas
            .sort((a, b) => new Date(a.dataInicio) - new Date(b.dataInicio))
            .slice(0, 5);

        if (agendamentosFuturos.length > 0) {
            agendamentosFuturos.forEach(ag => {
                const veiculo = veiculoMap[ag.idVeiculo] || 'Veículo desconhecido';
                const li = document.createElement('li');
                li.className = 'list-group-item bg-dark text-white border-secondary';
                li.innerHTML = `
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <strong>${ag.descricao || 'Agendamento'}</strong><br>
                            <small class="text-muted">${veiculo}</small>
                        </div>
                        <small>${formatarData(ag.dataInicio)}</small>
                    </div>
                `;
                agendamentosList.appendChild(li);
            });
        } else {
            agendamentosList.innerHTML = '<li class="list-group-item bg-dark text-white border-secondary">Nenhum agendamento encontrado</li>';
        }

    } catch (err) {
        console.error('Erro:', err);
        alert(err.message);
        sessionStorage.removeItem('cliente');
        window.location.href = '/login.html';
    }
});

// === Função auxiliar ===

function formatarData(dataIso) {
    if (!dataIso) return '-';
    return new Date(dataIso).toLocaleDateString('pt-PT');
}
