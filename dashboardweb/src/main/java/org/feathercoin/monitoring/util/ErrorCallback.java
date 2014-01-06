package org.feathercoin.monitoring.util;

import java.util.List;

public interface ErrorCallback {
    public void onError(List<Throwable> errors);
}
