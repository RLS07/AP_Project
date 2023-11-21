package com.example.demo5;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class QuizController implements Initializable {

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                optOne.setText("CHANGED");
            }
        });

    }
    public void initialize(String username){
        this.username2=username;

        System.out.println(username);




    }
    public void setUserInformation(String username, String nation, String fullname, String gender, String dob) {
        StringBuilder capitalizedFullName = new StringBuilder();
        String[] words = fullname.split("\\s+");
        for (String word : words) {
            if (!word.isEmpty()) {
                capitalizedFullName.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
        }
        String result = capitalizedFullName.toString().trim();
        fName.setText(result);


        System.out.println("User information set in QuizController: " + username + ", " + nation + ", " + fullname + ", " + gender + ", " + dob);

        // Update other UI elements with the user information if needed
        String imagePath = "/images/" + nation + ".png";

        try {
            // Use getClass().getResource() to obtain a valid URL
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                Image newImage = new Image(imageUrl.toExternalForm());
                flagImg.setImage(newImage);
            } else {
                System.err.println("Image resource not found: " + imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }



}
