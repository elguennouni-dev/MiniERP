package com.messanger.app.src.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SqlDateDeserializer implements JsonDeserializer<Date> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            String dateStr = json.getAsString();
            return new Date(DATE_FORMAT.parse(dateStr).getTime());
        } catch (ParseException e) {
            throw new JsonParseException("Failed to parse date: " + json.getAsString(), e);
        }
    }
}