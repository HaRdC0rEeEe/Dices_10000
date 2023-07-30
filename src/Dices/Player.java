package Dices;

public class Player{
    private final String name;
    private int score;
    private boolean isOnTurn = false;

    public Player(String name) {
        this.name = name;
        score = 0;
    }

    public boolean isOnTurn() {
        return isOnTurn;
    }

    public void setOnTurn(boolean onTurn) {
        isOnTurn = onTurn;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    @Override
    public String toString() {
        return String.format("%s:\t %d", name, score);
    }

}
