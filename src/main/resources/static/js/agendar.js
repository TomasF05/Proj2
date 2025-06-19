document.addEventListener('DOMContentLoaded', async () => {
    const form = document.getElementById('formAgendar');
    const mensagem = document.getElementById('mensagem');
    const veiculoSelect = document.getElementById('idVeiculo');
    const dataInicioInput = document.getElementById('dataInicio');

    // Define o mínimo da data para agora (yyyy-MM-ddThh:mm)
    const agora = new Date();
    const ano = agora.getFullYear();
    const mes = String(agora.getMonth() + 1).padStart(2, '0');
    const dia = String(agora.getDate()).padStart(2, '0');
    const hora = String(agora.getHours()).padStart(2, '0');
    const minuto = String(agora.getMinutes()).padStart(2, '0');
    const minDateTime = `${ano}-${mes}-${dia}T${hora}:${minuto}`;
    dataInicioInput.min = minDateTime;

    function mostrarMensagem(texto, tipo) {
        mensagem.textContent = texto;
        mensagem.className = `alert alert-${tipo}`;
        mensagem.classList.remove('d-none');
    }

    // Carrega veículos
    try {
        const resp = await fetch('/api/vehicles');
        const veiculos = await resp.json();
        veiculos.forEach(v => {
            const opt = document.createElement('option');
            opt.value = v.idVeiculo;
            opt.textContent = `${v.marca} ${v.modelo}`;
            veiculoSelect.appendChild(opt);
        });
    } catch {
        mostrarMensagem('Erro ao carregar veículos.', 'danger');
    }

    // Submete o formulário
    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const dados = {
            descricao: document.getElementById('descricao').value.trim(),
            dataInicio: document.getElementById('dataInicio').value,
            idVeiculo: document.getElementById('idVeiculo').value
        };

        // Validação local para não permitir datas anteriores
        const dataInicioValue = dados.dataInicio;
        const dataInicioDate = new Date(dataInicioValue);
        const hoje = new Date();
        hoje.setSeconds(0, 0, 0);
        dataInicioDate.setSeconds(0, 0, 0);

        if (dataInicioDate < hoje) {
            mostrarMensagem('Não pode agendar reparações para datas anteriores a hoje.', 'danger');
            return;
        }

        try {
            const resp = await fetch('/api/repairs/agendar', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dados)
            });

            if (!resp.ok) {
                const erroMsg = await resp.text();
                throw new Error(erroMsg);
            }

            mostrarMensagem('Reparação agendada com sucesso!', 'success');
            form.reset();
        } catch (err) {
            mostrarMensagem(err.message, 'danger');
        }
    });
});
