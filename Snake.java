/*
 * Created on 2024-11-21
 *
 * Copyright (c) 2024 Nadine von Frankenberg
 */

import java.util.ArrayList;

public class Snake {
    // The snake head
    private SnakeSegment snakeHead;
    // The current direction the snake is moving to
    private Direction direction = Direction.RIGHT; // Default direction
    // Initialize shouldgrow to false to handle the snake growing only by one
    // segment for each food it eat
    private boolean shouldGrow = false;

    // Constructor to initialize the starting position
    public Snake() {
        // You may change this code for extra credit (implement some fancy stuff!)
        // Feel free to make the starting position random
        Position startingPosition = Position.randomPosition();
        // put the head of the snake at a random position at the start the game
        snakeHead = new SnakeSegment(startingPosition);
    }

    // TODO: The snake should grow whenever it "eats" a food item
    public void shouldGrow() {
        this.shouldGrow = true;

    }

    // TODO: Remove the last node (tail) of the snake, leave head untouched
    private void removeTail() {
        SnakeSegment currentSnakeSegment = snakeHead;
        // Check if there is only one segment, if yes do not remove the tail
        if (currentSnakeSegment.getNext() == null) {
            return;
        }

        // go the the second last segment
        while (currentSnakeSegment.getNext().getNext() != null) {
            currentSnakeSegment = currentSnakeSegment.getNext();
        }

        // Remove the tail
        currentSnakeSegment.setNext(null);

    }

    // Returns true if the snake is colliding with itself
    public boolean isColliding() {
        if (isBodyPartAt(snakeHead.getPosition())) {
            return true;
        }
        return false;
    }

    // TODO: Implement isInSnake()
    // Returns false if the specified position is inside the body of the snake
    public boolean isBodyPartAt(Position position) {
        SnakeSegment currentSnakeSegment = snakeHead.getNext();
        // Check if position is part of the snake body, return true if yes and False for
        // no
        while ((currentSnakeSegment != null)) {
            if (currentSnakeSegment.getPosition().equals(position)) {
                return true;
            }

            currentSnakeSegment = currentSnakeSegment.getNext();

        }
        return false;
    }

    // Sets the direction the snake will move in
    public void setDirection(Direction direction) {
        this.direction = direction;

    }

    // TODO: Get the length of the snake
    public int getLength() {
        int snakeLenght = 0;
        SnakeSegment currentSnakeSegment = snakeHead;

        while (currentSnakeSegment != null) {
            snakeLenght++;
            currentSnakeSegment = currentSnakeSegment.getNext();
        }
        return snakeLenght;
    }

    // Moves the snake by one in the next direction
    // TODO: Implement move()
    public boolean move() {
        // Get the new position depending on the current direction
        Position newPosition = snakeHead.getPosition().add(direction.deltaPosition());
        // ...
        // HINT: You may add and remove nodes here

        // Check if the snake collide with itself and return false if there is a
        // collision
        if (isBodyPartAt(newPosition)) {
            return false;
        }

        // Create a new head at a new position
        SnakeSegment newSnakeHead = new SnakeSegment(newPosition);
        newSnakeHead.setNext(snakeHead);
        snakeHead = newSnakeHead;
        // TODO: Uncomment and use the following code snippet
        // Remove the tail when the snake is not growing
        if (!shouldGrow) {
            removeTail();
        } else {
            shouldGrow = false;
        }

        return true;
    }

    // Return the head of the snake
    public SnakeSegment getSnakeHead() {
        return this.snakeHead;
    }

    // TODO: Return the start of the body (NOT the head!)
    public SnakeSegment getBody() {
        return snakeHead.getNext();
    }

    // OPTIONAL: Implement an algorithm that moves the food for us
    public Direction findNextMove(ArrayList<Food> food) {

        return null;
    }

    // get the snake direction
    public Direction getDirection() {
        return this.direction;
    }

    // Return the snake lenght -2 to get the actual score of the player
    public int getScore() {
        return getLength() - 2;
    }
}

class SnakeSegment {
    // The position of the current Snake segment
    private Position position;
    // The next snake segment
    private SnakeSegment next;

    // Constructor to initialize a snake segment at a specific position
    public SnakeSegment(Position pos) {
        this.position = pos;
    }

    // Get the position of the Snake Segment
    public Position getPosition() {
        return this.position;
    }

    // get the next segment in the snake body
    public SnakeSegment getNext() {
        return this.next;
    }

    // Set next Snake Segment
    public void setNext(SnakeSegment next) {
        this.next = next;
    }

    // set the position of the Snake Segment
    public void setPosition(Position position) {
        this.position = position;
    }
}
