import entidade.Agendamento;
import java.util.Scanner;
import dao.AgendamentoDAO;
import dao.CarroDAO;
import dao.ClienteDAO;
import entidade.Carro;
import entidade.Cliente;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int escolha;

        do {
            System.out.println("Bem-Vindo(a) ao Bosch Car Service!");
            System.out.println("Esse é um canal oficial da Bosch Car Service. Para mais informações, siga nossas redes sociais!");
            System.out.println("Selecione entre as opções da lista, qual serviço deseja receber: \n\n\n");
        } while (false);

        System.out.println("1. Agendar um atendimento\n2. Alterar um agendamento\n3. Cancelar um agendamento\n");
        escolha = scanner.nextInt();
        if (escolha == 1) {
            Agendamento agendamento = new Agendamento();
            agendamento.criarAgendamento();
            agendamento.exibirHorariosDisponiveis();
        }
    }
}


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