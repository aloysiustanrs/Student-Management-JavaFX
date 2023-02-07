package com.example.student_management;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    static Connection con;

    public static Connection createConnection(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String user="root";
            String pass="Aloywashere0";
            String url = "jdbc:mysql://127.0.0.1:3306/?user=root";

            con= DriverManager.getConnection(url,user,pass);

        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return con;
    }
}
