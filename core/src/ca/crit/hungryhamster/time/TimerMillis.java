package ca.crit.hungryhamster.time;

public class TimerMillis extends Timer {
    protected final TimeMillis desiredTime;
    protected final TimeMillis currentTime;

    public TimerMillis(Modes mode, int desiredMinutes, int desiredSeconds, int desiredMillis) throws TimeFormatException {
        super(mode, desiredMinutes, desiredSeconds);
        this.desiredTime = new TimeMillis(desiredMinutes, desiredSeconds, desiredMillis);
        currentTime = new TimeMillis();
        timePast = System.currentTimeMillis();
    }

    public TimerMillis(Modes mode, TimeMillis desiredTime) {
        super(mode, desiredTime);
        this.desiredTime = desiredTime;
        this.currentTime = new TimeMillis();
        timePast = System.currentTimeMillis();
    }

    public TimerMillis (Modes mode) {
        super(mode);
        this.desiredTime = new TimeMillis();
        this.currentTime = new TimeMillis();
        timePast = System.currentTimeMillis();
    }

    public void update(boolean secAfterResume) {
        long time;

        if(state == States.RUNNING) {
            time = System.currentTimeMillis();
            if (time - timePast >= 1000) {
                if(secondElapsedCallback != null)
                    secondElapsedCallback.secondElapsedCallback();
                currentTime.addSecond();
                timePast = time;
            }
        }
        if(secAfterResume) { //false if want to count a second after resume
            if(state == States.STOP)
                timePast = System.currentTimeMillis();
        }

        if(currentTime.equals(desiredTime) && mode != Modes.TIME_MEASURE) {
            if(mode == Modes.ONE_SHOT) {
                state = States.STOP;
            }
            periodElapsedCallback.periodElapsedCallback();
        }
    }
}
