package ca.crit.hungryhamster.time;

public class Time {
    private int seconds;
    private int minutes;
    private final int[] compoundTime = new int[2];

    public Time (int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
        setCompoundTime();
    }
    public Time() {
        this.minutes = 0;
        this.seconds = 0;
        setCompoundTime();
    }
    @Override
    public boolean equals(Object obj) {
        //If the object is compared with itself it returns true
        if(obj == this) {
            return true;
        }

        //Check if the object is an instance of Time or not
        if(!(obj instanceof Time)) {
            return false;
        }
        Time time = (Time) obj;

        return (time.getSeconds() == this.getSeconds()) && (time.getMinutes() == this.getMinutes());
    }

    @Override
    public String toString() {
        String secs;
        if(seconds < 10)
            secs = "0" + seconds;
        else
            secs = String.valueOf(seconds);
        return minutes + ":" + secs;
    }

    public static Time parseTime(String s) throws NumberFormatException, TimeFormatException {
        if(s == null)
            throw new NumberFormatException("Cannot parse a null string");
        if(s.equals(""))
            throw new TimeFormatException("Cannot parse an empty string");
        char firstChar = s.charAt(0);
        if(firstChar == '-')
            throw TimeFormatException.NegativeTimeException();
        if(firstChar == ':')
            throw new TimeFormatException("Have to put a 0 before the delimiter");

        int minutes = -1;
        int seconds = -1;
        int middlePoint = -1;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == ':') {
                middlePoint = i;
                StringBuilder tmpStr = new StringBuilder();
                for(int j = middlePoint+1; j < s.length(); j++)
                    tmpStr.append(s.charAt(j));
                try {
                    seconds = Integer.parseInt(String.valueOf(tmpStr));
                }
                catch (NumberFormatException ex) {
                    throw new TimeFormatException("Cannot parse a no number time");
                }
            }
            if(middlePoint != -1) {
                StringBuilder tmpStr = new StringBuilder();
                for(int j = middlePoint-1; j >= 0; j--)
                    tmpStr.append(s.charAt(j));
                try {
                    minutes = Integer.parseInt(String.valueOf(tmpStr));
                }
                catch (NumberFormatException ex) {
                    throw new TimeFormatException("Cannot parse a no number time");
                }
                break;
            }

        }
        if(middlePoint == -1)
            throw new TimeFormatException("Missing the delimiter (:)");
        return new Time(minutes, seconds);
    }

    public static Time parseTime(float value) throws TimeFormatException{
        int integerPart;
        int decimalPart;

        if(value < 0)
            throw TimeFormatException.NegativeTimeException();
        integerPart = (int) value;
        decimalPart = (int) ((float) value - integerPart) * 100;
        if(decimalPart > 60)
            throw TimeFormatException.SecondsOutLimitException();
        return new Time(integerPart, decimalPart);
    }

    public void addSecond() {
        seconds++;
        if(seconds >= 60) {
            seconds = 0;
            minutes++;
        }
        setCompoundTime();
    }

    public void subtractSecond() {
        seconds--;
        if(seconds <= 0) {
            if(minutes > 0) {
                seconds = 59;
                minutes--;
            }
            else {
                minutes = 0;
                seconds = 0;
            }

        }
        setCompoundTime();
    }

    public void addMinute() {
        minutes++;
        setCompoundTime();
    }

    public void subtractMinute() {
        minutes--;
        if(minutes < 0)
            minutes = 0;
        setCompoundTime();
    }
    public void setZeros() {
        seconds = 0;
        minutes = 0;
    }

    public void setSeconds(int seconds) throws TimeFormatException {
        if(seconds < 60) {
            throw TimeFormatException.SecondsOutLimitException();
        }
        else {
            this.seconds = seconds;
        }
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public float getFloatTime() {
        return minutes + ((float)seconds /100);
    }

    public int[] getTime() {
        return compoundTime;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setTime(int minutes, int seconds) {
        while(seconds >= 60) {
            seconds -= 60;
            this.minutes++;
        }
        this.minutes = minutes;
        this.seconds = seconds;
        setCompoundTime();
    }

    private void setCompoundTime() {
        compoundTime[0] = minutes;
        compoundTime[1] = seconds;
    }
}
