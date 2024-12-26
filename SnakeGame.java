/*
 * Created on 2024-11-21
 *
 * Copyright (c) 2024 Nadine von Frankenberg
 */

import java.util.HashMap;


import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

public class SnakeGame extends PApplet {

    // Declaring and initializing game parameter constant

    public static final int SQUARE_SIZE = 20;
    public static final int MAX_ACTIVE_FOOD = 1;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int SPEED = 10;

    // Declaring and initializing game assets
    public Snake snake = new Snake();
    public HashMap<String, PImage> assets = new HashMap<>();
    // List of active food items
    public ArrayList<Food> foodList = new ArrayList<>();
    // Snake head image
    private PImage headImage;

    // Initializing Game flags
    // make the flag for game is over false by default
    // make the flag for game is paused false by default
    private boolean isPaused = false;
    // flag to toogle the edge wrapping false by default
    private boolean wrappingToggle = false;
    // flag to make visible the edge wrapping button true by default
    private boolean showToggleButton = true;
    // Track the current game State
    private GameStates gamestates = GameStates.WELCOME;
   

    public static void main(String[] args) {
        // Start the snake game
        String[] appletArgs = new String[] { "SnakeGame" };
        PApplet.main(appletArgs);
    }

    @Override
    public void settings() {
        smooth(0);
        // Define the size of the game window
        size(800, 600);
    }

    @Override
    public void setup() {
        // Change the framerate for a harder/easier game
        frameRate(SPEED);

        // Load all the assets that are used often (just the snake parts, not the food!)
        assets.put("head_up", loadAsset("./assets/head_up.png"));
        assets.put("head_down", loadAsset("./assets/head_down.png"));
        assets.put("head_left", loadAsset("./assets/head_left.png"));
        assets.put("head_right", loadAsset("./assets/head_right.png"));
        assets.put("body", loadAsset("./assets/body.png"));

        // The snake always moves to the right by default
        // Hence, the right head is set as starting head
        headImage = assets.get("head_right");

        // Grow the snake so we do not start at 0
        snake.shouldGrow();

        // Create food at random positions
        for (int i = 0; i < MAX_ACTIVE_FOOD; i++) {
            foodList.add(Food.randomFood(Position.randomPosition()));
        }

    }

    public PImage loadAsset(String path) {
        PImage image = loadImage(path);
        return image;
    }

    @Override
    public void draw() {
        background(0);
        // Game States WELCOME, RUNNING, GAMEOVER
        switch (gamestates) {
            case WELCOME:
                // Show the welcome screen
                showWelcomeScreen();
                break;

            case RUNNING:
                if (isPaused) {
                    // Show the pause screen if isPaused is true
                    textAlign(CENTER, CENTER);
                    fill(255);
                    textSize(64);
                    text("GAME PAUSED", WIDTH / 2.0f, HEIGHT / 2.0f);
                } else {
                    // call runSnakeGame method to run the game
                    // to the game is ispause is false
                    runSnakeGame();
                }

                break;
            case GAMEOVER:
                // call showGameOverScreen method to show the gameover screen
                // the game over screen
                showGameOverScreen();
                break;

        }

        if (gamestates == GameStates.RUNNING && !isPaused) {
            // Draw food first so the snake covers it up
            drawFood();

            // Draw the snake body and the snake head
            drawBody();

            image(headImage, snake.getSnakeHead().getPosition().x * SQUARE_SIZE,
                    snake.getSnakeHead().getPosition().y * SQUARE_SIZE,
                    SQUARE_SIZE, SQUARE_SIZE);

            // Check if the snake ate the food or collide with itself
            checkFoodCollision();
            checkGameOver();

        }

        // Move once a frame: findNextMove not implemented
        Direction randomMove = snake.findNextMove(foodList);
        if (randomMove != null) {
            snake.setDirection(randomMove);
        }

    }

    // Check if the snake has eaten some food
    public void checkFoodCollision() {
        Food eatenFood = null;
        for (Food food : foodList) {
            // Check if the snake collide with itself
            if (snake.getSnakeHead().getPosition().equals(food.getPosition())) {
                // Make the snake grow
                snake.shouldGrow();
                // Indicate that the snake ate the food
                eatenFood = food;
                break;
            }
        }
        if (eatenFood != null) {
            // remove the eaten food from the board then generate a new random food
            foodList.remove(eatenFood);
            // Generate a random position for the new food
            Position randomPosition;
            // generate a random position that is not occupied by the snake
            do {
                randomPosition = Position.randomPosition();
            } while (snake.isBodyPartAt(randomPosition));
            // Add new food at the random position generated
            foodList.add(Food.randomFood(randomPosition));
        }
    }

