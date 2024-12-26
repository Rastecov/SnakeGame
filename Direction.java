/*
 * Created on 2024-11-21
 *
 * Copyright (c) 2024 Nadine von Frankenberg
 */

public enum Direction {
    // Snake movement
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    // Define x and y coordinate od the snake direction
    private final int x;
    private final int y;

    // Constructor to initialize x and y coordinates
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position deltaPosition() {
        // TODO: should return the new position after the snake has moved
        // The position should either move +1 or -1 along the x or y axis
        return new Position(x, y); // default position if not moving
    }
}
