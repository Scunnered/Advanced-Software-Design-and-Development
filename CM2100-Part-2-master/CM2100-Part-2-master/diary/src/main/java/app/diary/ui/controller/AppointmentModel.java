package app.diary.ui.controller;

import app.diary.core.*;
import javafx.beans.property.SimpleStringProperty;

/**
 * CM2100 Advanced Software Design and Development Assessment Part 2 - Coursework (Deadline: 13/01/2020, 16:00)
 *
 * @author Ross Morrison 1105864
 */

public class AppointmentModel {
    private final Appointment appointment;

    public AppointmentModel(Appointment appointment) {
        this.appointment = appointment;
    }

    public String getAppointmentType(){
        if(appointment instanceof TimedRepeatAppointment) {
            return "Timed Repeat Appointment";
        } else if(appointment instanceof UntimedRepeatAppointment) {
            return "Untimed Repeat Appointment";
        } else if(appointment instanceof TimedAppointment) {
            return "Timed Appointment";
        } else {
            return "Untimed Appointment";
        }
    }

    public String getRepeatType(){
        if(appointment instanceof RepeatAppointment) {
            RepeatAppointment repeatAppointment = (RepeatAppointment)appointment;
            return repeatAppointment.getRepeatType().toString();
        }
        return "";
    }

    public String getDate(){
        return this.appointment.getDate().toString();
    }

    public String getDescription(){
        return this.appointment.getDescription();
    }

    public String getStartTime(){
        if(this.appointment instanceof TimedAppointment){
            TimedAppointment t = (TimedAppointment) this.appointment;
            return t.getStartTime().toString();
        }
        return "";
    }

    public String getEndTime(){
        if(this.appointment instanceof  TimedAppointment){
            TimedAppointment t = (TimedAppointment) this.appointment;
            return t.getEndTime().toString();
        }

        return "";
    }

    public SimpleStringProperty appointmentTypeProperty(){
        return new SimpleStringProperty(getAppointmentType());
    }

    public SimpleStringProperty repeatTypeProperty(){
        return new SimpleStringProperty(getRepeatType());
    }

    public SimpleStringProperty dateProperty(){
        return new SimpleStringProperty(getDate());
    }

    public SimpleStringProperty descriptionProperty(){
        return new SimpleStringProperty(getDescription());
    }

    public SimpleStringProperty startTimeProperty(){
        return new SimpleStringProperty(getStartTime());
    }

    public SimpleStringProperty endTimeProperty(){
        return new SimpleStringProperty(getEndTime());
    }

    public Appointment getAppointment() {
        return appointment;
    }
}
