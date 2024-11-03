package entidade;

import java.util.Date;

public class Agendamento {



    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(Date dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCarroDescricao() {
        return carroDescricao;
    }

    public void setCarroDescricao(String carroDescricao) {
        this.carroDescricao = carroDescricao;
    }

    // CHAVES ESTRANGEIRAS
    private String cpf;
    private String placa;
    //
    private Date dataCriacao;
    private Date dataAgendada;
    //
    private String carroDescricao;

}






















