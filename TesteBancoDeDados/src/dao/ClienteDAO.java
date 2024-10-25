    package dao;

import conexaofabrica.Conexao;
import entidade.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    public void salvar (Cliente cliente) {
        String sql = "INSERT INTO cliente(nome, email, telefone) VALUES (?,?,?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, cliente.getNome_cliente());
            pstm.setString(2, cliente.getEmail_cliente());
            pstm.setString(3, cliente.getTelefone_cliente());

            pstm.execute();

            try (ResultSet generatedKeys = pstm.getGeneratedKeys()){
                if (generatedKeys.next()) {
                    cliente.setId_cliente(generatedKeys.getInt(1));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm!=null) {
                    pstm.close();
                }

                if (conn!=null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void atualizar (Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, email = ?, telefone = ?" + "WHERE idcliente = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, cliente.getNome_cliente());
            pstm.setString(2, cliente.getEmail_cliente());
            pstm.setString(3, cliente.getTelefone_cliente());

            //LOCALIZADOR
            pstm.setInt(4, cliente.getId_cliente());
            pstm.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            if (pstm!=null) {
                pstm.close();
            }
            if (conn!=null) {
                conn.close();
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void deletar (int id) {
        String sql = "DELELTE FROM cliente WHERE idcliente = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setInt(1, id);
            pstm.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            if (conn!=null) {
            conn.close();
            }
            if (pstm!=null) {
            pstm.close();
                }
            } catch (Exception e) {
            e.printStackTrace();
            }
        }
    }

    public List<Cliente> getClientes() {
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

                cliente.setId_cliente(rset.getInt("idcliente"));
                cliente.setNome_cliente(rset.getString("nome"));
                cliente.setEmail_cliente((rset.getString("email")));
                cliente.setTelefone_cliente(rset.getString("telefone"));

                clientes.add(cliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Falha na Leitura");
        } finally {
            try {

            if (rset!=null) {
                rset.close();
            }
            if (pstm!=null) {
                pstm.close();
            }
            if (conn!=null) {
                conn.close();
            }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return clientes;
        }
    }

}
