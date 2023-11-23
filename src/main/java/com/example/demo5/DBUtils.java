package com.example.demo5;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.sql.*;

import java.io.IOException;

public class DBUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String nation, String fullname,String gender, String dob){
        Parent root = null;
        if(username !=null & nation!=null){
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();

                // Check the type of controller based on the loaded FXML file
                if (fxmlFile.equals("logged-in.fxml")) {
                    LoggedInController loggedInController = loader.getController();
                    loggedInController.setUserInformation(username, nation, fullname, gender, dob);
                } else if (fxmlFile.equals("Quiz.fxml")) {
                    QuizController quizController = loader.getController();
                    quizController.setUserInformation(username, nation, fullname, gender, dob);
                }else if (fxmlFile.equals("Result.fxml")) {
                    ResultController resultController = loader.getController();
                    resultController.setUserInformation(username, nation, fullname, gender, dob);
                }else if (fxmlFile.equals("Fail.fxml")) {
                    FailController failController = loader.getController();
                    failController.setUserInformation(username, nation, fullname, gender, dob);
                }
            }catch (IOException e){
                e.printStackTrace();;
            }
        }else {
            try {
                root=FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root,960, 530));
        stage.show();

    }
    public static void signUpUser(ActionEvent event, String fullname,String username, String password, String  nation,String gender,String dob){
         Connection connection = null;
         PreparedStatement psInsert=null;
         PreparedStatement psCgeckUserExists=null;
         ResultSet resultSet=null;

         try{
                connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/quizproject","root","Lal123lal");
                psCgeckUserExists = connection.prepareStatement("SELECT * from users where username =?");
                psCgeckUserExists.setString(1,username);
                resultSet= psCgeckUserExists.executeQuery();

                if(resultSet.isBeforeFirst()){
                    System.out.println("USER ALREADY EXISTS");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("CANNOT USE this username");
                    alert.show();
                }else{
                    psInsert = connection.prepareStatement("INSERT INTO users (full_name,username,password,nationality,gender,dob) VALUES (?,?,?,?,?,?)");
                    psInsert.setString(1,fullname);
                    psInsert.setString(2,username);
                    psInsert.setString(3,password);
                    psInsert.setString(4,nation);
                    psInsert.setString(5,gender);
                    psInsert.setString(6,dob);


                    psInsert.executeUpdate();

                    changeScene(event,"logged-in.fxml","Welcome",username,nation,fullname,gender,dob);

                }
         }catch(SQLException e){
             e.printStackTrace();
        }finally {
             if(resultSet!=null){
                 try{
                     resultSet.close();
                 }catch (SQLException e){
                     e.printStackTrace();
                 }
             }
             if(psCgeckUserExists!=null){
                 try{
                     psCgeckUserExists.close();
                 }catch (SQLException e){
                     e.printStackTrace();
                 }
             }
             if(psInsert!=null){
                 try{
                     psInsert.close();
                 }catch (SQLException e){
                     e.printStackTrace();
                 }
             }
             if(connection!=null){
                 try{
                     connection.close();
                 }catch (SQLException e){
                     e.printStackTrace();
                 }
             }
         }



    }
    public static void logInUser(ActionEvent event, String username,String password){
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;

        try{
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/quizproject","root","Lal123lal");
            preparedStatement = connection.prepareStatement("SELECT password,nationality,full_name,gender,dob FROM users WHERE username=?");
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("USER NOT FOUND");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("USER NOT FOUND");
                alert.show();
            }else{
                while(resultSet.next()){
                    String retriovedPassword=resultSet.getString("password");
                    String retriveNation= resultSet.getString("nationality");
                    String retriveFullname= resultSet.getString("full_name");
                    String retriveGender= resultSet.getString("gender");
                    String retriveDob= resultSet.getString("dob");

                    if (retriovedPassword.equals(password)){
                        changeScene(event,"logged-in.fxml","WELCOME!",username,retriveNation,retriveFullname,retriveGender,retriveDob);
                    }else{
                        System.out.println("PASSWORD DONT MATCH");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("CREDENTIALS DONT MATCH");
                        alert.show();

                    }

                }

            }


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(resultSet!=null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement!=null){
                try{
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }


        }
    }
}
