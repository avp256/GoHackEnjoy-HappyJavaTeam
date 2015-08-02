package com.codenjoy.dojo.fifteen.client.ai;

import com.codenjoy.dojo.client.Direction;
import com.codenjoy.dojo.client.LocalGameRunner;
import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.fifteen.client.Board;
import com.codenjoy.dojo.fifteen.services.GameRunner;
import com.codenjoy.dojo.services.*;
import com.codenjoy.dojo.services.Point;

import java.util.*;

/**
 * Это алгоритм твоего бота. Он будет запускаться в игру с первым
 * зарегистрировавшимся игроком, чтобы ему не было скучно играть самому.
 * Реализуй его как хочешь, хоть на Random.
 * Для его запуска воспользуйся методом {@see FifteenSolver#main}
 */
public class FifteenSolver implements Solver<Board> {
//    private static final char[][] expected =
//            {
//                    {'*', '*', '*', '*', '*', '*'},
//                    {'*', 'a', 'b', 'c', 'd', '*'},
//                    {'*', 'e', 'f', 'g', 'h', '*'},
//                    {'*', 'i', 'j', 'k', 'l', '*'},
//                    {'*', 'm', 'n', 'o', '+', '*'},
//                    {'*', '*', '*', '*', '*', '*'}
//            };
//
//    //private Map<String, char[][]> open;
//    private int size;

    public FifteenSolver(Dice dice) {

    }

    @Override
    public String get(final Board board) {
        String[] directions = {
                Direction.DOWN.toString(),
                Direction.UP.toString(),
                Direction.RIGHT.toString(),
                Direction.LEFT.toString()};

        int random = 0;
        do {
            random = new Random().nextInt(directions.length);
        } while (!checkDirection(directions[random], board));

        return directions[random];

        //return "UP";
//        if (board.isGameOver()) return "";
//
//
//        size = board.size() - 2;
//        char[][] first = getArray(board);
//
//        return calcDirection(first);
    }

    private boolean checkDirection(String direction, Board board) {
        Point me = board.getMe();

        int newX = me.getX();
        int newY = me.getY();

        if(direction.equals("UP")){
            newY--;
        } else if(direction.equals("DOWN")){
            newY++;
        } else if(direction.equals("LEFT")){
            newX--;
        } else if(direction.equals("RIGHT")) {
            newX++;
        }

        return !board.isBarrierAt(newX, newY);
    }

//    private String calcDirection(char[][] prev) {
//        if (checkDirection(prev, -1, 0)) {
//            return "UP";
//        }
//        if (checkDirection(prev, 1, 0)) {
//            return "DOWN";
//        }
//        if (checkDirection(prev, 0, -1)) {
//            return "LEFT";
//        }
//        if (checkDirection(prev, 0, 1)) {
//            return "RIGHT";
//        }
//        return "";
//    }
//
//    private boolean checkDirection(char[][] prev, int dX, int dY) {
//        ArrayList<char[][]> open = new ArrayList<>();
//        char[][] start = move(prev, dX, dY);
//        if (start == null) {
//            return false;
//        }
//        open.add(prev);
//        open.add(start);
//        while (true) {
//            char[][][] befores = new char[Math.min(4, open.size())][][];
//            if (open.size() - 1 >= 0) {
//                befores[0] = open.get(open.size() - 1);
//            }
//            if (open.size() - 2 >= 0) {
//                befores[1] = open.get(open.size() - 2);
//            }
//            if (open.size() - 3 >= 0) {
//                befores[2] = open.get(open.size() - 3);
//            }
//            if (open.size() - 4 >= 0) {
//                befores[3] = open.get(open.size() - 4);
//            }
//            boolean flag = false;
//            for (char[][] before : befores) {
//                char[][] up = move(before, -1, 0);
//                if (up != null) {
//                    if (equels(up, expected)) {
//                        return true;
//                    }
//                    if (!exist(open, up)) {
//                        flag = true;
//                        open.add(up);
//                    }
//                }
//                char[][] down = move(before, 1, 0);
//                if (down != null) {
//                    if (equels(down, expected)) {
//                        return true;
//                    }
//                    if (!exist(open, down)) {
//                        flag = true;
//                        open.add(down);
//                    }
//                }
//                char[][] left = move(before, 0, -1);
//                if (left != null) {
//                    if (equels(left, expected)) {
//                        return true;
//                    }
//                    if (!exist(open, left)) {
//                        flag = true;
//                        open.add(left);
//                    }
//                }
//                char[][] right = move(before, 0, 1);
//                if (right != null) {
//                    if (equels(right, expected)) {
//                        return true;
//                    }
//                    if (!exist(open, right)) {
//                        flag = true;
//                        open.add(right);
//                    }
//                }
//            }
//            if (!flag) {
//                return false;
//            }
//        }
//    }
//
//    private boolean exist(ArrayList<char[][]> open, char[][] up) {
//        for (char[][] array : open) {
//            if (equels(array, up)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private char[][] move(char[][] prev, int dX, int dY) {
//        char[][] result = new char[size][size];
//
//        int x = -1; //положениие игрока
//        int y = -1; //положениие игрока
//
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                result[i][j] = prev[i][j]; // копируем в результат
//                if (prev[i][j] == '+') {// вычисляем положениие игрока
//                    x = i;
//                    y = j;
//                }
//            }
//        }
//
//        int newX = x + dX;
//        int newY = y + dY;
//
//        if (newX < 0 || newY < 0 || newX >= size || newY >= size) {
//            return null;
//        }
//
//        char temp = result[x][y];
//        result[x][y] = result[newX][newY];
//        result[newX][newY] = temp;
//
//        return result;
//    }
//
//    private boolean equels(char[][] array1, char[][] array2) {
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                if (array1[i][j] != array2[i][j]) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    private char[][] getArray(Board board) {
//        char[][] result = new char[size][size];
//        for (int i = 1; i <= size; i++) {
//            for (int j = 1; j <= size; j++) {
//                result[i - 1][j - 1] = board.getAt(j, i).ch();
//            }
//        }
//        return result;
//    }

//    private void initBoard(Board board) {
//        //open = new LinkedHashMap<>();
//        size = board.size();
//
//        char[][] first = new char[size][size];
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                first[i][j] = board.getAt(j, i).ch();
//            }
//        }
//        //open.put(first);
//    }

    /**
     * Метод для запуска игры с текущим ботом. Служит для отладки.
     */
    public static void main(String[] args) {
        LocalGameRunner.run(new GameRunner(),
                new FifteenSolver(new RandomDice()),
                new Board());
//        start(WebSocketRunner.DEFAULT_USER, WebSocketRunner.Host.LOCAL);
    }

    public static void start(String name, WebSocketRunner.Host server) {
        try {
            WebSocketRunner.run(server, name,
                    new FifteenSolver(new RandomDice()),
                    new Board());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
