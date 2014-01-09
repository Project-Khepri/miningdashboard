package org.feathercoin.monitoring.json;

import com.google.gson.GsonBuilder;

/**
 * Transformer for Json responses to Java Classes using the <a href="https://code.google.com/p/google-gson/">GSON API</a>
 * @param <C> Class of the expected result's java class
 */
public class JsonResponseTransformer<C> {
    private Class<C> type;

    /**
     * Constructor with passing the type for casting to the right java class
     * @param type Class of the result's java class. It's necessary to provide the GSON builder with the
     *             information to which java type to transform
     */
    public JsonResponseTransformer(Class<C> type) {
        this.type = type;
    }

    /**
     * Transform the given Json to a java object of class C. For the java mapping
     * <a href="https://code.google.com/p/google-gson/">GSON API</a> is used.
     * @param response the Json to transform
     * @return a new instance of a java object of type C provided with data from the passed Json if data could be mapped
     *
     */
    public C transform(String response){
        C jsonObject = null;
        try {
            jsonObject = (C)new GsonBuilder().setPrettyPrinting().create().fromJson(response, type.newInstance().getClass());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

}
