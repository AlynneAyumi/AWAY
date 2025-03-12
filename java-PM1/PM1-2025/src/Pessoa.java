import java.text.SimpleDateFormat;
import java.util.Date;

abstract class Pessoa {
    protected String nome;
    protected Date dataNasc;
    protected String CPF;
    protected String CEP;

    public Pessoa(String nome, Date dataNasc, String CPF, String CEP) {
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

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormt = formato.format(dataNasc);

        return dataFormt;
    }

    public void setDataNasc(Date dataNasc) {
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