    public void checkGameOver() {
        // Check if the snake collided with itself
        if (snake.isColliding()) {
            // Put the game as over then change the states of the game to game over
            gamestates = GameStates.GAMEOVER;

        }
    }

    public void showWelcomeScreen() {

        background(0);
        // Align the text at the center
        textAlign(CENTER, CENTER);
        // make the text color white
        fill(255);
        textSize(54);
        // Centered, slightly above vertical center
        text("WELCOME TO THE SNAKE GAME ", WIDTH / 2.0f, HEIGHT / 2.70f);
        // Welcome text
        textSize(25);
        text("Press ENTER or START GAME button", WIDTH / 2.0f, HEIGHT / 2.20f);
        // Display the edge wrapping button
        drawWrappingToggleButton(WIDTH / 2 - 75, HEIGHT / 2);
        // start game button
        fill(255);
        textAlign(CENTER, CENTER);
        textSize(20);
        text("START GAME", WIDTH / 2, HEIGHT / 2 + 70);



    }

    public void showGameOverScreen() {
        // Center text alignment
        textAlign(CENTER, CENTER);
        textSize(64);
        // Centered, slightly above vertical center
        text("Your Score: " + snake.getScore(), WIDTH / 2.0f, HEIGHT / 2.5f);
        textSize(32);
        // Center horizontally, slightly below vertical center
        text("Press SPACE to go Back to the Welcome Screen....", WIDTH / 2.0f, HEIGHT / 1.6f);
        drawWrappingToggleButton(WIDTH / 2 - 75, HEIGHT / 2);

    }

    public void startGame() {
        //

        gamestates = GameStates.RUNNING;
        snake = new Snake();

        // Reset direction to default
        snake.setDirection(Direction.RIGHT);
        snake.shouldGrow();

        // Reset head image to match the starting direction
        headImage = assets.get("head_right");

        // Reset food list
        foodList.clear();
        for (int i = 0; i < MAX_ACTIVE_FOOD; i++) {
            // Generate a random position that is not occupied by the snake to add the food
            Position randomPosition;
            do {
                randomPosition = Position.randomPosition();
            } while (snake.isBodyPartAt(randomPosition));
            // Add new food at the random position generated
            foodList.add(Food.randomFood(randomPosition));
        }
        gamestates = GameStates.RUNNING;

    }

    private void runSnakeGame() {
        if (isPaused) {
            return;
        }
        if (!snake.move()) {
            gamestates = GameStates.GAMEOVER;
            return;
        }

        if (wrappingToggle) {
            edgeWrapping();
        } else {
            checkEdgeCollision();
        }
        checkFoodCollision();

    }

    public void restart() {
        // reset the game state to the welcome screen and show the Edge wrapping toogle
        // button
        gamestates = GameStates.WELCOME;
        showToggleButton = true;
    }

    // OPTIONAL: Implement a pause() functionality
    public void pause() {
        isPaused = !isPaused;
    }

