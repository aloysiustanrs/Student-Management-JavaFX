package com.example.student_management;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.*;

public class LoginController {

    @FXML
    private TextField password_input;
    @FXML
    private TextField username_input;


    //Create a stage object
    public static Stage mainStage;

    //Sets the stage object to the stage in 'Application.java'
    public void setStage(Stage stage) {
        mainStage = stage;
    }

    /*
        Check username & password
        if wrong , output error message
        if correct , change scene to 'student_management.fxml'
     */
    public void loginCheck() throws Exception{

        String username_check = username_input.getText().trim();
        String password_check = password_input.getText().trim();

        if (username_check.equals("admin") && password_check.equals("123456")){
            JOptionPane.showMessageDialog(null,"Login Successful");
            Parent root = FXMLLoader.load(getClass().getResource("student_management.fxml"));
            Scene scene = new Scene(root, 700, 500);

            mainStage.setTitle("Student Management");
            mainStage.setScene(scene);
            mainStage.show();
        }else{
            JOptionPane.showMessageDialog(null,"Wrong username or password");
        }
    }

}