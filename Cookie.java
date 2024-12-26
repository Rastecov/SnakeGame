
/*
* Created on 2024-11-21
*
* Copyright (c) 2024 Nadine von Frankenberg
*/

public class Cookie extends Food {
    // Cookie position on the game board
    private Position position;
    // Cookie Icon path
    private String icon = "./assets/cookie.png";

    // Constructor to Initialize a cookie at a position
    public Cookie(Position position) {
        this.position = position;
    }

    // Getters for the cookie position and the icon path
    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public String getIcon() {
        return this.icon;
    }
}
