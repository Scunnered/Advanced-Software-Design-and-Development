package app.diary.core;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * This class models a diary that contains multiple appointments.
 *
 * CM2100 Advanced Software Design and Development Assessment Part 2 - Coursework (Deadline: 13/01/2020, 16:00)
 * @author Ross Morrison 1105864
 */

public class Diary {
    private List<Appointment> store;

    /**
     * Create an empty diary.
     */

    public Diary() {
        this.store = new ArrayList<>();
    } //end method

    /**
     * Return the number of appointments in the diary.
     *
     * @return The number of appointment stored.
     */

    public int getNumberOfAppointments() {
        return this.store.size();
    } //end method

    /**
     * Add an appointment into the diary.
     *
     * @param a The appointment to add.
     */

    public void add(Appointment a) {
        this.store.add(a);
    } //end method

    /**
     * Delete an appointment from the diary.
     *
     * @param a The appointment to delete.
     */

    public void delete(Appointment a) {
        this.store.remove(a);
    } //end method

    /**
     * Delete all appointments in the diary.
     */

    public void clear() {
        this.store.clear();
    } //end method

    /**
     * Return the diary as a multi-line formatted String.
     *
     * @return The diary represented as a String.
     */

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("No. of appointments: " + this.getNumberOfAppointments() + "\n");
        for (Appointment a : this.store) {
            result.append(a.toString()).append("\n");
        }
        return result.toString();
    } //end method

    public List<Appointment> getAppointments() {
        return this.store;
    }

    /**
     * Find all appointments that occur on a given Date.
     *
     * @param date The date to look for.
     * @return All appointments occurring on that date as an ArrayList.
     */

    public ArrayList<Appointment> findAppointments(Date date) {
        ArrayList<Appointment> result = new ArrayList<>();    //create empty ArrayList to hold result

        for (Appointment a : this.store)      //iterate through all appointments
            if (a.occursOn(date))   //if it occurs on the date
                result.add(a);              //add it into result lilst
        return result;
    } //end method

    /**
     * Find all appointments with a keyword in their description.
     *
     * @param keyword The keyword to look for.
     * @return All appointments with the keyword as an ArrayList.
     */

    public ArrayList<Appointment> findAppointments(String keyword) {
        ArrayList<Appointment> result = new ArrayList<>();    //create empty ArrayList to hold result

        for (Appointment a : this.store)                      //iterate through all appointments
            if (a.getDescription().toLowerCase().indexOf(keyword.toLowerCase()) >= 0) //keyword is found as a substring in description, case-insensitive
                result.add(a);          //add appointment to result list
        return result;
    } //end method

    /**
     * Sort all appointments using their description.
     */
    public void sortByDescription() {
        Collections.sort(this.store);
    } //end method

    /**
     * Sort all appointments using their date.
     */
    public void sortByDateTime() {
        Collections.sort(this.store, new Appointment.DateTimeComparator());
    } //end method


    public static void saveAppointments(List<Appointment> appointments, File file) throws IOException {
        BufferedWriter w = new BufferedWriter(new FileWriter(file));   //create BufferedWriter for writing to file
        for (Appointment a : appointments)      //iterate through all appointments
        {
            w.write(a.formatForFile());     //format appointment as a String, then write it to file
            w.newLine();                    //add a new line at the end of each appointment
        }
        w.close();
    }

    /**
     * Save diary into a file.
     *
     * @param f The File object to write to. You can get this using a FileChooser.
     */

    public void save(File f) throws IOException {
        saveAppointments(this.store, f);
    } //end method

    public static List<Appointment> loadAppointmentsFromFile(File file) throws FileNotFoundException {
        final List<Appointment> appointmentList = new ArrayList<>();
        Scanner input = new Scanner(file);
        while (input.hasNext()) {
            String line = input.nextLine();
            String[] tokens = line.split(",");
            String type = tokens[0];          //appointment type
            String description = tokens[1];   //description

            int startYear, startMonth, startDay, endYear, endMonth, endDay;
            Date startDate, endDate;
            int startHour, startMinute, endHour, endMinute;
            Time startTime, endTime;

            RepeatType repeatType;

            Appointment appointment = null;
            switch (type) {
                case "UA":
                    startYear = Integer.parseInt(tokens[2]);  //start date year
                    startMonth = Integer.parseInt(tokens[3]);  //start date year
                    startDay = Integer.parseInt(tokens[4]);  //start date year
                    startDate = new Date(startYear, startMonth, startDay);   //start date
                    appointment = new UntimedAppointment(description, startDate);
                    break;
                case "TA":
                    startYear = Integer.parseInt(tokens[2]);  //start date year
                    startMonth = Integer.parseInt(tokens[3]);  //start date year
                    startDay = Integer.parseInt(tokens[4]);  //start date year
                    startDate = new Date(startYear, startMonth, startDay);   //start date
                    startHour = Integer.parseInt(tokens[5]);  //start time hour
                    startMinute = Integer.parseInt(tokens[6]);    //start time minute
                    endHour = Integer.parseInt(tokens[7]);        //end time hour
                    endMinute = Integer.parseInt(tokens[8]);      //end time minute
                    startTime = new Time(startHour, startMinute); //start time
                    endTime = new Time(endHour, endMinute);       //end time
                    appointment = new TimedAppointment(description, startDate, startTime, endTime);
                    break;
                case "RUA":
                    switch (tokens[2])  //based on repeat type
                    {
                        case "DAILY":
                            repeatType = RepeatType.DAILY;
                            break;
                        case "WEEKLY":
                            repeatType = RepeatType.WEEKLY;
                            break;
                        default:
                            repeatType = RepeatType.YEARLY;
                    }
                    startYear = Integer.parseInt(tokens[3]);  //start date year
                    startMonth = Integer.parseInt(tokens[4]);  //start date year
                    startDay = Integer.parseInt(tokens[5]);  //start date year
                    startDate = new Date(startYear, startMonth, startDay);   //start date

                    endYear = Integer.parseInt(tokens[6]);  //end date year
                    endMonth = Integer.parseInt(tokens[7]);  //end date year
                    endDay = Integer.parseInt(tokens[8]);  //end date year
                    endDate = new Date(endYear, endMonth, endDay);   //end date

                    appointment = new UntimedRepeatAppointment(description, startDate.toJavaDate(), endDate.toJavaDate(), repeatType);
                    break;
                case "RTA":
                    switch (tokens[2])  //based on repeat type
                    {
                        case "DAILY":
                            repeatType = RepeatType.DAILY;
                            break;
                        case "WEEKLY":
                            repeatType = RepeatType.WEEKLY;
                            break;
                        default:
                            repeatType = RepeatType.YEARLY;
                    }
                    startYear = Integer.parseInt(tokens[3]);  //start date year
                    startMonth = Integer.parseInt(tokens[4]);  //start date year
                    startDay = Integer.parseInt(tokens[5]);  //start date year
                    startDate = new Date(startYear, startMonth, startDay);   //start date

                    endYear = Integer.parseInt(tokens[6]);  //end date year
                    endMonth = Integer.parseInt(tokens[7]);  //end date year
                    endDay = Integer.parseInt(tokens[8]);  //end date year
                    endDate = new Date(endYear, endMonth, endDay);   //end date

                    startHour = Integer.parseInt(tokens[9]);  //start time hour
                    startMinute = Integer.parseInt(tokens[10]);    //start time minute
                    endHour = Integer.parseInt(tokens[11]);        //end time hour
                    endMinute = Integer.parseInt(tokens[12]);      //end time minute
                    startTime = new Time(startHour, startMinute); //start time
                    endTime = new Time(endHour, endMinute);       //end time

                    appointment = new TimedRepeatAppointment(description, startDate.toJavaDate(), endDate.toJavaDate(), startTime, endTime, repeatType);
                    break;
            }
            if (appointment != null)
                appointmentList.add(appointment);
        }
        input.close();

        return appointmentList;
    }

    /**
     * Load appointments from a text file.
     *
     * @param f The file to load.
     */

    public void load(File f) throws FileNotFoundException {
        this.store = loadAppointmentsFromFile(f);
    } //end method
} //end class
