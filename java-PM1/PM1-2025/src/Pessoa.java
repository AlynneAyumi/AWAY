abstract class Pessoa {
    protected String nome;
    protected String dataNasc;
    protected String CPF;
    protected String CEP;

    public Pessoa(String nome, String dataNasc, String CPF, String CEP) {
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.CPF = CPF;
        this.CEP = CEP;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String cPF) {
        CPF = cPF;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String cEP) {
        CEP = cEP;
    }

    public abstract void exibirInformacoes();
}
