import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConversorMonedas {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/4c0da43a15f2bc75f1e8b2cf/latest/USD";

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner teclado = new Scanner(System.in);
        Map<String, Double> rates = tasaDeConversion();

        if (rates == null) {
            return;
        }


        while (true) {
            System.out.println("Bienvenido al Conversor de Monedas");
            System.out.println("Seleccione la moneda a convertir:");
            System.out.println("1. USD a ARS");
            System.out.println("2. USD a BOB");
            System.out.println("3. USD a BRL");
            System.out.println("4. USD a CLP");
            System.out.println("5. USD a COP");
            System.out.println("6. Salir");

            int opcion = teclado.nextInt();
            if (opcion == 6) {
                break;
            }

            System.out.println("Introduce la cantidad en USD:");
            double cantidad = teclado.nextDouble();

            double resultado = 0;
            switch (opcion) {
                case 1:
                    resultado = convertirMoneda(cantidad, rates.get("ARS"));
                    System.out.println(cantidad + " USD son " + resultado + " ARS");
                    break;
                case 2:
                    resultado = convertirMoneda(cantidad, rates.get("BOB"));
                    System.out.println(cantidad + " USD son " + resultado + " BOB");
                    break;
                case 3:
                    resultado = convertirMoneda(cantidad, rates.get("BRL"));
                    System.out.println(cantidad + " USD son " + resultado + " BRL");
                    break;
                case 4:
                    resultado = convertirMoneda(cantidad, rates.get("CLP"));
                    System.out.println(cantidad + " USD son " + resultado + " CLP");
                    break;
                case 5:
                    resultado = convertirMoneda(cantidad, rates.get("COP"));
                    System.out.println(cantidad + " USD son " + resultado + " COP");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }


    public static Map<String, Double> tasaDeConversion() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(API_URL)).build();

        HttpResponse<String> Response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        JsonObject jsonObject = JsonParser.parseString(Response.body()).getAsJsonObject();
        JsonObject ratesDeConversion = jsonObject.getAsJsonObject("conversion_rates");

        Gson gson = new Gson();
        Map<String, Double> allRates = gson.fromJson(ratesDeConversion, Map.class);
        return allRates.entrySet().stream()
                .filter(entry -> entry.getKey().equals("ARS") ||
                        entry.getKey().equals("BOB") ||
                        entry.getKey().equals("BRL") ||
                        entry.getKey().equals("CLP") ||
                        entry.getKey().equals("COP"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static double convertirMoneda(double cantidad, Double tasaCambio){
        if(tasaCambio == null){
            return 0;
        }
        return cantidad * tasaCambio;

    }


}
