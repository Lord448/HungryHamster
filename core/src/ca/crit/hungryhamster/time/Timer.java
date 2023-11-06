package ca.crit.hungryhamster.time;

public class Timer {
    public enum Modes{
        ONE_SHOT, //Calls one callback, then it stops
        PERIODIC, //Calls a callback periodically
        TIME_MEASURE //Just measure the time
    }

    public enum States{
        RUNNING,
        FINISHED,
        STOP,
    }
    protected final Modes mode;
    protected final Time desiredTime;
    protected final Time currentTime;
    protected States state = States.STOP;
    protected PeriodElapsedCallback periodElapsedCallback;
    protected SecondElapsedCallback secondElapsedCallback;
    protected long timePast;

    public Timer(Modes mode, int desiredMinutes, int desiredSeconds) throws TimeFormatException{
        this.mode = mode;
        desiredTime = new Time(desiredMinutes, desiredSeconds);
        currentTime = new Time();
        timePast = System.currentTimeMillis();
    }

    public Timer(Modes mode, Time desiredTime) {
        this.mode = mode;
        this.desiredTime = desiredTime;
        currentTime = new Time();
        timePast = System.currentTimeMillis();
    }

    public Timer(Modes mode) {
        this.mode = mode;
        this.desiredTime = new Time();
        this.currentTime = new Time();
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

    public Time getTime() {
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

    public interface PeriodElapsedCallback {
        void periodElapsedCallback();
    }

    public void setPeriodElapsedCallback(PeriodElapsedCallback eventHandler) {
        periodElapsedCallback = eventHandler;
    }

    public interface SecondElapsedCallback {
        void secondElapsedCallback();
    }

    public void setSecondElapsedCallback(SecondElapsedCallback eventHandler) {
        secondElapsedCallback = eventHandler;
    }
}
