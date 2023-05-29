import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SmartCoin extends Application {
    private TextField results;
    private Button yesBtn;
    private Button noBtn;
    private Image image;
    private ImageView imageView;

    private AnimationTimer timer;
    private LocalDateTime targetTime;

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Both teams to score?");
        yesBtn = new Button("Yes - 16.3");
        noBtn = new Button("No - 0.02");
        image = new Image("log.png");
        imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        results = new TextField();
        results.setPrefHeight(80);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(yesBtn, noBtn);

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER); // Center alignment for the VBox
        root.getChildren().addAll(imageView, label, buttonBox, results);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("SmartCoin");
        primaryStage.show();

        // Event handlers for the buttons
        yesBtn.setOnAction(event -> startCountdown());
        noBtn.setOnAction(event -> startCountdown());
    }

    private void startCountdown() {
        // Calculate the target time as tomorrow at 00:00
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = now.plusDays(1).with(LocalTime.MIDNIGHT);
        targetTime = LocalDateTime.of(tomorrow.toLocalDate(), LocalTime.MIDNIGHT);

        // Create an AnimationTimer
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                LocalDateTime currentTime = LocalDateTime.now();

                // Check if the countdown is completed
                if (currentTime.isAfter(targetTime)) {
                    showResults("Time elapsed!");
                    stopCountdown();
                    return;
                }

                // Calculate the remaining time
                Duration remainingTime = Duration.between(currentTime, targetTime);

                // Convert remaining time to hours, minutes, and seconds
                long hours = remainingTime.toHours();
                long minutes = remainingTime.toMinutesPart();
                long seconds = remainingTime.toSecondsPart();

                // Display the remaining time
                String remainingTimeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                showResults("Time remaining: " + remainingTimeString);
            }
        };

        // Start the AnimationTimer
        timer.start();
    }

    private void stopCountdown() {
        // Stop the AnimationTimer
        if (timer != null) {
            timer.stop();
        }
    }

    private void showResults(String message) {
        results.setText(message);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
