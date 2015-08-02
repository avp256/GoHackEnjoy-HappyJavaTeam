package com.codenjoy.dojo.sample.model;

import com.codenjoy.dojo.services.PrinterFactory;
import com.codenjoy.dojo.utils.TestUtils;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.EventListener;
import com.codenjoy.dojo.services.PrinterFactoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class FifteenTest {

    private Fifteen game;
    private Hero hero;
    private Dice dice;
    private EventListener listener;
    private Player player;
    private PrinterFactory printer = new PrinterFactoryImpl();

    @Before
    public void setup() {
        dice = mock(Dice.class);
    }

    private void dice(int...ints) {
        OngoingStubbing<Integer> when = when(dice.next(anyInt()));
        for (int i : ints) {
            when = when.thenReturn(i);
        }
    }

    private void givenFl(String board) {
        LevelImpl level = new LevelImpl(board);
        //Hero hero = level.getHero().get(0);

        game = new Fifteen(level, dice);
        listener = mock(EventListener.class);
        player = new Player(listener);
        game.newGame(player);
//        player.hero = hero;
//        hero.init(game);
        this.hero = game.getHeroes().get(0);
    }

    private void assertE(String expected) {
        assertEquals(TestUtils.injectN(expected),
                printer.getPrinter(game.reader(), player).print());
    }

    // есть поле карта со мной
    @Test
    public void shouldFieldAtStart() {
        givenFl("******" +
                "*abcd*" +
                "*efgh*" +
                "*ijkl*" +
                "*mno+*" +
                "******");

        assertE("******" +
                "*abcd*" +
                "*efgh*" +
                "*ijkl*" +
                "*mno+*" +
                "******");
    }

    @Test
    public void newGameTest() {
        givenFl("******" +
                "*abcd*" +
                "*efgh*" +
                "*ijk+*" +
                "*mnol*" +
                "******");

        game.tick();
        assertTrue(hero.isAlive());

        assertE("******" +
                "*abcd*" +
                "*efgh*" +
                "*ijk+*" +
                "*mnol*" +
                "******");

        hero.down();
        game.tick();

        assertE("******" +
                "*abcd*" +
                "*efgh*" +
                "*ijkl*" +
                "*mno+*" +
                "******");

        assertFalse(hero.isAlive());
        game.newGame(player);

        assertE("******" +
                "*abcd*" +
                "*efgh*" +
                "*ijk+*" +
                "*mnol*" +
                "******");
    }

    //движение вверх
    @Test
    public void shouldMoveUp() {
        givenFl("******" +
                "*abcd*" +
                "*efgh*" +
                "*ijkl*" +
                "*mno+*" +
                "******");

        hero.up();
        game.tick();

        assertE("******" +
                "*abcd*" +
                "*efgh*" +
                "*ijk+*" +
                "*mnol*" +
                "******");
    }

    //движение вниз
    @Test
    public void shouldMoveDown() {
        givenFl("******" +
                "*abcd*" +
                "*efgh*" +
                "*ijk+*" +
                "*mnol*" +
                "******");

        hero.down();
        game.tick();

        assertE("******" +
                "*abcd*" +
                "*efgh*" +
                "*ijkl*" +
                "*mno+*" +
                "******");
    }

    //движение вправо
    @Test
    public void shouldMoveRight() {
        givenFl("******" +
                "*abcd*" +
                "*efgh*" +
                "*ijkl*" +
                "*m+no*" +
                "******");

        hero.right();
        game.tick();

        assertE("******" +
                "*abcd*" +
                "*efgh*" +
                "*ijkl*" +
                "*mn+o*" +
                "******");
    }

    //движение влево
    @Test
    public void shouldMoveLeft() {
        givenFl("******" +
                "*abcd*" +
                "*efgh*" +
                "*ijkl*" +
                "*mno+*" +
                "******");

        hero.left();
        game.tick();

        assertE("******" +
                "*abcd*" +
                "*efgh*" +
                "*ijkl*" +
                "*mn+o*" +
                "******");
    }

    //в стену - нечего не делаем
    @Test
    public void shouldDoNothingWhenMoveToWall() {
        givenFl("******" +
                "*abcd*" +
                "*efgh*" +
                "*ijkl*" +
                "*mno+*" +
                "******");

        hero.right();
        game.tick();

        assertE("******" +
                "*abcd*" +
                "*efgh*" +
                "*ijkl*" +
                "*mno+*" +
                "******");
    }
}
