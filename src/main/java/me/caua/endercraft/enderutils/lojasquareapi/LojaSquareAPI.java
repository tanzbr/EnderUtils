package me.caua.endercraft.enderutils.lojasquareapi;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LojaSquareAPI {

    public static String url = "https://api.lojasquare.net/v1";
    public static String requestBody = "{\"key\": \"value\"}";
    public static String requestHeader = "Authorization: Token token_value";

    public static String createCheckout(int idProduto, int quantity, String player, String cpf, String email) {
        


        return null;
    }


    public static String sendPostRequest(String url, String requestBody, String requestHeader) throws IOException, InterruptedException, IOException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("User-Agent", "Java HTTP Client")
                .header("Accept", "application/json")
                .header("Authorization", requestHeader)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = httpResponse.statusCode();
        HttpHeaders headers = httpResponse.headers();

        return httpResponse.body();
    }

}
