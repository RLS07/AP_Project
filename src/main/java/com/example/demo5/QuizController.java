package com.example.demo5;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class QuizController  {

    @FXML
    private Label fName;

    @FXML
    private Label qNo;

    @FXML
    private ImageView flagImg;

    @FXML
    private Label questionText;

    @FXML
    private Label optOne;

    @FXML
    private Label optTwo;

    @FXML
    private Label optThree;

    @FXML
    private Label optFour;

    @FXML
    private Button btn_next;

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//        // Initialization logic if needed
//    }
    private String username2;
    public void initialize(String username){
        this.username2=username;
        System.out.println(username);
    }
    public void setUserInformation(String username, String nation, String fullname, String gender, String dob) {
        fName.setText(username);
        System.out.println("User information set in QuizController: " + username + ", " + nation + ", " + fullname + ", " + gender + ", " + dob);

        // Update other UI elements with the user information if needed
    }


}
