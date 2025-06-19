document.addEventListener('DOMContentLoaded', async () => {
    const tabela = document.getElementById('agendamentos-table');
    const emptyMsg = document.getElementById('empty-message');
    const wrapper = document.getElementById('table-wrapper');

    try {
        const response = await fetch('/api/appointments');
        if (!response.ok) throw new Error('Não autenticado');

        const agendamentos = await response.json();

        if (agendamentos.length === 0) {
            emptyMsg.classList.remove('d-none');
            return;
        }

        wrapper.classList.remove('d-none');

        agendamentos.forEach(a => {
            const linha = document.createElement('tr');
            linha.innerHTML = `
                <td>${formatarDataHora(a.dataHora)}</td>
                <td>${a.observacoes || 'Sem observações'}</td>
                <td><span class="badge bg-info">${a.estadoPagamento || 'Pendente'}</span></td>
                <td>Veículo #${a.idVeiculo}</td>
            `;
            tabela.appendChild(linha);
        });

    } catch (err) {
        alert(err.message);
        window.location.href = '/login.html';
    }
});

function formatarDataHora(dataIso) {
    const d = new Date(dataIso);
    return d.toLocaleDateString('pt-PT') + ' ' + d.toLocaleTimeString('pt-PT', { hour: '2-digit', minute: '2-digit' });
}
