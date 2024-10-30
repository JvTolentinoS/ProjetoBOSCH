// COMPLETO

package dao;

import conexaofabrica.Conexao;
import entidade.Cliente;
import entidade.Agendamento;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    //CRIA UM AGENDAMENTO (CPF)
    public void salvar(Agendamento agendamento, Cliente cliente, int ano, int mes, int dia) {
        String sql = "INSERT INTO agenda(cpf, data_criacao, data_agendada) VALUES (?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setString(1, cliente.getCpf_cliente());
            pstm.setDate(2, new Date(agendamento.getDataCriacao().getTime()));

            //DATA QUE VAI SER DEFINDA PARA CONSULTA
            LocalDate dataPrevista = LocalDate.of(ano, mes, dia);
            pstm.setDate(3, Date.valueOf(dataPrevista));
            //
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
    public void atualizar(Agendamento agendamento, int ano, int mes, int dia) {

        String sql = "UPDATE agenda SET data_agendada = ? WHERE cpf = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = Conexao.criarConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            //DATA QUE VAI SER DEFINDA PARA CONSULTA
            LocalDate dataPrevista = LocalDate.of(ano, mes, dia);
            pstm.setDate(1, Date.valueOf(dataPrevista));
            //
            pstm.setString(2, agendamento.getCpf());
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
    public void deletar (String cpf) {
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

    //APENAS PARA O ADMINISTRADOR
    public List<Agendamento> getAgendaTodos() {
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
                agendamento.setDataAgendada(rset.getDate("data_agendada"));


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
                agendamento.setDataAgendada(rset.getDate("data_agendada"));

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

