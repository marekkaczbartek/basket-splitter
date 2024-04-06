package com.ocado.basket;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class BasketSplitterTest
{
//    json-to-map tests
//
//    algorithm tests - edge cases
//      empty basket
//      empty config file
//
    BasketSplitter basketSplitter;

    final String pathToConfig2 = "C:\\Users\\barte\\projects\\ocado-recruitment\\basket-splitter\\resources\\config-2.json";
    final String pathToEmptyConfig = "C:\\Users\\barte\\projects\\ocado-recruitment\\basket-splitter\\resources\\empty-config.json";
    final String pathToInvalidConfig = "C:\\Users\\barte\\projects\\ocado-recruitment\\basket-splitter\\resources\\invalid-config.json";
    @Test
    void mapJsonToConfig_Test() {
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
    void mapJsonToConfig_InvalidFile_Test() {
        assertThrows(RuntimeException.class, () -> basketSplitter = new BasketSplitter(pathToInvalidConfig));
    }

    @Test
    void mapJsonToConfig_EmptyFile_Test() {
        Exception e = assertThrows(IllegalStateException.class, () ->
                basketSplitter = new BasketSplitter(pathToEmptyConfig));

        assertEquals("Config file is empty", e.getMessage());
    }

    @Test
        void emptyBasketSplitTest() {
            basketSplitter = new BasketSplitter(pathToConfig2);
            val items = new ArrayList<String>();
            val split = basketSplitter.split(items);

        assertTrue(split.isEmpty());
    }

    @Test
    void validBasketSplitTest() {
        basketSplitter = new BasketSplitter(pathToConfig2);
        List<String> items = Arrays.asList(
                "Steak (300g)",
                "Carrots (1kg)",
                "Cold Beer (330ml)",
                "AA Battery (4 Pcs.)",
                "Espresso Machine",
                "Garden Chair"
        );

        Map<String, List<String>> expectedSplit = new HashMap<>() {{
            put(
                    "Express Delivery",
                    Arrays.asList(
                            "Steak (300g)",
                            "Carrots (1kg)",
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

        Map<String, List<String>> split = basketSplitter.split(items);
        assertEquals(expectedSplit, split);
    }




}