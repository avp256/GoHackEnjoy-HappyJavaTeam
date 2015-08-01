package com.codenjoy.dojo.sample.services;

import com.codenjoy.dojo.services.PlayerScores;
import com.codenjoy.dojo.services.settings.Settings;
import com.codenjoy.dojo.services.settings.SettingsImpl;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: sanja
 * Date: 05.06.13
 * Time: 20:35
 */
public class ScoresTest {
    private PlayerScores scores;

    private Settings settings;
//    private Integer loosePenalty;
//    private Integer winScore;
    private Integer bonusScore;

//    public void loose() {
//        scores.event(Events.LOOSE);
//    }
//
//    public void win() {
//        scores.event(Events.WIN);
//    }

    public void bonus() {
        scores.event(Events.BONUS);
    }

    @Before
    public void setup() {
        settings = new SettingsImpl();
        scores = new Scores(0, settings);

//        loosePenalty = settings.getParameter("Loose penalty").type(Integer.class).getValue();
//        winScore = settings.getParameter("Win score").type(Integer.class).getValue();
        bonusScore = settings.getParameter("Bonus score").type(Integer.class).getValue();
    }

    @Test
    public void shouldCollectScores() {
        scores = new Scores(250, settings);

        bonus(); // + 100
        bonus(); // + 100

        Assert.assertEquals(250 + bonusScore * 2, scores.getScore());
    }

//    @Test
//    public void shouldCollectScores() {
//        scores = new Scores(140, settings);
//
//        win();  //+30
//        win();  //+30
//        win();  //+30
//        win();  //+30
//
//        loose(); //-100
//
//        Assert.assertEquals(140 + 4 * winScore - loosePenalty, scores.getScore());
//    }
//
//    @Test
//    public void shouldStillZeroAfterDead() {
//        loose();    //-100
//
//        Assert.assertEquals(0, scores.getScore());
//    }
//
//    @Test
//    public void shouldClearScore() {
//        win();    // +30
//
//        scores.clear();
//
//        Assert.assertEquals(0, scores.getScore());
//    }


}
