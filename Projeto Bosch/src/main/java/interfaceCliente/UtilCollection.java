package interfaceCliente;

import java.util.Scanner;

public class UtilCollection {
    public static void defaultSwitch(Scanner scanner) {
        System.out.println("Opção inválida!");
        scanner.next();
    }

    public static void defaultError(Scanner scanner) {
        System.out.println("\nInválido, tente novamente!");
    }

    public static void defaultConfirm() {
        System.out.println("\n========================== CONFIRMAÇÃO  ===========================");
        System.out.println("1 - Confirmar");
        System.out.println("0 - Cancelar");
        System.out.print("Resposta: ");
    }

    public static void cancel(){
        System.out.println("\n========================== CANCELANDO  ===========================");
    }

    public static void confirm(){
        System.out.println("\n========================== CONFIRMANDO  ===========================");
    }
}
