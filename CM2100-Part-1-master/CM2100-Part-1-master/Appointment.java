/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Diary;

import java.util.Comparator;

/**
 *
 * @author 1105864
 */

// Make Appointment an abstract class.
public abstract class Appointment implements Comparable<Appointment>{
    private Date date;
    private String description;

    public Appointment(Date date, String description) {
        this.date = date;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", description, date);  //Complete the toString() method so that it returns a String in the format “Description (Date of appointment)”. E.g. “Annual leave (29-Dec-2019)”.
    }

    //Add a public occursOn(..) method that checks if the appointment occurs on a given data. The input parameter is a Date object. The method return true if the appointment occurs on the given date.
    public boolean occursOn (Date date){
        if (date == null || this.date == null) return false;
        return this.date.compareTo(date) == 0;
    }

    @Override
    public int compareTo(Appointment appointment) {
        return this.description.compareToIgnoreCase(appointment.description);
    }

    //Add a public, abstract clash(…) method that checks if the appointment clashes with another appointment.
    // The input parameter is an Appointment object. The method returns a boolean true/false value.
    public abstract boolean clash(Appointment appointment);

    //Add a nested class to Appointment (or create a separate class if you prefer), called DateTimeComparator
    // which implements the Comparator interface.
    public static class DateTimeComparator implements Comparator<Appointment> {

        @Override
        public int compare(Appointment a1, Appointment a2) {
            //  If comparison of dates settles the issue, no need to compare time
            int dateComparison = a1.date.compareTo(a2.date);
            if ( dateComparison != 0) return dateComparison;

            //  If dates are equal, check if both are timedAppointments
            boolean areBothTimed = a1 instanceof TimedAppointment && a2 instanceof TimedAppointment;
            if (areBothTimed){
                TimedAppointment timedAppointment1 = (TimedAppointment) a1;
                TimedAppointment timedAppointment2 = (TimedAppointment) a2;

                //  Let the comparison be on startTime, irrespective of endTime
                return timedAppointment1.getStartTime().compareTo(timedAppointment2.getStartTime());
            }

            //  If both are untimed, or even one is untimed, then assume them to be equal
            return 0;
        }
    }
    
}
