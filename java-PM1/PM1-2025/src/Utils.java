import java.util.Scanner;

public class Utils {
    static void limpaTela() {

        try {
            String sistemaOperacional = System.getProperty("os.name");

            if (sistemaOperacional.contains("Windows")) {
                // Limpa a tela no Windows
                ProcessBuilder builder = new ProcessBuilder("cmd", "/c", "cls");
                builder.inheritIO().start().waitFor();
            } else {
                // Limpa a tela no Linux/Mac
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
}
