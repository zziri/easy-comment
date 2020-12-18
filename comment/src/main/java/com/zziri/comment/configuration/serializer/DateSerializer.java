package com.zziri.comment.configuration.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zziri.comment.domain.dto.Date;

import java.io.IOException;
import java.time.LocalDateTime;

public class DateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(value != null) {
            gen.writeObject(LocalDateTime.of(value.getYear(), value.getMonth(), value.getDay(), value.getHour(), value.getMin(), value.getSec()).toString());
        }
    }
}
