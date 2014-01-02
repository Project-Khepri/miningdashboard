package org.feathercoin.monitoring.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.util.Date;

public class DateTimeSerializer implements JsonSerializer<Date> {
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        DateTime dateTime = new DateTime(src).withTimeAtStartOfDay();

        return new JsonPrimitive(dateTime.toString("MM-dd"));
    }
}
