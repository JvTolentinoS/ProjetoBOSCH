package interfaceAdmin;

import java.util.Scanner;

public class UtilCollection {
    public static void defaultSwitch(Scanner scanner) {
        System.out.println("Opção inválida!");
        System.out.println("_____________________");
        scanner.nextLine();
    }

    public static void defaultError(Scanner scanner) {
        System.out.println("\n\nInsira uma valor válido!");
        System.out.println("_____________________");
        scanner.nextLine();
    }
}
