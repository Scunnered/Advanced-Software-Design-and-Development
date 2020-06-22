package app.diary.ui.controller;

import app.diary.core.Appointment;
import app.diary.core.Date;
import app.diary.core.RepeatAppointment;
import app.diary.core.UntimedAppointment;
import app.diary.ui.service.AppointmentRepository;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * CM2100 Advanced Software Design and Development Assessment Part 2 - Coursework (Deadline: 13/01/2020, 16:00)
 *
 * @author Ross Morrison 1105864
 */

@Controller
public class CalendarViewController implements Initializable {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @FXML
    public BorderPane calendarPane;

    //construct a cell factory
    private final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
        @Override
        public DateCell call(final DatePicker datePicker) {
            return new DateCell() {
                @Override public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    //if flag is true and date is within range, set style
                    Date genericDate = new Date(item.getYear(), item.getMonthValue(), item.getDayOfMonth());
                    if (appointmentRepository.appointmentOccursOn(genericDate)) {
                        setStyle("-fx-background-color: #ffc0cb;");
                    }
                }
            };
        }
    };



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatePicker d = new DatePicker(LocalDate.now());
        d.setDayCellFactory(dayCellFactory);

        DatePickerSkin datePickerSkin = new DatePickerSkin(d);
        calendarPane.setCenter(datePickerSkin.getPopupContent());
    }
}
