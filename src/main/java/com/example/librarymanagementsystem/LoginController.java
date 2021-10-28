package com.example.librarymanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label loginMessage;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private ImageView loginCover;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void btnLoginClick() {

        //delete this
//        toDashboard();

        //Validation
        if (!txtUsername.getText().isBlank() && !txtPassword.getText().isBlank()) {

            validateLogin();

        } else {
            loginMessage.setText("Please enter username and password");
        }

    }

    //Initialising AccountDB
    AccountDB currentUser = new AccountDB();

    //Connecting to MySQL
    public void validateLogin() {

        String verifyLogin = "SELECT count(1) as row_count,acc_id, username, password, firstname, lastname, current_book FROM `library_db`.`account` WHERE username = '" + txtUsername.getText() + "' AND password ='" + txtPassword.getText() + "'";

        try {
            Connection connectDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", "root", "mysql1234");
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if ( queryResult.getInt("row_count") == 1){    //IF LOGIN SUCCESSFUL

                    //print text
                    loginMessage.setText("Login Successful");

                    //create AccountDB object
                    currentUser.setAccount(
                            queryResult.getInt("acc_id"),
                            queryResult.getString("username"),
                            queryResult.getString("password"),
                            queryResult.getString("firstname"),
                            queryResult.getString("lastname"),
                            queryResult.getInt("current_book")

                    );

                    System.out.println("User: "+currentUser.getAcc_id());

                    //go to Dashboard Stage
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
            Parent root = (Parent) fxmlLoader.load();
            //parsing data to dashboard
            DashboardController DC = fxmlLoader.getController();
            DC.fillAccountSection(currentUser);

            Stage stage = new Stage();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(root,900,600));
            stage.setResizable(false);
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