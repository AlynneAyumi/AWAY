import java.util.List;

class RelatorioAssistido implements RelatorioGeral {

    @Override
    public void gerarRelatorio(List<Assistido> assistidos) {
        Utils.limpaTela();

        for(Assistido assistidoAtual : assistidos) {
            System.out.println("----+----+----+----+----");
            System.out.println("INFORMACOES\n");
            System.out.println("Nome: " + assistidoAtual.getNome());
            System.out.println("Data de nascimento: " + assistidoAtual.getDataNasc());
            System.out.println("CPF: " + assistidoAtual.getCPF());
            System.out.println("CEP: " + assistidoAtual.getCEP());

            System.out.println("\n\nHISTORICO\n");
            if (assistidoAtual.getAtividades().isEmpty()) {
                System.out.println("O assistido nao possui historico de comparecimento\n");
                continue;
            }
            
            for(Comparecimento atividade : assistidoAtual.getAtividades()) {
                System.out.println("-");
                System.out.println("Descricao: " + atividade.getDescricao());
                System.out.println("Data: " + atividade.getData());
                System.out.println(atividade.isCompareceu() ? "Compareceu" : "Nao compareceu");
                System.out.println("-\n");
            }

            System.out.println("\n");
        }
        Utils.pause();
    }
}