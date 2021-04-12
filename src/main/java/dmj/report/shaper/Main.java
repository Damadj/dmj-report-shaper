package dmj.report.shaper;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/shaper.fxml"));
        stage.setResizable(false);
        stage.getIcons().add(new Image("/icons/folder.png"));
        stage.setTitle("Damadj's report shaper");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }
}
