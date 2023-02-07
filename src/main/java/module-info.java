module com.example.student_management {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.student_management to javafx.fxml;

    exports com.example.student_management;




}