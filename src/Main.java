import entidade.*;
import dao.*;

public class Main {
    public static void main(String[] args) {

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = new Cliente();
        cliente.setNome_cliente("Ricardo");
        cliente.setTelefone_cliente("30033003");
        cliente.setEmail_cliente("Ricardo@gmail.com");
        clienteDAO.salvar(cliente);
    }
}

/*for (Pedido p : pedidoDAO.getPedidosUsuario(cliente.getId_cliente())) {
            System.out.println("Pedido: " + p.getPlaca() + " " + p.getDataCriacao());
        }*/