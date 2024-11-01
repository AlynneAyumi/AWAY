function openNav() {
    document.getElementById("sideBar").style.width = "200px";
  }
  
  function closeNav() {
    document.getElementById("sideBar").style.width   
   = "0";
  }


/* Tela Principal ----------------------- */

function search() {
  const table = document.getElementById("resultTable");

  for (let i = 0; i < 10; i++) {
      const row = table.insertRow();

      const nome = row.insertCell(0);
      const autos = row.insertCell(1);
      const genero = row.insertCell(2);
      const vara = row.insertCell(3);
      const observacao = row.insertCell(4);

      nome.textContent = "------------";
      autos.textContent = "------------";
      genero.textContent = "------------ ";
      vara.textContent = "------------ ";
      observacao.textContent = "------------ ";
  }
}