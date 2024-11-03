package conexaofabrica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//FABRICA DE CONEXÕES | TESTE
public class Conexao {

    //CREDENCIAIS
    private static final String USERNAME = "root";
    private static final String SENHA = "root";

    //CAMINHO
    private static final String URL = "jdbc:mysql://34.151.249.135:3306/boschbd";

    public static Connection criarConexao() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(URL, USERNAME, SENHA);

            return connection;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception{

        Connection con = criarConexao();


        if (con!=null) {
            System.out.println("Conexão obtida com sucesso");
            System.out.println(con);
            con.close();
        }
    }
}
