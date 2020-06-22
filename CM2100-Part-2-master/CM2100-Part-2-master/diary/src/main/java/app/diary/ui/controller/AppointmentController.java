package app.diary.ui.controller;

import app.diary.core.*;
import app.diary.ui.service.AppointmentRepository;
import app.diary.ui.service.NotificationService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * CM2100 Advanced Software Design and Development Assessment Part 2 - Coursework (Deadline: 13/01/2020, 16:00)
 *
 * @author Ross Morrison 1105864
 */

@Controller
public class AppointmentController implements Initializable {
    public ComboBox<String> appointmentTypeCBox;
    public AnchorPane appointmentTimePane;
    public CheckBox repeatAppointmentCheckBox;
    public AnchorPane repeatAppointmentPane;
    public ComboBox<RepeatType> repeatTypeCBox;
    public ComboBox<String> startTimeHourCBox;
    public ComboBox<String> startTimeMinuteCBox;
    public ComboBox<String> endTimeHourCBox;
    public ComboBox<String> endTimeMinuteCBox;
    public DatePicker appointmentDateField;
    public TextArea descriptionField;
    public DatePicker endRepeatDate;
    public Text headerLabel;
    public Button addAppointmentBtn;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    // appointment to be edited (if in edit mode)
    private Appointment selectedAppointment;

    public void setSelectedAppointment(Appointment selectedAppointment) {
        this.selectedAppointment = selectedAppointment;

        // set the header text to reflect the edit mode
        headerLabel.setText("Edit Appointment");

        // update the button to reflect the edit mode
        addAppointmentBtn.setText("Edit Appointment");

        // set the description
        descriptionField.setText(selectedAppointment.getDescription());

        // convert generic date to LocalDate and update the appointment date control.
        Date date = selectedAppointment.getDate();
        LocalDate localDate = LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
        appointmentDateField.setValue(localDate);

        // set the type of the appointment
        if (selectedAppointment instanceof TimedAppointment) {
            // update the appointment type
            appointmentTypeCBox.getSelectionModel().select("Timed Appointment");

            DecimalFormat decimalFormat = new DecimalFormat("00");

            // update the start time
            TimedAppointment t = (TimedAppointment) selectedAppointment;
            Time startTime = t.getStartTime();
            startTimeHourCBox.getSelectionModel().select(decimalFormat.format(startTime.getHour()));
            endTimeMinuteCBox.getSelectionModel().select(decimalFormat.format(startTime.getMinute()));

            // update the end time
            Time endTime = t.getEndTime();
            endTimeHourCBox.getSelectionModel().select(decimalFormat.format(endTime.getHour()));
            endTimeMinuteCBox.getSelectionModel().select(decimalFormat.format(endTime.getMinute()));
        }

        if (selectedAppointment instanceof RepeatAppointment) {
            RepeatAppointment r = ((RepeatAppointment) selectedAppointment);

            // this is a repeat appointment, so update the repeat checkbox
            repeatAppointmentCheckBox.setSelected(true);

            // set the repeat type
            repeatTypeCBox.getSelectionModel().select(r.getRepeatType());

            // set the end repeat date
            Date genericEndRepeatDate = Date.toGenericDate(r.getEndDate());
            LocalDate d = LocalDate.of(genericEndRepeatDate.getYear(), genericEndRepeatDate.getMonth(),
                    genericEndRepeatDate.getDay());
            endRepeatDate.setValue(d);

        }
    }

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
        // populate the combobox with the appointment types
        appointmentTypeCBox.setItems(FXCollections.observableArrayList("Timed Appointment", "Untimed Appointment"));

        // set the default appointment type
        appointmentTypeCBox.getSelectionModel().select("Untimed Appointment");

