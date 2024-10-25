package dao;

import conexaofabrica.Conexao;
import entidade.Cliente;
import entidade.Pedido;
import entidade.Carro;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class PedidoDAO {
    public void salvar(Pedido pedido, Cliente cliente, Carro carro) {
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
}
