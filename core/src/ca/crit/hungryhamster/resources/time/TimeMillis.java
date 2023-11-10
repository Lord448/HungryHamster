package ca.crit.hungryhamster.resources.time;

public class TimeMillis extends Time{
    protected int milliseconds;
    protected final int[] compoundTime = new int[3];

    public TimeMillis(int minutes, int seconds, int milliseconds) throws TimeFormatException {
        super(minutes, seconds);
        assertPositiveTime(minutes, seconds, milliseconds);
        this.milliseconds = milliseconds;
    }

    public TimeMillis() {
        super();
        milliseconds = 0;
    }
    public TimeMillis(TimeMillis timeMillis) {
        super(timeMillis.getMinutes(), timeMillis.getSeconds());
        milliseconds = timeMillis.getMilliseconds();
    }

    public TimeMillis(Time time) {
        super(time.getMinutes(), time.getSeconds());
        milliseconds = 0;
    }

    @Override
    public boolean equals(Object obj) {
        //If the object is compared with itself it returns true
        if(obj == this) {
            return true;
        }
        //Check if the object is an instance of Time or not
        if(!(obj instanceof TimeMillis)) {
            return false;
        }
        TimeMillis millisTime = (TimeMillis) obj;

        return (millisTime.getSeconds() == this.seconds)
                && (millisTime.getMinutes() == this.minutes)
                && (millisTime.getMilliseconds() == this.milliseconds);
    }

    @Override
    public String toString() {
        String secs;
        String millis;
        if(seconds < 10)
            secs = "0" + seconds;
        else
            secs = String.valueOf(seconds);
        if(milliseconds < 10)
            millis = "0" + milliseconds;
        else
            millis = String.valueOf(milliseconds);
        return minutes + ":" + secs + ":" + millis;
    }

    //TODO Make parse of millis
    public static TimeMillis parseTimeMillis(String s) throws NumberFormatException, TimeFormatException {
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
        int milliseconds = -1;
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
        return new TimeMillis(minutes, seconds, milliseconds);
    }

    public void addMilli() {
        milliseconds++;
        if(milliseconds >= 100) {
            milliseconds = 0;
            seconds++;
            if(seconds >= 60) {
                seconds = 0;
                minutes++;
            }
        }
        setCompoundTime();
    }

    public void subtractMilli() {
        milliseconds--;
        if(milliseconds < 0) {
            if(seconds > 0) {
                milliseconds = 99;
                seconds--;
            }
            else if(seconds == 0 && minutes > 0) {
                milliseconds = 99;
                seconds = 59;
                minutes--;
            }
            else
                setZeros();
        }
        setCompoundTime();
    }

