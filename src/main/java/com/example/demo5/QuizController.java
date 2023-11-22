package com.example.demo5;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
    private Label displaymessage;

    @FXML
    private Label qNo;

    @FXML
    private ImageView flagImg;

    @FXML
    private Label quest;



    @FXML
    private ImageView qImg;
    @FXML
    private Button submitbtn;

    @FXML
    private Button btnopt1,btnopt2,btnopt3,btnopt4;

    @FXML
    private Button btn_next;

    static int counter=0;

    private List<Integer> clickedQuestionsList = new ArrayList<>();
    private int questionsAttemptedCounter;
    private void updateQuestionsAttemptedMessage() {
        int totalQuestions = 20;
        int questionsAttempted = clickedQuestionsList.size();
        qNo.setText(String.valueOf(questionsAttempted));
    }

    @FXML
    private void option1clicked(ActionEvent event){
        if (!answerSelected){
            checkAnswer(1);
            clickedQuestionsList.add(currentQuestionIndex);
            setButtonColorGreen(btnopt1);
            System.out.println("Current Question Index: " + currentQuestionIndex);
            updateQuestionsAttemptedMessage();


        }


    }
    @FXML
    private void option2clicked(ActionEvent event){
        if (!answerSelected){
            checkAnswer(2);
            clickedQuestionsList.add(currentQuestionIndex);
            setButtonColorGreen(btnopt2);
            System.out.println("Current Question Index: " + currentQuestionIndex);
            updateQuestionsAttemptedMessage();


        }


    }
    @FXML
    private void option3clicked(ActionEvent event){
        if (!answerSelected){
            checkAnswer(3);
            clickedQuestionsList.add(currentQuestionIndex);
            setButtonColorGreen(btnopt3);
            System.out.println("Current Question Index: " + currentQuestionIndex);
            updateQuestionsAttemptedMessage();
        }


    }
    @FXML
    private void option4clicked(ActionEvent event){
        if (!answerSelected){
            checkAnswer(4);
            clickedQuestionsList.add(currentQuestionIndex);
            setButtonColorGreen(btnopt4);
            System.out.println("Current Question Index: " + currentQuestionIndex);
            updateQuestionsAttemptedMessage();
        }


    }
    private List<Integer> wrongAnswersList = new ArrayList<>();
    public void checkAnswer(int selectedOption) {
        if (questionsList != null && currentQuestionIndex >= 0 && currentQuestionIndex < questionsList.size()) {
            String[] currentQuestion = questionsList.get(currentQuestionIndex);
            if (currentQuestion.length >= 7) {
                String answerString = currentQuestion[6].trim();
                String answerIndexString = answerString.substring(0, 1);
                try {
                    int correctAnswerIndex = Integer.parseInt(answerIndexString);
                    if (selectedOption == correctAnswerIndex) {
                        correctAnswersCounter++;
                    } else {
                        wrongAnswersList.add(currentQuestionIndex + 1);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing answer index: " + answerIndexString);
                }
            }
            if (currentQuestionIndex < questionsList.size() - 1) {
                questionsAttemptedCounter++;

            }
            answerSelected = true;
        }
    }
    public void displayCurrentQuestion() {

        if (questionsList != null && currentQuestionIndex >= 0 && currentQuestionIndex < questionsList.size()) {
            String[] currentQuestion = questionsList.get(currentQuestionIndex);
            if (currentQuestion.length >= 6) {
                quest.setText(currentQuestion[0]);
                btnopt1.setText(currentQuestion[1]);
                btnopt2.setText(currentQuestion[2]);
                btnopt3.setText(currentQuestion[3]);
                btnopt4.setText(currentQuestion[4]);
                String img = currentQuestion[5];
                String imagePath = "/images/" + img;
                    try {
                        // Use getClass().getResource() to obtain a valid URL
                        URL imageUrl = getClass().getResource(imagePath);
                        if (imageUrl != null) {
                            Image newImage = new Image(imageUrl.toExternalForm());
                            qImg.setImage(newImage);
                        } else {
                            System.err.println("Image resource not found: " + imagePath);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Handle the exception as needed
                    }

            } else {
                System.err.println("Question data is incomplete for question " + currentQuestionIndex);
            }
        } else {
            System.err.println("Invalid question index: " + currentQuestionIndex);
        }
    }
    private boolean answerSelected = false;
    private  int correctAnswersCounter;
    private boolean calculatePassOrFail() {
        int passThreshold = 8;
        return correctAnswersCounter >= passThreshold;
    }


    private void setButtonColorGreen(Button button) {
        button.setStyle("-fx-text-fill: green;");
    }
    private void setButtonColorwhite(Button button) {
        button.setStyle("-fx-strikethrough: true;");
    }
    @FXML
    void getpreviousquestion(ActionEvent event) {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            setButtonColorwhite(btnopt1);
            setButtonColorwhite(btnopt2);
            setButtonColorwhite(btnopt3);
            setButtonColorwhite(btnopt4);
            displayCurrentQuestion();
            checkAndDisableOptionButtons(currentQuestionIndex);
            answerSelected = false;
        }

    }
    private void disableAllButtons() {
        btnopt1.setDisable(true);
        btnopt2.setDisable(true);
        btnopt3.setDisable(true);
        btnopt4.setDisable(true);
        submitbtn.setDisable(false);
    }
    private void checkAndDisableOptionButtons(int index) {
        if (clickedQuestionsList.contains(index)) {
            disableAllButtons();
        }else {
            enableallbtn();
        }
    }
    private void printWrongAnswers() {
        int totalQuestions = questionsList.size();
        int wrongAnswers = wrongAnswersList.size();
        System.out.println("Number of wrong answers: " + wrongAnswers);
        System.out.println("Questions attempted: " + questionsAttemptedCounter + "/" + totalQuestions);
        System.out.print("Wrong answers: ");
        for (int i = 0; i < wrongAnswersList.size(); i++) {
            System.out.print(wrongAnswersList.get(i));
            if (i < wrongAnswersList.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
    private List<String[]> questionsList;
    private int currentQuestionIndex;
    public void getquestion() throws FileNotFoundException {
        System.out.println("--------------");
        File selectedFile = null;


        selectedFile = new File("src/main/resources/questions.txt");
        if (selectedFile != null && selectedFile.exists()) {
            loadQuestions(selectedFile);
            questionsAttemptedCounter = 1;

        } else {
            System.err.println("File does not exist: " + (selectedFile != null ? selectedFile.getAbsolutePath() : "null"));
        }

    }
    private void loadQuestions(File selectedFile) {
//        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/questions.txt"))) {
//            String line;
//            for (int i = 0; i < counter && (line = br.readLine()) != null; i++) {
//                // Skip lines until you reach the desired question
//            }
//
//            line = br.readLine(); // Read the next question
//            if (line != null) {
//                String[] parts = line.split(",");
//                if (parts.length == 7) {
//                    String question = parts[0];
//                    String optOneText = parts[1];
//                    String optTwoText = parts[2];
//                    String optThreeText = parts[3];
//                    String optFourText = parts[4];
//                    String img = parts[5];
//                    String imagePath = "/images/" + img;
//                    try {
//                        // Use getClass().getResource() to obtain a valid URL
//                        URL imageUrl = getClass().getResource(imagePath);
//                        if (imageUrl != null) {
//                            Image newImage = new Image(imageUrl.toExternalForm());
//                            qImg.setImage(newImage);
//                        } else {
//                            System.err.println("Image resource not found: " + imagePath);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        // Handle the exception as needed
//                    }
//
//
//                    getquestion.setText(question);
//                    btnopt1.setText(optOneText);
//                    btnopt2.setText(optTwoText);
//                    btnopt3.setText(optThreeText);
//                    btnopt4.setText(optFourText);
//
//                }
//            }
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        try {
            if (selectedFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                String line;
                questionsList = new ArrayList<>();
                while ((line = reader.readLine()) != null) {
                    String[] questionData = line.split(",");
                    questionsList.add(questionData);
                }
                currentQuestionIndex = 0;
                displayCurrentQuestion();
            } else {
                System.err.println("File does not exist: " + selectedFile.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        public String username;
    @FXML
    void submitButtonClicked(ActionEvent event) {
        if (clickedQuestionsList.size() == 20) {
            printCorrectAnswers();
            printWrongAnswers();

            System.out.println("Current Logged In Username " + username);
            writeResultsToFile("src/main/resources/results.txt", username);
            gotoresultpage(event);
        } else {

            System.out.println("Error: Not all questions have been answered.");
            System.out.println(username);
            displaymesage.setText("Not all questions have been answered.");

        }
    }
    @FXML
    private  Label displaymesage;
    public void gotoresultpage(ActionEvent event) {

        DBUtils.changeScene(event,"Result.fxml","Result",username,null,null,null,null);

    }
    private void writeResultsToFile(String filePath, String username) {
        if (questionsList != null) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(filePath));
                boolean userExists = false;

                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i);
                    if (line.startsWith(username + ",")) {
                        lines.set(i, getUserResultString(username));
                        userExists = true;
                        break;
                    }
                }

                if (!userExists) {
                    Files.write(Paths.get(filePath), (getUserResultString(username) + "\n").getBytes(), StandardOpenOption.APPEND);
                } else {
                    Files.write(Paths.get(filePath), lines);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Questions list is null.");
        }
    }
    public void printCorrectAnswers() {

        System.out.println("Number of correct answers: " + correctAnswersCounter);
        String results;
        if (correctAnswersCounter <8) {
            results="fail";
            System.out.println(results);

        }else{
            results="pass";
            System.out.println(results);
        }
    }
    private String getUserResultString(String username) {
        String userData = username;
        if (!userData.isEmpty()) {
            return username + "," + userData + "," +
                    correctAnswersCounter + "," + wrongAnswersList.size() + "," +
                    questionsAttemptedCounter + "/" + questionsList.size() + "," +
                    (calculatePassOrFail() ? "pass" : "fail");
        } else {
            System.out.println("Error: User data is empty.");
            return "";
        }
    }

    public void enableallbtn(){
        btnopt1.setDisable(false);
        btnopt2.setDisable(false);
        btnopt3.setDisable(false);
        btnopt4.setDisable(false);
    }





    private String username2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println(username);
        displayCurrentQuestion();
        System.out.println("this is quiz"+username);
        updateQuestionsAttemptedMessage();
        try {
            getquestion();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        printClickedQuestions();
//        btn_next.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                counter++;
//                loadQuestions();
//            }
//        });


    }
    private void printClickedQuestions() {
        System.out.print("Clicked questions: ");
        for (int i = 0; i < clickedQuestionsList.size(); i++) {
            System.out.print(clickedQuestionsList.get(i));
            if (i < clickedQuestionsList.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }


    public void initialize(String username){
        this.username2=username;

        System.out.println(username);




    }
    @FXML
    void loadNextQuestion(ActionEvent event) {
        if(currentQuestionIndex < questionsList.size() - 1){
            currentQuestionIndex++;
            setButtonColorwhite(btnopt1);
            setButtonColorwhite(btnopt2);
            setButtonColorwhite(btnopt3);
            setButtonColorwhite(btnopt4);
            displayCurrentQuestion();
            checkAndDisableOptionButtons(currentQuestionIndex);
            answerSelected = false;
            if (currentQuestionIndex >= questionsList.size()) {

            }
        }

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
        this.username = result;


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
