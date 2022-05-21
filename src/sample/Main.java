/*
-----------------------------------------------------------------------------------------------------

        this program is implemented with javafx so you have to add the bath to the library of javafx folder
        before running

        steps for IntelliJ IDE:
        1- Run >> Edit Configurations >> Modify options >> Add VM options.
        2- --module-path "" --add-modules javafx.controls,javafx.fxml
        3- copy step number 2 and paste it in the VM options.
        4- copy the path of the lib file located in the project file and paste it between the quotations marks.
        5- press apply then okay.

        steps for Eclipse IDE:
        1- Run >> Run Configurations... >> Arguments.
        2- --module-path "" --add-modules javafx.controls,javafx.fxml
        3- copy step number 2 and paste it in the VM arguments.
        4- copy the path of the lib file located in the project file and paste it between the quotations marks.
        5- press apply then Run.

        -----------------------------------------------------------------------------------------------------
*/
package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Stage window = primaryStage;
        window.setTitle("Hyperlinks Integrity Checker for Web Documents");
        GUI.displayStartingUI(window);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
/*
test cases of time elapsed when increasing the number of threads
from 1 thread to 20 threads and ending with the 180(the total number of links)
600418,301168,218293,155288,132225,114826,105327,82973,84977,82181,78392,74925,72314,71405,69302,68346,66183,63932,62439,59668
*/