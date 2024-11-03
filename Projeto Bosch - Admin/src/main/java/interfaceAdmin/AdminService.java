package interfaceAdmin;

import dao.CarroDAO;
import dao.ClienteDAO;
import entidade.Agendamento;
import dao.AgendamentoDAO;
import entidade.Carro;
import entidade.Cliente;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import static interfaceAdmin.InterfaceAdmin.adminService;

public class AdminService {
    private Cliente clienteAdmin;
    private Agendamento agendamentoAdmin;
    private Carro carroAdmin;
    static AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    static CarroDAO carroDAO = new CarroDAO();
    static ClienteDAO clienteDAO = new ClienteDAO();

    public void AdminServiceInitialize() {
        clienteAdmin = new Cliente();
        agendamentoAdmin = new Agendamento();
        carroAdmin = new Carro();
        consultarAgenda(clienteAdmin, agendamentoAdmin, carroAdmin);
    }

    public static void consultarAgenda(Cliente cliente, Agendamento agendamento, Carro carro) {
        int idDigitado = 0;
        boolean continuar = true;
        Scanner scanner = new Scanner(System.in);

        do {
            try {
                listagemDeAgendas(cliente, agendamento);

                System.out.println("\nEscolha um agendamento para consultar os detalhes");
                System.out.println("\nDigite 0 para Voltar");
                System.out.println("\nDigite D para Deletar um agendamento");
                System.out.print("\nResposta: ");
                String resposta = scanner.next().trim().toUpperCase();

                if (resposta.equals("0")) {
                    InterfaceAdmin.initialInterface(adminService);
                    continuar = false;
                } else if (resposta.equals("D")) {
                    System.out.print("Digite o ID do agendamento que deseja deletar: ");
                    idDigitado = scanner.nextInt();
                    if (apagarAgenda(idDigitado)) {
                        System.out.println("Agendamento deletado com sucesso.");
                    } else {
                        System.out.println("Falha ao deletar o agendamento. Verifique o ID.");
                    }
                } else if (resposta.matches("\\d+")) {
                    try {
                    idDigitado = scanner.nextInt();
                    if (verificarId(idDigitado, agendamento, cliente, carro)) {
                        listarCarro(carro, cliente);
                        listarCliente(cliente);
                        listagemEscolhida(cliente, carro, agendamento);

                        boolean respostaValida = false;
                        while (!respostaValida) {
                            System.out.print("\nDeseja consultar outro agendamento? (S/N): ");
                            String resp = scanner.next().trim().toUpperCase();

                            if (resp.equals("S")) {
                                respostaValida = true;
                            } else if (resp.equals("N")) {
                                continuar = false; // Sai do loop se o usuário não quiser mais consultar
                                respostaValida = true;
                            } else {
                                System.out.println("Entrada inválida! Por favor, digite apenas 'S' para Sim ou 'N' para Não.");
                            }
                        }
                            } else {
                            System.out.println("ID não encontrado. Por favor, escolha um ID válido.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada Inválida");
                        scanner.next();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInsira um valor válido!");
                scanner.next();
            }
        } while (continuar);
    }

    public static void listagemDeAgendas(Cliente cliente, Agendamento agendamento) {
        System.out.println("\n========================== LISTA DE AGENDAMENTOS ===========================");
        System.out.format("+----+-------------+-----------+-----------------------+------------------+%n");
        System.out.format("| ID |     CPF     |   PLACA   |     DATA E HORÁRIO    |  DATA DE CRIACAO |%n");
        System.out.format("+----+-------------+-----------+-----------------------+------------------+%n");

        String rowFormat = "| %-2d | %-11s | %-9s | %-20s | %-16s |%n";
        for (Agendamento listagem : agendamentoDAO.getAgendaTodos()) {
            System.out.format(rowFormat,
                    listagem.getIdAgenda(),
                    listagem.getCpf(),
                    listagem.getPlaca(),
                    listagem.getDataAgendada(),
                    listagem.getDataCriacao());
            System.out.format("+----+-------------+-----------+-----------------------+------------------+%n");
        }
    }

    public static boolean verificarId(int id, Agendamento agendamento, Cliente cliente, Carro carro) {
        for (Agendamento a : agendamentoDAO.getAgendaTodos()) {
            if (a.getIdAgenda() == id) {
                agendamento.setIdAgenda(a.getIdAgenda());
                agendamento.setDataAgendada(a.getDataAgendada());
                agendamento.setCarroDescricao(a.getCarroDescricao());
                cliente.setCpf_cliente(a.getCpf());
                carro.setPlaca(a.getPlaca());

                return true;
            }
        }
        return false;
    }

    public static boolean apagarAgenda(int id) {
        for (Agendamento a : agendamentoDAO.getAgendaTodos()) {
            if (a.getIdAgenda() == id) {
                agendamentoDAO.deletarAgendamentoPorId(a.getIdAgenda());
                return true;
            }
        }
        return false;
    }

    public static void listarCarro(Carro carro, Cliente cliente) {
        for (Carro c : carroDAO.getCarrosCliente(cliente.getCpf_cliente())) {
            if (Objects.equals(carro.getPlaca(), c.getPlaca())) {
                carro.setPlaca(c.getPlaca());
                carro.setMarca(c.getMarca());
                carro.setModelo(c.getModelo());
                carro.setAno(c.getAno());
            }
        }
    }

    public static void listarCliente(Cliente cliente) {
        for (Cliente c : clienteDAO.getClientes()) {
            if (Objects.equals(cliente.getCpf_cliente(), c.getCpf_cliente())) {
                cliente.setNome_cliente(c.getNome_cliente());
            }
        }
    }

    public static void listagemEscolhida(Cliente cliente, Carro carro, Agendamento agendamento) {
        // Exibindo detalhes do agendamento em formato de tópicos
        System.out.println("\n======================== DETALHES DO AGENDAMENTO ========================");
        System.out.println("ID do Agendamento: " + agendamento.getIdAgenda());
        System.out.println("Data e Horário Escolhido: " + agendamento.getDataAgendada());
        System.out.println("Placa do Carro: " + carro.getPlaca());
        System.out.println("Marca do Carro: " + carro.getMarca());
        System.out.println("Modelo do Carro: " + carro.getModelo());
        System.out.println("Ano do Carro: " + carro.getAno());
        System.out.println("Defeito: " + agendamento.getCarroDescricao());
        System.out.println("Nome do Cliente: " + cliente.getNome_cliente());
        System.out.println("CPF do Cliente: " + cliente.getCpf_cliente());
        System.out.println("===========================================================================");
    }
}
