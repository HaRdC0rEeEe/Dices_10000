package Controller;

import Dices.Player;

import javax.swing.*;

public class CheckWinner{
    private final int TEN_K = 10000;
    private final Player p1;
    private final Player p2;
    private boolean wasSecondThrow = false;
    private Player winner;

    public CheckWinner(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        winner = null;
    }

    public boolean foundWinner() {

        //compare which player has higher score after first player has passed 10 000
        if(wasSecondThrow){
            winner = comparePlayers();
            if(winner == null)
                wasSecondThrow = false;
        } else{
            if(!validate(p1, p2))
                validate(p2, p1);
        }
        return wasSecondThrow && winner != null;

    }


    private boolean validate(Player winner, Player loser) {
        if(winner.getScore() >= TEN_K){
            String winnerTxt = winner.getName() + " has passed " + TEN_K;
            String loserTxt = loser.getName() + " has last round to receive higher score than " + winner.getName();

            JOptionPane.showMessageDialog(null, winnerTxt + "\n" + loserTxt);
            return wasSecondThrow = true;
        }
        return false;
    }

    private Player comparePlayers() {

        //players will throw again if they will have same score after second throw
        if(p1.getScore() >= TEN_K || p2.getScore() >= TEN_K){
            return p1.getScore() > p2.getScore() ? p1 : tieBreaker(p1, p2);
        }
        return null;
    }

    private Player tieBreaker(Player p1, Player p2) {
        return p1.getScore() == p2.getScore() ? null : p2;
    }

    public Player getWinner() {
        return winner;
    }
}
