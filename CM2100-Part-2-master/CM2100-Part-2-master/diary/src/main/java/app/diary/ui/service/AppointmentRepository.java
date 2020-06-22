package app.diary.ui.service;

import app.diary.core.Appointment;
import app.diary.core.Date;
import app.diary.core.Diary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CM2100 Advanced Software Design and Development Assessment Part 2 - Coursework (Deadline: 13/01/2020, 16:00)
 *
 * @author Ross Morrison 1105864
 */

@Service
public class AppointmentRepository {
    // appointment list
    private List<Appointment> appointmentList = new ArrayList<>();

    // return the current list of appointments.
    public List<Appointment> getAppointments() {
        return appointmentList;
    }

    // load appointments from file, and then update the current appointment list.
    public void loadAppointmentsFromFile(File file) throws FileNotFoundException {
        this.appointmentList = Diary.loadAppointmentsFromFile(file);
    }

    public void addAppointment(Appointment appointment) {
        this.appointmentList.add(appointment);
    }

    public boolean appointmentClashesWithAnother(Appointment newAppointment){
        return this.appointmentList.stream().anyMatch(appointment -> appointment.clash(newAppointment));
    }

    public boolean appointmentClashesWithAnother(Appointment selectedAppointment, Appointment replacementAppointment) {
        appointmentList.remove(selectedAppointment);
        boolean result = appointmentClashesWithAnother(replacementAppointment);
        appointmentList.add(selectedAppointment);
        return result;
    }

    public void editAppointment(Appointment selectedAppointment, Appointment replacementAppointment) {
        appointmentList.remove(selectedAppointment);
        appointmentList.add(replacementAppointment);
    }

    public void deleteAppointment(Appointment appointment){
        appointmentList.remove(appointment);
    }

    public boolean appointmentOccursOn(Date date) {
        return this.appointmentList.stream().anyMatch(appointment -> appointment.occursOn(date));
    }

    public List<Appointment> getAppointmentsOnDate(Date date){
        return appointmentList.stream().filter(appointment -> appointment.occursOn(date))
                .collect(Collectors.toList());
    }

    public void clearAppointments(){
        this.appointmentList.clear();
    }
}
