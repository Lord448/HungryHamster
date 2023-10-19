package ca.crit.hungryhamster.main;

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
    private final Modes mode;
    private final Time desiredTime;
    private final Time currentTime;
    private States state = States.STOP;
    private PeriodElapsedCallback periodElapsedCallback;
    private long timePast;

    public Timer(Modes mode, int desiredMinutes, int desiredSeconds) {
        this.mode = mode;
        desiredTime = new Time(desiredMinutes, desiredSeconds);
        currentTime = new Time();
        timePast = System.currentTimeMillis();
    }

    public Timer(Modes mode) {
        this.mode = mode;
        this.desiredTime = new Time();
        this.currentTime = new Time();
        timePast = System.currentTimeMillis();
    }

    public void timerRender() {
        long time;

        if(state == States.RUNNING) {
            time = System.currentTimeMillis();
            if (time - timePast >= 1000) {
                currentTime.addSecond();
                timePast = time;
            }
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

    @Override
    public String toString() {
        return currentTime.toString();
    }

    public String getDesiredTime() {
        return desiredTime.toString();
    }

    public int[] getMeasure() {
        return currentTime.getTime();
    }

    public float getFloatMeasure() {
        return currentTime.getFloatTime();
    }

    public String getStringMeasure() {
        return currentTime.toString();
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

    //Inner Class
    private static class Time {
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
            return minutes + ":" + seconds;
        }

        public void addSecond() {
            seconds++;
            if(seconds >= 60) {
                seconds = 0;
                minutes++;
            }
            setCompoundTime();
        }
        public void setZeros() {
            seconds = 0;
            minutes = 0;
        }

        public void setSeconds(int seconds) throws SecondsOutOfLimitException {
            if(seconds <= 60) {
                throw new SecondsOutOfLimitException("No more than 60 seconds");
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

        //Exception inner class
        private static class SecondsOutOfLimitException extends Exception{
            public SecondsOutOfLimitException(String message) {
                super(message);
            }
        }
    }
}
