package com.codenjoy.dojo.fifteen.model;

import com.codenjoy.dojo.fifteen.services.Events;
import com.codenjoy.dojo.services.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * О! Это самое сердце игры - борда, на которой все происходит.
 * Если какой-то из жителей борды вдруг захочет узнать что-то у нее, то лучше ему дать интефейс {@see Field}
 * Борда реализует интерфейс {@see Tickable} чтобы быть уведомленной о каждом тике игры. Обрати внимание на {Fifteen#tick()}
 */
public class Fifteen implements Tickable, Field {
    private final Level level;
    private List<Player> players;

    private List<Digit> digits;
    private List<Wall> walls;

    private int size;
    private Dice dice;

    public Fifteen(Level level, Dice dice) {
        this.level = level;
        this.dice = dice;
        size = level.getSize();
        players = new LinkedList<>();
    }

    /**
     * @see Tickable#tick()
     */
    @Override
    public void tick() {
        for (Player player : players) {
            Hero hero = player.getHero();
            hero.tick();

            if(isAllPositionCorrect()){
                player.event(Events.WIN);
                player.getHero().die();
            }
        }
    }

    private boolean isAllPositionCorrect() {
        for(Digit digit: digits){
            if(!(new DigitHandler().isRightPosition(digit))){
               return false;
            }
        }
        return true;
    }

    public int size() {
        return size;
    }

    @Override
    public boolean isBarrier(int x, int y) {
        Point pt = PointImpl.pt(x, y);
        return x > size - 1 || x < 0 || y < 0 || y > size - 1 || walls.contains(pt);
    }

    @Override
    public Digit getDigit(int x, int y) {
        for (Digit point : digits) {
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
        Point pt = PointImpl.pt(x, y);

        return !digits.contains(pt) &&
                !walls.contains(pt) &&
                !getHeroes().contains(pt);
    }

    public List<Hero> getHeroes() {
        List<Hero> result = new ArrayList<>();
        for (Player player : players) {
            result.add(player.getHero());
        }
        return result;
    }

    public void newGame(Player player) {
        walls = level.getWalls();
        digits = level.getDigits();
        size = level.getSize();
        List<Hero> heros = level.getHero();
        if (!players.contains(player)) {
            players.add(player);
        }
        player.setHero(heros.get(0));
        player.hero.init(this);
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
            private int size = Fifteen.this.size;

            @Override
            public int size() {
                return size;
            }

            @Override
            public Iterable<? extends Point> elements() {
                List<Point> result = new LinkedList<Point>();
                result.addAll(Fifteen.this.getWalls());
                result.addAll(Fifteen.this.getHeroes());
                result.addAll(Fifteen.this.getDigits());
                return result;
            }
        };
    }
}
