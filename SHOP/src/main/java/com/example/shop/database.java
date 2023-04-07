package com.example.shop;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {

    public static Connection connectDb(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/myshop", "root", "halehgh1383");
        }catch(Exception e){e.printStackTrace();}
        return null; // LETS MAKE OUR DATABASE  : ) book is our database name : )
    }

}