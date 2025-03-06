import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    private static List<Assistido> assistidos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            Utils.limpaTela();
            System.out.println("\n<<<<< MENU PRINCIPAL >>>>>");
            System.out.println("1. Cadastrar Assistido");
            System.out.println("2. Registrar Comparecimento");
            System.out.println("3. Exibir Assistidos");
            System.out.println("4. Remover Assistido");
            System.out.println("5. Gerar Relatório");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarAssistido();
                case 2 -> registrarComparecimento();
                case 3 -> exibirAssistidos();
                case 4 -> removerAssistido();
                case 5 -> gerarRelatorio();
                case 6 -> {
                    System.out.println("\nSaindo...");
                    Utils.delay(1);
                    Utils.limpaTela();
                    return;
                }
                default -> {
                    System.out.println("\nOpção inválida!");
                    Utils.delay(1);
                }
            }
        }
    }

    private static void cadastrarAssistido() {
        Utils.limpaTela();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("RG: ");
        String rg = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Histórico Criminal: ");
        String historico = scanner.nextLine();

        Assistido assistido = new Assistido(nome, rg, cpf, historico, LocalDate.now());
        assistidos.add(assistido);
        System.out.println("\nAssistido cadastrado com sucesso!");
        Utils.delay(1);
    }

    private static void registrarComparecimento() {
        Utils.limpaTela();
        if (assistidos.isEmpty()) {
            System.out.println("Nenhum assistido cadastrado.");
            Utils.delay(1);
            return;
        }

        System.out.println("Selecione um assistido:");
        for (int i = 0; i < assistidos.size(); i++) {
            System.out.println((i + 1) + ". " + assistidos.get(i).getNome());
        }

        int escolha = scanner.nextInt() - 1;
        scanner.nextLine();

        if (escolha < 0 || escolha >= assistidos.size()) {
            System.out.println("\nEscolha inválida.");
            Utils.delay(1);
            return;
        }
        Utils.limpaTela();

        System.out.print("Descrição da atividade: ");
        String descricao = scanner.nextLine();

        System.out.print("O assistido compareceu? (true/false): ");
        boolean compareceu = scanner.nextBoolean();
        scanner.nextLine();

        Comparecimento atividade = new Comparecimento(descricao, LocalDate.now(), compareceu);
        assistidos.get(escolha).adicionarAtividade(atividade);
        System.out.println("\nAtividade registrada com sucesso!");
        Utils.delay(1);
    }

    private static void exibirAssistidos() {
        Utils.limpaTela();
        if (assistidos.isEmpty()) {
            System.out.println("Nenhum assistido cadastrado.");
            Utils.delay(1);
            return;
        }

        for (int i = 0; i < assistidos.size(); i++) {
            System.out.println((i + 1) + ". " + assistidos.get(i).getNome());
        }
        Utils.pause();
    }

    private static void removerAssistido() {
        Utils.limpaTela();
        if (assistidos.isEmpty()) {
            System.out.println("Nenhum assistido cadastrado.");
            Utils.delay(1);
            return;
        }

        System.out.println("Selecione um assistido para remover:");
        for (int i = 0; i < assistidos.size(); i++) {
            System.out.println((i + 1) + ". " + assistidos.get(i).getNome());
        }

        int escolha = scanner.nextInt() - 1;
        scanner.nextLine();

        if (escolha >= 0 && escolha < assistidos.size()) {
            System.out.println("Assistido " + assistidos.get(escolha).getNome() + " removido com sucesso!");
            assistidos.remove(escolha);
            Utils.delay(1);
        } else {
            System.out.println("Escolha inválida.");
            Utils.delay(1);
        }
    }

    private static void gerarRelatorio() {
        Utils.limpaTela();
        System.out.println("Relatório ainda em desenvolvimento...");
        Utils.delay(1);
    }
}
