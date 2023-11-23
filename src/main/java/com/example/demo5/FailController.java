package com.example.demo5;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;


public class FailController implements Initializable {
    @FXML
    private Label user;
    @FXML
    private ImageView flagImg;

    public String uname;
    @FXML
    private Button otherResults;

    @FXML
    private ProgressIndicator pieChr;
    @FXML
    private Label scoreInPercent;
    @FXML
    private Label incorrect;
    @FXML
    private Label correct;
    @FXML
    private Button btnFinish;
    private void exitApplication() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readAndSetValues();
        btnFinish.setOnAction(event -> exitApplication());
        otherResults.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"allResult.fxml","ALL RESULTS!!",null,null,null,null,null);
                System.out.println("HELO");


            }
        });



    }

    public void setUserInformation(String username, String nation, String fullname, String gender, String dob) {
        StringBuilder capitalizedFullName = new StringBuilder();
        String[] words = username.split("\\s+");
        for (String word : words) {
            if (!word.isEmpty()) {
                capitalizedFullName.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
        }
        String result = capitalizedFullName.toString().trim();
        user.setText(result);
//       this.user = result;
        this.uname=result;
        readAndSetValues();
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
    private void readAndSetValues() {
        File file = new File("src/main/resources/results.txt"); // Replace with the actual path to your file

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) { // Assuming each line has 6 values
                    String username = parts[0].trim();
                    username=capitalizeFirstLetter(username);
                    int correctAnswers = Integer.parseInt(parts[2].trim());
                    int wrongAnswers = Integer.parseInt(parts[3].trim());
                    String totalAttempted = parts[4].trim();
                    String result = parts[5].trim();
                    System.out.println(username);
                    System.out.println(correctAnswers);
                    System.out.println(wrongAnswers);

                    // Check if the username matches uname
                    if (username.equals(uname)) {
                        // Set values to the UI elements
                        user.setText(username);
                        correct.setText(String.valueOf(correctAnswers));
                        incorrect.setText(String.valueOf(wrongAnswers));
                        pieChr.setProgress(((double) correctAnswers / 20));
                        scoreInPercent.setText(String.valueOf(((double) correctAnswers / 20) * 100));
                        // Set other values as needed
                        // ...

                        break; // Stop reading once the correct user is found
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}

