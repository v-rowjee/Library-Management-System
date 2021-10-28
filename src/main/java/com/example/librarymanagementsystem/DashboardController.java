package com.example.librarymanagementsystem;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class DashboardController implements Initializable {

    //Stack Pane
    @FXML
    private AnchorPane pane1;
    @FXML
    private AnchorPane pane2;

    //Account Section
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;

    @FXML
    private Button btnBorrow;
    @FXML
    private TextField txtBookTitle;
    @FXML
    private TextField txtAuthor;
    @FXML
    private DatePicker dpBorrowedOn;

    @FXML
    private Button btnEditAcc;
    @FXML
    private Button btnCancelEditAcc;
    @FXML
    private Button btnSaveEditAcc;

    @FXML
    private Label lblDueDate;
    @FXML
    private Label lblDaysLeft;


    //Book Section
    @FXML
    private TextField txtSearchBook;
    @FXML
    private TableView<BookDB> bookTable;
    @FXML
    private TableColumn<BookDB, Integer> col_id;
    @FXML
    private TableColumn<BookDB, String> col_title;
    @FXML
    private TableColumn<BookDB, String> col_author;

    @FXML
    private TextField txtSelectedBook;



    // Initialise current account
    AccountDB currentUser = new AccountDB();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // check if user is already borrowing a book
        fillBookBorrow();

        //Connecting to MySQL database to fetch information to populate tableView
        ObservableList<BookDB> dataList = FXCollections.observableArrayList();

        try {

            Connection connectDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", "root", "mysql1234");
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT * FROM `library_db`.`book`");

            while (queryResult.next()) {
                dataList.add(new BookDB(
                        queryResult.getInt("book_id"),
                        queryResult.getString("title"),
                        queryResult.getString("author")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //POPULATING TABLE BOOK
        col_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_author.setCellValueFactory(new PropertyValueFactory<>("author"));

        bookTable.setItems(dataList);

        //FILTERING TABLE BY SEARCH
        filterTable(dataList);

        //Binding selected row from tableView to text field
        bookTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal != null) {
                txtSelectedBook.setText(newVal.getTitle() + " by " + newVal.getAuthor());
            }
        });

        //CHECKING IF A BOOK IS ALREADY BORROWED
        verifyBorrowBook();



    }

    // FILLING BOOK BORROWED
    public void fillBookBorrow(){

        try {

            Connection connectDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", "root", "mysql1234");
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT * FROM `library_db`.`book` WHERE book_id = '"+currentUser.getCurrent_book()+"'");

            while (queryResult.next()) {

                        txtBookTitle.setText(queryResult.getString("title"));
                        txtAuthor.setText(queryResult.getString("author"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    // FILLING ACCOUNT DETAILS
    public void fillAccountSection(AccountDB acc){

        currentUser = acc;

        txtFirstName.setText(acc.getFirstname());
        txtLastName.setText(acc.getLastname());
        txtUsername.setText(acc.getUsername());
        txtPassword.setText(acc.getPassword());

    }

    // edit account details
    @FXML
    private void editAccount(){
        btnEditAcc.setDisable(true);
        btnCancelEditAcc.setDisable(false);
        btnSaveEditAcc.setDisable(false);

    }

    // cancel account edit details
    @FXML
    private void cancelEditAccount(){
        btnEditAcc.setDisable(false);
        btnCancelEditAcc.setDisable(true);
        btnSaveEditAcc.setDisable(true);
    }

    // save account edit details
    @FXML
    private void saveEditAccount(){

        cancelEditAccount();

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cannot edit account detail as you are not an admin", ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();


    }



    //CHECKING IF A BOOK IS ALREADY BORROWED
    public void verifyBorrowBook(){
        //CHECKING IF A BOOK IS ALREADY BORROWED
        if(txtBookTitle.getText()==""){
            btnBorrow.setDisable(false);
        }else{
            btnBorrow.setDisable(true);
        }
    }

    //CLEAR BUTTON
    @FXML
    private void btnClear(){
        txtSearchBook.setText("");
    }


    //FILTERING SEARCH
    private void filterTable(ObservableList<BookDB> dataList){
        FilteredList<BookDB> filteredList = new FilteredList<>(dataList, b -> true);

        txtSearchBook.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(bookDB -> {

                if(newValue.isEmpty()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(bookDB.getBook_id().toString().indexOf(searchKeyword) > -1){
                    return true;
                } else if (bookDB.getTitle().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else if (bookDB.getAuthor().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else
                    return false; //no match found
            });
        });

        SortedList<BookDB> sortedData =new SortedList<>(filteredList);

        sortedData.comparatorProperty().bind(bookTable.comparatorProperty());

        bookTable.setItems(sortedData);
    }



    //Borrow a book
    @FXML
    private void borrowBook(){

        BookDB selectedBook = bookTable.getSelectionModel().getSelectedItem();
        txtBookTitle.setText(selectedBook.getTitle());
        txtAuthor.setText(selectedBook.getAuthor());
        dpBorrowedOn.setValue(LocalDate.now());

        lblDueDate.setText("Due " + LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
        lblDaysLeft.setText("7 days left");

        verifyBorrowBook();
    }
    @FXML
    private void returnBook(){
        txtBookTitle.setText("");
        txtAuthor.setText("");
        dpBorrowedOn.setValue(null);
        lblDueDate.setText("");
        lblDaysLeft.setText("");

        verifyBorrowBook();
    }




    // DASHBOARD BUTTON NAVIGATION
    @FXML
    private void toAccount() {
        pane1.setVisible(true);
        pane2.setVisible(false);
    }

    @FXML
    private void toBooks() {
        pane1.setVisible(false);
        pane2.setVisible(true);
    }



    //LOG OUT
    @FXML
    private void toLogin(){
        try{
            //opening dashboard window
            FXMLLoader fxmlLoader = new FXMLLoader(_App.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 300, 400);

            Stage stage = new Stage();
            stage.setTitle("Library Assistant");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            //closing login window
            Stage oldStage = (Stage) btnBorrow.getScene().getWindow();
            oldStage.close();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
