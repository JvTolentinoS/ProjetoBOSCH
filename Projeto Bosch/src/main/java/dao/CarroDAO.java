//COMPLETO

package dao;

import conexaofabrica.Conexao;
import entidade.Carro;
import entidade.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CarroDAO {
    public void salvar(Carro carro, Cliente cliente) {
        String sql = "INSERT INTO carro (carro_placa, carro_modelo, carro_marca, carro_ano, cpf) VALUE (?, ?, ?, ?, ?)";

        Connection conn = null;

        PreparedStatement pstm = null;

        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setString(1, carro.getPlaca());
            pstm.setString(2, carro.getModelo());
            pstm.setString(3, carro.getMarca());
            pstm.setString(4, carro.getAno());
            pstm.setString(5, cliente.getCpf_cliente());

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

    public void atualizar(Carro carro, Cliente cliente) {
        String sql = "UPDATE carro SET carro_modelo = ?, carro_marca = ?, carro_ano = ? WHERE carro_placa = ?";

        Connection conn = null;

        PreparedStatement pstm = null;

        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setString(1, carro.getModelo());
            pstm.setString(2, carro.getMarca());
            pstm.setString(3, carro.getAno());
            pstm.setString(4, carro.getPlaca());

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

    public void deletar(Carro carro, Cliente cliente, String cpf) {
        String sql = "DELETE FROM carro WHERE cpf = ?";

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

    public List<Carro> getCarrosCliente(String cpf) {
        String sql = "SELECT * FROM carro WHERE cpf = ?";
        List<Carro> carros = new ArrayList<Carro>();

        Connection conn = null;

        PreparedStatement pstm = null;

        ResultSet rset = null;
        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setString(1, cpf);

            rset = pstm.executeQuery();
            while (rset.next()) {
                Carro carro = new Carro();

                carro.setCpf(rset.getString("cpf"));
                carro.setPlaca(rset.getString("carro_placa"));
                carro.setModelo(rset.getString("carro_modelo"));
                carro.setMarca(rset.getString("carro_marca"));
                carro.setAno(rset.getString("carro_ano"));

                carros.add(carro);
            }
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
                if (rset!=null) {
                    rset.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return carros;
        }
    }

    public List<Carro> getCarros() {
        String sql = "SELECT * FROM carro";
        List<Carro> carros = new ArrayList<Carro>();

        Connection conn = null;

        PreparedStatement pstm = null;

        ResultSet rset = null;
        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rset = pstm.executeQuery();
        while (rset.next()) {
            Carro carro = new Carro();

            carro.setCpf(rset.getString("cpf"));
            carro.setPlaca(rset.getString("carro_placa"));
            carro.setModelo(rset.getString("carro_modelo"));
            carro.setMarca(rset.getString("carro_marca"));
            carro.setAno(rset.getString("carro_ano"));

            carros.add(carro);
        }
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
                if (rset!=null) {
                    rset.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return carros;
        }
    }
}


