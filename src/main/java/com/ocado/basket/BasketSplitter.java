package com.ocado.basket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Getter
@Setter
public class BasketSplitter
{
    private Map<String, List<String>> config;
    public BasketSplitter(String absolutePathToConfigFile) {
        Map<String, List<String>> configMap = mapJsonToConfigMap(absolutePathToConfigFile);
        if (configMap.isEmpty()) {
            throw new IllegalStateException("Config file is empty");
        }
        config = configMap;
    }

    private Map<String, List<String>> mapJsonToConfigMap(String absolutePathToConfigFile) {
        Map<String, List<String>> config;
        ObjectMapper mapper = new ObjectMapper();

        try {
            config = mapper.readValue(
                    new File(absolutePathToConfigFile),
                    new TypeReference<>() {}
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return config;
    }

    private void sortDeliveryCountMap(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        map.clear();
        for (Map.Entry<String, Integer> entry : list) {
            map.put(entry.getKey(), entry.getValue());
        }
    }

    private Map<String, Integer> getDeliveryCountMap(List<String> items) {
        Map<String, Integer> deliveryCount = new LinkedHashMap<>();
        for (String item : items) {
            for (String option : config.get(item)) {
                Integer count = deliveryCount.getOrDefault(option, 0);
                deliveryCount.put(option, count + 1);
            }
        }
        sortDeliveryCountMap(deliveryCount);
        return deliveryCount;
    }

    public Map<String, List<String>> split(List<String> items) {
        Map<String, Integer> deliveryCount = getDeliveryCountMap(items);
        Map<String, List<String>> basketSplit = new LinkedHashMap<>();

        for (String item : items) {
            boolean found = false;
            for (String option : deliveryCount.keySet()) {
                if (config.get(item).contains(option)) {
                    if (!found) {
                        List<String> list = basketSplit.getOrDefault(option, new ArrayList<>());
                        list.add(item);
                        basketSplit.put(option, list);
                        found = true;
                    }
                    else {
                        Integer count = deliveryCount.get(option);
                        deliveryCount.put(option, count-1);
                    }
                }
            }
            sortDeliveryCountMap(deliveryCount);
        }
        return basketSplit;
    }


}


