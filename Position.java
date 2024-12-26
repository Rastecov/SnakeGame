/*
 * Created on 2024-11-21
 *
 * Copyright (c) 2024 Nadine von Frankenberg
 */

import java.util.Random;

public class Position {
    private static Random rand = new Random();
    // define x and y coordinate for the game board
    protected int x;
    protected int y;

    // Intialize a constructor with coordinate
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // return a new position that represent the sum of the current and a new
    // position
    public Position add(Position position) {
        return new Position(this.x + position.x, this.y + position.y);
    }

    // Return true if the position are equal and false if they are not
    public boolean equals(Position position) {
        return this.x == position.x && this.y == position.y;
    }

    // generate a random position based on the game board dimension
    public static Position randomPosition() {
        int randomX = SnakeGame.WIDTH / SnakeGame.SQUARE_SIZE;
        int randomY = SnakeGame.HEIGHT / SnakeGame.SQUARE_SIZE;

        return new Position(rand.nextInt(randomX), rand.nextInt(randomY));
    }
}
