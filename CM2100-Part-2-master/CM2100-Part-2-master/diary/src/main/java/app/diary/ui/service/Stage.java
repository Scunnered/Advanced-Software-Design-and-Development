package app.diary.ui.service;

/**
 * CM2100 Advanced Software Design and Development Assessment Part 2 - Coursework (Deadline: 13/01/2020, 16:00)
 *
 * @author Ross Morrison 1105864
 */

public enum Stage {
    PRELOADER("fxml/preloader.fxml"),
    APPOINTMENTS("fxml/appointments.fxml"),
    NEW_APPOINTMENT("fxml/new_appointment.fxml"),
    CALENDAR_VIEW("fxml/calendar_view.fxml"),
    ADD_DIARY_APPOINTMENT("fxml/add_diary_appointment.fxml");

    private String fileName;
    Stage(String fileName){
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
