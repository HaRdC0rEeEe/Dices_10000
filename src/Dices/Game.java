package Dices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Game{

    private static int highestScore;
    private final int ONE = 1;
    private final int TWO = 2;
    private final int THREE = 3;
    private final int FOUR = 4;
    private final int FIVE = 5;
    private final int SIX = 6;
    private int previousScore;
    private int score;
    private HashMap<Integer, Integer> frequencies;
    private ArrayList<Dice> keptDices;
    private ValidateDices validation;
    public Game() {
        keptDices = new ArrayList<>();
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setValidation(ValidateDices validation) {
        this.validation = validation;
    }

    public void resetGame() {
        frequencies = null;
        score = 0;
        previousScore = 0;
        keptDices = new ArrayList<>();
    }

    public ArrayList<Dice> getKeptDices() {
        return keptDices;
    }

    public HashMap<Integer, Integer> getFrequencies(ArrayList<Dice> keptDices) {

        ArrayList<Integer> tempDieList = new ArrayList<>();

        for(Dice d : keptDices)
            if(d != null)
                tempDieList.add(d.getThrow());

        int freq_one = Collections.frequency(tempDieList, ONE);
        int freq_two = Collections.frequency(tempDieList, TWO);
        int freq_three = Collections.frequency(tempDieList, THREE);
        int freq_four = Collections.frequency(tempDieList, FOUR);
        int freq_five = Collections.frequency(tempDieList, FIVE);
        int freq_six = Collections.frequency(tempDieList, SIX);

        frequencies = new HashMap<>();
        frequencies.put(ONE, freq_one);
        frequencies.put(TWO, freq_two);
        frequencies.put(THREE, freq_three);
        frequencies.put(FOUR, freq_four);
        frequencies.put(FIVE, freq_five);
        frequencies.put(SIX, freq_six);

        return frequencies;
    }

    public void keepDice(Dice keptDice) {
        keptDices.add(keptDice);
    }

    public void calculateScore() {

        if(!checkStrongestCombos())
            assignScore();

    }

    private void assignScore() {

        frequencies = getFrequencies(keptDices);
        score = 0;
        for(int i = ONE; i <= SIX; i++){

            int currVal = frequencies.get(i);

            switch(i){
                case ONE:
                    if(isTriple_orAbove(ONE))
                        scoreAboveTriples(ONE, 1000);
                    else if(currVal > 0)
                        score += 100 * currVal;
                    break;

                case TWO:
                    if(isTriple_orAbove(TWO))
                        scoreAboveTriples(TWO, 200);
                    break;

                case THREE:
                    if(isTriple_orAbove(THREE))
                        scoreAboveTriples(THREE, 300);
                    break;

                case FOUR:
                    if(isTriple_orAbove(FOUR))
                        scoreAboveTriples(FOUR, 400);
                    break;

                case FIVE:
                    if(isTriple_orAbove(FIVE))
                        scoreAboveTriples(FIVE, 500);
                    else if(currVal > 0)
                        score += 50 * currVal;
                    break;

                case SIX:
                    if(isTriple_orAbove(SIX))
                        scoreAboveTriples(SIX, 600);
                    break;
            }
        }
        score += previousScore;

    }

    public void checkHighscore() {
        highestScore = Math.max(highestScore, score);
    }

    private void scoreAboveTriples(int thrownValue, int multiplyBy) {
        score += multiplyBy * Math.pow(2, frequencies.get(thrownValue) - 3);
    }

    private boolean isTriple_orAbove(int thrownValue) {
        return frequencies.get(thrownValue) >= 3;
    }

    private boolean checkStrongestCombos() {

        if(validation.isStraight()){
            score = 2000 + previousScore;
            return true;
        }
        if(validation.isDoubles()){
            score = 1000 + previousScore;
            return true;
        }

        if(validation.isFullhouse()){
            score = 750 + previousScore;

            if(keptDices.size() == 6){
                //check if last dice is either 1 or 5
                if(frequencies.get(1) == 1)
                    score += 100;

                if(frequencies.get(5) == 1)
                    score += 50;
            }
            return true;
        }
        return false;

    }

    public int getScore() {
        return score;
    }

    public int getPreviousScore() {
        return previousScore;
    }

    public void setPreviousScore(int score) {
        previousScore = score;
    }

    public void resetHighscore() {
        highestScore = 0;
    }
}
