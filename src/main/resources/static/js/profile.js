document.addEventListener('DOMContentLoaded',async () => {
    console.log('JS carregado');
    const form = document.getElementById('formPerfil');
    const mensagemDiv = document.getElementById('mensagem');
    const contactoInput = document.getElementById('contacto');
    const passwordInput = document.getElementById('password');
    const confirmarPasswordInput = document.getElementById('confirmarPassword');

    IMask(contactoInput, {
        mask: '900 000 000',
        lazy: false, // Força a máscara a ser aplicada imediatamente
        overwrite: true // Sobrescreve o input para evitar entradas inválidas
    });

    function mostrarMensagem(texto, tipo) {
        mensagemDiv.textContent = texto;
        mensagemDiv.className = `alert alert-${tipo}`;
        mensagemDiv.classList.remove('d-none');
    }

    // Carrega os dados do perfil
    try {
        const resp = await fetch('/api/profile');
        if (!resp.ok) throw new Error('Sessão expirada ou acesso negado.');
        const cliente = await resp.json();
        form.nome.value = cliente.nome || '';
        form.contacto.value = cliente.contacto || '';
    } catch (err) {
        mostrarMensagem(err.message, 'danger');
    }

    // Validação em tempo real para o contacto
    contactoInput.addEventListener('input', () => {
        const contacto = contactoInput.value.replace(/\D/g, '');
        if (contacto.length === 9 && !/^9\d{8}$/.test(contacto)) {
            mostrarMensagem('O contacto deve começar por 9.', 'danger');
        } else {
            mensagemDiv.classList.add('d-none');
        }
    });

    // Validação em tempo real para as palavras-passe
    confirmarPasswordInput.addEventListener('input', () => {
        const password = passwordInput.value.trim();
        const confirmarPassword = confirmarPasswordInput.value.trim();
        if (password && confirmarPassword && password !== confirmarPassword) {
            mostrarMensagem('As palavras-passe não coincidem.', 'danger');
        } else {
            mensagemDiv.classList.add('d-none');
        }
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault(); // ← já é suficiente, mas vamos reforçar
        mensagemDiv.classList.add('d-none');

        const nome = form.nome.value.trim();
        const contacto = contactoInput.value.replace(/\D/g, '');
        const password = passwordInput.value.trim();
        const confirmarPassword = confirmarPasswordInput.value.trim();

        // Validação do contacto
        if (!contacto) {
            mostrarMensagem('O campo contacto é obrigatório.', 'danger');
            return false;
        }

        if (contacto.length !== 9) {
            mostrarMensagem('O contacto deve ter exatamente 9 dígitos.', 'danger');
            return false;
        }

        if (!/^9\d{8}$/.test(contacto)) {
            mostrarMensagem('O contacto deve começar por 9.', 'danger');
            return false;
        }

        // Validação da password
        if (password || confirmarPassword) {
            if (!password || !confirmarPassword) {
                mostrarMensagem('Preencha ambos os campos de palavra-passe.', 'danger');
                return false;
            }

            if (password !== confirmarPassword) {
                mostrarMensagem('As palavras-passe não coincidem.', 'danger');
                return false;
            }
        }

        // Dados válidos
        const dados = { nome, contacto };
        if (password) dados.password = password;

        try {
            const resp = await fetch('/api/profile/update', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dados)
            });

            if (!resp.ok) {
                const erroData = await resp.json().catch(() => null);
                throw new Error(erroData?.message || 'Erro ao atualizar perfil.');
            }

            mostrarMensagem('Perfil atualizado com sucesso!', 'success');
            passwordInput.value = '';
            confirmarPasswordInput.value = '';
        } catch (err) {
            mostrarMensagem(err.message, 'danger');
        }

        return false; // impede submissão por segurança
    });
    });

    function togglePassword(id) {
    const input = document.getElementById(id);
    input.type = input.type === 'password' ? 'text' : 'password';
    }