        // enable/disable the appointment time pane based on the user selection.
        appointmentTypeCBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.equals("Timed Appointment")) {
                appointmentTimePane.setDisable(false);
            } else {
                appointmentTimePane.setDisable(true);
            }
        });

        // enable/disable the repeat appointment pane based on user selection.
        repeatAppointmentCheckBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            this.repeatAppointmentPane.setDisable(!newValue);
        });

        // set the repeat types
        repeatTypeCBox.setItems(FXCollections.observableArrayList(RepeatType.values()));

        // set the default repeat value
        repeatTypeCBox.getSelectionModel().select(RepeatType.DAILY);

        // set the hours
        startTimeHourCBox.setItems(FXCollections.observableArrayList(getHours()));
        endTimeHourCBox.setItems(FXCollections.observableArrayList(getHours()));

        // set the default hours
        startTimeHourCBox.getSelectionModel().select("23");
        endTimeHourCBox.getSelectionModel().select("23");

        // set the minutes
        startTimeMinuteCBox.setItems(FXCollections.observableArrayList(getMinutes()));
        endTimeMinuteCBox.setItems(FXCollections.observableArrayList(getMinutes()));

        // set the default minutes
        startTimeMinuteCBox.getSelectionModel().select("59");
        endTimeMinuteCBox.getSelectionModel().select("59");

        // set the date converter for the date-time fields
        appointmentDateField.setConverter(dateConverter);
        endRepeatDate.setConverter(dateConverter);
    }


    // get hours from 0 to 23 in a formatted way
    private List<String> getHours() {
        DecimalFormat f = new DecimalFormat("00");

        return IntStream.range(0, 24).mapToObj(f::format)
                .collect(Collectors.toList());
    }

    // get minutes from 0 to 59 in a formatted way
    private List<String> getMinutes() {
        DecimalFormat f = new DecimalFormat("00");

        return IntStream.range(0, 60).mapToObj(f::format)
                .collect(Collectors.toList());
    }

    private void clearFields() {
        this.appointmentTypeCBox.getSelectionModel().select("Untimed Appointment");
        this.appointmentDateField.getEditor().clear();
        this.descriptionField.clear();
        this.repeatTypeCBox.getSelectionModel().select(RepeatType.DAILY);
        this.repeatAppointmentCheckBox.setSelected(false);
        this.startTimeHourCBox.getSelectionModel().select("23");
        this.endTimeHourCBox.getSelectionModel().select("23");
        this.startTimeMinuteCBox.getSelectionModel().select("59");
        this.endTimeMinuteCBox.getSelectionModel().select("59");
        this.endRepeatDate.getEditor().clear();
    }

    public void addAppointment(ActionEvent actionEvent) {
        LocalDate appointmentDate = appointmentDateField.getValue();
        if (appointmentDate == null) {
            notificationService.showErrorNotification("Enter the appointment date!");
            return;
        }

        String description = descriptionField.getText();
        if (description.isEmpty()) {
            notificationService.showErrorNotification("Enter the appointment description");
            return;
        }

        if (repeatAppointmentPane.isDisabled()) {
            // the new appointment is a one-off appointment

            if (appointmentTimePane.isDisabled()) {
                // this is a one-off untimed appointment. Create the the appointment and add it to the
                // list of existing appointments if it does not clash with any existing appointment.
                Date date = new Date(appointmentDate.getYear(), appointmentDate.getMonthValue(), appointmentDate.getDayOfMonth());
                UntimedAppointment untimedAppointment = new UntimedAppointment(description, date);

                if (selectedAppointment == null && appointmentRepository.appointmentClashesWithAnother(untimedAppointment)) {
                    notificationService.showErrorNotification("Appointment clash detected. Try again.");
                    return;
                }

                if (selectedAppointment != null && appointmentRepository.appointmentClashesWithAnother(selectedAppointment, untimedAppointment)) {
                    notificationService.showErrorNotification("Appointment clash detected. Try again.");
                    return;
                }

                if (selectedAppointment == null) {
                    appointmentRepository.addAppointment(untimedAppointment);
                    notificationService.showSuccessMessage("Appointment was added successfully!");
                    clearFields();
                } else {
                    appointmentRepository.editAppointment(selectedAppointment, untimedAppointment);
                    selectedAppointment = untimedAppointment;
                    notificationService.showSuccessMessage("Apppointment was edited successfully!");
                }


            } else {
                // extract the start time.
                String startTimeHour = startTimeHourCBox.getSelectionModel().getSelectedItem();
                String startTimeMinute = startTimeMinuteCBox.getSelectionModel().getSelectedItem();
                Time startTime = new Time(Integer.parseInt(startTimeHour), Integer.parseInt(startTimeMinute));

                // extract the end time.
                String endTimeHour = endTimeHourCBox.getSelectionModel().getSelectedItem();
                String endTimeMinute = endTimeMinuteCBox.getSelectionModel().getSelectedItem();
                Time endTime = new Time(Integer.parseInt(endTimeHour), Integer.parseInt(endTimeMinute));

                // ensure the end time is ahead of the start time.
                if (endTime.compareTo(startTime) == 0 || endTime.compareTo(startTime) < 0) {
                    notificationService.showErrorNotification("Invalid times given. Check and try again.");
                    return;
                }

                // create the appointment date
                Date date = new Date(appointmentDate.getYear(), appointmentDate.getMonthValue(), appointmentDate.getDayOfMonth());

                // create the timed appointment
                TimedAppointment timedAppointment = new TimedAppointment(description, date, startTime, endTime);

                // ensure the appointment does not clash with any other existing appointment.
                if (selectedAppointment == null && appointmentRepository.appointmentClashesWithAnother(timedAppointment)) {
                    notificationService.showErrorNotification("Appointment clash. Check and try again.");
                    return;
                }

                if (selectedAppointment != null &&
                        appointmentRepository.appointmentClashesWithAnother(selectedAppointment, timedAppointment)) {
                    notificationService.showErrorNotification("Appointment clash. Check and try again.");
                    return;
                }

                // add the appointment and clear any fields.
                if (selectedAppointment == null) {
                    appointmentRepository.addAppointment(timedAppointment);
                    this.clearFields();
                    notificationService.showSuccessMessage("Added appointment successfully!");
                } else {
                    appointmentRepository.editAppointment(selectedAppointment, timedAppointment);
                    selectedAppointment = timedAppointment;
                    notificationService.showSuccessMessage("Edited appointment successfully!");
                }


            }
        } else {
            // the new appointment is a repeat appointment


            if (appointmentTimePane.isDisabled()) {
                // this is a repeat untimed appointment. Create the the appointment and add it to the
                // list of existing appointments if it does not clash with any existing appointment.
                Date startDate = new Date(appointmentDate.getYear(), appointmentDate.getMonthValue(), appointmentDate.getDayOfMonth());

                // get the end repeat date
                LocalDate endRepeatDate = this.endRepeatDate.getValue();
                if (endRepeatDate == null) {
                    notificationService.showErrorNotification("Provide the end repeat date!");
                    return;
                }

                Date endRepeatDateGeneric = new Date(endRepeatDate.getYear(), endRepeatDate.getMonthValue(), endRepeatDate.getDayOfMonth());

                if (endRepeatDateGeneric.compareTo(startDate) <= 0) {
                    notificationService.showErrorNotification("The end repeat date is invalid!");
                    return;
                }

                // create the untimed repeat appointment.
                UntimedRepeatAppointment untimedRepeatAppointment =
                        new UntimedRepeatAppointment(description, startDate.toJavaDate(), endRepeatDateGeneric.toJavaDate(),
                                repeatTypeCBox.getSelectionModel().getSelectedItem());

                // ensure the appointment does not clash with any existing one
                if (selectedAppointment == null && appointmentRepository.appointmentClashesWithAnother(untimedRepeatAppointment)) {
                    notificationService.showErrorNotification("There was an appointment clash. Try again.");
                    return;
                }

                if (selectedAppointment != null && appointmentRepository.appointmentClashesWithAnother(selectedAppointment, untimedRepeatAppointment)) {
                    notificationService.showErrorNotification("There was an appointment clash. Try again.");
                    return;
                }

                if (selectedAppointment == null) {
                    appointmentRepository.addAppointment(untimedRepeatAppointment);
                    notificationService.showSuccessMessage("Appointment was added successfully!");
                    clearFields();
                } else {
                    appointmentRepository.editAppointment(selectedAppointment, untimedRepeatAppointment);
                    selectedAppointment = untimedRepeatAppointment;
                    notificationService.showSuccessMessage("Appointment was edited successfully!");
                }


            } else {
                // this is a timed repeat appointment
                Date startDate = new Date(appointmentDate.getYear(), appointmentDate.getMonthValue(), appointmentDate.getDayOfMonth());

                // get the end repeat date
                LocalDate endRepeatDate = this.endRepeatDate.getValue();
                if (endRepeatDate == null) {
                    notificationService.showErrorNotification("Provide the end repeat date!");
                    return;
                }

                Date endRepeatDateGeneric = new Date(endRepeatDate.getYear(), endRepeatDate.getMonthValue(), endRepeatDate.getDayOfMonth());

                if (endRepeatDateGeneric.compareTo(startDate) <= 0) {
                    notificationService.showErrorNotification("The end repeat date is invalid!");
                    return;
                }

                // extract the start time.
                String startTimeHour = startTimeHourCBox.getSelectionModel().getSelectedItem();
                String startTimeMinute = startTimeMinuteCBox.getSelectionModel().getSelectedItem();
                Time startTime = new Time(Integer.parseInt(startTimeHour), Integer.parseInt(startTimeMinute));

                // extract the end time.
                String endTimeHour = endTimeHourCBox.getSelectionModel().getSelectedItem();
                String endTimeMinute = endTimeMinuteCBox.getSelectionModel().getSelectedItem();
                Time endTime = new Time(Integer.parseInt(endTimeHour), Integer.parseInt(endTimeMinute));

                // ensure the end time is ahead of the start time.
                if (endTime.compareTo(startTime) == 0 || endTime.compareTo(startTime) < 0) {
                    notificationService.showErrorNotification("Invalid times given. Check and try again.");
                    return;
                }

                // create the appointment
                TimedRepeatAppointment timedRepeatAppointment = new TimedRepeatAppointment(description,
                        startDate.toJavaDate(), endRepeatDateGeneric.toJavaDate(), startTime, endTime,
                        repeatTypeCBox.getSelectionModel().getSelectedItem());


                // ensure the appointment does not clash.
                if (selectedAppointment == null && appointmentRepository.appointmentClashesWithAnother(timedRepeatAppointment)) {
                    notificationService.showErrorNotification("Appointment clash detected. Try again.");
                    return;
                }

                if (selectedAppointment != null && appointmentRepository.appointmentClashesWithAnother(selectedAppointment, timedRepeatAppointment)) {
                    notificationService.showErrorNotification("Appointment clash detected. Try again.");
                    return;
                }

                if (selectedAppointment == null) {
                    appointmentRepository.addAppointment(timedRepeatAppointment);
                    notificationService.showSuccessMessage("Appointment was added successfully!");
                    clearFields();
                } else {
                    appointmentRepository.editAppointment(selectedAppointment, timedRepeatAppointment);
                    this.selectedAppointment = timedRepeatAppointment;
                    notificationService.showSuccessMessage("Appointment edited successfully!");
                }

            }
        }

    }
}
