import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {

    private static final String API_KEY = "64f42b009f38f08bf5f756bf";
    private static final String BASE_URL =
            "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== CONVERSOR DE MONEDA ===");
            System.out.println("1. Dólar => Peso argentino");
            System.out.println("2. Peso argentino => Dólar");
            System.out.println("3. Dólar => Real brasileño");
            System.out.println("4. Real brasileño => Dólar");
            System.out.println("5. Dólar => Peso colombiano");
            System.out.println("6. Peso colombiano => Dólar");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            if (opcion >= 1 && opcion <= 6) {
                System.out.print("Ingresa el monto a convertir: ");
                double monto = scanner.nextDouble();

                switch (opcion) {
                    case 1 -> convertir("USD", "ARS", monto);
                    case 2 -> convertir("ARS", "USD", monto);
                    case 3 -> convertir("USD", "BRL", monto);
                    case 4 -> convertir("BRL", "USD", monto);
                    case 5 -> convertir("USD", "COP", monto);
                    case 6 -> convertir("COP", "USD", monto);

                }
            } else if (opcion != 7) {
                System.out.println("Opción inválida");
            }

        } while (opcion != 7);

        System.out.println("Gracias por usar el conversor :)");
        scanner.close();
    }

    // MÉTODO DE CONVERSIÓN
    private static void convertir(String from, String to, double monto) {
        try {
            String urlStr = BASE_URL + from + "/" + to + "/" + monto;
            URL url = new URL(urlStr);

            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conexion.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
            double resultado = json.get("conversion_result").getAsDouble();

            System.out.println("\nEl valor " + monto + " " + from +
                    " corresponde al valor de " + resultado + " " + to);

        } catch (Exception e) {
            System.out.println("Error al realizar la conversión");
            e.printStackTrace();
        }
    }
}
