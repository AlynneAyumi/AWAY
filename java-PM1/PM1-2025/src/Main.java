import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    private static List<Assistido> assistidos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
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
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarAssistido() {
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
        System.out.println("Assistido cadastrado com sucesso!");
    }

    private static void registrarComparecimento() {
        if (assistidos.isEmpty()) {
            System.out.println("Nenhum assistido cadastrado.");
            return;
        }

        System.out.println("Selecione um assistido:");
        for (int i = 0; i < assistidos.size(); i++) {
            System.out.println((i + 1) + ". " + assistidos.get(i).getNome());
        }

        int escolha = scanner.nextInt() - 1;
        scanner.nextLine();

        if (escolha < 0 || escolha >= assistidos.size()) {
            System.out.println("Escolha inválida.");
            return;
        }

        System.out.print("Descrição da atividade: ");
        String descricao = scanner.nextLine();

        System.out.print("O assistido compareceu? (true/false): ");
        boolean compareceu = scanner.nextBoolean();
        scanner.nextLine();

        Comparecimento atividade = new Comparecimento(descricao, LocalDate.now(), compareceu);
        assistidos.get(escolha).adicionarAtividade(atividade);
        System.out.println("Atividade registrada com sucesso!");
    }

    private static void exibirAssistidos() {
        if (assistidos.isEmpty()) {
            System.out.println("Nenhum assistido cadastrado.");
            return;
        }

        assistidos.forEach(System.out::println);
    }

    private static void removerAssistido() {
        if (assistidos.isEmpty()) {
            System.out.println("Nenhum assistido cadastrado.");
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
        } else {
            System.out.println("Escolha inválida.");
        }
    }

    private static void gerarRelatorio() {
        System.out.println("Relatório ainda em desenvolvimento...");
    }
}
