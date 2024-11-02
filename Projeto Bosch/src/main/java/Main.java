import interfaceGeral.*;
import jnr.ffi.annotations.In;

public class Main {
    static ClienteService clienteService = new ClienteService();
    static InterfaceService instancia = new InterfaceService();
    public static void main(String[] args) {
        InterfaceService service = new InterfaceService();
        service.initialInterface(clienteService);
    }


}

//        System.out.println("1. Agendar um atendimento\n2. Alterar um agendamento\n3. Cancelar um agendamento\n");
//        escolha = scanner.nextInt();
//        if (escolha == 1) {
//            Agendamento agendamento = new Agendamento();
//            agendamento.criarAgendamento();
//            agendamento.exibirHorariosDisponiveis();
//        }

//  for (Carro c : carroDAO.getCarros()) {
//            System.out.println("Carro: " +c.getPlaca());
//        }
//
//  for (Agendamento a : agendamentoDAO.getAgendaTodos()) {
//        System.out.println("Agenda: " + a.getDataCriacao() + " " + a.getDataAgendada() + " " + a.getCpf());
//        }
//
//  for (Cliente c : clienteDAO.getClientes()) {
//            System.out.println("Cliente: " + c.getNome_cliente() + " " + c.getCpf_cliente());
//        }