    public void addTime(TimeMillis timeMillis) {
        try {
            addTime(timeMillis.getMinutes(), timeMillis.getSeconds(), timeMillis.getMilliseconds());
        }
        catch (TimeFormatException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addTime(int mins, int secs, int millis) throws TimeFormatException{
        assertPositiveTime(mins, secs, millis);
        int tmp;
        minutes += mins;
        for(tmp = millis + milliseconds; tmp > 60; tmp-=60)
            seconds++;
        milliseconds = tmp;
        for(tmp = secs + seconds; tmp > 60; tmp-=60)
            minutes++;
        seconds = tmp;
        setCompoundTime();
    }

    public void subtract(TimeMillis timeMillis) {
        if(timeMillis.equals(this)) {
            setZeros();
            return;
        }
        super.subtract(timeMillis);
        int millis = milliseconds - timeMillis.getMilliseconds();
        if(millis <= 0) {
            if(seconds > 0) {
                milliseconds = 99;
                seconds--;
            }
            else if(seconds == 0 && minutes > 0){
                milliseconds = 99;
                seconds = 59;
                minutes--;
            }
            else
                setZeros();
        }
        setCompoundTime();
    }

    //TODO Make compatible millis
    /* !NOT TESTED! */
    public void subtract(int mins, int secs, int millis) {
        try {
            assertPositiveTime(mins, secs, millis);
        }
        catch (TimeFormatException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
        for(; secs > 60; secs-=60)
            mins++;
        mins = minutes - mins;
        if(mins >= 0) {
            secs = seconds - secs;
            if(secs < 0) {
                if(mins != 0)
                    mins--;
                //Taking the complement to 60 (time is in base 60)
                secs += 60;
            }
        }
        else
            mins = 0;
        //Assignments at the end in order to avoid NegativeTimeException
        try {
            assertPositiveTime(mins, secs, millis);
        }
        catch (TimeFormatException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
        minutes = mins;
        seconds = secs;
    }

    public void divide(int denominator) {
        if(denominator == 0)
            throw new NumberFormatException("Cannot divide by zero");
        if(denominator < 0)
            throw new NumberFormatException("Cannot divide by a negative number");

        int totalTime = translateToInt(this);
        totalTime /= denominator;
        TimeMillis tmp = translateToTime(totalTime);

        assert tmp != null;
        minutes = tmp.getMinutes();
        seconds = tmp.getSeconds();
        milliseconds = tmp.getMilliseconds();
    }

    @Override
    public void setZeros() {
        super.setZeros();
        milliseconds = 0;
    }

    public void setTime(int minutes, int seconds, int milliseconds) {
        try {
            assertPositiveTime(minutes, seconds, milliseconds);
        }
        catch (TimeFormatException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
        while(milliseconds >= 100) {
            milliseconds -= 100;
            seconds++;
        }
        while(seconds >= 60) {
            seconds -= 60;
            minutes++;
        }
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
        setCompoundTime();
    }

    public void setTime(TimeMillis timeMillis) {
        try {
            assertPositiveTime(timeMillis.getMinutes(), timeMillis.getSeconds(), timeMillis.getMilliseconds());
        }
        catch (TimeFormatException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
        minutes = timeMillis.getMinutes();
        seconds = timeMillis.getSeconds();
        milliseconds = timeMillis.getMilliseconds();
        setCompoundTime();
    }

    public void setMilliseconds(int milliseconds) {
        try {
            assertPositiveTime(0, 0 , milliseconds);
        }
        catch (TimeFormatException ex){
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
        this.milliseconds = milliseconds;
        setCompoundTime();
    }

    public int getMilliseconds() {
        return milliseconds;
    }

    public int translateToInt(TimeMillis timeMillis) {
        int mins = timeMillis.minutes;
        int secs = timeMillis.seconds;
        int totalTime = timeMillis.milliseconds;

        if(mins > 0) {
            for (; mins > 0; mins--)
                secs += 60;
        }
        if(secs > 0) {
            for (; secs > 0; secs--)
                totalTime += 100;
        }
        return totalTime;
    }

    public TimeMillis translateToTime(int number) {
        if(number < 0)
            throw new TimeFormatException("Cannot take a negative number");

        int mins = 0;
        int secs = 0;

        if(number > 100) {
            for (secs = 0; number > 0; number -= 100) {
                secs++;
                if (secs > 60) {
                    secs = 0;
                    mins++;
                }
            }
            if(number < 0) {
                number += 100; //Hundreds complement
                if(secs > 0)
                    secs--;
                else {
                    if(mins > 0)
                        mins--;
                }
            }
        }

        try {
            return new TimeMillis(mins, secs, number);
        }
        catch (TimeFormatException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
            return null;
        }
    }

    private void setCompoundTime() {
        compoundTime[0] = minutes;
        compoundTime[1] = seconds;
        compoundTime[2] = milliseconds;
    }

    protected void assertPositiveTime(int mins, int secs, int millis) throws TimeFormatException {
        if(mins < 0)
            throw TimeFormatException.NegativeTimeException();
        if(secs < 0)
            throw TimeFormatException.NegativeTimeException();
        if(millis < 0)
            throw TimeFormatException.NegativeTimeException();
    }
}
