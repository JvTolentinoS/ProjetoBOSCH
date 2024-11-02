package interfaceGeral;

import dao.AgendamentoDAO;
import dao.CarroDAO;
import dao.ClienteDAO;
import entidade.Agendamento;
import entidade.Carro;
import entidade.Cliente;
import jnr.posix.WString;
import org.checkerframework.checker.units.qual.A;

import java.time.LocalDate;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

    public class ClienteService {
        static InterfaceService interfaceService = new InterfaceService();
        static ClienteService clienteService = new ClienteService();
        static ClienteDAO clienteDAO = new ClienteDAO();
        static Cliente cliente = new Cliente();
        static Carro carro = new Carro();
        static CarroDAO carroDAO = new CarroDAO();
        static Agendamento agendamento = new Agendamento();
        static AgendamentoDAO agendamentoDAO = new AgendamentoDAO();

        public static void main(String[] args) {

        }

        // SERVIÇO BÁSICO DO CLIENTE
        public void clientInitializer() { // Agendamento
            registrador(cliente, clienteDAO, interfaceService);
        }

        // REGISTRO DE CLIENTE
        public ClienteService registrador(Cliente cliente, ClienteDAO clienteDAO, InterfaceService interfaceService) {
            String cpf, nome;
            int i = -1; // SELECIONADOR
            Scanner scanner = new Scanner(System.in);
            do {
                try {
                    System.out.println("\n_____________________________________");
                    System.out.println("Para ter acesso, por favor digite seu NOME e CPF: ");
                    do {
                        System.out.print("Digite seu CPF: ");
                        cpf = scanner.next();
                        if (validarCPF(cpf)) {
                            System.out.println("CPF Válido!");
                            cliente.setCpf_cliente(cpf);
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
                            cliente.setNome_cliente(nome);
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
                                        limparCliente(interfaceService.initialInterface(clienteService));
                                        break;
                                    case 0:
                                        System.out.println("\n_____________________________________");
                                        System.out.println("Confirmando...");
                                        if (verificarSeClienteExiste(cliente, clienteDAO)) {
                                            if (verificarCarro(cliente, carroDAO)) {
                                                instrucaoCarroExiste(carro);
                                            }
                                        } else {
                                            clienteDAO.salvar(cliente);
                                        }
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
                            scanner.nextLine();
                        }
                    } while (i != 0);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
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

        // METODO VERIFICADOR - CLIENTE
        public static boolean verificarSeClienteExiste(Cliente cliente, ClienteDAO clienteDAO) {
            for (Cliente clienteLista : clienteDAO.getClientes()) {
                if (Objects.equals(clienteLista.getCpf_cliente(), cliente.getCpf_cliente())) {
                    return true;
                }
            }
            return false;
        }

        // LIMPANDO O BUFFER DE CLIENTE
        public static void limparCliente(InterfaceService interfaceService) {
            if (cliente != null) {
                cliente.setCpf_cliente(null);
                cliente.setNome_cliente(null);
            }
        }

        /* LÓGICA DA PARTE DO CARRO */

        // SERVIÇO BÁSICO DO CLIENTE PARA O USO DOS CARROS
        public void instrucaoCarroExiste(Carro carro) {
            int i = -1; // CONTADOR
            Scanner scanner = new Scanner(System.in);
            do {
                try {
                    System.out.println("Por favor, escolha entre as seguintes opções para prosseguir:");
                    System.out.println("0 - Voltar");
                    System.out.println("1 - Escolher entre um carro existente");
                    System.out.println("2 - Registrar um novo carro");
                    System.out.print("Resposta: ");
                    i = scanner.nextInt();
                    if (i >= 0 && i <= 2) {
                        switch (i) {
                            case 0:
                                registrador(cliente, clienteDAO, interfaceService); // Voltar
                            case 1: // Pula para o metodo de agendamento
                            case 2:
                                registrarNovoCarro(carro);// Inicia o metodo de registro de carro
                        }
                    } else {
                        System.out.println("Resposta inválida");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Resposta Inválida");
                }
            } while (i != 0);
        }

        // REGISTRAR NOVO CARRO
        public static void registrarNovoCarro(Carro carro) {
            int i = -1;
            Scanner scanner = new Scanner(System.in);
            String buffer;

            do {
                try {
                    System.out.println("\n_____________________________________");
                    System.out.println("Prossiga com o agendamento descrevendo seu carro!");

                    do {
                        System.out.println("\n_____________________________________");
                        System.out.print("Escreva sua placa: ");
                        buffer = scanner.nextLine();
                        if (verificarPlaca(buffer)) { // verificar placa
                            carro.setPlaca(buffer);
                            scanner.next();
                            break;
                        }
                    } while (true);

                    do {
                        System.out.println("\n_____________________________________");
                        System.out.print("Escreva sua marca: ");
                        buffer = scanner.nextLine();
                        if (verificarMarca(buffer)) { // verificar marca
                            carro.setPlaca(buffer);
                            scanner.next();
                            break;
                        }
                    } while (true);

                    do {
                        System.out.println("\n_____________________________________");
                        System.out.print("Escreva sua modelo: ");
                        buffer = scanner.nextLine();
                        if (verificarModelo(buffer)) { // verificar modelo
                            carro.setPlaca(buffer);
                            scanner.next();
                            break;
                        }
                    } while (true);

                    do {
                        System.out.println("\n_____________________________________");
                        System.out.print("Escreva o ano de fabricação: ");
                        buffer = scanner.nextLine();
                        if (verificarAno(buffer)) { // verificar ano
                            carro.setPlaca(buffer);
                            scanner.next();
                            break;
                        }
                    } while (true);

                    do {
                        try {
                            System.out.println("\n_____________________________________");
                            System.out.println("Confirme o cadastro do carro");
                            System.out.println("1 - Cancelar");
                            System.out.println("0 - Confirmar");
                            System.out.print("Resposta: ");
                            i = scanner.nextInt();
                            if (i >= 0 && i <= 1) {
                                switch (i) {
                                    case 1:
                                        System.out.println("\n_____________________________________");
                                        System.out.println("Cancelando...");
                                        limparCliente(interfaceService.initialInterface(clienteService));
                                        break;
                                    case 0:
                                        System.out.println("\n_____________________________________");
                                        System.out.println("Confirmando...");
                                        if (verificarCarroExiste(carro, carroDAO)) {
                                            System.out.println("Esse carro já foi cadastrado, tente novamente.");
                                        } else {
                                            carroDAO.salvar(carro, cliente);
                                        }
                                }
                            } else {
                                System.out.println("\n_____________________________________");
                                System.out.println("Resposta inválida");
                                scanner.nextLine();
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("\n_____________________________________");
                            System.out.println("Resposta inválida");
                            scanner.nextLine();
                        }
                    } while (i != 0);

                    //Iniciar Agendamento

                } catch (InputMismatchException e) {
                }
            } while (true);
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
        public static boolean verificarCarroExiste(Carro carro, CarroDAO carroDAO) {
            for (Carro carroLista : carroDAO.getCarros()) {
                if (Objects.equals(carroLista.getPlaca(), cliente.getCpf_cliente())) {
                    return true;
                }
            }
            return false;
        }

        // METODO VERIFICADOR - PLACA
        public static boolean verificarPlaca(String placa) {
            String padraoAntigo = "[A-Z a-z]{3}\\d{4}$";
            String padraoNovo = "[A-Z a-z]{3}\\d[A-Z a-z]\\d{2}$";
            placa = placa.replace("-", "");
            if (!placa.isEmpty() && (placa.matches(padraoNovo)) || placa.matches(padraoAntigo)) {
                return true;
            }
            return false;
        }

        // METODO VERIFICADOR - MODELO
        public static boolean verificarModelo(String modelo) {
            return modelo.isEmpty() && modelo.matches("^[A-Za-z0-9]{2,}$");
        }

        // METODO VERIFICADOR - MARCA
        public static boolean verificarMarca(String marca) {
            return marca != null && marca.matches("^[A-Za-z]{2,}$");
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
            dataComparada = dataAtual.getYear() + 1;


            if (anoConvertido >= 1980 && anoConvertido <= dataComparada) {
                System.out.println(dataComparada);
                return true;
                }

            } catch (Exception e) {
                System.out.println("Falha na Conversão do Ano: " + e);
            }
            // CASO O ANO NÃO SEJA CORRESPONDENTE
            return false;
        }

    }


        /* LÓGICA DA PARTE DO AGENDAMENTO */

