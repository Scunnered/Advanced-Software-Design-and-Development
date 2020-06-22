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
public class Date implements Comparable<Date>{
    private int year,month,day;

    //no. of days in a month in a non-leap year
    private static final int[] DAYS_IN_MONTH={31,28,31,30,31,30,31,31,30,31,30,31};

    //months in a year
    private static final String[] MONTHS={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
    
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Date))
            return false;
        
        Date otherDate=(Date)other;
        return this.getYear()==otherDate.getYear() &&
                this.getMonth()==otherDate.getMonth() &&
                this.getDay()==otherDate.getDay();
    }

    public String toString()
    {
        return String.format("%02d-%s-%04d", day, Date.MONTHS[month-1], year);  //Complete the toString method to return the Date object as a String in the format “DD-M-YYYY”. For example, “02-Dec-2019”
    }

    //Make Date implement the java.lang.Comparable interface and complete the required compareTo(…) method.
    @Override
    public int compareTo(Date date) {
        int yearComparison = ((Integer) year).compareTo(date.year);
        if (yearComparison != 0) return yearComparison;
        else{
            int monthComparison = ((Integer) month).compareTo(date.month);
            if (monthComparison != 0) return monthComparison;
            else return ((Integer) day).compareTo(date.day);
        }
    }

    //  Add a public, static method daysInMonth(…) where: The 1st parameter is the year (e.g. 2019),
    //  The 2nd parameter is the calendar month (e.g. 11 for November) and It returns the number of days in that month.
    public static int daysInMonth(int year, int month) {
        //  Assuming months to be numbered starting from 1, i.e. 3 corresponds to March
        int noOfDays = Date.DAYS_IN_MONTH[month -1];

        //  For non-February month return the value straight away
        if (month != 2) return noOfDays;

        //  If the month is February then return incremented value, if the year is leap year
        boolean isLeapYear = (year % 4 == 0) && (year % 400 != 0);
        return isLeapYear? noOfDays + 1: noOfDays;
    }
} //end class
