function openNav() {
    document.getElementById("sideBar").style.width = "200px";
  }
  
  function closeNav() {
    document.getElementById("sideBar").style.width   
   = "0";
  }


/* Tela Principal ----------------------- */

function goToPage(nome, idade, sexo, nascimento) {
    localStorage.setItem('assistido', JSON.stringify({nome, idade, sexo, nascimento}));
    window.location.href = '/Telas/Tela_Assistido.html';
}