package app.diary.ui.controller;

import app.diary.core.Appointment;
import app.diary.ui.service.AppointmentRepository;
import app.diary.ui.service.NotificationService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * CM2100 Advanced Software Design and Development Assessment Part 2 - Coursework (Deadline: 13/01/2020, 16:00)
 *
 * @author Ross Morrison 1105864
 */

@Controller
public class AddDiaryAppointmentController implements Initializable {
    public TableView<AppointmentModel> appointmentsTable;
    public TableColumn<AppointmentModel, String> typeCol;
    public TableColumn<AppointmentModel, String> repeatTypeCol;
    public TableColumn<AppointmentModel, String> descriptionCol;
    public TableColumn<AppointmentModel, String> dateCol;
    public TableColumn<AppointmentModel, String> endTimeCol;
    public TableColumn<AppointmentModel, String> startTimeCol;

    public TableView<AppointmentModel> diaryAppointmentsTable;
    public TableColumn<AppointmentModel, String> descriptionCol1;
    public TableColumn<AppointmentModel, String> typeCol1;
    public TableColumn<AppointmentModel, String> dateCol1;
    public TableColumn<AppointmentModel, String> startTimeCol1;
    public TableColumn<AppointmentModel, String> endTimeCol1;
    public TableColumn<AppointmentModel, String> repeatTypeCol1;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private HomeController homeController;

    @Autowired
    private AllAppointmentsController allAppointmentsController;

    @Autowired
    private NotificationService notificationService;

    private Appointment selectedAppointment;
    private Appointment selectedDiaryAppointment;

    private List<Appointment> cachedAppointments;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // configure the table columns
        typeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        repeatTypeCol.setCellValueFactory(new PropertyValueFactory<>("repeatType"));

        // save the selected appointment when the table is clicked.
        appointmentsTable.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, oldModel, newModel) -> {
                    selectedAppointment = newModel.getAppointment();
        });



        typeCol1.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        descriptionCol1.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateCol1.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeCol1.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeCol1.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        repeatTypeCol1.setCellValueFactory(new PropertyValueFactory<>("repeatType"));

        // save the selected diary appointment when the table is clicked.
        diaryAppointmentsTable.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, oldModel, newModel) -> {
                    selectedDiaryAppointment = newModel.getAppointment();
                });

        // save a copy of the existing appointments to be used when adding or removing diary appointments.
        // This is to avoid messing up with the existing appointments.
        this.cachedAppointments = appointmentRepository.getAppointments();

        updateAppointments();

    }

    // load each of the tables appointments
    public void updateAppointments() {
        List<AppointmentModel> models = allAppointmentsController.getAppointmentModels(cachedAppointments);
        appointmentsTable.setItems(FXCollections.observableArrayList(models));

        // populate the diary appointment table
        List<Appointment> diaryAppointments = homeController.getCachedDiary().getAppointments();
        List<AppointmentModel> diaryAppointmentModels =
                allAppointmentsController.getAppointmentModels(diaryAppointments);
        diaryAppointmentsTable.setItems(FXCollections.observableArrayList(diaryAppointmentModels));
    }

    public void removeDiaryAppointment(ActionEvent actionEvent) {
        if(this.selectedDiaryAppointment == null){
            notificationService.showErrorNotification("Select appointment from 'Diary Appointments' Table");
            return;
        }

        // add the diary appointment to the list of cached appointments
        cachedAppointments.add(selectedDiaryAppointment);

        // remove the diary appointment from the list of diary appointments
        homeController.getCachedDiary().getAppointments().remove(selectedDiaryAppointment);

        // remove the selected diary appointment
        selectedDiaryAppointment = null;

        // update the tables
        updateAppointments();
    }

    public void addDiaryAppointment(ActionEvent actionEvent) {
        if(this.selectedAppointment == null){
            notificationService.showErrorNotification("Select appointment from 'All Appointments' Table");
            return;
        }

        // add the new appointment to the list of appointments in the diary
        homeController.getCachedDiary().getAppointments().add(this.selectedAppointment);

        // remove the added appointment from the all appointment table
        cachedAppointments.remove(this.selectedAppointment);

        // remove the selected appointment
        this.selectedAppointment = null;

        // update the tables
        updateAppointments();

    }
}
