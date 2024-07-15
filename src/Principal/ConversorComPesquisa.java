package Principal;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.net.http.HttpClient.newHttpClient;

public class ConversorComPesquisa {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Carregar a configuração
        Config config = new Config();
        String apiKey = config.getApiKey();
        Scanner leitura = new Scanner(System.in);

        int tentativasInvalidas = 0; // Contador de tentativas inválidas
        int conversoesRealizadas = 0; // Contador de conversões bem-sucedidas

        while (tentativasInvalidas < 5 && conversoesRealizadas < 5) { // Limites de tentativas
            System.out.println("Olá, bem-vindo ao conversor de moeda.");
            System.out.println("Selecione a opção desejada para consultar o valor necessário em Reais:");
            System.out.println("1. Dólar Americano (USD)");
            System.out.println("2. Euro (EUR)");
            System.out.println("3. Libra Esterlina (GBP)");
            System.out.println("4. Dólar Canadense (CAD)");
            System.out.println("5. Peso Argentino (ARS)");
            System.out.println("6. Guarani (PYG)");
            System.out.println("0. Sair");

            int opcao = -1; // Inicializando com um valor padrão

            try {
                opcao = leitura.nextInt();
                if (opcao < 0 || opcao > 6) {
                    System.out.println("Opção inválida. Tente novamente.");
                    tentativasInvalidas++;
                    continue; // Volta ao início do loop
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, digite um número válido.");
                leitura.next(); // Limpa o buffer
                tentativasInvalidas++;
                continue; // Volta ao início do loop
            }

            if (opcao == 0) {
                System.out.println("Grato. Até breve!");
                break; // Sai do loop
            }

            System.out.println("Digite o valor desejado na moeda de destino: ");
            double quantiaDesejada;
            try {
                String input = leitura.next(); // Lê a entrada como string
                input = input.replace(',', '.'); // Substitui vírgula por ponto
                quantiaDesejada = Double.parseDouble(input); // Tenta converter para double
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite conforme o exemplo 1000,23.");
                continue; // Volta ao início do loop
            }

            String moedaOrigem = "";

            switch (opcao) {
                case 1: moedaOrigem = "USD"; break;
                case 2: moedaOrigem = "EUR"; break;
                case 3: moedaOrigem = "GBP"; break;
                case 4: moedaOrigem = "CAD"; break;
                case 5: moedaOrigem = "ARS"; break;
                case 6: moedaOrigem = "PYG"; break;
                default:
                    System.out.println("Opção inválida.");
                    tentativasInvalidas++;
                    continue; // Volta ao início do loop
            }

            String consulta = String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/BRL", apiKey, moedaOrigem);

            HttpClient client = newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(consulta))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            // System.out.println("Resposta da API: " + json); // Remover ou comentar esta linha

            Gson gson = new Gson();
            Moeda moedaDesejada = gson.fromJson(json, Moeda.class);

            if ("success".equals(moedaDesejada.getResult())) {
                double taxaDeConversao = moedaDesejada.getConversion_rate();
                double valorConvertido = quantiaDesejada * taxaDeConversao; // Multiplicar para obter o valor em BRL
                System.out.printf("Para obter o montante de %.2f %s será necessário R$ %.2f\n", quantiaDesejada, moedaOrigem, valorConvertido);
                conversoesRealizadas++; // Incrementa contador de conversões
            } else {
                System.out.println("Não foi possível obter a taxa de conversão.");
            }
        }

        if (tentativasInvalidas == 3) {
            System.out.println("Desculpe. Opção inválida. Até breve!");
        } else if (conversoesRealizadas == 5) {
            System.out.println("Limite de conversões atingido. Grato.");
        }

        leitura.close(); // Fecha o scanner ao final
    }
}
