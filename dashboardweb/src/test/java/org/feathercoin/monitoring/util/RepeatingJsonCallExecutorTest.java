package org.feathercoin.monitoring.util;

import org.junit.Test;

import java.util.List;

import static org.feathercoin.monitoring.util.RepeatingJsonCallExecutor.executeJsonCall;
import static org.junit.Assert.*;

public class RepeatingJsonCallExecutorTest {
    @Test
    public void testExecuteJsonCall_SimpleCall() throws Exception {

        JSONCall<Integer> jsonCall = new JSONCall<Integer>() {
            @Override
            public Integer executeJSONCall() {
                return 100;
            }
        };

        assertEquals((int)100, (int)executeJsonCall(jsonCall, 5)) ;
    }

    @Test
    public void testExecuteJsonCallWithCallbacks_SimpleCall() throws Exception {

        JSONCall<Integer> jsonCall = new JSONCall<Integer>() {
            @Override
            public Integer executeJSONCall() {
                return 100;
            }
        };

        final boolean[] onSuccessCalled = {false};

        assertTrue(executeJsonCall(jsonCall,
                new SuccessCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer result) {
                        assertEquals(100,(int)result);
                        onSuccessCalled[0]=true;
                    }
                }, new ErrorCallback() {
                    @Override
                    public void onError(List<Throwable> errors) {
                        fail();
                    }
                }, 5
        ));
        assertTrue(onSuccessCalled[0]);
    }

    @Test
    public void testExecuteJsonCallWithCallbacks_Error() throws Exception {

        JSONCall<Integer> jsonCall = new JSONCall<Integer>() {
            @Override
            public Integer executeJSONCall() {
                throw new RuntimeException("dummy exception");
            }
        };

        final boolean[] onErrorCalled = {false};

        assertFalse(executeJsonCall(jsonCall,
                new SuccessCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer result) {
                        fail();
                    }
                }, new ErrorCallback() {
                    @Override
                    public void onError(List<Throwable> errors) {
                        onErrorCalled[0]=true;
                        assertEquals(5,errors.size());
                    }
                }, 5
        ));
        assertTrue(onErrorCalled[0]);
    }


    @Test
    public void testExecuteJsonCallWithCallbacks_RepeatedCall() throws Exception {

        final int[] repetitionCounter = {0};
        JSONCall<Integer> jsonCall = new JSONCall<Integer>() {
            @Override
            public Integer executeJSONCall() {
                if (repetitionCounter[0]<3){
                    repetitionCounter[0]++;
                    throw new RuntimeException("testException call "+(repetitionCounter[0]+1));
                }
                return 100;
            }
        };

        final boolean[] onSuccessCalled = {false};

        assertTrue(executeJsonCall(jsonCall,
                new SuccessCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer result) {
                        assertEquals(100,(int)result);
                        onSuccessCalled[0]=true;
                    }
                }, new ErrorCallback() {
                    @Override
                    public void onError(List<Throwable> errors) {
                        fail();
                    }
                }, 5
        ));
        assertTrue(onSuccessCalled[0]);
        assertEquals(3, repetitionCounter[0]);
    }




    @Test
    public void testExecuteJsonCall_RepeatedCall() throws Exception {

        final int[] repetitionCounter = {0};
        JSONCall<Integer> jsonCall = new JSONCall<Integer>() {
            @Override
            public Integer executeJSONCall() {
                if (repetitionCounter[0]<3){
                    repetitionCounter[0]++;
                    throw new RuntimeException("testException call "+(repetitionCounter[0]+1));
                }
                return 100;
            }
        };


        assertEquals(100, (int) executeJsonCall(jsonCall, 5)) ;
        assertEquals(3, repetitionCounter[0]) ;
    }

    @Test
    public void testExecuteJsonCall_RepeatedCallWithException() throws Exception {

        final int[] repetitionCounter = {0};
        JSONCall<Integer> jsonCall = new JSONCall<Integer>() {
            @Override
            public Integer executeJSONCall() {
                repetitionCounter[0]++;
                throw new RuntimeException("testException call "+(repetitionCounter[0]+1));
            }
        };


        try{
            executeJsonCall(jsonCall, 3);
            fail();
        } catch(JSONCallException e){
            assertEquals(3, e.getExceptions().size()) ;
        }
        assertEquals(3, repetitionCounter[0]) ;
    }
}
