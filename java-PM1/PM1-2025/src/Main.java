import java.util.Scanner;

public class Main {
    private static GerenciadorAssistidos gerenciador = new GerenciadorAssistidos();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        while (true) {
            Utils.limpaTela();
            System.out.println("\n<<<<< MENU PRINCIPAL >>>>>");
            System.out.println("1. Cadastrar Assistido");
            System.out.println("2. Registrar Comparecimento");
            System.out.println("3. Exibir Assistidos");
            System.out.println("4. Editar informacoes");
            System.out.println("5. Remover Assistido");
            System.out.println("6. Gerar Relatorio");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opcao: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            Utils.limpaTela();
            switch (opcao) {
                case 1 -> gerenciador.cadastrarAssistido();
                case 2 -> gerenciador.registrarComparecimento();
                case 3 -> gerenciador.exibirAssistidos();
                case 4 -> gerenciador.editarInformacoes();
                case 5 -> gerenciador.removerAssistido();
                case 6 -> gerenciador.gerarRelatorio();
                case 7 -> {
                    System.out.println("\nSaindo...");
                    Utils.delay(1);
                    Utils.limpaTela();
                    return;
                }
                default -> {
                    System.out.println("\nOpcao invalida!");
                    Utils.delay(1);
                }
            }
        }
    }
}
