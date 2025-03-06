
import java.time.LocalDate;

class RelatorioAssistido implements RelatorioGeral {
    private LocalDate data;
    private String conteudo;
    private Assistido assistido;

    public RelatorioAssistido(LocalDate data, String conteudo, Assistido assistido) {
        this.data = data;
        this.conteudo = conteudo;
        this.assistido = assistido;
    }

    @Override
    public void gerarRelatorio() {
        System.out.println("Relatório do Assistido: " + assistido.nome);
        System.out.println("Conteúdo: " + conteudo);
        System.out.println("Data: " + data);
    }
}