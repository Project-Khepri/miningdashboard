package org.feathercoin.monitoring.json.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Base class for JSON requests
 */
@Component
public abstract class AbstractJsonRequestExecutor implements Serializable{
    @Autowired
    private JsonRequestExecutor jsonRequestExecutor;

    public JsonRequestExecutor getJsonRequestExecutor() {
        return jsonRequestExecutor;
    }

    public void setJsonRequestExecutor(JsonRequestExecutor jsonRequestExecutor) {
        this.jsonRequestExecutor = jsonRequestExecutor;
    }
}
