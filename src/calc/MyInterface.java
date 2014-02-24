package calc;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class that forms the basis of a custom UI built with JavaFX.
 *
 * @author Ian Murphy - 20057028
 */
public class MyInterface extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("interface.fxml"));
        stage.setScene(new Scene(root, 200, 200));
        stage.show();
    }


}
