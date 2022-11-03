module com.example.lab1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.json;

    opens com.example.lab1 to javafx.fxml;
    exports com.example.lab1;
}