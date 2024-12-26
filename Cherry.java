/*
 * Created on 2024-11-21
 *
 * Copyright (c) 2024 Nadine von Frankenberg
 */

public class Cherry extends Food {
    // Cherry position on the game board
    private Position position;
    // Cherry Icon path
    private String icon = "./assets/cherries.png";

    // Constructor to Initialize a cherry at a position
    public Cherry(Position position) {
        this.position = position;
    }

    // Getters for the cherry position and the icon path
    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public String getIcon() {
        return this.icon;
    }
}
