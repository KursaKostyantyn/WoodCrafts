package io.teamchallenge.woodCrafts.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.hypersistence.utils.hibernate.type.util.ObjectMapperWrapper;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class JacksonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .findAndRegisterModules()
            .registerModule(
                    new SimpleModule()
                            .addSerializer(OffsetDateTime.class, ObjectMapperWrapper.OffsetDateTimeSerializer.INSTANCE)
                            .addDeserializer(OffsetDateTime.class, ObjectMapperWrapper.OffsetDateTimeDeserializer.INSTANCE)
            );

    public static <T> T fromString(String string, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(string, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("The given string value: " + string + " cannot be transformed to Json object", e);
        }
    }

    public static <T> T fromObjectNode(ObjectNode node, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.convertValue(node, clazz);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The given ObjectNode: " + node + " cannot be transformed to class:" + clazz);
        }
    }

    public static String toString(Object value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The given Json value: " + value + " cannot be transformed to String.", e);
        }
    }

    public static ObjectNode toObjectNode(String value) {
        try {
            return (ObjectNode) OBJECT_MAPPER.readTree(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The given value: " + value + " cannot be transformed to ObjectNode", e);
        }
    }

    public static ObjectNode createNewObjectNod() {
        return OBJECT_MAPPER.createObjectNode();
    }

    public static List<String> toStringList(JsonNode photosNode) {
        List<String> photos = new ArrayList<>();
        for (JsonNode node : photosNode) {
            photos.add(node.asText());
        }
        return photos;
    }
}


