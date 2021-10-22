package com.example.librarymanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class LoginController {
    @FXML
    private Label loginMessage;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;

    @FXML
    public void btnLoginClick() {

        //Validation
        if (!txtUsername.getText().isBlank() && !txtPassword.getText().isBlank()) {
            validateLogin();
        } else {
            loginMessage.setText("Please enter username and password");
        }

    }

    //Connecting to MySQL
    public void validateLogin() {

        String verifyLogin = "SELECT count(1) FROM `library_db`.`account` WHERE username = '" + txtUsername.getText() + "' AND password ='" + txtPassword.getText() + "'";

        try {
            Connection connectDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", "root", "mysql1234");
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if (queryResult.getInt(1) == 1){
                    loginMessage.setText("Login Successful");
                    //If login is successful load and go to next window
                    toDashboard();
                }else {
                    loginMessage.setText("Invalid Login. Please try again");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void toDashboard(){
        try{
            //opening dashboard window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(fxmlLoader.load(),600,400));
            stage.show();

            //closing login window
            Stage oldStage = (Stage) loginMessage.getScene().getWindow();
            oldStage.close();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}