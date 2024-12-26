public class Lemon extends Food {
    // Lemon position on the game board
    private Position position;
    // Lemon Icon Path
    private String icon = "./assets/lemon.png";

    // Constructor to Initialize a lemon at a position
    public Lemon(Position position) {
        this.position = position;
    }

    // Getters for the lemon position and the icon path
    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public String getIcon() {
        return this.icon;
    }
}
