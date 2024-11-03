import interfaceAdmin.AdminService;
import interfaceAdmin.InterfaceAdmin;

import java.sql.Connection;

import static conexaofabrica.Conexao.criarConexao;

public class App {
    static AdminService adminService = new AdminService();
    static InterfaceAdmin interfaceAdmin = new InterfaceAdmin();

    public static void main(String[] args) throws Exception {
        try {
        Connection con = criarConexao();
        if (con!=null) {
            System.out.println("Conexão obtida com sucesso");
            System.out.println(con);
            con.close();
                InterfaceAdmin.initialInterface(adminService);
        }

        } catch (Exception e) {
            System.out.println("Erro ao Conectar");
            System.exit(0);
        }
    }
}
