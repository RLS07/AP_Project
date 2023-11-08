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
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String nation){
        Parent root = null;
        if(username !=null & nation!=null){
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root =loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username,nation);

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
        stage.setScene(new Scene(root,680, 500));
        stage.show();

    }
    public static void signUpUser(ActionEvent event, String fullname,String username, String password, String nation ){
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
                    psInsert = connection.prepareStatement("INSERT INTO users (full_name,username,password,nationality) VALUES (?,?,?,?)");
                    psInsert.setString(1,fullname);
                    psInsert.setString(2,username);
                    psInsert.setString(3,password);
                    psInsert.setString(4,nation);
                    psInsert.executeUpdate();

                    changeScene(event,"logged-in.fxml","Welcome",username,nation);

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
            preparedStatement = connection.prepareStatement("SELECT password,nationality FROM users WHERE username=?");
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
                    if (retriovedPassword.equals(password)){
                        changeScene(event,"logged-in.fxml","WELCOME!",username,retriveNation);
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
