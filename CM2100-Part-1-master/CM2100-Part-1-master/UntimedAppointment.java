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
public class UntimedAppointment extends Appointment{

    //  Add a constructor for UntimedAppointment which takes 2 parameters: The 1st parameter is the appointment’s description.
    //  The 2nd parameter is the appointment’s date.
    public UntimedAppointment(String description, Date date){
        super(date, description);
    }

    //  Add and complete a public, concrete clash(…) method that checks if the appointment clashes with another appointment.
    //  The input parameter is an Appointment object. Note:
    //  As the current appointment is untimed, if the other appointment occurs on the same date, then there is a clash.
    //  It does not matter if the other one is time/untimed.
    @Override
    public boolean clash(Appointment appointment) {
        return appointment.getDate().compareTo(this.getDate()) == 0;
    }

    //Make UntimedAppointment a concrete subclass of Appointment.
} //end class
