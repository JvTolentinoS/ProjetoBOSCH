package conexaofabrica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    //CREDENCIAIS
    private static final String USERNAME = "root";
    private static final String SENHA = "jvts2003";

    //CAMINHO
    private  static final String URL = "jdbc:mysql://localhost:3306/boschbd";

    public static Connection criarConexao() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection(URL, USERNAME, SENHA);

        return connection;
    }

    public static void main(String[] args) throws Exception{

        Connection con = criarConexao();

        if (con!=null) {
            System.out.println("Conexão obtida com sucesso");
            con.close();
        }
    }
}
