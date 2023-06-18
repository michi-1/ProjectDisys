package com.project.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.awt.Desktop;



public class AppController {
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
    protected void onShowInvoiceClick() throws IOException {
        s = customerID.getText();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/get/" + s))
                .build();
        System.out.println("http://localhost:8080/get/" + s);
        client.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray())
                .thenApply(response -> {
                    // Check the response status code
                    if (response.statusCode() == 404) {
                        // Display an error message to the user
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("File Not Found");
                            alert.setHeaderText(null);
                            alert.setContentText("The requested PDF file was not found.");
                            alert.showAndWait();
                        });
                        return null;
                    } else if (response.statusCode() == 200) {
                        return response.body();
                    } else {
                        throw new RuntimeException("Unexpected response status code: " + response.statusCode());
                    }
                })
                .thenAccept(responseBody -> {
                    if (responseBody != null) {
                        try {
                            // Save the response body as a temporary PDF file
                            String tempFileName = "temp.pdf";
                            Files.write(Paths.get(tempFileName), responseBody);

                            // Open the PDF file in the default web browser
                            Desktop.getDesktop().open(new File(tempFileName));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
