package interfaceCliente;

import java.util.Scanner;

public class ValidarCPF {

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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();

        if (ValidarCPF.validarCPF(cpf)) {
            System.out.println("CPF válido!");
        } else {
            System.out.println("CPF inválido!");
        }

        scanner.close();
    }
}