package com.codenjoy.dojo.sample.model;

import com.codenjoy.dojo.sample.model.Elements;
import com.codenjoy.dojo.sample.model.LevelImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 01.08.2015.
 */
public class Randomizer {
    private static final String TEMPLATE =
                    "******" +
                    "*%s*" +
                    "*%s*" +
                    "*%s*" +
                    "*%s*" +
                    "******";

    public String getRamdomMap() {
        String randomElements = getRandomElements();

        return String.format(TEMPLATE,
                randomElements.substring(0, 4),
                randomElements.substring(4, 8),
                randomElements.substring(8, 12),
                randomElements.substring(12, 16));
    }

    private String getRandomElements() {
        StringBuilder result = new StringBuilder();

        List<Elements> randomElements = getRandomList();

        for (int i = 0; i < randomElements.size(); i++) {
            result.append(randomElements.get(i));
        }

        return result.toString();
    }

    private List<Elements> getRandomList() {
        List<Elements> result = new LinkedList<>();
        result.addAll(Arrays.asList(LevelImpl.DIGITS));
        result.add(Elements.HERO);

        do {
            Collections.shuffle(result);
        } while (!isSolvability(result));

        return result;
    }

    private boolean isSolvability(List<Elements> result) {
        int sum = 0;
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i) == Elements.HERO) {
                sum += 1 + i / (int) Math.sqrt(result.size());
                continue;
            }
            for (int j = i + 1; j < result.size(); j++) {
                if (result.get(j) == Elements.HERO) {
                    continue;
                }
                if (result.get(j).ch() < result.get(i).ch()) {
                    sum++;
                }
            }
        }
        return (sum & 1) == 0;
    }
}
