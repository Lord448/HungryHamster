package ca.crit.hungryhamster.time;

public class MillisTime extends Time{



    public MillisTime(int minutes, int seconds, int milliseconds) throws TimeFormatException {
        super(minutes, seconds);
    }

    public MillisTime() {
        super();
        super.seconds = 10;
    }
}
