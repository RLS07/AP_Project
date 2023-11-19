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

public class LoggedInController implements Initializable {

    @FXML
    private Button btn_logout;

    @FXML
    private Label lab_nation;

    @FXML
    private Label lab_welcome;
    @FXML
    private ImageView img_flag;
    
    @FXML
    private Label inf_fullname;
    @FXML
    private Label lab_dob;
    @FXML
    private Label lab_gender;
    @FXML
    private Label lab_username;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"hello-view.fxml","LOGIN!",null,null,null,null,null);

            }
        });

    }

    public void setUserInformation(String username, String Nation, String Fullname,String gender, String dob){
        String username1 = username;
        String welcomeText = "Welcome " + username.substring(0, 1).toUpperCase() + username.substring(1) + " !";
        lab_welcome.setText(welcomeText);
        lab_nation.setText(Nation);
        inf_fullname.setText(Fullname);
        lab_dob.setText(dob);
        lab_gender.setText(gender);
        lab_username.setText(username);
        String imagePath = "/images/" + Nation + ".png";

        try {
            // Use getClass().getResource() to obtain a valid URL
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                Image newImage = new Image(imageUrl.toExternalForm());
                img_flag.setImage(newImage);
            } else {
                System.err.println("Image resource not found: " + imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
        }




    }
}
