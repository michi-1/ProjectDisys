package com.project.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.stream.Collectors;

public class HelloController {
    public String s="";
    @FXML
    private Label welcomeText;
    public TextField customerID;

//wreqr
    @FXML
    protected void onDataGatherClick() {
        s=customerID.getText();
        try {
            // parameters will be transferred within the request body
            HashMap<String, String> params = new HashMap<>();
            params.put("id", customerID.getText());
            params.put("value", "dummy");
            StringBuilder requestBody = new StringBuilder();
            params.entrySet().forEach((v) -> requestBody.append(v + "\n"));

            var request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/post/"+s))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();
            System.out.printf("%s\n%s\n", request, params);

            var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response Status Code: " + response.statusCode());

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    @FXML
    protected void onShowInvoiceClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}