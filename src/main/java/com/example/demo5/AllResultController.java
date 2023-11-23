package com.example.demo5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class AllResultController implements Initializable {
    @FXML
    private Label mean;
    @FXML
    private Label median;
    @FXML
    private Label mode;
    @FXML
    private Label max;
    @FXML
    private Label min;
    @FXML
    private Label sd;

    @FXML
    private Label scoreInPercent,correct,incorrect;

    @FXML
    private ProgressIndicator pieChr;
    @FXML
    private Button btnFinish;

    @FXML
    private ChoiceBox<String> choiceBox;

    private List<Integer> correctAnswerScores = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate the ChoiceBox with usernames from the file
        populateUsernames();
        btnFinish.setOnAction(event -> exitApplication());


        // Set an event handler to update results when a username is selected
        choiceBox.setOnAction(event -> updateUserResults(choiceBox.getValue()));
    }

    private void populateUsernames() {
        File file = new File("src/main/resources/results.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String username = parts[0].trim();
                    choiceBox.getItems().add(username);

                    int correctAnswers = Integer.parseInt(parts[2].trim());
                    correctAnswerScores.add(correctAnswers);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void exitApplication() {
        System.exit(0);
    }
    private void updateUserResults(String selectedUsername) {
        File file = new File("src/main/resources/results.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String username = parts[0].trim();
                    int correctAnswers = Integer.parseInt(parts[2].trim());
                    int wrongAnswers = Integer.parseInt(parts[3].trim());
                    String totalAttempted = parts[4].trim();
                    String result = parts[5].trim();

                    if (username.equals(selectedUsername)) {
                        // Update UI elements with the selected user's results
                        mean.setText(Double.toString(calculateMean()));
                        median.setText( Double.toString(calculateMedian()));
                        mode.setText(Integer.toString(calculateMode()));
                        max.setText(Integer.toString(calculateMax()));
                        min.setText(Integer.toString(calculateMin()));
                        sd.setText(Double.toString(calculateStandardDeviation()));

                        // Update score-related UI elements
                        int totalQuestions = Integer.parseInt(totalAttempted.split("/")[1].trim());
                        updateScoreElements(correctAnswers, totalQuestions);

                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void updateScoreElements(int correctAnswers, int totalQuestions) {
        double scorePercentage = ((double) correctAnswers / totalQuestions) * 100;
        scoreInPercent.setText(String.valueOf(scorePercentage));
        correct.setText(String.valueOf(correctAnswers));
        incorrect.setText(String.valueOf(totalQuestions - correctAnswers));
        pieChr.setProgress(scorePercentage / 100.0);
    }
    private double calculateMean() {
        return correctAnswerScores.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    private double calculateMedian() {
        Collections.sort(correctAnswerScores);
        int size = correctAnswerScores.size();

        if (size % 2 == 0) {
            int middle1 = correctAnswerScores.get(size / 2 - 1);
            int middle2 = correctAnswerScores.get(size / 2);
            return (middle1 + middle2) / 2.0;
        } else {
            return correctAnswerScores.get(size / 2);
        }
    }

    private int calculateMode() {
        // Implementation for mode calculation (you may need to adapt this to your specific requirements)
        // For simplicity, let's assume the mode is the most frequently occurring correct answer score
        return correctAnswerScores.stream()
                .distinct()
                .max((a, b) -> Collections.frequency(correctAnswerScores, a) - Collections.frequency(correctAnswerScores, b))
                .orElse(0);
    }

    private int calculateMax() {
        return Collections.max(correctAnswerScores);
    }

    private int calculateMin() {
        return Collections.min(correctAnswerScores);
    }

    private double calculateStandardDeviation() {
        double meanValue = calculateMean();
        double sumSquaredDifferences = correctAnswerScores.stream()
                .mapToDouble(score -> Math.pow(score - meanValue, 2))
                .sum();
        return Math.sqrt(sumSquaredDifferences / correctAnswerScores.size());
    }
}
