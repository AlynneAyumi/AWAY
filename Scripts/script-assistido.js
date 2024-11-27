const botao = document.getElementById('btnTeste');
const conteudo = document.getElementById('conteudo');

/*
botao.addEventListener('click', () => {
    conteudo.style.display = 'none';
});
*/

/* Função para trazer as informações do assistido */
(function carregarAssistido() {
    let assistido = JSON.parse(localStorage.getItem('assistido')) || {};

    console.log(assistido);

    document.getElementById('nome').value = assistido.nome;
    document.getElementById('idade').value = assistido.idade;
    document.getElementById('sexo').value = assistido.sexo;
    document.getElementById('dataNascimento').value = assistido.nascimento;
    document.getElementById('nome-presenca').value = assistido.nome;
})();

/* Função para adicionar item no histórico */
function adicionarHistorico(nome, data, status, dataHora, atrasado) {
    let historicoItem = document.createElement('li');
    if (atrasado) {
        historicoItem.style.color = 'red';
        historicoItem.textContent = `Atrasado: ${nome} - ${data} - ${status} - ${dataHora}`;
    } else {
        historicoItem.textContent = `${nome} - ${data} - ${status} - ${dataHora}`;
    }
    document.getElementById('historico').appendChild(historicoItem);
}

/* Aparecer Histórico toda vez que marcar CheckBox e Notificar quando for atrasado*/
document.querySelectorAll('.presenca-checkbox').forEach(checkbox => {
    checkbox.addEventListener('change', function() {
        let nome = this.getAttribute('data-nome');
        let data = this.getAttribute('data-data');
        let dataAtual = new Date();
        let dataEvento = new Date(data.split('/').reverse().join('-'));
        let status = this.checked ? 'Presença confirmada' : 'Presença ausente';
        let dataHora = `${dataAtual.toLocaleDateString()} ${dataAtual.toLocaleTimeString()}`;
        
        let atrasado = dataEvento < dataAtual;
        adicionarHistorico(nome, data, status, dataHora, atrasado);
    });
});

// Função para adicionar nova linha na tabela
document.getElementById("add-row-btn").addEventListener("click", function() {
    let tbody = document.getElementById("presenca-tbody");

    let newRow = document.createElement("tr");
    newRow.innerHTML = `
        <td><input type="text" class="form-control" value="Nome do Participante"></td>
        <td><input type="date" class="form-control" value="2024-11-01"></td>
        <td><input type="checkbox" class="presenca-checkbox"></td>
        <td><button type="button" class="btn btn-danger btn-sm delete-btn">Excluir</button></td>
    `;
    tbody.appendChild(newRow);

    // Adiciona o evento de excluir para a nova linha
    newRow.querySelector(".delete-btn").addEventListener("click", function() {
        tbody.removeChild(newRow);
    });

    // Adiciona o evento de presença para a nova checkbox
    newRow.querySelector(".presenca-checkbox").addEventListener("change", function() {
        let nome = newRow.querySelector("input[type='text']").value;
        let data = newRow.querySelector("input[type='date']").value;
        let dataAtual = new Date();
        let dataEvento = new Date(data);
        let status = this.checked ? 'Presença confirmada' : 'Presença ausente';
        let dataHora = `${dataAtual.toLocaleDateString()} ${dataAtual.toLocaleTimeString()}`;
        
        let atrasado = dataEvento < dataAtual;
        adicionarHistorico(nome, data, status, dataHora, atrasado);
    });
});

// Função para salvar presença
document.getElementById("save-presence-btn").addEventListener("click", function() {
    let rows = document.querySelectorAll("#presenca-tbody tr");
    let notificacoes = document.getElementById("historico");

    rows.forEach(function(row) {
        let nome = row.querySelector("input[type='text']").value;
        let data = row.querySelector("input[type='date']").value;
        let presente = row.querySelector("input[type='checkbox']").checked ? "Presente" : "Ausente";

        let li = document.createElement("li");
        li.textContent = `Nome: ${nome}, Data: ${data}, Status: ${presente}`;
        notificacoes.appendChild(li);
    });
});
