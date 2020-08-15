package com.github.hronosf.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hronosf.enums.Constants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.joda.time.format.DateTimeFormat;

import java.io.File;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Util {

    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static String commonPathPart() {
        return Constants.PATH.getValue().toAbsolutePath().toString() + File.separator + UUID.randomUUID().toString().substring(0, 8);
    }

    public static String parseJsDatePickerDate(String date) {
        return DateTimeFormat.forPattern("dd.MM.yyyy")
                .print(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parseDateTime(date));
    }
}
