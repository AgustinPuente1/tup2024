package ar.edu.utn.frbb.tup.presentation.input;

public class LimpiarScreen {
    protected static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
