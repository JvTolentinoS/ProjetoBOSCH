package interfaceCliente;

import dao.AgendamentoDAO;
import entidade.Agendamento;
import entidade.Carro;
import entidade.Cliente;

import java.util.Date;

public class TestService {

        public static void main(String[] args) {
            Carro carro = new Carro();
            Cliente cliente = new Cliente();
            Agendamento agendamento = new Agendamento();
            AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
            agendamento.setCpf("0000000");
            agendamento.setPlaca("placa");
            agendamento.setDataCriacao(new Date());
            agendamentoDAO.salvar(agendamento, cliente, carro, 9,0);
        }
    }
