function openNav() {
    document.getElementById("sideBar").style.width = "200px";
  }
  
  function closeNav() {
    document.getElementById("sideBar").style.width   
   = "0";
  }


/* Tela Principal ----------------------- */

function goToPage(nome, auto, genero, vara, obs) {
    window.location.href = `Tela_Assistido.html?nome=${nome}&auto=${auto}&genero=${genero}&vara=${vara}&obs=${obs}`;
}