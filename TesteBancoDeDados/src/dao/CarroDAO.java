package dao;

import conexaofabrica.Conexao;
import entidade.Carro;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CarroDAO {
    public void salvar(Carro carro) {
        String sql = "INSERT INTO carro(placa, modelo, marca, ano) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setString(1, carro.getPlaca());
            pstm.setString(2,carro.getModelo());
            pstm.setString(3,carro.getMarca());
            pstm.setInt(4, carro.getAno());

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
