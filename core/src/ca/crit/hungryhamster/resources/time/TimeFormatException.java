package ca.crit.hungryhamster.resources.time;

public class TimeFormatException extends IllegalArgumentException {
    public enum ExId{
        GENERIC,
        NEGATIVE_TIME,
        SECONDS_LIMIT
    }
    private static ExId ExId;
    private static String message;

    public TimeFormatException(String message) {
        super(message);
        TimeFormatException.message = message;
        ExId = ExId.GENERIC;
    }

    static TimeFormatException NegativeTimeException() {
        ExId = ExId.NEGATIVE_TIME;
        message = "Cannot have negative times";
        return new TimeFormatException(message);
    }

    static TimeFormatException SecondsOutLimitException() {
        ExId = ExId.SECONDS_LIMIT;
        message = "No more than 59 seconds";
        return new TimeFormatException(message);
    }

    public TimeFormatException.ExId getExId() {
        return ExId;
    }
}