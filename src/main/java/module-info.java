module com.example.testjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.testjavafx to javafx.fxml;
    opens com.example.testjavafx.client to javafx.fxml;
    exports com.example.testjavafx;
    exports com.example.testjavafx.gui_controllers;
    opens com.example.testjavafx.gui_controllers to javafx.fxml;
}