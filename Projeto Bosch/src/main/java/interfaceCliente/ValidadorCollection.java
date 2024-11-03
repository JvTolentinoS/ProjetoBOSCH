package interfaceCliente;

import dao.CarroDAO;
import dao.ClienteDAO;

import entidade.Carro;
import entidade.Cliente;

import java.time.LocalDate;
import java.util.Objects;

public class ValidadorCollection {

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
        return !nome.isEmpty() && nome.matches("^[\\p{L}\\s]*$") && nome.length() < 100;
    }

    // METODO VERIFICADOR - CLIENTE
    public static boolean verificarSeClienteExiste(Cliente cliente, ClienteDAO clienteDAO) {
        for (Cliente clienteLista : clienteDAO.getClientes()) {
            if (Objects.equals(clienteLista.getCpf_cliente(), cliente.getCpf_cliente())) {
                return true;
            }
        }
        return false;
    }

    // METODO VERIFICADOR - CARRO
    public static boolean verificarCarro(Cliente cliente, CarroDAO carroDAO) {
        for (Carro carroLista : carroDAO.getCarros()) {
            if (Objects.equals(carroLista.getCpf(), cliente.getCpf_cliente())) {
                return true;
            }
        }
        return false;
    }

    // METODO VERIFICADOR - SERVE PARA VERIFICAR SE O CLIENTE NÃO ESTÁ ADICIONANDO UM CARRO JÁ EXISTENTE
    public static boolean verificarCarroExiste(Cliente cliente, CarroDAO carroDAO) {
        for (Carro carroLista : carroDAO.getCarros()) {
            if (Objects.equals(carroLista.getPlaca(), cliente.getCpf_cliente())) {
                return true;
            }
        }
        return false;
    }

    public static boolean verificarCarroExiste(Carro carro, CarroDAO carroDAO) { // SE BASEIA NA PLACA
        for (Carro carroLista : carroDAO.getCarros()) {
            if (Objects.equals(carroLista.getPlaca(), carro.getPlaca())) {
                return true;
            }
        }
        return false;
    }

    // METODO VERIFICADOR - PLACA
    public static boolean verificarPlaca(String placa) {
        placa = placa.replace("-", "");
        if (placa.length() == 7 &&
                placa.substring(0, 3).matches("[A-Za-z]{3}") &&
                placa.substring(3, 4).matches("[0-9]") &&
                placa.substring(4, 5).matches("[A-Za-z0-9]") &&
                placa.substring(5, 7).matches("[0-9]{2}")) {
            String placaCarro = placa.toUpperCase();
            return true;
        } else {
            return false;
        }
    }

    // METODO VERIFICADOR - MODELO
    public static boolean verificarModelo(String modelo) {
        return !modelo.isEmpty() && modelo.matches("^[A-Za-z0-9]{2,}$") && modelo.length() < 100;
    }

    // METODO VERIFICADOR - MARCA
    public static boolean verificarMarca(String marca) {
        return marca != null && marca.matches("^[A-Za-z]{2,}$") && marca.length() < 20;
    }

    // METODO VERIFICADOR - ANO
    public static boolean verificarAno(String ano) {

        if (ano.isEmpty()) {
            return false;
        }

        if (ano.length() != 4) {
            return false;
        }

        if (!ano.matches("^[0-9]{4}$")) {
            return false;
        }

        try {

            LocalDate dataAtual = LocalDate.now();
            int dataComparada;
            int anoConvertido = Integer.parseInt(ano);
            dataComparada = dataAtual.getYear();

            if (anoConvertido >= 1980 && anoConvertido <= dataComparada) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("Falha na Conversão do Ano: " + e);
        }
        // CASO O ANO NÃO SEJA CORRESPONDENTE
        return false;
    }
}