package app.diary.ui.controller;

import app.diary.core.Diary;
import app.diary.ui.service.AppointmentRepository;
import app.diary.ui.service.NotificationService;
import app.diary.ui.service.Stage;
import app.diary.ui.service.StageLoaderService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * CM2100 Advanced Software Design and Development Assessment Part 2 - Coursework (Deadline: 13/01/2020, 16:00)
 * @author Ross Morrison 1105864
 *
 * Controller class for the "home.fxml" UI file. It implements {@code Initializable}' in order to do any
 * JavaFX initialization. It implements {@code ApplicationContextAware} in order to shutdown the app context
 * when exiting.
 */

@Controller
public class HomeController implements Initializable, ApplicationContextAware {
    public MenuItem saveAllAppointmentsMenuItem;
    public MenuItem saveDiaryMenuItem;
    public MenuItem loadDiaryMenuItem;
    public MenuItem loadAppointmentsMenuItem;
    private ConfigurableApplicationContext appContext;

    @FXML
    private BorderPane contentPane;

    @Autowired
    private StageLoaderService stageLoaderService;

    @Autowired
    private PreloaderController preloaderController;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AllAppointmentsController allAppointmentsController;

    private Diary cachedDiary = new Diary();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadStage(Stage.APPOINTMENTS);
    }


    public void exit(ActionEvent actionEvent) {
        // stop spring context.
        appContext.stop();

        // exit the app using JavaFX api.
        Platform.exit();
    }

    private void updateMenus(){
        if(allAppointmentsController.getCurrentDiary() == null){
            this.saveAllAppointmentsMenuItem.setDisable(false);
            this.loadAppointmentsMenuItem.setDisable(false);

            this.saveDiaryMenuItem.setDisable(true);
            this.loadDiaryMenuItem.setDisable(true);
        } else {
            this.saveAllAppointmentsMenuItem.setDisable(true);
            this.loadAppointmentsMenuItem.setDisable(true);

            this.saveDiaryMenuItem.setDisable(false);
            this.loadDiaryMenuItem.setDisable(false);
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.appContext = (ConfigurableApplicationContext) applicationContext;
    }

    public void newAppointment(ActionEvent actionEvent) {
        loadStage(Stage.NEW_APPOINTMENT);
    }

    public void loadStage(Stage stage) {
        try {
            Parent parent = stageLoaderService.loadStage(stage);
            this.contentPane.setCenter(parent);
            updateMenus();
        } catch (Exception e) {
            notificationService.showErrorNotification(e.getMessage());
        }
    }

    public void calendarView(ActionEvent actionEvent) {
        loadStage(Stage.CALENDAR_VIEW);
    }

    public void showAllAppointments(ActionEvent actionEvent) {
        // save the current diary if present
        this.cachedDiary = allAppointmentsController.getCurrentDiary();
        if(cachedDiary == null) cachedDiary = new Diary();

        // remove the current diary to force the view to load all appointments
        allAppointmentsController.setCurrentDiary(null);
        loadStage(Stage.APPOINTMENTS);
    }

    public void loadAppointments(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);

        if (file != null && allAppointmentsController.getCurrentDiary() == null) {
            try {
                appointmentRepository.loadAppointmentsFromFile(file);
                allAppointmentsController.updateAppointments();
                notificationService.showSuccessMessage("Appointments were loaded successfully!");
            } catch (FileNotFoundException e) {
                notificationService.showErrorNotification(e.getMessage());
            }

        } else if (file != null && allAppointmentsController.getCurrentDiary() != null) {
            try {
                allAppointmentsController.getCurrentDiary().load(file);
                allAppointmentsController.updateAppointments();
                notificationService.showSuccessMessage("Diary Appointments loaded successfully!");
            } catch (Exception e) {
                notificationService.showErrorNotification(e.getMessage());
            }
        }
    }

    public void saveAppointments(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showSaveDialog(null);

        if (file != null && allAppointmentsController.getCurrentDiary() == null) {
            try {
                Diary.saveAppointments(appointmentRepository.getAppointments(), file);
                notificationService.showSuccessMessage("Saved Appointments Successfully!");
            } catch (IOException e) {
                notificationService.showErrorNotification(e.getLocalizedMessage());
            }
        } else if (file != null && allAppointmentsController.getCurrentDiary() != null) {
            try {
                allAppointmentsController.getCurrentDiary().save(file);
                notificationService.showSuccessMessage("Saved Diary Appointments Successfully!");
            } catch (IOException e) {
                notificationService.showErrorNotification(e.getLocalizedMessage());
            }
        }
    }

    public void viewDiaryAppointments(ActionEvent actionEvent) {
        allAppointmentsController.setCurrentDiary(cachedDiary);
        loadStage(Stage.APPOINTMENTS);
    }

    public Diary getCachedDiary() {
        return cachedDiary;
    }
}
