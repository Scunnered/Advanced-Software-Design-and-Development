package app.diary.ui.service;

import app.diary.DiaryApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * CM2100 Advanced Software Design and Development Assessment Part 2 - Coursework (Deadline: 13/01/2020, 16:00)
 *
 * @author Ross Morrison 1105864
 */

@Service
public class StageLoaderService implements ApplicationContextAware {
    private ConfigurableApplicationContext applicationContext;

    public Parent loadStage(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(DiaryApplication.class.getResource(stage.getFileName()));
        loader.setControllerFactory(applicationContext::getBean);
        return loader.load();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}
