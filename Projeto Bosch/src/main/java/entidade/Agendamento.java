package entidade;

import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;

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

    // CHAVES ESTRANGEIRAS
    private String cpf;
    //

    private Date dataCriacao;
    private Date dataAgendada;


    //
    private ArrayList<String> horariosDisponiveis;
    private String horarioEscolhido;

    public Agendamento() {
        horariosDisponiveis = new ArrayList<>();
        inicializarHorarios();
    }

    private void inicializarHorarios() {
        horariosDisponiveis.add("28/10/2024 - 08:00");
        horariosDisponiveis.add("28/10/2024 - 09:00");
        horariosDisponiveis.add("28/10/2024 - 10:00");
        horariosDisponiveis.add("28/10/2024 - 11:00");
        horariosDisponiveis.add("28/10/2024 - 12:00");
    }

    public void exibirHorariosDisponiveis() {
        System.out.println("Horários disponíveis:");
        for (int i = 0; i < horariosDisponiveis.size(); i++) {
            System.out.println((i + 1) + ". " + horariosDisponiveis.get(i));
        }
    }

    public void escolherHorario() {
        Scanner scanner = new Scanner(System.in);
        exibirHorariosDisponiveis();

        System.out.print("Escolha um horário (digite o número correspondente): ");
        int escolha = scanner.nextInt();

        if (escolha > 0 && escolha <= horariosDisponiveis.size()) {
            horarioEscolhido = horariosDisponiveis.get(escolha - 1);
            System.out.println("Horário escolhido: " + horarioEscolhido);
            horariosDisponiveis.remove(escolha - 1);
        } else {
            System.out.println("Opção inválida! Tente novamente.");
            escolherHorario();
        }
    }

    public String getHorarioEscolhido() {
        return horarioEscolhido;
    }

    public static void main(String[] args) {
        Agendamento agendamento = new Agendamento();
        agendamento.escolherHorario();
        System.out.println("Seu agendamento foi confirmado para: " + agendamento.getHorarioEscolhido());
    }
}






















