/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Diary;

/**
 *
 * @author 1105864
 */
public class TimedAppointment extends Appointment{
    //Make TimedAppointment a concrete subclass of Appointment.
    private Time startTime;
    private Time endTime;

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    //Add a constructor for TimedAppointment which takes 4 parameters: The 1st parameter is the appointment’s description.
    // The 2nd parameter is the appointment’s date. The 3rd parameter is the appointment’s starting time.
    // The 4th parameter is the appointment’s ending time.
    // You can assume that given starting time is always before or equal to the ending time. There is no need to check.
    public TimedAppointment(String description, Date date, Time startTime, Time endTime){
        super(date, description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    //Complete the concrete clash(…) method: The parameter is another Appointment.
    // The method returns true if the current TimedAppointment clashes with the other appointment given in the parameters.
    @Override
    public boolean clash(Appointment appointment) {

        if (appointment instanceof UntimedAppointment){
            //  If the other appointment is Untimed, then use the clash method of the supplied untimed appointment
            UntimedAppointment untimedAppointment = (UntimedAppointment) appointment;
            return untimedAppointment.clash(this);
        }else if (appointment instanceof TimedAppointment){
            TimedAppointment timedAppointment = (TimedAppointment) appointment;
            if (this.getDate().compareTo(timedAppointment.getDate()) != 0) return false;
            else{
                //  Date being the same, check time clash
//                System.out.printf("This [%s]\t Other [%s]\n", this, timedAppointment);
//                System.out.printf("this.endTime vs other.startTime %s\n", this.endTime.compareTo(timedAppointment.startTime));
//                System.out.printf("this.startTime vs other.endTime %s\n\n", this.startTime.compareTo(timedAppointment.endTime));
                return ! (this.endTime.compareTo(timedAppointment.startTime) <= 0
                        || this.startTime.compareTo(timedAppointment.endTime) >= 0);
            }
        }else {
            return false;
        }
    }

    //Complete the toString() method to return a String in the format “Description (Date, Start time-end time)”.
    // E.g. “Christmas Party (25-Dec-2019, 18:00-23:00)”.
    @Override
    public String toString() {
        return String.format("%s (%s, %s-%s)", getDescription(), getDate(), startTime, endTime);
    }



}
