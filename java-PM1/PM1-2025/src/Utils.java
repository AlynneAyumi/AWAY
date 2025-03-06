public class Utils {
    static void limpaTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void delay(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
