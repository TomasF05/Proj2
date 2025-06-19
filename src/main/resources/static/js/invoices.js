document.addEventListener('DOMContentLoaded', async () => {
    const tabela = document.getElementById('faturas-table');
    const emptyMsg = document.getElementById('empty-message');
    const wrapper = document.getElementById('table-wrapper');

    try {
        const response = await fetch('/api/invoices');
        if (!response.ok) throw new Error("Não autenticado");

        const faturas = await response.json();

        if (faturas.length === 0) {
            emptyMsg.classList.remove('d-none');
            return;
        }

        wrapper.classList.remove('d-none');

        faturas.forEach(f => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${f.nFatura}</td>
                <td>${formatarData(f.data)}</td>
                <td>${f.valorTotal?.toFixed(2) || '-'}€</td>
                <td>${f.metodoPagamento || '-'}</td>
            `;
            tabela.appendChild(row);
        });
    } catch (err) {
        alert(err.message);
        window.location.href = '/login.html';
    }
});

function formatarData(dataIso) {
    return new Date(dataIso).toLocaleDateString('pt-PT');
}
