package modelo; // Pertenece al paquete modelo

// Importaciones para manejar peticiones HTTP, JSON y colecciones
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;          // Librería para convertir objetos Java a JSON y viceversa
import com.google.gson.JsonObject;    // Representa objetos JSON
import com.google.gson.JsonArray;     // Representa arrays JSON
import com.google.gson.JsonParser;    // Para parsear cadenas JSON

public class LlmService {
    // URL de la API del modelo de lenguaje (OpenRouter)
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";

    // Clave API para autenticarse en el servicio (debería mantenerse segura)
    private static final String API_KEY = "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";

    /*
    // Comentado: alternativa para cargar la API_KEY desde un archivo de configuración externo
    static {
        // Asegúrate de que el archivo config.properties esté bien configurado
        API_KEY = Config.get("openrouter.api.key");
    }
    */

    // Método que consulta al modelo de lenguaje con un prompt y devuelve la respuesta
    public static String consultarLLM(String prompt) {
        try {
            // Construir el cuerpo JSON para la petición, con el modelo y el mensaje
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "mistralai/mistral-7b-instruct:free");
            // El mensaje incluye el rol "user" y el contenido que es el prompt recibido
            requestBody.put("messages", List.of(Map.of("role", "user", "content", prompt)));

            // Convertir el mapa requestBody a JSON string usando Gson
            String jsonBody = new Gson().toJson(requestBody);

            // Crear la petición HTTP POST con la URL, cabeceras y cuerpo JSON
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY) // Autenticación con token Bearer
                .header("Content-Type", "application/json")  // Tipo de contenido JSON
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)) // Cuerpo de la petición
                .build();

            // Crear cliente HTTP para enviar la petición
            HttpClient client = HttpClient.newHttpClient();

            // Enviar la petición y obtener la respuesta como String
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parsear la respuesta JSON recibida para extraer la respuesta del modelo
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

            // Acceder al array "choices" que contiene posibles respuestas
            JsonArray choices = jsonResponse.getAsJsonArray("choices");

            // Tomar el primer elemento y obtener el contenido del mensaje
            String respuesta = choices.get(0).getAsJsonObject()
                                .getAsJsonObject("message")
                                .get("content").getAsString();

            // Devolver la respuesta limpia sin espacios extra
            return respuesta.trim();

        } catch (Exception e) {
            // En caso de error devolver mensaje descriptivo
            return "Error al consultar el modelo: " + e.getMessage();
        }
    }

    // Método público usado por la vista para enviar el prompt y obtener la respuesta
    public static String enviarPrompt(String prompt) {
        return consultarLLM(prompt);
    }
}
