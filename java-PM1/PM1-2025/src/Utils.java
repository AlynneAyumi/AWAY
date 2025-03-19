import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Utils {
    static void limpaTela() {

        try {
            String sistemaOperacional = System.getProperty("os.name");

            if (sistemaOperacional.contains("Windows")) {
                ProcessBuilder builder = new ProcessBuilder("cmd", "/c", "cls");
                builder.inheritIO().start().waitFor();
            } else {
                ProcessBuilder builder = new ProcessBuilder("clear");
                builder.inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void delay(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    static void pause() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("\n\nPressione enter para continuar... ");
        scanner.nextLine();
        
    }

    static Date formatarData(int dia, int mes, int ano){

        Calendar calendario = Calendar.getInstance();

        calendario.set(Calendar.YEAR, ano);
        calendario.set(Calendar.MONTH, mes-1); // Mês começa em 0
        calendario.set(Calendar.DAY_OF_MONTH, dia);

        Date dataNasc = calendario.getTime();

        return dataNasc;
        
    }
}
