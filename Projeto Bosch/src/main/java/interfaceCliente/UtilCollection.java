package interfaceCliente;

import java.util.Scanner;

public class UtilCollection {
    public static void defaultSwitch(Scanner scanner) {
        System.out.println("\n_____________________________________");
        System.out.println("Opção inválida! Reiniciando a seleção...");
        scanner.nextLine();
    }

    public static void defaultError(Scanner scanner) {
        System.out.println("Inválido, tente novamente.");
        scanner.nextLine();
    }

    public static void defaultConfirm() {
        System.out.println("\n_____________________________________");
        System.out.println("Confirmar:");
        System.out.println("1 - Cancelar");
        System.out.println("0 - Confirmar");
        System.out.print("Resposta: ");
    }

    public static void cancel(){
        System.out.println("\n_____________________________________");
        System.out.println("Cancelando...");
    }

    public static void confirm(){
        System.out.println("\n_____________________________________");
        System.out.println("Confirmando...");
    }
}
