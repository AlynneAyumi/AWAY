import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.Date;

public final class GerenciadorAssistidos {

    private List<Assistido> assistidos = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private RelatorioAssistido relatorio = new RelatorioAssistido();

    public void cadastrarAssistido() {

        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("<<Data Nascimento>>\n");
        System.out.print("Digite o dia: ");
        int dia = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Digite o mês: ");
        int mes = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Digite o ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        System.out.print("CPF: ");
        String CPF = scanner.nextLine();
        System.out.print("CEP: ");
        String CEP = scanner.nextLine();
        System.out.print("Historico Criminal: ");
        String historico = scanner.nextLine();

        Date dataNasc = Utils.formatarData(dia,mes,ano);

        assistidos.add(new Assistido(nome, dataNasc, CPF, CEP, historico, LocalDate.now()));
        System.out.println("\nAssistido cadastrado com sucesso!");
        Utils.delay(1);
    }


    public void registrarComparecimento() {
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
            System.out.println("\nEscolha invalida.");
            Utils.delay(1);
            return;
        }
        Utils.limpaTela();

        System.out.print("Descricao da atividade: ");
        String descricao = scanner.nextLine();

        System.out.print("O assistido compareceu? (true/false): ");
        boolean compareceu = scanner.nextBoolean();
        scanner.nextLine();

        Comparecimento atividade = new Comparecimento(descricao, LocalDate.now(), compareceu);
        assistidos.get(escolha).adicionarAtividade(atividade);
        System.out.println("\nAtividade registrada com sucesso!");
        Utils.delay(1);
    }


    public void exibirAssistidos() {
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
    
    
    public void editarInformacoes() {
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
            System.out.println("Escolha invalida.");
            Utils.delay(1);
            return;
        }           
        
        Assistido assistidoEscolhido = assistidos.get(escolha);

        Utils.limpaTela();
        System.out.println("Selecione qual informacao deseja editar:");
        System.out.println("1. Nome - " + assistidoEscolhido.getNome());
        System.out.println("2. Data Nascimento - " + assistidoEscolhido.getDataNasc());
        System.out.println("3. CPF  - " + assistidoEscolhido.getCPF());

        escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha < 1 || escolha > 3) {
            System.out.println("Escolha invalida.");
            Utils.delay(1);
            return;
        }

        Utils.limpaTela();
        switch (escolha) {
            case 1:
                System.out.println("Insira o novo nome");
                assistidoEscolhido.setNome(scanner.nextLine());
                break;
            /*case 2:
                System.out.println("Insira a nova data de nascimento");
                assistidoEscolhido.setDataNasc(scanner.nextLine());
                break;*/
            case 3:
                System.out.println("Insira o novo CPF");
                assistidoEscolhido.setCPF(scanner.nextLine());
                break;
        }
    }


    public void removerAssistido() {
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
            System.out.println("Escolha invalida.");
            Utils.delay(1);
        }
    }


    public void gerarRelatorio() {
        relatorio.gerarRelatorio(assistidos);
    }
}