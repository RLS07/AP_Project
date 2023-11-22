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
                String toggleName = getToggleText(toggleGroup);
                String toggleGender = getToggleText(genderToggle);

                LocalDate selectedDate = dp_dob.getValue();
                String formattedDate = selectedDate != null ? selectedDate.toString() : "";

                String emptyField = getFirstEmptyField(tf_fullname.getText(), tf_username.getText(), tf_password.getText(), toggleName, toggleGender, formattedDate);

                if (emptyField != null) {
                    System.out.println("PLEASE FILL ALL INFO");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all required information. Missing: " + emptyField);
                    alert.show();
                } else if (!isAgeAbove18(selectedDate)) {
                    System.out.println("AGE MUST BE ABOVE 18");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Age must be above 18.");
                    alert.show();
                } else {
                    DBUtils.signUpUser(event, tf_fullname.getText(), tf_username.getText(), tf_password.getText(), toggleName, toggleGender, formattedDate);
                }
            }

            private String getToggleText(ToggleGroup toggleGroup) {
                Toggle selectedToggle = toggleGroup.getSelectedToggle();
                return selectedToggle != null ? ((RadioButton) selectedToggle).getText() : null;
            }

            private String getFirstEmptyField(String... fields) {
                for (String field : fields) {
                    if (field == null || field.trim().isEmpty()) {
                        return getFieldLabel(field);
                    }
                }
                return null; // All fields are filled
            }
            private boolean isAgeAbove18(LocalDate birthDate) {
                if (birthDate != null) {
                    LocalDate currentDate = LocalDate.now();
                    return birthDate.plusYears(18).isBefore(currentDate);
                }
                return false; // If birthDate is null, age cannot be determined
            }

            private String getFieldLabel(String field) {
                switch (field) {
                    case null:
                        return "Date of Birth, Toggle Name, or Toggle Gender";
                    case "":
                        return "Full Name, Username, Password";
                    default:
                        return field;
                }
            }


        });

        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              DBUtils.changeScene(event,"signed-in.fxml","LOGIN",null,null,null,null,null);
            }

        });
    }

}
