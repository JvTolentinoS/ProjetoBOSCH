package entidade;

import java.util.Date;

public class Pedido {

    //CHAVES ESTRANGEIRAS
    private int id_cliente;
    private String placa;
    //
    private int id_pedido;
    private Date dataEntrega;
    private Date dataPrevista;
    private Date dataCriacao;
    private String status;
    private String descricao_pedido;

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public Date getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(Date dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getDescricao_pedido() {
        return descricao_pedido;
    }

    public void setDescricao_pedido(String descricao_pedido) {
        this.descricao_pedido = descricao_pedido;
    }
}
