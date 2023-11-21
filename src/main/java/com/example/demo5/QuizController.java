package com.example.demo5;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    private Button opt2clicked,opt1clicked,opt3clicked,opt4clicked;

    @FXML
    private Button btn_next;
    @FXML
    public void opt1clicked(ActionEvent event) {
    }
    @FXML
    public void opt2clicked(ActionEvent event) {
    }
    @FXML
    public void opt3clicked(ActionEvent event) {
    }
    @FXML
    public void opt4clicked(ActionEvent event) {
    }
    static int counter=0;
    static int correct = 0;
    static int wrong = 0;

    private void loadQuestions() {
        if(counter==0){
            try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/questions.txt"))) {
                String line = br.readLine();
                if (line != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 7) {
                        String question = parts[0];
                        String optOneText = parts[1];
                        String optTwoText = parts[2];
                        String optThreeText = parts[3];
                        String optFourText = parts[4];

                        questionText.setText(question);
                        optOne.setText(optOneText);
                        optTwo.setText(optTwoText);
                        optThree.setText(optThreeText);
                        optFour.setText(optFourText);
                    }
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        }



    private String username2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadQuestions();
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
