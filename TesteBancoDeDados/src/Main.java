import entidade.*;
import dao.*;

public class Main {
    public static void main(String[] args) {

        ClienteDAO clienteDAO = new ClienteDAO();

        Cliente cliente = new Cliente();
        cliente.setNome_cliente("Jão");
        cliente.setEmail_cliente("Jão@gmail.com");
        cliente.setTelefone_cliente("0000-0000");

        clienteDAO.salvar(cliente);


    }
}
