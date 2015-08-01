package com.codenjoy.dojo.sample.model;

import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 02.08.2015.
 */
public class PositionHandler {
    private static final Map<Elements, Point> CORRECT_POSITION = new HashMap<>();

    static {
        CORRECT_POSITION.put(Elements.A, new PointImpl(0, 3));
        CORRECT_POSITION.put(Elements.B, new PointImpl(1, 3));
        CORRECT_POSITION.put(Elements.C, new PointImpl(2, 3));
        CORRECT_POSITION.put(Elements.D, new PointImpl(3, 3));
        CORRECT_POSITION.put(Elements.E, new PointImpl(0, 2));
        CORRECT_POSITION.put(Elements.F, new PointImpl(1, 2));
        CORRECT_POSITION.put(Elements.G, new PointImpl(2, 2));
        CORRECT_POSITION.put(Elements.H, new PointImpl(3, 2));
        CORRECT_POSITION.put(Elements.I, new PointImpl(0, 1));
        CORRECT_POSITION.put(Elements.J, new PointImpl(1, 1));
        CORRECT_POSITION.put(Elements.K, new PointImpl(2, 1));
        CORRECT_POSITION.put(Elements.L, new PointImpl(3, 1));
        CORRECT_POSITION.put(Elements.M, new PointImpl(0, 0));
        CORRECT_POSITION.put(Elements.N, new PointImpl(1, 0));
        CORRECT_POSITION.put(Elements.O, new PointImpl(2, 0));
    }


    public boolean isRightPosition(Digit digit) {
        Elements element = digit.state(null);
        return digit.itsMe(CORRECT_POSITION.get(element));
    }
}
