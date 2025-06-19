document.addEventListener('DOMContentLoaded', async () => {
    const container = document.getElementById('veiculos-container');
    const emptyMsg = document.getElementById('empty-message');

    try {
        const response = await fetch('/api/vehicles');
        if (!response.ok) throw new Error('Não autenticado');

        const veiculos = await response.json();

        if (veiculos.length === 0) {
            emptyMsg.classList.remove('d-none');
            return;
        } else {
            emptyMsg.classList.add('d-none');
        }

        container.innerHTML = ''; // limpa container antes de adicionar

        veiculos.forEach(v => {
            const ano = v.ano != null ? v.ano : 'N/A';

            const col = document.createElement('div');
            col.className = 'col-md-6 col-lg-4 mb-4';
            col.innerHTML = `
                <div class="card bg-dark text-white rounded-3">
                    <div class="card-header">
                        <h5 class="mb-0">${v.marca} ${v.modelo}</h5>
                    </div>
                    <div class="card-body">
                        <div class="row mb-2"><div class="col-6"><strong>Matrícula:</strong></div><div class="col-6">${v.matricula || '-'}</div></div>
                        <div class="row mb-2"><div class="col-6"><strong>Ano:</strong></div><div class="col-6">${ano}</div></div>
                        <div class="row mb-2"><div class="col-6"><strong>Marca:</strong></div><div class="col-6">${v.marca || '-'}</div></div>
                        <div class="row mb-2"><div class="col-6"><strong>Modelo:</strong></div><div class="col-6">${v.modelo || '-'}</div></div>
                    </div>
                </div>`;

            container.appendChild(col);
        });
    } catch (err) {
        alert('Erro: ' + err.message);
        window.location.href = '/login.html';
    }
});
