package Dices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ValidateDices{

    private ArrayList<Integer> throwValues;
    private ArrayList<Integer> throwValues_allDices;
    private HashMap<Integer, Integer> frequencies;
    private boolean isStraight;
    private boolean isFullhouse;
    private boolean isDoubles;
    private boolean possibleStrongestCombo;

    /***
     * @param keptDicesList list of kept dices by the Player
     * @param everyDice list of every dice in play used for possible strong combo validation
     */
    public ValidateDices(ArrayList<Dice> keptDicesList, ArrayList<Dice> everyDice) {
        setLists(keptDicesList, everyDice);
    }

    public boolean isPossibleStrongestCombo() {
        return possibleStrongestCombo;
    }

    public void setFrequencies(HashMap<Integer, Integer> frequencies) {
        this.frequencies = frequencies;
    }

    public boolean isStraight() {
        return isStraight;
    }

    public boolean isFullhouse() {
        return isFullhouse;
    }

    public boolean isDoubles() {
        return isDoubles;
    }

    public boolean validate() {

        int size = throwValues.size();
        boolean wasOneDiceKept = throwValues_allDices.size() == 6;

        //check if exactly one die was kept by player and also if it is first die to be kept in whole game
        if(size == 1 && wasOneDiceKept)
            possibleStrongestCombo = checkPossibleStrongestCombos();
        else
            possibleStrongestCombo = false;

        if(size == 5){
            if(Fullhouse())
                return true;
        } else if(size == 6)
            if(checkStrongestCombos())
                return true;

        return checkLowDices();

    }

    private boolean checkPossibleStrongestCombos() {
        if(possibleStraight())
            return true;

        if(possibleFullhouse())
            return true;

        return possibleDoubles();
    }

    private boolean possibleDoubles() {

        if(Collections.frequency(frequencies.values(), 3)==1)
            return Collections.frequency(frequencies.values(), 2)==1;
        if(Collections.frequency(frequencies.values(), 2)==2)
            return Collections.frequency(frequencies.values(), 1)==2;
        return false;

    }

    private boolean possibleFullhouse() {

        boolean result = false;
        for(Map.Entry<Integer, Integer> entry : frequencies.entrySet()
        ){
            //3 pairs and 2 pairs = FH
            int numOfCopies = entry.getValue();

            if(numOfCopies != 0){
                if(numOfCopies >= 5)
                    break;

                else{
                    ///when four pairs are present, throwing with one die will always result of possible FH
                    if(numOfCopies == 4)
                        return true;

                    //when triples are present, FH is only possible of other three different dices are present
                    if(numOfCopies == 3)
                        result = Collections.frequency(frequencies.values(), 1) == 3;

                    //for one pair of dices FH is possible if we have at least two pairs present
                    if(numOfCopies == 2){
                        int freqDoubles = Collections.frequency(frequencies.values(), 2);
                        result = freqDoubles != 1;
                    }
                }
            }
            if(result)
                break;
        }
        return result;
    }

    private boolean possibleStraight() {
        return Collections.frequency(frequencies.values(), 0) == 1;
    }

    private boolean checkLowDices() {
        for(Integer currDice : throwValues
        ){
            if(Collections.frequency(throwValues, currDice) < 3)
                if(currDice != 5 && currDice != 1)
                    return false;
        }
        return throwValues.size() != 0;

    }

    public boolean checkStrongestCombos() {

        if(Straight())
            return true;

        if(Fullhouse())
            return true;

        return Doubles();
    }

    private boolean Straight() {
        isStraight = Collections.frequency(frequencies.values(), 1) == 6;
        return isStraight;
    }

    private boolean Fullhouse() {
        isFullhouse = Collections.frequency(frequencies.values(), 2) == 1 && Collections.frequency(frequencies.values(), 3) == 1;
        return isFullhouse;
    }

    private boolean Doubles() {
        isDoubles = Collections.frequency(frequencies.values(), 2) == 3;
        return isDoubles;
    }

    public void setDefault() {
        isStraight = false;
        isDoubles = false;
        isFullhouse = false;
    }

    public void setLists(ArrayList<Dice> keptDices, ArrayList<Dice> dices) {
        setDefault();
        throwValues = new ArrayList<>();
        throwValues_allDices = new ArrayList<>();

        keptDices.forEach(e -> throwValues.add(e.getThrow()));

        dices.forEach(e -> {
            if(e != null){
                throwValues_allDices.add(e.getThrow());
            }

        });

        Collections.sort(throwValues);
        Collections.sort(throwValues_allDices);
    }
}
