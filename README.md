# Conversor de Moedas
Desafio Oracle & Alura

Desenvolva um conversor de moedas utilizando API de taxas de câmbio para converter valores entre diferentes moedas. 
O projeto permite a conversão de várias moedas para o Real (BRL) e aceita entradas numéricas de forma flexível.

## Funcionalidades

- Ao solicitar a moeda desejada e informar o valor, será calculado o montante necessário em Reais equivalente a moeda escolhida.
- Moedas disponiveis:
  - Dólar Americano (USD)
  - Euro (EUR)
  - Libra Esterlina (GBP)
  - Dólar Canadense (CAD)
  - Peso Argentino (ARS)
  - Guarani (PYG)
- Aceita entradas numéricas com ponto ou vírgula como separador decimal.
- Limita a cinco tentativas de conversão com sucesso ou três tentativas inválidas.

## Tecnologias Utilizadas

- Java 21
- Gson para manipulação de JSON
- API de taxas de câmbio para conversão de moedas https://www.exchangerate-api.com/
- IntelliJ Idea Community Edition

## Como Usar

1. Clone este repositório.
2. Abra o projeto em sua IDE Java favorita (ex: IntelliJ IDEA).
3. Adicione sua chave de API em um arquivo `config.properties` na pasta `resources`.
   - O arquivo deve ter o seguinte formato:
     ```
     apiKey=SUACHAVEDEAPI
     ```
4. Execute a classe `ConversorComPesquisa.java`.
5. Siga as instruções no console para selecionar a moeda e inserir o valor desejado.

## Exemplo de Uso

Após a seleção, insira o valor desejado na moeda selecionada, e o programa retornará o valor correspondente em Reais (BRL).

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests.

## Autor: Bruno Longo
