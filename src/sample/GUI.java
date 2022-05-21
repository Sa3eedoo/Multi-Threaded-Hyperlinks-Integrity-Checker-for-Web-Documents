package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GUI {
    private static TextField urlTextField;
    private static TextField depthTextField;
    private static TextField numberOfThreadsTextField;
    private static long elapsedTime;

    public static void displayStartingUI(Stage window) {
        Label titleLabel = new Label("Hyperlinks Integrity Checker for Web Documents");
        titleLabel.setFont(new Font("Agency FB", 25));

        Label urlLabel = new Label("URL:");
        Label depthLabel = new Label("Depth:");
        Label numberOfThreadsLabel = new Label("Number Of Threads: ");


        urlTextField = new TextField();
        urlTextField.setPromptText("URL");
        urlTextField.setMinSize(200, 20);
        /*urlTextField.setText("https://www.alexu.edu.eg/index.php/en/discover-au/4025-diamond-jubilee-celebrations-launch" +
                "-in-alexandria-university");*/

        depthTextField = new TextField();
        depthTextField.setPromptText("Depth");
        depthTextField.setMinSize(200, 20);

        numberOfThreadsTextField = new TextField();
        numberOfThreadsTextField.setPromptText("Number Of Threads");
        numberOfThreadsTextField.setMinSize(200, 20);

        VBox labelsLayout = new VBox(28);
        labelsLayout.getChildren().addAll(urlLabel, depthLabel, numberOfThreadsLabel);

        VBox textFieldsLayout = new VBox(20);
        textFieldsLayout.getChildren().addAll(urlTextField, depthTextField, numberOfThreadsTextField);

        HBox informationLayout = new HBox(40);
        informationLayout.setAlignment(Pos.CENTER);
        informationLayout.getChildren().addAll(labelsLayout, textFieldsLayout);

        Button validateButton = new Button("Validate");
        validateButton.setMinSize(10, 5);
        validateButton.setOnAction(e -> {
            if (validateFields()) {
                long start = System.currentTimeMillis();
                ThreadValidate threadValidate = new ThreadValidate(urlTextField.getText(),
                        0, Integer.parseInt(depthTextField.getText()),
                        Integer.parseInt(numberOfThreadsTextField.getText()));
                threadValidate.start();
                try {
                    threadValidate.join();
                } catch (InterruptedException interruptedException) {}
                long end = System.currentTimeMillis();
                elapsedTime = end - start;
                displayResult(window);
            }
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(titleLabel, informationLayout, validateButton);

        Scene scene = new Scene(layout, 450, 250);
        window.setScene(scene);
    }

    private static void displayResult(Stage window) {
        Label numberOfValidLinksLabel = new Label("Number Of Valid Links: ");
        Label numberOfInvalidLinksLabel = new Label("Number Of Invalid Links: ");
        Label elapsedTimeLabel = new Label("Elapsed Time: ");
        Label validLinksCountLabel = new Label("" + LinkValidation.numberOfValidLinks);
        Label invalidLinksCountLabel = new Label("" + LinkValidation.numberOfInvalidLinks);
        Label elapsedTimeCountLabel = new Label("" + elapsedTime + " milliseconds");

        VBox labels = new VBox(10);
        labels.getChildren().addAll(numberOfValidLinksLabel, numberOfInvalidLinksLabel, elapsedTimeLabel);
        labels.setAlignment(Pos.CENTER);
        VBox counts = new VBox(10);
        counts.getChildren().addAll(validLinksCountLabel, invalidLinksCountLabel, elapsedTimeCountLabel);
        counts.setAlignment(Pos.CENTER);
        HBox layout = new HBox(50);
        layout.getChildren().addAll(labels, counts);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 450, 250);
        window.setScene(scene);
    }

    private static boolean validateFields() {
        if (!LinkValidation.singleLinkValidation(urlTextField.getText())) {
            AlertBox.display("Wrong input", "Enter valid link");
            return false;

        }

        if (!isNumeric(depthTextField.getText())) {
            AlertBox.display("Wrong input", "Enter valid depth");
            return false;
        }

        if (!isNumeric(numberOfThreadsTextField.getText())) {
            AlertBox.display("Wrong input", "Enter valid number of threads");
            return false;
        }

        return true;
    }

    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        if (strNum.contains(".")) {
            return false;
        }
        try {
            Integer d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        if (Integer.parseInt(strNum) <= 0) {
            return false;
        }
        return true;
    }
}
