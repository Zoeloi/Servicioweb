package com.desarrollo.serviciosweb.service;

import com.desarrollo.serviciosweb.model.Customer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CustomerService {

    public List<Customer> obtenerCustomers() throws IOException, InterruptedException {
        // Leemos la URL y la apikey del archivo de configuración (no van en el código)
        Properties props = new Properties();
        try (InputStream in = getClass().getResourceAsStream("/config.properties")) {
            if (in == null) {
                throw new RuntimeException("No se encontró config.properties en resources");
            }
            props.load(in);
        }
        String url = props.getProperty("supabase.url");
        String key = props.getProperty("supabase.key");

        // Es el cliente que hace la conexión
        HttpClient cliente = HttpClient.newHttpClient();

        // Es la petición que se envía al servidor
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(15))
                .header("apikey", key)
                .GET()
                .build();

        HttpResponse<String> response = cliente.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode datos = mapper.readTree(response.body());

        if (!datos.isArray()) {
            throw new RuntimeException("Failed : la respuesta no es una lista");
        }

        List<Customer> customers = new ArrayList<>();

        for (JsonNode dato : datos) {
            Customer customer = new Customer(
                    dato.path("id").asText(),
                    dato.path("name").asText(),
                    dato.path("email").asText(),
                    dato.path("phone").asText(),
                    dato.path("created_at").asText()
            );
            customers.add(customer);
        }
        return customers;
    }
}
