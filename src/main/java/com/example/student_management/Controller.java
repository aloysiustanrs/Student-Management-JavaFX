package com.example.student_management;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    String currentId = "";

    @FXML
    private TableView<Student> table;
    @FXML
    private TableColumn<Student, Integer> id_col;
    @FXML
    private TableColumn<Student, String> name_col;
    @FXML
    private TableColumn<Student, Integer> age_col;
    @FXML
    private TableColumn<Student, String> code_col;
    @FXML
    private TableColumn<Student, Double> gpa_col;
    @FXML
    private TextField age_input;
    @FXML
    private ChoiceBox<String> code_input;
    @FXML
    private TextField gpa_input;
    @FXML
    private TextField name_input;

    @FXML
    private TextField search_bar;

    @FXML
    void getSelected() {
        int currentSelectedIndex = table.getSelectionModel().getSelectedIndex();
        if (currentSelectedIndex <= -1) {
            return;
        }

        name_input.setText(name_col.getCellData(currentSelectedIndex));
        age_input.setText(age_col.getCellData(currentSelectedIndex).toString());
        code_input.setValue(code_col.getCellData(currentSelectedIndex));
        gpa_input.setText(gpa_col.getCellData(currentSelectedIndex).toString());
        currentId = id_col.getCellData(currentSelectedIndex).toString();


    }
    private int generateRandomId(){
        Random rand = new Random();
        return rand.nextInt(10000);
    }
    private final ArrayList<Integer> idList = new ArrayList<>();
    public ObservableList<Student> getStudents() {

        ObservableList<Student> data = FXCollections.observableArrayList();

        try(Connection con = DBConnection.createConnection()) {


//          Execute query
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students.student_details");

            while(rs.next()){
                data.add(new Student(rs.getInt(1),rs.getString(2), rs.getInt(3), rs.getString(4),rs.getDouble(5)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;

    }
    public void searchStudent(){
        //          Execute query
        String searchText = search_bar.getText().toLowerCase();

        try  {
            ObservableList<Student> studentList = getStudents();
            ObservableList<Student> filteredList = FXCollections.observableArrayList();

            for (Student student : studentList){
                if (student.getName().toLowerCase().contains(searchText)){
                    filteredList.add(student);
                }
            }

            table.setItems(filteredList);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addStudent() {
        try(Connection con = DBConnection.createConnection()) {

            PreparedStatement getIdList = con.prepareStatement("SELECT id FROM students.student_details");
            ResultSet rs = getIdList.executeQuery();

            while(rs.next()){
                idList.add(rs.getInt("id"));
            }

//          Execute query
            String addUserQuery = "INSERT INTO students.student_details (id,name,age,programCode,gpa) values(?,?,?,?,?)";
            try{
                PreparedStatement addUserPst = con.prepareStatement(addUserQuery);
                int currRandomId = generateRandomId();
                while (idList.contains(currRandomId)){
                    currRandomId = generateRandomId();
                }

                String name_add = name_input.getText();
                String age_add = age_input.getText();
                String code_add = code_input.getValue();
                String gpa_add = gpa_input.getText();

                if (!(checkInput(name_add,Integer.parseInt(age_add),Double.parseDouble(gpa_add)))){
                    JOptionPane.showMessageDialog(null,"Data entered exceeds boundaries :\n" +
                            "Name string is less than 50 characters\n" +
                            "Age < 100\n" +
                            "GPA <= 5.00");
                    return;
                }


                addUserPst.setString(1,String.valueOf(currRandomId));
                addUserPst.setString(2,name_add);
                addUserPst.setString(3,age_add);
                addUserPst.setString(4,code_add);
                addUserPst.setString(5,gpa_add);
                addUserPst.execute();

                updateTable();
                clearInput();

                JOptionPane.showMessageDialog(null,"Student added");
            }
            catch(Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"Failed to add student");

            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void editStudent(){
        //          Execute query

        String name_edit = name_input.getText();
        String age_edit = age_input.getText();
        String code_edit = code_input.getValue();
        String gpa_edit = gpa_input.getText();

        if (!(checkInput(name_edit,Integer.parseInt(age_edit),Double.parseDouble(gpa_edit)))){
            JOptionPane.showMessageDialog(null,"Data entered exceeds boundaries :\n" +
                    "Name string is less than 50 characters\n" +
                    "Age < 100\n" +
                    "GPA <= 5.00");
            return;
        }



        String editStudentQuery = "UPDATE students.student_details SET name=\""+ name_edit +"\",age=\""+ age_edit +"\",programCode = \""+ code_edit +"\",gpa =\"" +  gpa_edit +"\" WHERE id = \"" + currentId + "\"";
        try (Connection con = DBConnection.createConnection()) {
            PreparedStatement editStudentPst = con.prepareStatement(editStudentQuery);
            editStudentPst.execute();
            updateTable();
            clearInput();

            JOptionPane.showMessageDialog(null, "Student edited");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to edit student");

        }

    }
    public void deleteStudent() {

//          Execute query
            String deleteStudentQuery = "DELETE FROM students.student_details WHERE id = ?";
            try (Connection con = DBConnection.createConnection()) {
                PreparedStatement deleteStudentPst = con.prepareStatement(deleteStudentQuery);
                deleteStudentPst.setString(1,currentId);
                deleteStudentPst.execute();
                updateTable();

                JOptionPane.showMessageDialog(null, "Student deleted");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to delete student");

            }

    }
    public void updateTable(){
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        age_col.setCellValueFactory(new PropertyValueFactory<>("age"));
        code_col.setCellValueFactory(new PropertyValueFactory<>("code"));
        gpa_col.setCellValueFactory(new PropertyValueFactory<>("gpa"));



        ObservableList<Student> list = getStudents();
        table.setItems(list);
    }


// primary key for ID

    public void clearInput(){
        //clear textbox after editing / adding
        name_input.clear();
        age_input.clear();
        code_input.setValue("");
        gpa_input.clear();

    }
    public boolean checkInput(String name, int age, double gpa){
        //    Returns false when input is out of range or too many characters
        return name.length() <= 50 && age <= 100 && !(gpa > 5.00);
    }

    public void initialize(URL url, ResourceBundle rb) {

        String[] codeList = {"CS","MED","BIO","CHEM"};

        for (String code : codeList){
            code_input.getItems().add(code);
        }

        updateTable();

    }


}




