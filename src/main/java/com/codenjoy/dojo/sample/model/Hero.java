package com.codenjoy.dojo.sample.model;

import com.codenjoy.dojo.sample.services.Events;
import com.codenjoy.dojo.services.*;
import com.codenjoy.dojo.services.Point;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Это реализация героя. Обрати внимание, что он имплементит {@see Joystick}, а значит может быть управляем фреймворком
 * Так же он имплементит {@see Tickable}, что значит - есть возможность его оповещать о каждом тике игры.
 */
public class Hero extends PointImpl implements Joystick, Tickable, State<Elements, Player> {
    private Player player;
    private Field field;
    private boolean alive;
    private Direction direction;

    public Hero(int x, int y) {
        super(x, y);
        direction = null;
        alive = true;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Hero(Point xy) {
        super(xy);
        direction = null;
        alive = true;
    }

    public void init(Field field) {
        this.field = field;

    }

    @Override
    public void down() {
        if (!alive) return;

        direction = Direction.DOWN;
    }

    @Override
    public void up() {
        if (!alive) return;

        direction = Direction.UP;
    }

    @Override
    public void left() {
        if (!alive) return;

        direction = Direction.LEFT;
    }

    @Override
    public void right() {
        if (!alive) return;

        direction = Direction.RIGHT;
    }

    @Override
    public void act(int... p) {
        // Do nothing
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void tick() {
        if (!alive) return;

        if (direction != null) {
            int newX = direction.changeX(getX());
            int newY = direction.changeY(getY());

            if (!field.isBarrier(newX, newY)) {
                Digit digit = field.getDigit(newX, newY);
                digit.move(getX(), getY());
                move(newX, newY);

                if (new PositionHandler().isRightPosition(digit)) {
                    // TODO
                    player.event(new Bonus(1, 1));
                }
            }

        }
        direction = null;
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public Elements state(Player player, Object... alsoAtPoint) {
        return Elements.HERO;
    }
}
