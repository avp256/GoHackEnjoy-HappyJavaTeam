package com.codenjoy.dojo.sample.model;

import com.codenjoy.dojo.services.LengthToXY;
import com.codenjoy.dojo.services.Point;

import java.util.LinkedList;
import java.util.List;

/**
 * Полезный утилитный класс для получения объектов на поле из текстового вида.
 */
public class LevelImpl implements Level {
    public static final Elements[] DIGITS =
            {
                    Elements.A,
                    Elements.B,
                    Elements.C,
                    Elements.D,
                    Elements.E,
                    Elements.F,
                    Elements.G,
                    Elements.H,
                    Elements.I,
                    Elements.J,
                    Elements.K,
                    Elements.L,
                    Elements.M,
                    Elements.N,
                    Elements.O
            };

    private final LengthToXY xy;

    private String map;

    public LevelImpl(String map) {
        this.map = map;
        xy = new LengthToXY(getSize());
    }

    @Override
    public List<Digit> getDigits() {
        List<Digit> result = new LinkedList<Digit>();

        for (Elements digit : DIGITS) {
            for (Point pt : getPointsOf(digit)) {
                result.add(new Digit(pt, digit));
            }
        }
        return result;
    }

    @Override
    public int getSize() {
        return (int) Math.sqrt(map.length());
    }

    @Override
    public List<Hero> getHero() {
        List<Hero> result = new LinkedList<Hero>();

        for (Point pt : getPointsOf(Elements.HERO)) {
            result.add(new Hero(pt));
        }

        return result;
    }

    @Override
    public List<Gold> getGold() {
        List<Gold> result = new LinkedList<Gold>();
//
//        for (Point pt : getPointsOf(Elements.GOLD)) {
//            result.add(new Gold(pt));
//        }

        return result;
    }

    @Override
    public List<Wall> getWalls() {
        List<Wall> result = new LinkedList<Wall>();


//        for (Point pt : getPointsOf(Elements.WALL)) {
//            result.add(new Wall(pt));
//        }

        return result;
    }

    private List<Point> getPointsOf(Elements element) {
        List<Point> result = new LinkedList<Point>();
        for (int index = 0; index < map.length(); index++) {
            if (map.charAt(index) == element.ch) {
                result.add(xy.getXY(index));
            }
        }
        return result;
    }
}
