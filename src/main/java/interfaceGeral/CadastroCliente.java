package interfaceGeral;

import entidade.Cliente;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CadastroCliente extends Cliente {

    public static void main(String[] args) {
        registroCliente("","");
    }

    //EM CASO DE LOGIN COMO CLIENTE
    public void CadastroInterface() { // Agendamento
        int i = -1;
        Scanner scanner = new Scanner(System.in);
        do {
            try {
            System.out.println("\n_____________________________________");
            System.out.println("Escolha uma das opções a seguir:");
            System.out.println("0 - Voltar");
            System.out.println("1 - Registrar Cliente");
            System.out.println("2 - Iniciar um Agendamento");
            System.out.println("_____________________________________");
            System.out.print("Resposta: ");
            i = scanner.nextInt();
            System.out.println("\n_____________________________________");
            if (i >= 0 && i <= 2) {
            switch (i) {
                case 0: // Voltar
                    System.out.println("\nVoltando para o menu inicial");
                    break;
                case 1: // Registro
                    System.out.println("\nProsseguindo para o Registro do Cliente...");
                    break;
                case 2: // Agendamento
                    System.out.println("\nProsseguindo para o Agendamento...");
                    break;
                    }
                } else {
                System.out.println("\nInsira um valor válido");
            }
            } catch (InputMismatchException e) {
                System.out.println("\nInsira um valor válido");
                scanner.nextLine();
            }
        } while (i != 0);
    }

    //EM CASO DE #1 "Registrar Cliente - Perguntar o CPF e Nome"
    public static void registroCliente(String cpf, String nome) {
    int i; // SELECIONADOR
    Scanner scanner = new Scanner(System.in);
    do {
            try {
                System.out.println("\n_____________________________________");
                System.out.println("Cadastrando Cliente");

                do {
                    System.out.print("Digite seu CPF: ");
                    cpf = scanner.next();
                    if (validarCPF(cpf)) {
                        System.out.println("CPF Válido!");
                        break;
                    } else {
                        System.out.println("CPF Inválido, tente novamente.");
                        scanner.nextLine();
                    }
                } while (true);

                do {
                    System.out.print("Digite seu Nome:");
                    nome = scanner.next();
                    if (validarNome(nome)) {
                        System.out.println("Nome Válido!");
                        break;
                    } else {
                        System.out.println("Nome Inválido, tente novamente.");
                        scanner.nextLine();
                    }
                } while (true);

                do {
                    try {
                    System.out.println("\n_____________________________________");
                    System.out.println("Confirme o Cadastro:");
                    System.out.println("1 - Cancelar");
                    System.out.println("0 - Confirmar");
                    System.out.print("Resposta: ");
                    i = scanner.nextInt();
                    if (i >= 0 && i <= 1) {
                        switch (i) {
                        case 1:
                            System.out.println("\n_____________________________________");
                            System.out.println("Cancelando...");
                            break;
                        case 0:
                            System.out.println("\n_____________________________________");
                            System.out.println("Confirmando...");
                            break;
                        }
                    } else {
                        System.out.println("\n_____________________________________");
                        System.out.println("Resposta inválida");
                        scanner.nextLine();
                    }
                    } catch (InputMismatchException e) {
                        System.out.println("\n_____________________________________");
                        System.out.println("Resposta inválida");
                    }
                    break;
                } while (i!=0);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            break;
        } while (true);
    }

    // METODO VERIFICADOR - CPF
    public static boolean validarCPF(String cpf) {
        // REMOVE AS PONTUAÇÕES
        cpf = cpf.replaceAll("[^0-9]", "");

        // VERIFICA SE O CPF TEM 11 DÍGITOS
        if (cpf.length() != 11) {
            return false;
        }

        // VERIFICA SE TODOS OS DÍGITOS SÃO IGUAIS
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // CÁLCULO DO PRIMEIRO DÍGITO VERIFICADOR
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int primeiroDigitoVerificador = 11 - (soma % 11);
        if (primeiroDigitoVerificador >= 10) {
            primeiroDigitoVerificador = 0;
        }

        // VERIFICA O PRIMEIRO DÍGITO
        if (primeiroDigitoVerificador != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        // CÁLCULO DO SEGUNDO DÍGITO VERIFICADOR
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int segundoDigitoVerificador = 11 - (soma % 11);
        if (segundoDigitoVerificador >= 10) {
            segundoDigitoVerificador = 0;
        }
        // VERIFICA O SEGUNDO DÍGITO
        return segundoDigitoVerificador == Character.getNumericValue(cpf.charAt(10));
    }

    // METODO VERIFICADOR - NOME
    public static boolean validarNome(String nome) {
        return !nome.isEmpty() && nome.matches("^[\\p{L}\\s]*$");
    }
}


