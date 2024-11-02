package interfaceGeral;

import dao.AgendamentoDAO;
import dao.CarroDAO;
import dao.ClienteDAO;
import entidade.Agendamento;
import entidade.Carro;
import entidade.Cliente;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main2 extends ClienteService {
    static ClienteService clienteServiceExtension = new ClienteService();
    public static void main(String[] args) {
        instrucaoAgendamento(carroDAO, carro, agendamento, agendamentoDAO, cliente);
    }

    public static void instrucaoAgendamento(CarroDAO carroDAO, Carro carro, Agendamento agendamento, AgendamentoDAO agendamentoDAO, Cliente cliente) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true; // Variável para controle do loop principal

        while (continuar) { // Loop principal para o processo de agendamento
            String placa;

            // EXIBE AS DATAS DISPONÍVEIS
            System.out.println("Selecione uma data disponível para agendamento");
            System.out.println("0. Voltar ao menu anterior");
            System.out.println("1. 04/11/2024\n2. 05/11/2024\n3. 06/11/2024\n4. 07/11/2024\n5. 08/11/2024");

            int opcaoData = -1;
            boolean entradaValida = false;

            while (!entradaValida) {
                try {
                    System.out.print("Escolha uma opção (0-5): ");
                    opcaoData = scanner.nextInt();
                    scanner.nextLine();
                    if (opcaoData < 0 || opcaoData > 5) {
                        throw new InputMismatchException(); // Lança exceção se a opção não estiver entre 1 e 5
                    }
                    entradaValida = true;
                } catch (InputMismatchException e) {
                    System.out.println("Opção inválida! Por favor, escolha uma das opções (0-5)");
                    scanner.nextLine(); // Limpa o buffer
                }
            }

            if (opcaoData == 0) {
                System.out.println("Voltando ao menu anterior...");
                clienteServiceExtension.instrucaoCarroExiste(carro);
            }

            // EXIBE OS HORÁRIOS DISPONÍVEIS COM BASE NA DATA ESCOLHIDA
            System.out.println("Selecione um horário disponível:");

            switch (opcaoData) {
                case 1:
                    System.out.println("1. 08:00\n2. 09:00\n3. 10:00\n4. 11:00\n5. 13:00\n6. 14:00\n7. 15:00\n8. 16:00\n9. 17:00\n10. 18:00\n11. 19:00");
                    break;
                case 2:
                    System.out.println("1. 08:00\n2. 09:00\n3. 10:00\n4. 11:00\n5. 13:00\n6. 14:00\n7. 15:00\n8. 16:00\n9. 17:00\n10. 18:00\n11. 19:00");
                    break;
                case 3:
                    System.out.println("1. 08:00\n2. 09:00\n3. 10:00\n4. 11:00\n5. 13:00\n6. 14:00\n7. 15:00\n8. 16:00\n9. 17:00\n10. 18:00\n11. 19:00");
                    break;
                case 4:
                    System.out.println("1. 08:00\n2. 09:00\n3. 10:00\n4. 11:00\n5. 13:00\n6. 14:00\n7. 15:00\n8. 16:00\n9. 17:00\n10. 18:00\n11. 19:00");
                    break;
                case 5:
                    System.out.println("1. 08:00\n2. 09:00\n3. 10:00\n4. 11:00\n5. 13:00\n6. 14:00\n7. 15:00\n8. 16:00\n9. 17:00\n10. 18:00\n11. 19:00");
                    break;
                default:
                    System.out.println("Opção inválida! Reiniciando a seleção...");
                    continue; // Reinicia o loop se chegar a esse ponto
            }

            // Processa a seleção do horário
            int opcaoHorario = 0;
            entradaValida = false;

            while (!entradaValida) {
                try {
                    opcaoHorario = scanner.nextInt();
                    scanner.nextLine();
                    if (opcaoHorario < 1 || opcaoHorario > 11) {
                        throw new InputMismatchException(); // Verifica se a opção do horário é válida
                    }
                    entradaValida = true;
                } catch (InputMismatchException e) {
                    System.out.println("Opção inválida! Por favor, escolha um horário disponível (1-11)");
                    scanner.nextLine(); // Limpa o buffer
                }
            }

            System.out.println("Data escolhida: " + opcaoData);
            System.out.println("Horário escolhido: " + opcaoHorario);

            // Validação da placa do veículo
            while (true) {
                System.out.print("Digite a placa do veículo: ");
                placa = scanner.nextLine();

                // Remove o hífen para validação
                String placaSemHifen = removeHifen(placa);

                // Verifica se a placa sem hífen possui exatamente 7 caracteres,
                // sendo os 3 primeiros letras e os 4 últimos alfanuméricos
                if (placaSemHifen.length() == 7 &&
                        placaSemHifen.substring(0, 3).matches("[A-Za-z]{3}") &&
                        placaSemHifen.substring(3).matches("[A-Za-z0-9]{4}") &&
                        placaSemHifen.substring(3).matches(".*[0-9].*")) {
                    String placaCarro = placaSemHifen.toUpperCase();
                    for (Carro c : carroDAO.getCarros()) {
                        if (c.getPlaca().equals(placaCarro)) {
                            agendamento.setPlaca(carro.getPlaca());
                        }
                    }
                    System.out.println("Placa registrada: " + placaSemHifen);
                    break; // Sai do loop se a entrada estiver correta
                } else {
                    System.out.println("Placa inválida!");
                }
            }

            // SOLICITAR A DESCRIÇÃO DO DEFEITO
            System.out.print("Descreva o defeito do veículo: ");
            String defeito = scanner.nextLine();
            carro.setCarroDefeito(defeito);
            System.out.println("Defeito descrito: " + defeito);

            // CONFIRMAÇÃO DO AGENDAMENTO
            boolean confirmarAgendamento = false;
            while (!confirmarAgendamento) {
                System.out.print("Deseja confirmar o agendamento? Digite 0 para confirmar ou 1 para não prosseguir: ");
                int opcaoConfirmacao = scanner.nextInt();
                scanner.nextLine();

                if (opcaoConfirmacao == 0) {
                    System.out.println("Agendamento confirmado!");
                    confirmarAgendamento = true; // Finaliza a confirmação
                    continuar = false; // Finaliza o loop principal
                } else if (opcaoConfirmacao == 1) {
                    System.out.println("Agendamento não prosseguido. Reiniciando o processo...");
                    confirmarAgendamento = true; // Finaliza a confirmação
                    // O loop principal vai reiniciar
                } else {
                    System.out.println("Opção inválida! Por favor, digite 0 para confirmar ou 1 para não prosseguir.");
                }
            }
        }

        scanner.close(); // Fecha o scanner no final do processo
    }

    // METODO PARA REMOVER O HIFEN DA PLACA
    public static String removeHifen(String placa) {
        return placa.replace("-", "");
    }
}