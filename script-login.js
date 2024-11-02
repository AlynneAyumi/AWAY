localStorage.setItem('usuario', 'admin@away.com');
localStorage.setItem('senha', 'senha123');

document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const usuarioLogin = document.getElementById('usuario').value;
    const senhaLogin = document.getElementById('senha').value;

    const usuarioSalvo = localStorage.getItem('usuario');
    const senhaSalva = localStorage.getItem('senha');


    if (usuarioLogin === usuarioSalvo && senhaLogin === senhaSalva) {
        window.location.href = 'index.html';

    } else {
        alert('Email ou senha incorretos.');
    }
});