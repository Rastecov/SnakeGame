/*
 * Created on 2024-11-21
 *
 * Copyright (c) 2024 Nadine von Frankenberg
 */

import java.util.Random;

public abstract class Food {
    // Get the position of the food
    public abstract Position getPosition();

    // Get the icon of the food in the file path
    public abstract String getIcon();

    // Put a random food at a position
    public static Food randomFood(Position position) {
        int maxActiveFoodItems = 3; // Represents the number of food items

        int randomNumber = new Random().nextInt(maxActiveFoodItems);

        switch (randomNumber) {
            // TODO: Add two more food items!
            case 0:
                return new Cherry(position);
            case 1:
                return new Cookie(position);
            case 2:
                return new Lemon(position);
            default:
                return new Cookie(position);
        }

    }
}
