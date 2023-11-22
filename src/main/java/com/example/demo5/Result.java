package com.example.demo5;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;


public class Result {
    @FXML
    private Label user;
    @FXML
    private ImageView flagImg;

    public String uname;

    public void setUserInformation(String username, String nation, String fullname, String gender, String dob) {
        StringBuilder capitalizedFullName = new StringBuilder();
        String[] words = fullname.split("\\s+");
        for (String word : words) {
            if (!word.isEmpty()) {
                capitalizedFullName.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
        }
        String result = capitalizedFullName.toString().trim();
        user.setText(result);
//       this.user = result;
        this.uname=result;

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
}
