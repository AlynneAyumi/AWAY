// Função para carregar o conteudo do HTML 
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

// Carregar o header e o menu lateral nas paginas
window.addEventListener("DOMContentLoaded", () => {
    loadHTML("header", "/Telas/header.html");
    loadHTML("menu", "/Telas/menu.html");
});
