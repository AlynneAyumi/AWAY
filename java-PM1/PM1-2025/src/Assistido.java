import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Assistido extends Pessoa {
    private String historicoCriminal;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private List<Comparecimento> atividades;

    public Assistido(String nome, String dataNasc, String CPF, String CEP, String historicoCriminal, LocalDate dataEntrada) {
        super(nome, dataNasc, CPF, CEP);
        this.historicoCriminal = historicoCriminal;
        this.dataEntrada = dataEntrada;
        this.atividades = new ArrayList<>();
    }

    public String getHistoricoCriminal() {
        return historicoCriminal;
    }

    public void setHistoricoCriminal(String historicoCriminal) {
        this.historicoCriminal = historicoCriminal;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void registrarSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public void adicionarAtividade(Comparecimento atividade) {
        atividades.add(atividade);
    }

    public void removerAtividade(Comparecimento atividade) {
        atividades.remove(atividade);
    }

    public List<Comparecimento> getAtividades() {
        return new ArrayList<>(atividades); // Retorna uma cópia para evitar modificações externas
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Assistido: " + getNome() + "\n Data Nascimento: " + getDataNasc() + "\n CPF: " + getCPF());
        System.out.println("Historico Criminal: " + historicoCriminal);
        System.out.println("Data de Entrada: " + dataEntrada);
        System.out.println("Data de Saída: " + (dataSaida != null ? dataSaida : "Ainda presente"));
        System.out.println("Atividades:");
        if (atividades.isEmpty()) {
            System.out.println("Nenhuma atividade registrada.");
        } else {
            for (Comparecimento atividade : atividades) {
                System.out.println(" - " + atividade.getDescricao() + " em " + atividade.getData());
            }
        }
    }
}
