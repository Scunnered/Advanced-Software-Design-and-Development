package app.diary;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * CM2100 Advanced Software Design and Development Assessment Part 2 - Coursework (Deadline: 13/01/2020, 16:00)
 *
 * @author Ross Morrison 1105864
 */

@SpringBootApplication
public class DiaryApplication extends Application {
    private ConfigurableApplicationContext ctx;

    @Override
    public void start(Stage stage) throws Exception {
        // load SpringBoot context and save it.
        ctx  = SpringApplication.run(DiaryApplication.class);

        // construct the loader for the fxml/home.fxml file.
        FXMLLoader loader = new FXMLLoader(DiaryApplication.class.getResource("fxml/home.fxml"));

        // let spring be the one to instantiate controllers.
        loader.setControllerFactory(ctx::getBean);

        // load the parent container from the fxml loader.
        Parent parent = loader.load();

        // configure the stage with a scene, title and display it.
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Appointment App");
        stage.setMaximized(true);
        stage.show();

        // when the 'close' button is clicked, exit the app.
        stage.setOnCloseRequest(event -> {
            this.ctx.stop();
            Platform.exit();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
