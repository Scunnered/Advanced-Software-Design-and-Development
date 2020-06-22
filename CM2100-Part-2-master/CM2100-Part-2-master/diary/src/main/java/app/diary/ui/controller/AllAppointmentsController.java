package app.diary.ui.controller;

import app.diary.core.Appointment;
import app.diary.core.Date;
import app.diary.core.Diary;
import app.diary.ui.service.AppointmentRepository;
import app.diary.ui.service.Stage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * CM2100 Advanced Software Design and Development Assessment Part 2 - Coursework (Deadline: 13/01/2020, 16:00)
 *
 * @author Ross Morrison 1105864
 */

@Controller
public class AllAppointmentsController implements Initializable {

    public TableView<AppointmentModel> appointmentsTable;
    public TableColumn<AppointmentModel, String> typeCol;
    public TableColumn<AppointmentModel, String> descriptionCol;
    public TableColumn<AppointmentModel, String> dateCol;
    public TableColumn<AppointmentModel, String> startTimeCol;
    public TableColumn<AppointmentModel, String> endTimeCol;
    public TableColumn<AppointmentModel, String> repeatTypeCol;
    public AnchorPane appointmentActionPane;
    public TextField searchByDescriptionField;
    public DatePicker appointmentDateField;
    public Text title;
    public Button editBtn;
    public Button addAppointmentBtn;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentController appointmentController;

    @Autowired
    private HomeController homeController;

    // the current diary, if we are displaying diary appointments.
    private Diary diary;

    private Appointment selectedAppointment;

    // The date converter that will allow the date to be displayed in the format
    // date-month-year.
    private final StringConverter<LocalDate> dateConverter = new StringConverter<LocalDate>() {
        private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        @Override
        public String toString(LocalDate localDate) {
            if (localDate == null) {
                return "";
            }

            return dateFormatter.format(localDate);
        }

        @Override
        public LocalDate fromString(String dateString) {
            if (dateString == null || dateString.trim().isEmpty()) {
                return null;
            }

            return LocalDate.parse(dateString, dateFormatter);
        }
    };


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        repeatTypeCol.setCellValueFactory(new PropertyValueFactory<>("repeatType"));

        // load the current appointments from the repository.
        updateAppointments();

        // enable appointment action pane if there is at least one selected appointment model
        appointmentsTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldModel, newModel) -> {
            if (newModel != null) {
                this.selectedAppointment = newModel.getAppointment();
                appointmentActionPane.setDisable(false);
            } else {
                this.selectedAppointment = null;
                appointmentActionPane.setDisable(true);
            }
        });

        // filter the appointments based on the keyword found in the description field.
        searchByDescriptionField.setOnKeyReleased(actionEvent -> {
            this.filterByKeyWord(searchByDescriptionField.getText());
        });

        // set the date format for the date fields
        appointmentDateField.setConverter(dateConverter);

        if (diary != null) {
            this.title.setText("Diary Appointments");
            this.editBtn.setDisable(true);
            addAppointmentBtn.setVisible(true);
        }
    }

    public Diary getCurrentDiary(){
        return diary;
    }

    public void setCurrentDiary(Diary diary){
        this.diary = diary;
    }

    // reload the appointments from the repository. Useful for reflecting
    // new appointments loaded from a file.
    public void updateAppointments() {
        List<Appointment> appointments = getAppointments();
        List<AppointmentModel> models = this.getAppointmentModels(appointments);
        appointmentsTable.setItems(FXCollections.observableArrayList(models));
    }

    // convert raw appointments to AppointmentModel objects that can be
    // rendered by the JavaFX table.
    List<AppointmentModel> getAppointmentModels(List<Appointment> appointments) {
        return appointments.stream().map(AppointmentModel::new).collect(Collectors.toList());
    }

    // get the list of appointments.
    // Either from the repository, or from the diary.
    private List<Appointment> getAppointments() {
        if (diary != null) {
            return diary.getAppointments();
        } else {
            return appointmentRepository.getAppointments();
        }
    }

    // filter the appointments in the table that have the specified keyword in their description
    private void filterByKeyWord(String keyword) {
        List<Appointment> filtered = getAppointments().stream().filter(it -> {
            return it.getDescription().toLowerCase().contains(keyword.toLowerCase());
        }).collect(Collectors.toList());

        List<AppointmentModel> filteredModels = this.getAppointmentModels(filtered);

        this.appointmentsTable.setItems(FXCollections.observableArrayList(filteredModels));
    }

    public void editAppointment(ActionEvent actionEvent) {
        homeController.loadStage(Stage.NEW_APPOINTMENT);
        appointmentController.setSelectedAppointment(this.selectedAppointment);
    }

    public void deleteAppointment(ActionEvent actionEvent) {
        if(diary == null){
            appointmentRepository.deleteAppointment(this.selectedAppointment);
        } else {
            diary.getAppointments().remove(selectedAppointment);
        }

        updateAppointments();
    }

    public void filterByAppointmentDate(ActionEvent actionEvent) {
        LocalDate value = appointmentDateField.getValue();

        if (value == null) {
            List<AppointmentModel> filteredModels = this.getAppointmentModels(getAppointments());
            this.appointmentsTable.setItems(FXCollections.observableArrayList(filteredModels));
            return;
        }

        Date date = new Date(
                value.getYear(),
                value.getMonthValue(),
                value.getDayOfMonth()
        );

        List<Appointment> appointmentsOnDate = appointmentRepository.getAppointmentsOnDate(date);
        if (diary != null) {
            appointmentsOnDate = diary.findAppointments(date);
        }

        List<AppointmentModel> appointmentModels = appointmentsOnDate.stream().map(AppointmentModel::new)
                .collect(Collectors.toList());

        appointmentsTable.setItems(FXCollections.observableArrayList(appointmentModels));
    }

    public void clearAllAppointments(ActionEvent actionEvent) {
        if (diary == null) {
            appointmentRepository.clearAppointments();
        } else {
            diary.getAppointments().clear();
        }

        updateAppointments();
    }


    public void addAppointment(ActionEvent actionEvent) {
        this.homeController.loadStage(Stage.ADD_DIARY_APPOINTMENT);
    }
}
