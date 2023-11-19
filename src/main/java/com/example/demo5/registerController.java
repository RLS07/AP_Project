package com.example.demo5;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class registerController implements Initializable {
    @FXML
    private Button btn_signup;
    @FXML
    private Button btn_login;

    @FXML
    private RadioButton rb_malay;
    @FXML
    private RadioButton rb_thai;
    @FXML
    private RadioButton rb_singa;

    @FXML
    private RadioButton rb_male;
    @FXML
    private RadioButton rb_female;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_fullname;
    @FXML
    private DatePicker dp_dob;

    @FXML
    private PasswordField tf_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        rb_malay.setToggleGroup(toggleGroup);
        rb_singa.setToggleGroup(toggleGroup);
        rb_thai.setToggleGroup(toggleGroup);
        rb_thai.setSelected(true);
        ToggleGroup genderToggle = new ToggleGroup();
        rb_male.setToggleGroup(genderToggle);
        rb_female.setToggleGroup(genderToggle);





        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String toggleName=((RadioButton) toggleGroup.getSelectedToggle()).getText();
                String toggleGender=((RadioButton) genderToggle.getSelectedToggle()).getText();

                LocalDate selectedDate = dp_dob.getValue();
                String formattedDate = selectedDate.toString();

                if(!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()){
                    DBUtils.signUpUser(event,tf_fullname.getText(),tf_username.getText(),tf_password.getText(),toggleName,toggleGender,formattedDate);
                }else {
                    System.out.println("PLESE FILL ALL INFO");
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("PLESE FILL ALL INFO");
                    alert.show();
                }
            }
        });

        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              DBUtils.changeScene(event,"signed-in.fxml","LOGIN",null,null);
            }
        });
    }

}
