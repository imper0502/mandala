package digi.joy.mandala.infra.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MandalaUtils {
    public static ObjectMapper getObjectMapper() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    public static ObjectWriter getObjectWriter() {
        return getObjectMapper().writerWithDefaultPrettyPrinter();
    }
}
