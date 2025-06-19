document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('login-form');
    const erroDiv = document.getElementById('erro');
    const erroMsg = document.getElementById('erro-msg');

    form.addEventListener('submit', async function (e) {
        e.preventDefault();

        const formData = new FormData(form);
        const dados = {
            username: formData.get('username'),
            password: formData.get('password')
        };

        try {
            const resp = await fetch('/api/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dados)
            });

            if (!resp.ok) throw new Error('Credenciais inválidas');

            const cliente = await resp.json();
            sessionStorage.setItem('cliente', JSON.stringify(cliente));  // Guarda cliente localmente

            window.location.href = '/dashboard.html';
        } catch (err) {
            if (erroDiv && erroMsg) {
                erroDiv.classList.remove('d-none');
                erroMsg.textContent = err.message;
            } else {
                alert(err.message);
            }
        }
    });
});
