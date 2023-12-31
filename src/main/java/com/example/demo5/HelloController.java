package com.example.demo5;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public

class

HelloController

        implements

        Initializable

{
    @FXML


    private Button btn_login;
    @FXML
    private TextField tf_username;
    @FXML
    private Button btn_signup;
    @FXML


    private PasswordField tf_password;

    @Override


    public

    void

    initialize(URL url, ResourceBundle resourceBundle)

    {
        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override


            public

            void

            handle(ActionEvent event)

            {
                if (tf_username.getText().isEmpty() || tf_password.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please enter both username and password.");
                    alert.showAndWait();
                    return;
                }
                DBUtils.logInUser(event, tf_username.getText(), tf_password.getText());
            }
        });
        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override


            public

            void

            handle(ActionEvent event)

            {
                DBUtils.changeScene(event, "hello-view.fxml", "SIGNUP!!", null, null, null, null, null);
                System.out.println("HELO");
            }
        });
    }
}