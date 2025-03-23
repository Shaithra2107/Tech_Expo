module com.example.cw_1601_sem {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.javadoc;


    opens com.example.cw_1601_sem2 to javafx.fxml;
    exports com.example.cw_1601_sem2;
}