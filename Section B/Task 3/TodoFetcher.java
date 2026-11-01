package com.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.model.Todo;

public class TodoFetcher {

    public static void main(String[] args) throws Exception {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/todos/1"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();

        Todo todo = mapper.readValue(response.body(), Todo.class);

        System.out.println("User ID: " + todo.getUserId());
        System.out.println("Todo ID: " + todo.getId());
        System.out.println("Title: " + todo.getTitle());
        System.out.println("Completed: " + todo.isCompleted());
    }
}