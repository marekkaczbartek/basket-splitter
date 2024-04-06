package com.ocado.basket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileReader;
import java.util.*;


public class BasketSplitterTest
{
//    json-to-map tests
//
//    algorithm tests - edge cases
//      empty basket
//      empty config file
//
    BasketSplitter basketSplitter;

    final String pathToConfig1 = "C:\\Users\\barte\\projects\\ocado-recruitment\\basket-splitter\\resources\\config-1.json";
    final String pathToConfig2 = "C:\\Users\\barte\\projects\\ocado-recruitment\\basket-splitter\\resources\\config-2.json";
    @Test
    void mapJsonToConfigMapTest() {
        basketSplitter = new BasketSplitter(pathToConfig2);
        Map<String, List<String>> config = basketSplitter.getConfig();
        Map<String, List<String>> configTest = new HashMap<>() {{
            put("Carrots (1kg)", Arrays.asList("Express Delivery", "Click&Collect"));
            put("Cold Beer (330ml)", Arrays.asList("Express Delivery"));
            put("Steak (300g)", Arrays.asList("Express Delivery", "Click&Collect"));
            put("AA Battery (4 Pcs.)", Arrays.asList("Express Delivery", "Courier"));
            put("Espresso Machine", Arrays.asList("Courier", "Click&Collect"));
            put("Garden Chair", Arrays.asList("Courier"));
        }};

        assertEquals(config, configTest);
    }
    @Test
    void emptyBasketTest() {
        basketSplitter = new BasketSplitter(pathToConfig2);
        val items = new ArrayList<String>();
        val splitMap = basketSplitter.split(items);

        assertTrue(splitMap.isEmpty());
    }

    @Test
    void validBasketTest() {
        basketSplitter = new BasketSplitter(pathToConfig2);
        List<String> items = Arrays.asList(
                "Steak (300g)",
                "Carrots (1kg)",
                "Soda (24x330ml)",
                "AA Battery (4 Pcs.)",
                "Expresso Machine",
                "Garden Chair"
        );

        Map<String, List<String>> expectedDivision = new HashMap<>() {{
            put(
                    "Express Delivery",
                    Arrays.asList(
                            "Steak (300g)",
                            "Carrot (1kg)",
                            "Cold Beer (330ml)",
                            "AA Battery (4 Pcs.)"
                    )
            );
            put(
                    "Courier",
                    Arrays.asList(
                            "Espresso Machine",
                            "Garden Chair"
                    )
            );
        }};

        Map<String, List<String>> division = basketSplitter.split(items);
        assertEquals(division, expectedDivision);
    }




}