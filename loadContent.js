// Função para carregar o conteúdo de um arquivo HTML em um elemento específico
function loadHTML(elementId, file) {
    fetch(file)
        .then(response => {
            if (!response.ok) throw new Error("Erro ao carregar o arquivo.");
            return response.text();
        })
        .then(data => {
            document.getElementById(elementId).innerHTML = data;
        })
        .catch(error => console.error("Erro:", error));
}

// Carregar o header e o menu lateral nas páginas
window.addEventListener("DOMContentLoaded", () => {
    loadHTML("header", "header.html");
    loadHTML("menu", "menu.html");
});
