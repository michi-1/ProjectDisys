module com.project.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.desktop;


    opens com.project.client to javafx.fxml;
    exports com.project.client;
}