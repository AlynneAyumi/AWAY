import java.time.LocalDate;

class Comparecimento {
    private String descricao;
    private LocalDate data;
    private boolean compareceu;

    public Comparecimento(String descricao, LocalDate data, boolean compareceu) {
        this.descricao = descricao;
        this.data = data;
        this.compareceu = compareceu;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public boolean isCompareceu() {
        return compareceu;
    }
}
