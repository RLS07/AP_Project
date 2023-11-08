package com.example.demo5;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {

    @FXML
    private Button btn_logout;

    @FXML
    private Label lab_nation;

    @FXML
    private Label lab_welcome;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"hello-view.fxml","LOGIN!",null,null);
            }
        });

    }

    public void setUserInformation(String username, String Nation){
        lab_welcome.setText("Welcome "+username+" !");
        lab_nation.setText("You are from "+Nation+"!");
    }
}
