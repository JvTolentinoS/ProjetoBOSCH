package interfaceCliente;

import dao.AgendamentoDAO;
import dao.CarroDAO;
import dao.ClienteDAO;
import entidade.Agendamento;
import entidade.Carro;
import entidade.Cliente;

import java.time.LocalDate;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class ClienteService {

        static InterfaceService interfaceService = new InterfaceService();
        static ClienteService clienteService = new ClienteService();
        static ClienteDAO clienteDAO = new ClienteDAO();
        static CarroDAO carroDAO = new CarroDAO();
        static AgendamentoDAO agendamentoDAO = new AgendamentoDAO();

        private Cliente clientSession;
        private Carro carroSession;
        private Agendamento agendamentoSession;

        public static void main(String[] args) {
            ClienteService clienteService1 = new ClienteService();
            clienteService1.clientInitializer();
        }

        // SERVIÇO BÁSICO DO CLIENTE
        public void clientInitializer() {
            clientSession = new Cliente();
            carroSession = new Carro();
            agendamentoSession = new Agendamento();
            registrador(clientSession,
                    carroSession,
                    interfaceService,
                    agendamentoSession);
        }

        // REGISTRO DE CLIENTE
        public ClienteService registrador(Cliente cliente, Carro carro, InterfaceService interfaceService, Agendamento agendamento) {
            String cpf, nome;
            int i = -1; // SELECIONADOR
            Scanner scanner = new Scanner(System.in);
            do {
                System.out.println("\n_____________________________________");
                System.out.println("Para ter acesso, por favor digite seu NOME e CPF: ");

                    do {
                        System.out.print("Digite seu CPF: ");
                        cpf = scanner.next();
                        if (validarCPF(cpf)) {
                            cliente.setCpf_cliente(cpf);
                            break;
                        } else {
                            UtilCollection.defaultError(scanner); // FALHA
                        }
                    } while (true);

                    if (!verificarSeClienteExiste(cliente)) {
                        do {
                            System.out.print("Digite seu Nome: ");
                            nome = scanner.next();
                            if (validarNome(nome)) {
                                cliente.setNome_cliente(nome);
                                break;
                            } else {
                                UtilCollection.defaultError(scanner); // FALHA
                            }
                        } while (true);
                    } else {
                        clienteNome(cliente); // PEGA O NOME COM BASE NO CPF E BANCO DE DADOS
                    }

                    do {
                        try {
                            UtilCollection.defaultConfirm(); // CONFIRMAR
                            i = scanner.nextInt();
                            switch (i) {
                                case 1:
                                    UtilCollection.cancel(); // CANCELANDO...
                                    limparCliente(cliente, (interfaceService.initialInterface(clienteService)));
                                    break;
                                case 0:
                                    UtilCollection.confirm();   // CONFIRMANDO...
                                    if (verificarSeClienteExiste(cliente)) {
                                        if (verificarCarro(cliente)) {
                                            instrucaoCarroExiste(carro, cliente, agendamento);
                                        } else {
                                            registrarNovoCarro(carro, cliente, agendamento);
                                        }
                                        break;
                                    } else {
                                        clienteDAO.salvar(cliente);
                                        registrarNovoCarro(carro, cliente, agendamento);
                                    }
                                    break;
                                default:
                                    UtilCollection.defaultSwitch(scanner);
                                }
                        } catch (InputMismatchException e) {
                            UtilCollection.defaultError(scanner); // FALHA
                        }
                    } while (i != 0);
                return null;
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
        public static boolean verificarSeClienteExiste(Cliente cliente) {
            for (Cliente clienteLista : clienteDAO.getClientes()) {
                if (Objects.equals(clienteLista.getCpf_cliente(), cliente.getCpf_cliente())) {
                    return true;
                }
            }
            return false;
        }

        // LIMPANDO O BUFFER DE CLIENTE
        public static void limparCliente(Cliente cliente, InterfaceService interfaceService) {
            if (cliente != null) {
                cliente.setCpf_cliente(null);
                cliente.setNome_cliente(null);
            }
        }

        // LIMPANDO O BUFFER DE CARRO
        public static void limparCarro(Carro carro, InterfaceService interfaceService) {
            if (carro != null) {
                carro.setPlaca(null);
                carro.setMarca(null);
                carro.setModelo(null);
                carro.setAno(null);
            }
        }

        // LIMPANDO O BUFFER DE AGENDA
        public static void limparAgenda(Agendamento agendamento) {
            if (agendamento != null) {
                agendamento.setCpf(null);
                agendamento.setDataCriacao(null);
                agendamento.setDataAgendada(null);
                agendamento.setCarroDescricao(null);
            }
        }

        // RETORNAR NOME DO USUARIO
        public static String clienteNome(Cliente cliente) {
            for (Cliente c : clienteDAO.getClientes()) {
                if (Objects.equals(c.getCpf_cliente(), cliente.getCpf_cliente())) {
                    cliente.setNome_cliente(c.getNome_cliente());
                }
            }
            return null;
        }

        /* LÓGICA DA PARTE DO CARRO */

        // SERVIÇO BÁSICO DO CLIENTE PARA O USO DOS CARROS
        public void instrucaoCarroExiste(Carro carro, Cliente cliente, Agendamento agendamento) {
            int i = -1; // CONTADOR
            do {
                try {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Por favor, escolha entre as seguintes opções para prosseguir:");
                    System.out.println("0 - Voltar");
                    System.out.println("1 - Escolher entre um carro existente");
                    System.out.println("2 - Registrar um novo carro");
                    System.out.print("Resposta: ");
                    i = scanner.nextInt();
                        switch (i) {
                            case 0:
                                registrador(cliente, carro, interfaceService, agendamento);
                                break;// Voltar
                            case 1:
                                System.out.println("===============================");
                                System.out.println("LISTA DE CARROS CADASTRADOS EM: " +cliente.getNome_cliente());
                                for (Carro c : carroDAO.getCarrosCliente(cliente.getCpf_cliente())) {
                                    System.out.println(c.getPlaca() +" "+ c.getModelo() +" "+ c.getMarca() +" "+ c.getAno());
                                    System.out.println("_____________________________________");
                                }
                                instrucaoAgendamento(carro, agendamento, cliente);
                                break;
                            case 2:
                                registrarNovoCarro(carro, cliente, agendamento);
                                break;// Inicia o metodo de registro de carro
                            default:
                                UtilCollection.defaultSwitch(scanner);
                        }
                } catch (InputMismatchException e) {
                    System.out.println("Resposta Inválida");
                }
            } while (i != 0);
        }

        // REGISTRAR NOVO CARRO
        public static ClienteService registrarNovoCarro(Carro carro, Cliente cliente, Agendamento agendamento) {
            boolean verificador = false;
            int i = -1;
            String buffer;
                try {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("\n_____________________________________");
                    System.out.println("Prossiga com o agendamento descrevendo seu carro!");

                    do {
                        System.out.println("\n_____________________________________");
                        System.out.print("Digite a placa do veículo: ");
                        buffer = scanner.nextLine();
                        buffer = buffer.replace("-","");
                        buffer = buffer.toUpperCase();

                        Carro carroVerificador = new Carro();
                        carroVerificador.setPlaca(buffer);

                        if (verificarCarroExiste(carroVerificador)) {
                            System.out.println("A placa "+carroVerificador.getPlaca()+" já possui registro. Verifique a placa informada e tente novamente.");
                        } else {
                            if (verificarPlaca(buffer)) { // verificar placa
                            carro.setPlaca(buffer);
                            break;
                            }
                        }

                    } while (true);

                    do {
                        System.out.println("\n_____________________________________");
                        System.out.print("Digite a marca do veículo: ");
                        buffer = scanner.nextLine();
                        if (verificarMarca(buffer)) { // verificar marca
                            carro.setMarca(buffer);
                            break;
                        }
                    } while (true);

                    do {
                        System.out.println("\n_____________________________________");
                        System.out.print("Digite o modelo do veículo: ");
                        buffer = scanner.nextLine();
                        if (verificarModelo(buffer)) { // verificar modelo
                            carro.setModelo(buffer);
                            break;
                        }
                    } while (true);

                    do {
                        System.out.println("\n_____________________________________");
                        System.out.print("Digite o ano do veículo: ");
                        buffer = scanner.nextLine();
                        if (verificarAno(buffer)) { // verificar ano
                            carro.setAno(buffer);
                            break;
                        }
                    } while (true);

                    do {
                        try {
                            System.out.println("\n_____________________________________");
                            System.out.println("Confirmar o cadastro do carro");
                            System.out.println("1 - Cancelar");
                            System.out.println("0 - Confirmar");
                            System.out.print("Resposta: ");
                            i = scanner.nextInt();
                                switch (i) {
                                    case 1:
                                        System.out.println("\n_____________________________________");
                                        System.out.println("Cancelando...");
                                        limparCarro(carro,(interfaceService.initialInterface(clienteService)));
                                    case 0:
                                        System.out.println("\n_____________________________________");
                                        System.out.println("Confirmando...");
                                        if (verificarCarroExiste(cliente)) {
                                            System.out.println("Este carro já foi cadastrado, tente novamente.");
                                            scanner.nextLine();
                                        } else {
                                            carroDAO.salvar(carro, cliente);
                                            clienteService.instrucaoAgendamento(carro, agendamento, cliente);
                                            break;
                                        }
                                    default:
                                        System.out.println("Opção inválida! Reiniciando a seleção...");
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
                    System.out.println("Erro");
            }
                return null;
        }

        // METODO VERIFICADOR - CARRO
        public static boolean verificarCarro(Cliente cliente) {
            for (Carro carroLista : carroDAO.getCarros()) {
                if (Objects.equals(carroLista.getCpf(), cliente.getCpf_cliente())) {
                    return true;
                }
            }
            return false;
        }

        // METODO VERIFICADOR - SERVE PARA VERIFICAR SE O CLIENTE NÃO ESTÁ ADICIONANDO UM CARRO JÁ EXISTENTE
        public static boolean verificarCarroExiste(Cliente cliente) {
            for (Carro carroLista : carroDAO.getCarros()) {
                if (Objects.equals(carroLista.getPlaca(), cliente.getCpf_cliente())) {
                    return true;
                }
            }
            return false;
        }

        public static boolean verificarCarroExiste(Carro carro) { // SE BASEIA NA PLACA
        for (Carro carroLista : carroDAO.getCarros()) {
            if (Objects.equals(carroLista.getPlaca(), carro.getPlaca())) {
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
            return !placa.isEmpty() && (placa.matches(padraoNovo)) || placa.matches(padraoAntigo);
        }

        // METODO VERIFICADOR - MODELO
        public static boolean verificarModelo(String modelo) {
            return !modelo.isEmpty() && modelo.matches("^[A-Za-z0-9]{2,}$");
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


        /* LÓGICA DA PARTE DO AGENDAMENTO */

        // METODO DE SET AGENDA
        public static void setAgenda(Agendamento agendamento, Carro carro, Cliente cliente, String desc) {
        agendamento.setPlaca(carro.getPlaca());
        agendamento.setCarroDescricao(desc);
        agendamento.setCpf(cliente.getCpf_cliente());
        agendamento.setDataCriacao(new Date());
    }

        // METODO DE REGISTRO DE AGENDAMENTO
        public void instrucaoAgendamento(Carro carro, Agendamento agendamento, Cliente cliente) {
            boolean continuar = true; // Variável para controle do loop principal

            while (continuar) { // Loop principal para o processo de agendamento
                Scanner scanner = new Scanner(System.in);
                String placa;

                // EXIBE AS DATAS DISPONÍVEIS
                System.out.println("Selecione uma data disponível para agendamento");
                System.out.println("0. Voltar ao menu anterior");
                System.out.println("1. 04/11/2024\n2. 05/11/2024\n3. 06/11/2024\n4. 07/11/2024\n5. 08/11/2024");

                int opcaoData = -1;
                boolean entradaValida = false;
                int diaEscolhido = 0;

                while (!entradaValida) {
                    try {
                        System.out.print("Escolha uma opção (0-5): ");
                        opcaoData = scanner.nextInt();
                        scanner.nextLine();
                        if (opcaoData < 0 || opcaoData > 5) {
                            throw new InputMismatchException(); // Lança exceção se a opção não estiver entre 1 e 5
                        }
                        entradaValida = true;

                        switch (opcaoData) {
                            case 1: diaEscolhido = 4; break;
                            case 2: diaEscolhido = 5; break;
                            case 3: diaEscolhido = 6; break;
                            case 4: diaEscolhido = 7; break;
                            case 5: diaEscolhido = 8; break;
                            case 0:
                                System.out.println("Voltando ao menu anterior...");
                                instrucaoCarroExiste(carro, cliente, agendamento);
                                return;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Opção inválida! Por favor, escolha uma das opções (0-5)");
                        scanner.nextLine(); // Limpa o buffer
                    }
                }

                System.out.println("Selecione um horário disponível:");
                System.out.println("1. 08:00\n2. 09:00\n3. 10:00\n4. 11:00\n5. 13:00\n6. 14:00\n7. 15:00\n8. 16:00\n9. 17:00\n10. 18:00\n11. 19:00");

                int opcaoHorario = 0;
                int horaEscolhida = 0;
                entradaValida = false;

                while (!entradaValida) {
                    try {
                        opcaoHorario = scanner.nextInt();
                        scanner.nextLine();

                        if (opcaoHorario < 1 || opcaoHorario > 11) {
                            throw new InputMismatchException();
                        }
                        entradaValida = true;

                        //DEFINE A HORA ESCOLHIDA COM BASE NA OPÇÃO
                        switch (opcaoHorario) {
                            case 1: horaEscolhida = 8; break;
                            case 2: horaEscolhida = 9; break;
                            case 3: horaEscolhida = 10; break;
                            case 4: horaEscolhida = 11; break;
                            case 5: horaEscolhida = 13; break;
                            case 6: horaEscolhida = 14; break;
                            case 7: horaEscolhida = 15; break;
                            case 8: horaEscolhida = 16; break;
                            case 9: horaEscolhida = 17; break;
                            case 10: horaEscolhida = 18; break;
                            case 11: horaEscolhida = 19; break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Opção inválida! Por favor, escolha um horário disponível (1-11)");
                        scanner.nextLine();
                    }
                }

                System.out.println("Data escolhida: " + diaEscolhido);
                System.out.println("Horário escolhido: " + horaEscolhida);
                continuar = false; //ENCERRA O LOOP PRINCIPAL

                // Validação da placa do veículo
                while (true) {
                    System.out.print("Digite a placa do veículo: ");
                    placa = scanner.nextLine();

                    // Remove o hífen para validação
                    String placaSemHifen = removeHifen(placa);

                    // Verifica se a placa sem hífen possui exatamente 7 caracteres,
                    // sendo os 3 primeiros letras e os 4 últimos alfanuméricos
                    if (placaSemHifen.length() == 7 &&
                            placaSemHifen.substring(0, 3).matches("[A-Za-z]{3}") &&
                            placaSemHifen.substring(3).matches("[A-Za-z0-9]{4}") &&
                            placaSemHifen.substring(3).matches(".*[0-9].*")) {
                        String placaCarro = placaSemHifen.toUpperCase();
                        for (Carro c : carroDAO.getCarros()) {
                            if (c.getPlaca().equals(placaCarro)) {
                                carro.setPlaca(placaCarro);
                                System.out.println("Placa registrada: " + placaCarro);
                            }
                        }
                        break;
                    } else {
                        System.out.println("Placa inválida!");
                    }
                }

                // SOLICITAR A DESCRIÇÃO DO DEFEITO
                System.out.print("Descreva o defeito do veículo: ");
                String defeito = scanner.nextLine();
                System.out.println("Defeito descrito: " + defeito);

                // CONFIRMAÇÃO DO AGENDAMENTO
                boolean confirmarAgendamento = false;
                while (!confirmarAgendamento) {
                    System.out.print("Deseja confirmar o agendamento? Digite 0 para confirmar ou 1 para não prosseguir: ");
                    int opcaoConfirmacao = scanner.nextInt();
                    scanner.nextLine();

                    if (opcaoConfirmacao == 0) {
                        System.out.println("Agendamento confirmado!");
                        confirmarAgendamento = true; // Finaliza a confirmação
                        agendamento.setCarroDescricao(defeito);
                        agendamento.setDataCriacao(new Date());
                        agendamento.setCpf(cliente.getCpf_cliente());
                        agendamento.setPlaca(carro.getPlaca());
                        agendamentoDAO.salvar(agendamento,
                                cliente,
                                carro,
                                diaEscolhido,
                                horaEscolhida);
                        confirmarAgendamento = true; // Finaliza a confirmação
                        continuar = false; // Finaliza o loop principal
                    } else if (opcaoConfirmacao == 1) {
                        System.out.println("Agendamento não prosseguido. Reiniciando o processo...");
                        confirmarAgendamento = true; // Finaliza a confirmação
                        // O loop principal vai reiniciar
                        agendamento.setCarroDescricao(null);
                        agendamento.setDataAgendada(null);
                    } else {
                        System.out.println("Opção inválida! Por favor, digite 0 para confirmar ou 1 para não prosseguir.");
                    }
                }

                // DEPOIS DE DEFINIR A AGENDA
                int opcao;
                do {
                try {
                System.out.println("Selecione uma das opções:");
                System.out.println("1. Voltar ao início\n2. Sair");
                opcao = scanner.nextInt();
                switch (opcao)
                {
                    case 1: interfaceService.initialInterface(clienteService);
                    break;
                    case 2: System.exit(0);
                    default: UtilCollection.defaultSwitch(scanner);
                }
                    } catch (InputMismatchException e) {
                        UtilCollection.defaultError(scanner);
                    }
                } while (true);
            }
        }

        // METODO PARA REMOVER O HIFEN DA PLACA
        public static String removeHifen(String placa) {
            return placa.replace("-", "");
        }
}