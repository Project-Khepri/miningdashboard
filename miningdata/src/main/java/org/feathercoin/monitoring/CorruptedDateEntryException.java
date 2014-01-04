package org.feathercoin.monitoring;

public class CorruptedDateEntryException extends RuntimeException {
    public CorruptedDateEntryException() {
    }

    public CorruptedDateEntryException(String message) {
        super(message);
    }

    public CorruptedDateEntryException(String message, Throwable cause) {
        super(message, cause);
    }

    public CorruptedDateEntryException(Throwable cause) {
        super(cause);
    }

    public CorruptedDateEntryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
