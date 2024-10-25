package dao;

import conexaofabrica.Conexao;
import entidade.Cliente;
import entidade.Pedido;
import entidade.Carro;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public void salvar(Pedido pedido, Cliente cliente, Carro carro)     {
        String sql = "INSERT INTO pedido(idcliente, placa, dataCriacao, descricao_pedido) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setInt(1, cliente.getId_cliente());
            pstm.setString(2, carro.getPlaca());
            pstm.setDate(3, new Date(pedido.getDataCriacao().getTime()));
            pstm.setString(4, pedido.getDescricao_pedido());

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

    public void atualizar(Pedido pedido, Cliente cliente, Carro carro,
                          int ano, int mes, int dia) {

        String sql = "UPDATE pedido SET dataEntrega = ?, dataPrevista = ?, status = ?" + "WHERE idpedido = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            //DATA NO QUAL O PEDIDO FOI ENTREGUE
            pstm.setDate(1, new Date(pedido.getDataEntrega().getTime()));
            //

            //DATA QUE VAI SER DEFINDA PARA ENTREGA
            LocalDate dataPrevista = LocalDate.of(ano, mes, dia);
            pstm.setDate(2, Date.valueOf(dataPrevista));
            //

            pstm.setString(3, pedido.getStatus());
            pstm.setInt(4, pedido.getId_pedido());

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
    public void deletar (int id) {
        String sql = "DELETE FROM pedido WHERE idpedido = ?";

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
                if (conn!=null) {conn.close();}
                if (pstm!=null) {pstm.close();}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Pedido> getPedidosTodos() {
        String sql = "SELECT * FROM pedido";

        List<Pedido> pedidos = new ArrayList<Pedido>();

        Connection conn = null;
        PreparedStatement pstm = null;

        ResultSet rset = null;

        try {
            conn = Conexao.criarConexao();

            pstm = conn.prepareStatement(sql);

            rset = pstm.executeQuery();

            while (rset.next()) {
                Pedido pedido = new Pedido();

                pedido.setId_pedido(rset.getInt("idcliente"));
                pedido.setPlaca(rset.getString("placa"));
                pedido.setId_cliente(rset.getInt("idcliente"));
                pedido.setDataCriacao(rset.getDate("dataCriacao"));
                pedido.setDataEntrega(rset.getDate("dataEntrega"));
                pedido.setDataPrevista(rset.getDate("dataPrevista"));
                pedido.setStatus(rset.getString("status"));
                pedido.setDescricao_pedido(rset.getString("descricao_pedido"));

                pedidos.add(pedido);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Falha na Leitura dos Pedidos");
        } finally {
            try {

                if (rset!=null) {rset.close();}
                if (pstm!=null) {pstm.close();}
                if (conn!=null) {conn.close();}

            } catch (Exception e) {
                e.printStackTrace();
            }
            return pedidos;
        }
    }

    public List<Pedido> getPedidosUsuario(int id) {
        String sql = "SELECT * FROM pedido "+
                "WHERE idcliente = ?";

        List<Pedido> pedidos = new ArrayList<Pedido>();

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {

            conn = Conexao.criarConexao();

            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setInt(1, id);

            rset = pstm.executeQuery();
            while (rset.next()) {
                Pedido pedido = new Pedido();

                pedido.setPlaca(rset.getString("placa"));
                pedido.setId_cliente(rset.getInt("idcliente"));
                pedido.setDataCriacao(rset.getDate("dataCriacao"));
                pedido.setDataEntrega(rset.getDate("dataEntrega"));
                pedido.setDataPrevista(rset.getDate("dataPrevista"));
                pedido.setStatus(rset.getString("status"));
                pedido.setDescricao_pedido(rset.getString("descricao_pedido"));

                pedidos.add(pedido);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Falha na Leitura dos Pedidos");
        } finally {
            try {
                if (conn!=null){
                    conn.close();
                }
                if (pstm!=null){
                    pstm.close();
                }
                if (rset!=null){
                    rset.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return pedidos;
        }
    }
}

