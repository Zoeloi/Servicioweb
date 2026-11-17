package com.desarrollo.serviciosweb.service;

import com.desarrollo.serviciosweb.model.Usuario;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private static final String URL = "https://jsonplaceholder.typicode.com/users";

    public List<Usuario> obtenerUsuarios() throws IOException, InterruptedException {
        // Es el cliente que hace la conexión
        HttpClient cliente = HttpClient.newHttpClient();

        // Es la petición que se envía al servidor
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .timeout(Duration.ofSeconds(15))
                .GET()
                .build();

        // Es la respuesta del servidor (el JSON como texto)
        HttpResponse<String> response = cliente.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
        }

        // Jackson convierte el texto JSON en un árbol de nodos
        ObjectMapper mapper = new ObjectMapper();
        JsonNode datos = mapper.readTree(response.body());

        if (!datos.isArray()) {
            throw new RuntimeException("Failed : la respuesta no es una lista");
        }

        List<Usuario> usuarios = new ArrayList<>();

        for (JsonNode dato : datos) {
            Usuario usuario = new Usuario(
                    dato.path("id").asInt(),
                    dato.path("name").asText(),
                    dato.path("username").asText(),
                    dato.path("email").asText(),
                    dato.path("phone").asText(),
                    dato.path("address").path("city").asText(),
                    dato.path("company").path("name").asText()
            );
            usuarios.add(usuario);
        }
        return usuarios;
    }
}
