package org.feathercoin.monitoring.util;

import java.util.ArrayList;
import java.util.List;

public class JSONCallException extends RuntimeException {
    private ArrayList<Throwable> exceptions = new ArrayList<Throwable>();
    public JSONCallException() {
    }

    public JSONCallException(String message) {
        super(message);
    }

    public JSONCallException(String message, Throwable cause) {
        super(message, cause);
    }

    public JSONCallException(Throwable cause) {
        super(cause);
    }

    public JSONCallException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public JSONCallException setExceptions(List<Throwable> exceptions){
        this.exceptions.clear();
        this.exceptions.addAll(exceptions);
        return this;
    }

    public List<Throwable> getExceptions(){
        return exceptions;
    }
}
