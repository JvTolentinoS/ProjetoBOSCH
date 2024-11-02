package interfaceGeral;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InterfaceService {

    static ClienteService clienteService = new ClienteService();
    public static void main(String[] args) {
        InterfaceService logo = new InterfaceService();
        logo.initialInterface(clienteService);
    }

    public InterfaceService initialInterface(ClienteService clienteService) {
        logoText();
        introMessage();
        int i = -1;
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                initialMenu();
                i = scanner.nextInt();
                if (i >= 0 && i <= 3) {
                    switch (i) {
                        case 1: // Iniciar um Agendamento
                            System.out.println("Iniciando processo de criar agendamento...");
                            clienteService.clientInitializer();
                        break;
                        case 2: // Ver Agendamentos
                            System.out.println("Iniciando processo de ver agendamentos...");
                        break;
                        case 3: // Ver Carros
                            System.out.println("Iniciando processo de ver carros...");
                        break;
                        case 0:
                            System.out.println("Terminando aplicação...");
                            return null;
                    }
                } else {
                    System.out.println("Resposta Inválida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Resposta Inválida!");
                scanner.nextLine();
            }
        } while (true);
    }

    private static void initialMenu() {
        System.out.println("\n_____________________________________");
        System.out.println("0 - Sair");
        System.out.println("1 - Iniciar um Agendamento");
        System.out.println("2 - Ver Agendamentos");
        System.out.println("3 - Ver Carros");
        System.out.println("_____________________________________");
        System.out.print("Resposta: ");
    }

    private static void introMessage() {
            System.out.println("\n_____________________________________\n");
            System.out.println("Bem-Vindo(a) ao Bosch Car Service!");
            System.out.println("Esse é um canal oficial da Bosch Car Service. Para mais informações, siga nossas redes sociais!");
            System.out.println("Selecione entre as opções da lista, qual serviço deseja receber:");
    }

    private static void logoText() {
        System.out.println("    " +
                 "\n" +"██████╗  ██████╗ ███████╗ ██████╗██╗  ██╗" +
                 "\n" +"██╔══██╗██╔═══██╗██╔════╝██╔════╝██║  ██║" +
                 "\n" +"██████╔╝██║   ██║███████╗██║     ███████║" +
                 "\n" +"██╔══██╗██║   ██║╚════██║██║     ██╔══██║" +
                 "\n" +"██████╔╝╚██████╔╝███████║╚██████╗██║  ██║" +
                 "\n" +"╚═════╝  ╚═════╝ ╚══════╝ ╚═════╝╚═╝  ╚═╝ " +
                 "\n" + "" +
                 "\n" +" ██████╗ █████╗ ██████╗   " +
                 "\n" +"██╔════╝██╔══██╗██╔══██╗ " +
                 "\n" +"██║     ███████║██████╔╝" +
                 "\n" +"██║     ██╔══██║██╔══██╗ " +
                 "\n" +"╚██████╗██║  ██║██║  ██║" +
                 "\n" +" ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝ " +
                 "\n" +"" +
                 "\n" +"███████╗███████╗██████╗ ██╗   ██╗██╗ ██████╗███████╗" +
                 "\n" +"██╔════╝██╔════╝██╔══██╗██║   ██║██║██╔════╝██╔════╝" +
                 "\n" +"███████╗█████╗  ██████╔╝██║   ██║██║██║     █████╗  " +
                 "\n" +"╚════██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██║██║     ██╔══╝  " +
                 "\n" +"███████║███████╗██║  ██║ ╚████╔╝ ██║╚██████╗███████╗" +
                 "\n" +"╚══════╝╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚═╝ ╚═════╝╚══════╝" +
                 "\n");
    }

    private static void defaultOption() {
        boolean option = false;
        while (!option) {
            int i = -1;
            System.out.println("Confirmar?");
            System.out.println("0 - Sim");
            System.out.println("1 - Não");
            switch (i) {
                case 1: // Aqui vem o primeiro metodo parametro
                case 0: // Aqui vem o segundo metodo parametro
            }
        }
    }
    ///////

}
