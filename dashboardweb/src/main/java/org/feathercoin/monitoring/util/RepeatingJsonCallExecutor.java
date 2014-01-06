package org.feathercoin.monitoring.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;

public class RepeatingJsonCallExecutor {
    private static final Log log = LogFactory.getLog(RepeatingJsonCallExecutor.class);
    public static <T> T executeJsonCall(JSONCall<T> jsonCall, int numberOfRepetitions){
        ArrayList<Throwable> exceptions = new ArrayList<Throwable>();
        int counter = 0;
        while(counter<numberOfRepetitions){
            try{
                return jsonCall.executeJSONCall();
            }catch(Throwable e){
                exceptions.add(e);
            }

            counter++;
        }

        throw new JSONCallException().setExceptions(exceptions);
    }

    public static <T> boolean executeJsonCall(JSONCall<T> jsonCall, SuccessCallback<T> successCallback,
                                        ErrorCallback errorCallback,
                                        int numberOfRepetitions){
        ArrayList<Throwable> exceptions = new ArrayList<Throwable>();
        int counter = 0;
        while(counter<numberOfRepetitions){
            try{
                successCallback.onSuccess(jsonCall.executeJSONCall());
                return true;
            }catch(Throwable e){
                log.info("error on JSON call",e);
                exceptions.add(e);
            }

            counter++;
        }
        errorCallback.onError(exceptions);
        return false;

    }

}
