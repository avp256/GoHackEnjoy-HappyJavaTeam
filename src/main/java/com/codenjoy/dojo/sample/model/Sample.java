package com.codenjoy.dojo.sample.model;

import com.codenjoy.dojo.sample.services.Events;
import com.codenjoy.dojo.services.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * О! Это самое сердце игры - борда, на которой все происходит.
 * Если какой-то из жителей борды вдруг захочет узнать что-то у нее, то лучше ему дать интефейс {@see Field}
 * Борда реализует интерфейс {@see Tickable} чтобы быть уведомленной о каждом тике игры. Обрати внимание на {Sample#tick()}
 */
public class Sample implements Tickable, Field {
    private List<Player> players;

    private List<Digit> digits;
    private List<Wall> walls;

    private final int size;
    private Dice dice;

    public Sample(Level level, Dice dice) {
        this.dice = dice;

        walls = level.getWalls();
        digits = level.getDigits();
        size = level.getSize();

        players = new LinkedList<Player>();
    }

    /**
     * @see Tickable#tick()
     */
    @Override
    public void tick() {
        for (Player player : players) {
            Hero hero = player.getHero();

            hero.tick();

//            if (gold.contains(hero)) {
//                gold.remove(hero);
//                player.event(Events.WIN);
//
//                Point pos = getFreeRandom();
//                gold.add(new Gold(pos.getX(), pos.getY()));
//            }
        }

//        for (Player player : players) {
//            Hero hero = player.getHero();
//
//            if (!hero.isAlive()) {
//                player.event(Events.LOOSE);
//            }
//        }
    }

    public int size() {
        return size;
    }

    @Override
    public boolean isBarrier(int x, int y) {

        // TODO
        Point pt = PointImpl.pt(x, y);
        return x > size - 1 || x < 0 || y < 0 || y > size - 1 || walls.contains(pt);
    }

    @Override
    public Point getDigit(int x, int y) {
        for (Point point : digits) {
            if (point.itsMe(x, y)) {
                return point;
            }
        }
        return null;
    }

    @Override
    public Point getFreeRandom() {
        int rndX = 0;
        int rndY = 0;
        int c = 0;
        do {
            rndX = dice.next(size);
            rndY = dice.next(size);
        } while (!isFree(rndX, rndY) && c++ < 100);

        if (c >= 100) {
            return PointImpl.pt(0, 0);
        }

        return PointImpl.pt(rndX, rndY);
    }

    @Override
    public boolean isFree(int x, int y) {
//        Point pt = PointImpl.pt(x, y);

        return true;
//        !gold.contains(pt) &&
//                !bombs.contains(pt) &&
//                !walls.contains(pt) &&
//                !getHeroes().contains(pt);
    }

    public List<Hero> getHeroes() {
        List<Hero> result = new ArrayList<Hero>(players.size());
        for (Player player : players) {
            result.add(player.getHero());
        }
        return result;
    }

    public void newGame(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }

        player.newHero(this);
    }

    public void remove(Player player) {
        players.remove(player);
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Digit> getDigits() {
        return digits;
    }

    public BoardReader reader() {
        return new BoardReader() {
            private int size = Sample.this.size;

            @Override
            public int size() {
                return size;
            }

            @Override
            public Iterable<? extends Point> elements() {
                List<Point> result = new LinkedList<Point>();
                result.addAll(Sample.this.getWalls());
                result.addAll(Sample.this.getHeroes());
                result.addAll(Sample.this.getDigits());
                return result;
            }
        };
    }
}
