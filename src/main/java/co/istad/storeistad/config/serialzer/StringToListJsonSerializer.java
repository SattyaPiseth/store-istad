package co.istad.storeistad.config.serialzer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author Sombath
 * create at 3/5/23 10:32 PM
 */
public class StringToListJsonSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        String[] names = s.split(",");
        for (String name: names) {
            jsonGenerator.writeString(name);
        }
        jsonGenerator.writeEndArray();
    }

}