    public void drawFood() {
        // Save the food icons in the assets so we do not have to keep importing it
        for (Food food : foodList) {
            if (!assets.containsKey(food.getIcon())) {
                assets.put(food.getIcon(), loadAsset(food.getIcon()));
            }

            image(assets.get(food.getIcon()), food.getPosition().x * SQUARE_SIZE,
                    food.getPosition().y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }
    }

    public void drawSegment(SnakeSegment segment) {
        image(assets.get("body"), segment.getPosition().x * SQUARE_SIZE,
                segment.getPosition().y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    // TODO: Draw the body
    public void drawBody() {
        // HINT: Make use of drawSegment()!
        SnakeSegment current = snake.getBody();
        while (current != null) {
            drawSegment(current);
            current = current.getNext();

        }

    }

    // OPTIONAL:
    // Or check for a collision with the top and bottom of the frame
    public void checkEdgeCollision() {
        Position headPosition = snake.getSnakeHead().getPosition();
        int x = headPosition.x;
        int y = headPosition.y;

        // Check if head is out of bounds
        if (x < 0 || x >= WIDTH / SQUARE_SIZE || y < 0 || y >= HEIGHT / SQUARE_SIZE) {
            gamestates = GameStates.GAMEOVER;
        }
    }

    // OPTIONAL:
    // Handle left and right, top and bottom wrapping

    public void edgeWrapping() {
        // get the snake head position
        Position headPosition = snake.getSnakeHead().getPosition();
        int x = headPosition.x;
        int y = headPosition.y;
        // Wrap from left to right
        if (x < 0) {
            x = WIDTH / SQUARE_SIZE - 1;
        }
        // Wrap from right to left
        if (x >= WIDTH / SQUARE_SIZE) {
            x = 0;
        }
        // Wrap from top to bottom
        if (y < 0) {
            y = HEIGHT / SQUARE_SIZE - 1;
        }
        // Wrap from bottom to top
        if (y >= HEIGHT / SQUARE_SIZE) {
            y = 0;
        }
        // set the snake head to the coordinates(x,y) defined earlier
        snake.getSnakeHead().setPosition(new Position(x, y));

    }

    // Draw the toogle button to turn on or off the snake edge wrapping with
    // coordinates x and y
    private void drawWrappingToggleButton(int x, int y) {

        // Check if the toogle button is not visible, if yes do not display it
        if (!showToggleButton) {
            return;
        }

        textSize(20);
        textAlign(CENTER, CENTER);
        // Display "CLICK ON SNAKE: TO TURN THE" in white
        fill(255);
        text("CLICK ON SNAKE: TO TURN THE", x - 50, y + 20);
        // Display "SNAKE" in green
        fill(0, 255, 0);
        text("SNAKE", x + 120, y + 20);
        // Display the Edge wrapping text ON in white and the Edge wrapping text OFF in
        // red
        fill(wrappingToggle ? color(225) : color(255, 0, 0));
        // Display Edge wrapping text button OFF if the wrappingToggle flag is true else it is
        // false then display ON
        text("EDGE WRAPPING " + (wrappingToggle ? "OFF" : "ON"), x + 240, y + 20);

    }

    // TODO: Set the direction depending on pressing a key
    @Override
    public void keyPressed() {
        /**
         * keycode: There is an enum for all special keyboard keys
         * see
         * https://processing.github.io/processing-javadocs/core/processing/core/PApplet.html#keyCode
         * This enum is accessible in our current scope, so we do not
         * need have to use the dot ('.') operator to access them
         * (the 'keyCode' is not the same as 'Direction'!)
         */

            if(gamestates==GameStates.WELCOME && keyCode==ENTER){
               
                    startGame();
                // Set the edge wrapping toogle button to not visible
                showToggleButton = false;
                
            }else if (gamestates == GameStates.GAMEOVER && key == ' ') {

                // Restart the game by pressing spacebar
                restart();
            }

        if (gamestates == GameStates.RUNNING) {
            // Pause the game when the spacebar key is pressed
            if (key == ' ') {
                isPaused = !isPaused;
                return;
            }

            //
            if (!isPaused) {
                switch (keyCode) {
                    // Prevent snake reverse collision, update the snake head image and set the
                    // player direction
                    case UP:
                        if (snake.getDirection() != Direction.DOWN) {
                            headImage = assets.get("head_up");
                            snake.setDirection(Direction.UP);

                        }
                        break;
                    case DOWN:
                        if (snake.getDirection() != Direction.UP) {
                            headImage = assets.get("head_down");
                            snake.setDirection(Direction.DOWN);

                        }
                        break;
                    case LEFT:
                        if (snake.getDirection() != Direction.RIGHT) {

                            headImage = assets.get("head_left");
                            snake.setDirection(Direction.LEFT);

                        }
                        break;
                    case RIGHT:
                        if (snake.getDirection() != Direction.LEFT) {

                            headImage = assets.get("head_right");
                            snake.setDirection(Direction.RIGHT);
                        }

                        break;
                    default:
                        break;

                }

            }
        }
        if (gamestates == GameStates.GAMEOVER && key == ' ') {

            // Restart the game by pressing spacebar
            restart();
        }
    }

    @Override
    public void mousePressed() {

        // check is gamestate is in WELCOME screen
        if (gamestates == GameStates.WELCOME) {

            // Check if the mouse click is inside the edge wrapping toogle button text area
            // boundary
            if (mouseX >= WIDTH / 2 - 75 && mouseX <= WIDTH / 2 + 75 && mouseY >= HEIGHT / 2
                    && mouseY <= HEIGHT / 2 + 30) {
                // Toogle the wrapping button ON or OFF after each mouse click within the
                // boundary
                wrappingToggle = !wrappingToggle;
            }

            // Check if the mouse click is inside the start the game button text area
            // boundary
            if (mouseX >= WIDTH / 2 - 75 && mouseX <= WIDTH / 2 + 75 && mouseY >= HEIGHT / 2 + 50
                    && mouseY <= HEIGHT / 2 + 90) {
                // Start the game
                startGame();
                // Set the edge wrapping toogle button to not visible
                showToggleButton = false;
            }

        }

        // Check is gamestate is in GAMEOVER screen
        else if (gamestates == GameStates.GAMEOVER) {

            // Toogle the wrapping button ON or OFF following each mouse click
            wrappingToggle = !wrappingToggle;
            // }

        }

    }

}
