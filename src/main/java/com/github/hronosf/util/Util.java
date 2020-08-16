package com.github.hronosf.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hronosf.enums.Constants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @SuppressWarnings("java:S5361")
    public static String buildFileName(String name, String type, String extension) {
        return String
                .format("%s%s%s_%s_%s_%s.%s"
                        , Constants.PATH.getValue().toAbsolutePath().toString()
                        , File.separator
                        , new String(name.replaceAll(StringUtils.SPACE, "-").getBytes(), StandardCharsets.UTF_8)
                        , type
                        , new SimpleDateFormat("dd.MM.yyyy").format(new Date())
                        , UUID.randomUUID().toString().substring(0, 10)
                        , extension);
    }

    public static String parseJsDatePickerDate(String date) {
        return DateTimeFormat.forPattern("dd.MM.yyyy")
                .print(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parseDateTime(date));
    }
}
