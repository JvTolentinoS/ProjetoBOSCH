import conexaofabrica.Conexao;
import interfaceCliente.*;

import java.sql.Connection;

import static conexaofabrica.Conexao.criarConexao;

public class App {
    static ClienteServico clienteServico = new ClienteServico();
    static InterfaceService instancia = new InterfaceService();
    public static void main(String[] args) throws Exception {
        try {
        Connection con = criarConexao();
        if (con!=null) {
            System.out.println("Conexão obtida com sucesso");
            System.out.println(con);
            con.close();
                InterfaceService service = new InterfaceService();
                service.initialInterface(clienteServico);
        }

        } catch (Exception e) {
            System.out.println("Erro ao Conectar");
            System.exit(0);
        }
    }
}
