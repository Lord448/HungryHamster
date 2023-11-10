package ca.crit.hungryhamster.resources.time;

public class TimerMillis extends Timer {
    protected final TimeMillis desiredTime;
    protected final TimeMillis currentTime;
    protected MillisecondElapsedCallback millisecondElapsedCallback;
    protected long milliPast;

    public TimerMillis(Modes mode, int desiredMinutes, int desiredSeconds, int desiredMillis) throws TimeFormatException {
        super(mode, desiredMinutes, desiredSeconds);
        this.desiredTime = new TimeMillis(desiredMinutes, desiredSeconds, desiredMillis);
        currentTime = new TimeMillis();
        milliPast = System.currentTimeMillis();
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
            if(time - milliPast >= 1) {
                if(millisecondElapsedCallback != null)
                    millisecondElapsedCallback.millisecondElapsedCallback();
                if(currentTime.getMilliseconds() >= 99)
                    currentTime.setMilliseconds(0);
                currentTime.addMilli();
                milliPast = time;
            }
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

    public void reset() {
        switch (mode)
        {
            case ONE_SHOT:
            case TIME_MEASURE:
                state = States.STOP;
                break;
            case PERIODIC:
                break;
        }
        currentTime.setZeros();
    }

    public void start() {
        state = States.RUNNING;
    }

    public void stop() {
        state = States.STOP;
    }

    public void end() {
        state = States.STOP;
        reset();
    }

    public void restart() {
        state = States.RUNNING;
        currentTime.setZeros();
    }

    @Override
    public String toString() {
        return currentTime.toString();
    }

    public String getDesiredTime() {
        return desiredTime.toString();
    }

    public TimeMillis getTime() {
        return currentTime;
    }

    public float getFloatTime() {
        return currentTime.getFloatTime();
    }

    public String getStringTime() {
        return currentTime.toString();
    }

    public States getState() {
        return state;
    }

    public boolean isRunning() {
        return state == States.RUNNING;
    }

    public interface MillisecondElapsedCallback {
        void millisecondElapsedCallback();
    }

    public void setMillisecondElapsedCallback(TimerMillis.MillisecondElapsedCallback eventHandler){
        millisecondElapsedCallback = eventHandler;
    }
}
