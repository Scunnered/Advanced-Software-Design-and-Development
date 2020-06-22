package Diary;

/**
 * This class models a time with hour and minute. 
 */
public class Time implements Comparable<Time>{
    private int hour,minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
    
    @Override
    public boolean equals(Object other)
    {
    if (!(other instanceof Time))
        return false;
    
    Time otherTime=(Time)other;
    return this.getHour()==otherTime.getHour() &&
            this.getMinute()==otherTime.getMinute();
    }

    @Override
    public String toString()
    {
        return String.format("%02d:%02d", hour, minute);  //To return the Time object as a String in the format “hh:mm”. e.g. “07:45” for 7:45am.
    }

    @Override
    public int compareTo(Time time) {
        int hourComparison = this.hour - time.hour;
        if (hourComparison != 0) return hourComparison > 0? 1: -1;
        else{
            int minuteComparison = this.minute - time.minute;
            if (minuteComparison != 0) return minuteComparison > 0? 1: -1;
            else return 0;

        }
    }

    // Add and complete the compareTo(...) method required by the Comparable interface.
    //Make Time implement the java.lang.Comparable interface. Need to compare two Time objects
    
} //end class
