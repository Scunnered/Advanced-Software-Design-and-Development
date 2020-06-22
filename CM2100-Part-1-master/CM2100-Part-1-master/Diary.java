/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Diary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author 1105864
 */
public class Diary {
    
    //Using DateTimeComparator create a new method within the Diary class called sortByDateTime()which sorts all appointments in the diary by their date and time fields.
    
    //Add an extra field that is an ArrayList of Appointment objects called store to store appointments in the diary
    List<Appointment> stores;

    public Diary(){
        this.stores = new ArrayList<Appointment>();
    }

    //Include a method getNumberOfAppointments() that returns the number of Appointment objects in the diary.
    public int getNumberOfAppointments(){
        return stores.size();
    }
    //Add a method add(…) that adds an appointment into the diary. The parameter is an Appointment object to be added.
    public void add(Appointment appointment){
        this.stores.add(appointment);
    }
    //Add and override the toString() method so that it returns a wellformatted String that summarises the number of
    // appointments in the diary and details of all appointments, with each appointment on a separate line
    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
        String appintmentString;
        TimedAppointment timedAppointment = null;
        boolean isTimed;

        for (Appointment appointment: stores){
            isTimed = appointment instanceof TimedAppointment;

            if(isTimed) timedAppointment = (TimedAppointment) appointment;
            String singleLine = String.format("%s,%s,%s,%s,%s%s\n", isTimed? "TA": "UA", appointment.getDescription(), appointment.getDate().getYear(),
                    appointment.getDate().getMonth(), appointment.getDate().getDay(),
                    isTimed? String.format(",%s,%s,%s,%s", timedAppointment.getStartTime().getHour(), timedAppointment.getStartTime().getMinute(),
                            timedAppointment.getEndTime().getHour(), timedAppointment.getEndTime().getMinute()): "");
            stringBuffer.append(singleLine);
        }
        return stringBuffer.toString();
    }

    //Add a method findAppointments(…) that finds all appointments on a given date: The parameter is the Date to search.
    // It returns an ArrayList of Appointment objects occurring on the given date.
    public ArrayList<Appointment> findAppointments(Date date){
        return new ArrayList<>(stores.stream()
                .filter(c -> c.getDate().compareTo(date) == 0)
                .collect(Collectors.toList()));
    }

    //Add a second findAppointments(…) method that finds all appointments with a keyword in their description:
    // The parameter is a String which is the keyword to search. The search is case-insensitive. It returns an ArrayList
    // of Appointment objects containing the keyword in their description.
    public ArrayList<Appointment> findAppointments(String searchText){
        return new ArrayList<>(stores.stream()
                .filter(c -> c.getDescription().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList()));
    }
    //Add a method to the Diary class called sortByDescription() which sorts all appointments by their description. (You will need to carry out task 7 in order for this to be successful)
    public void sortByDescription(){
        this.stores = this.stores.stream().sorted().collect(Collectors.toList());
    }
    //Using DateTimeComparator , from task 9 , create a new method within the Diary class called sortByDateTime()
    // which sorts all appointments in the diary by their date and time fields.
    public void sortByDateTime(){
        Appointment.DateTimeComparator dateTimeComparator = new Appointment.DateTimeComparator();   //  Can be moved as class variable
        stores = stores.stream().sorted(dateTimeComparator).collect(Collectors.toList());
    }

    //Add a save(…) method to the Diary class which saves the details of all appointments as a comma separated text file.
    // The parameter to the method is a File object indicating where to save the data. You can create a File object from a String
    // which is the pathname of the file. The text should be written to the file with each appointment on a separate line,
    // and in the format below:
    public void save(File file){
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(this.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

/**
UA,Christmas,2019,12,25
UA,My birthday,2019,12,25
TA,Birthday party,2019,12,25,12,0,13,30
TA,Christmas party,2019,12,25,19,0,23,0
TA,Watch TV program,2019,12,25,19,30,20,0
 */
    //The 1st field is either “UA” or “TA” for untimed/timed appointment.
    //The 2nd field is the description.
    //For untimed appointment, the subsequent fields are the year,month, and day of the appointment date.
    //For timed appointment, the subsequent fields are the year, month, and day of the appointment date, follow by the hour and minute of the starting time, and hour and minute of the ending time.
  
} //end class
