package com.codenjoy.dojo.sample.model;

import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.services.State;

/**
 * Created by Administrator on 01.08.2015.
 */
public class Digit extends PointImpl implements State<Elements, Player> {

    private Elements element;

    public Digit(int x, int y ,Elements element) {
        super(x, y);
        this.element = element;
    }

    @Override
    public Elements state(Player player, Object... alsoAtPoint) {
        return element;
    }
}
