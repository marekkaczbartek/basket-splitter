package com.ocado.basket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class BasketSplitter
{
    private Map<String, List<String>> config;
    public BasketSplitter(String absolutePathToConfigFile) {
        config = mapJsonToConfigMap(absolutePathToConfigFile);
    }

    private Map<String, List<String>> mapJsonToConfigMap(String absolutePathToConfigFile) {
        Map<String, List<String>> config;
        ObjectMapper mapper = new ObjectMapper();

        try {
            config = mapper.readValue(
                    new File(absolutePathToConfigFile),
                    new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return config;
    }

    public Map<String, List<String>> split(List<String> items) {
        return new HashMap<>();
    }
}

