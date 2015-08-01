package com.codenjoy.dojo.sample.services;

import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.sample.client.ai.FiveTeenSolver;
import com.codenjoy.dojo.sample.model.*;
import com.codenjoy.dojo.services.*;
import com.codenjoy.dojo.services.settings.Parameter;
import com.codenjoy.dojo.services.settings.Settings;
import com.codenjoy.dojo.services.settings.SettingsImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.codenjoy.dojo.services.settings.SimpleParameter.v;

/**
 * Генератор игор - реализация {@see GameType}
 * Обрати внимание на {@see GameRunner#SINGLE} - там реализовано переключение в режимы "все на одном поле"/"каждый на своем поле"
 */
public class GameRunner implements GameType {

    public final static boolean SINGLE = false;
    private final Settings settings;
    private final Level level;
    private Sample game;

    public GameRunner() {
        settings = new SettingsImpl();
        new Scores(0, settings);
        level = new LevelImpl(getRamdomMap());
    }

    private String getRamdomMap() {
        String randomElements = getRandomElements();

        return "******" +
                "*"+randomElements.substring(0,4)+"*" +
                "*"+randomElements.substring(4,8)+"*" +
                "*"+randomElements.substring(8,12)+"*" +
                "*"+randomElements.substring(12,16)+"*" +
                "******";
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
        // TODO
        return true;
    }

    private String getRandomElements() {
        StringBuilder result = new StringBuilder();

        List<Elements> randomElements = getRandomList();

        for (int i = 0; i < randomElements.size(); i++) {
            result.append(randomElements.get(i));
        }

        return result.toString();
    }

    private Sample newGame() {
        return new Sample(level, new RandomDice());
    }

    @Override
    public PlayerScores getPlayerScores(int score) {
        return new Scores(score, settings);
    }

    @Override
    public Game newGame(EventListener listener, PrinterFactory factory) {
        if (!SINGLE || game == null) {
            game = newGame();
        }

        Game game = new Single(this.game, listener, factory);
        game.newGame();
        return game;
    }

    @Override
    public Parameter<Integer> getBoardSize() {
        return v(level.getSize());
    }

    @Override
    public String name() {
        return "sample";
    }

    @Override
    public Enum[] getPlots() {
        return Elements.values();
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    @Override
    public boolean isSingleBoard() {
        return SINGLE;
    }

    @Override
    public void newAI(String aiName) {
        FiveTeenSolver.start(aiName, WebSocketRunner.Host.REMOTE);
    }
}
