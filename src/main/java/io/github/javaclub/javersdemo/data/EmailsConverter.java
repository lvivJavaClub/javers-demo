package io.github.javaclub.javersdemo.data;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.javaclub.javersdemo.model.Email;
import javax.persistence.AttributeConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class EmailsConverter implements AttributeConverter<List<Email>, String> {

    Gson gson = new Gson();

    private static Type LIST_OF_EMAILS = new TypeToken<ArrayList<Email>>(){}.getType();


    @Override
    public String convertToDatabaseColumn(List<Email> attribute) {
        if (CollectionUtils.isEmpty(attribute)) {
            return null;
        }
        return gson.toJson(attribute);
    }

    @Override
    public List<Email> convertToEntityAttribute(String dbData) {
        if (!StringUtils.hasText(dbData)) {
            return null;
        }
        return gson.fromJson(dbData, LIST_OF_EMAILS);
    }
}
