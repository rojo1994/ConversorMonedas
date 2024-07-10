import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConversorMonedas {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/4c0da43a15f2bc75f1e8b2cf/latest/USD";

    public static void main(String[] args) {

    }


    public void api() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(API_URL)).build();

        HttpResponse<String> Response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    }


}
