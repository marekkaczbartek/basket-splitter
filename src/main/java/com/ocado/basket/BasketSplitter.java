package com.ocado.basket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class BasketSplitter
{
    private String absolutePathToConfigFile;
    public BasketSplitter(String absolutePathToConfigFile) {

    }

    public Map<String, List<String>> split(List<String> items) {
        
    }
}

