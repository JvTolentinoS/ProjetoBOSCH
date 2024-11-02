package dao;

import conexaofabrica.Conexao;
import entidade.Carro;
import entidade.Cliente;
import entidade.Agendamento;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    //CRIA UM AGENDAMENTO (CPF)
    public void salvar(Agendamento agendamento, Cliente cliente, Carro carro, int dia, int hora) {
        String sql = "INSERT INTO agenda (cpf, data_criacao, data_agendada, carro_placa, carro_descricao) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setString(1, cliente.getCpf_cliente());
            //DATA QUE VAI SER DEFINDA PARA CONSULTA
            LocalDateTime dataPrevista = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), dia, hora,0);
            pstm.setDate(2, new Date(agendamento.getDataCriacao().getTime()));
            pstm.setTimestamp(3, Timestamp.valueOf(dataPrevista));
            //
            pstm.setString(4, carro.getPlaca());
            pstm.setString(5, agendamento.getCarroDescricao());

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

    // ATUALIZA A DATA AGENDADA E OPCIONALMENTE A PLACA DO CARRO
    public void atualizar(Agendamento agendamento, int mes, int dia, int hora) {

        String sql = "UPDATE agenda SET data_agendada = ? WHERE cpf = ? AND carro_placa = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            //DATA QUE VAI SER DEFINDA PARA CONSULTA

            LocalDateTime dataPrevista = LocalDateTime.of(LocalDate.now().getYear(), mes, dia, hora,0);
            pstm.setTimestamp(1, Timestamp.valueOf(dataPrevista));
            pstm.setString(2, agendamento.getCpf());
            pstm.setString(3, agendamento.getPlaca());
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

    // DELETA O AGENDAMENTO COM BASE NO ID DA AGENDA
    public void deletarCPF (String cpf) {
        String sql = "DELETE FROM agenda WHERE cpf = ?";

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

    // DELETA O AGENDAMENTO COM BASE NA PLACA
    public void deletarPlaca (String placa) {
        String sql = "DELETE FROM agenda WHERE carro_placa = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setString(1, placa);
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

    //APENAS PARA O ADMINISTRADOR
    public List<Agendamento> getAgendaTodos()   {
        String sql = "SELECT * FROM agenda";

        List<Agendamento> agendamentos = new ArrayList<Agendamento>();

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {
            conn = Conexao.criarConexao();

            pstm = conn.prepareStatement(sql);

            rset = pstm.executeQuery();

            while (rset.next()) {
                Agendamento agendamento = new Agendamento();

                agendamento.setCpf(rset.getString("cpf"));
                agendamento.setDataCriacao(rset.getDate("data_criacao"));
                agendamento.setDataAgendada(rset.getTimestamp("data_agendada"));

                agendamentos.add(agendamento);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Falha na Leitura das Agendas");
        } finally {
            try {

                if (rset!=null) {rset.close();}
                if (pstm!=null) {pstm.close();}
                if (conn!=null) {conn.close();}

            } catch (Exception e) {
                e.printStackTrace();
            }
            return agendamentos;
        }
    }

    //APENAS USUÁRIOS
    public List<Agendamento> getAgendaUsuario(String cpf) {
        String sql = "SELECT * FROM agenda WHERE cpf = ?";

        List<Agendamento> agendamentos = new ArrayList<Agendamento>();

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {

            conn = Conexao.criarConexao();

            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setString(1, cpf);

            rset = pstm.executeQuery();
            while (rset.next()) {
                Agendamento agendamento = new Agendamento();

                agendamento.setCpf(rset.getString("cpf"));
                agendamento.setDataCriacao(rset.getDate("data_criacao"));
                agendamento.setDataAgendada(rset.getTimestamp("data_agendada"));

                agendamentos.add(agendamento);
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
            return agendamentos;
        }
    }
}

