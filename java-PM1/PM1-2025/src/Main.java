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
            System.out.println("4. Editar informações");
            System.out.println("5. Remover Assistido");
            System.out.println("6. Gerar Relatório");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            Utils.limpaTela();            
            switch (opcao) {
                case 1 -> cadastrarAssistido();
                case 2 -> registrarComparecimento();
                case 3 -> exibirAssistidos();
                case 4 -> editarInformacoes();
                case 5 -> removerAssistido();
                case 6 -> gerarRelatorio();
                case 7 -> {
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
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Data Nascimento: ");
        String dataNasc = scanner.nextLine();
        System.out.print("CPF: ");
        String CPF = scanner.nextLine();
        System.out.print("CEP: ");
        String CEP = scanner.nextLine();
        System.out.print("Histórico Criminal: ");
        String historico = scanner.nextLine();

        Assistido assistido = new Assistido(nome, dataNasc, CPF, CEP, historico, LocalDate.now());
        assistidos.add(assistido);
        System.out.println("\nAssistido cadastrado com sucesso!");
        Utils.delay(1);
    }


    private static void registrarComparecimento() {
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


    private static void editarInformacoes() {
        if (assistidos.isEmpty()) {
            System.out.println("Nenhum assistido cadastrado.");
            Utils.delay(1);
            return;
        }

        System.out.println("Selecione o assistido para editar:");
        for (int i = 0; i < assistidos.size(); i++) {
            System.out.println((i + 1) + ". " + assistidos.get(i).getNome());
        }
        
        int escolha = scanner.nextInt() - 1;
        scanner.nextLine();

        if (escolha < 0 || escolha > assistidos.size()) {
            System.out.println("Escolha inválida.");
            Utils.delay(1);
            return;
        }           
        
        Assistido assistidoEscolhido = assistidos.get(escolha);

        Utils.limpaTela();
        System.out.println("Selecione qual informação deseja editar:");
        System.out.println("1. Nome - " + assistidoEscolhido.getNome());
        System.out.println("2. Data Nascimento - " + assistidoEscolhido.getDataNasc());
        System.out.println("3. CPF  - " + assistidoEscolhido.getCPF());

        escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha < 1 || escolha > 3) {
            System.out.println("Escolha inválida.");
            Utils.delay(1);
            return;
        }

        Utils.limpaTela();
        switch (escolha) {
            case 1:
                System.out.println("Insira o novo nome");
                assistidoEscolhido.setNome(scanner.nextLine());
                break;
            case 2:
                System.out.println("Insira o novo RG");
                assistidoEscolhido.setDataNasc(scanner.nextLine());
                break;
            case 3:
                System.out.println("Insira o novo CPF");
                assistidoEscolhido.setCPF(scanner.nextLine());
                break;
        }
    }


    private static void removerAssistido() {
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

        for(Assistido assistidoAtual : assistidos) {
            System.out.println("----+----+----+----+----");
            System.out.println("INFORMAÇÔES\n");
            System.out.println("Nome: " + assistidoAtual.getNome());
            System.out.println("Data de nascimento: " + assistidoAtual.getDataNasc());
            System.out.println("CPF: " + assistidoAtual.getCPF());
            System.out.println("CEP: " + assistidoAtual.getCEP());

            System.out.println("\n\nHISTÓRICO\n");
            if (assistidoAtual.getAtividades().isEmpty()) {
                System.out.println("O assistido não possui histórico de comparecimento\n");
                continue;
            }
            
            for(Comparecimento atividade : assistidoAtual.getAtividades()) {
                System.out.println("-");
                System.out.println("Descrição: " + atividade.getDescricao());
                System.out.println("Data: " + atividade.getData());
                System.out.println(atividade.isCompareceu() ? "Compareceu" : "Não compareceu");
                System.out.println("-\n");
            }

            System.out.println("\n");
        }
        Utils.pause();
    }
}
