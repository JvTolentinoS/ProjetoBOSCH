// FUNCIONANDO NORMAL

package dao;

import conexaofabrica.Conexao;
import entidade.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void salvar (Cliente cliente) {
        String sql = "INSERT INTO cliente(nome, cpf) VALUES (?,?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {

            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setString(1, cliente.getNome_cliente());
            pstm.setString(2, cliente.getCpf_cliente());

            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn!=null) {conn.close();}
                if (pstm!=null) {pstm.close();}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void atualizar (Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?" + "WHERE cpf = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, cliente.getNome_cliente());

            //LOCALIZADOR
            pstm.setString(2, cliente.getCpf_cliente());
            pstm.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            if (pstm!=null) {pstm.close();}
            if (conn!=null) {conn.close();}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void deletar (String cpf) {
        String sql = "DELETE FROM cliente WHERE cpf = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setString(1, cpf);
            pstm.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn!=null) {conn.close();}
                if (pstm!=null) {pstm.close();}
            } catch (Exception e) {
            e.printStackTrace();
            }
        }
    }

    public List<Cliente> getClientes()  {
        String sql = "SELECT * FROM cliente";
        List<Cliente> clientes = new ArrayList<Cliente>();

        Connection conn = null;

        PreparedStatement pstm = null;

        ResultSet rset = null;

        try {
            conn = Conexao.criarConexao();

            pstm = conn.prepareStatement(sql);

            rset = pstm.executeQuery();

            while (rset.next()) {
                Cliente cliente = new Cliente();

                cliente.setCpf_cliente(rset.getString("cpf"));
                cliente.setNome_cliente(rset.getString("nome"));

                clientes.add(cliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Falha na Leitura dos Clientes");
        } finally {
            try {

            if (rset!=null) {rset.close();}
            if (pstm!=null) {pstm.close();}
            if (conn!=null) {conn.close();}

            } catch (Exception e) {
                e.printStackTrace();
            }

            return clientes;
        }
    }

}
