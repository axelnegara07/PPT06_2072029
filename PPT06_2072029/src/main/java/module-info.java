module com.example.ppt05_2072029 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;


    opens com.example.ppt06_2072029 to javafx.fxml;
    exports com.example.ppt06_2072029;
    exports com.example.ppt06_2072029.controller;
    opens com.example.ppt06_2072029.controller to javafx.fxml;
    exports com.example.ppt06_2072029.model;
    opens com.example.ppt06_2072029.model to javafx.fxml;
    exports com.example.ppt06_2072029.dao;
    opens com.example.ppt06_2072029.dao to javafx.fxml;
    exports com.example.ppt06_2072029.util;
    opens com.example.ppt06_2072029.util to javafx.fxml;
}