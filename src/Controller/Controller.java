package Controller;

import Dices.Dice;
import Dices.Game;
import Dices.Player;
import Dices.ValidateDices;
import GUI.BorderPanel;
import GUI.DicesPanel;

import javax.swing.*;
import java.util.ArrayList;

public class Controller{

    private final Game game;
    private final DicesPanel dicesPanel;
    private final BorderPanel borderPanel;
    private final ValidateDices validation;
    private CheckWinner checkWinner;
    private ArrayList<Dice> dices;
    private Player player1;
    private Player player2;

    /***
     *
     * @param dicesPanel JPanel containing all dices
     * @param borderPanel JPanel containing buttons for gameplay and informative labels
     * @param game Game class consists of logic and game rules
     */
    public Controller(DicesPanel dicesPanel, BorderPanel borderPanel, Game game) {

        player1 = new Player(nameValidation(true));
        player2 = new Player(nameValidation(false));

        player1.setOnTurn(true);

        this.dicesPanel = dicesPanel;
        this.borderPanel = borderPanel;
        this.game = game;

        checkWinner = new CheckWinner(player1, player2);

        createNewDices();

        //class for input and combo validations
        validation = new ValidateDices(game.getKeptDices(), dices);
        game.setValidation(validation);

        initBorderPanelComponents();

    }

    private String nameValidation(boolean isFirstPlayer) {
        String message = "Please input nickname for %%% player";

        message = isFirstPlayer ? message.replace("%%%", "first") : message.replace("%%%", "second");

        String nickname = JOptionPane.showInputDialog(null, message, "", JOptionPane.PLAIN_MESSAGE);
        while(nickname == null){
            nickname = JOptionPane.showInputDialog(null, "Incorrect input. Try again.", "", JOptionPane.PLAIN_MESSAGE);
        }

        return nickname;
    }

    private void initStrongComboButton() {
        borderPanel.getThrowIntoStrongCombo().addActionListener(e -> {

            //throw only kept dice for combo
            Dice first = game.getKeptDices().get(0);
            first.Throw();
            //remove dice which was kept previously
            game.getKeptDices().remove(first);

            dices.forEach(d -> {
                d.setEnabled(false);
                game.keepDice(d);
            });

            validation.setLists(game.getKeptDices(), dices);
            validation.setFrequencies(game.getFrequencies(dices));

            if(validation.checkStrongestCombos()){
                validationWasSuccessful();
            } else{
                game.resetGame();
                applyCurrentScore();
                validationWasFalse();
            }

            applyTextStrongCombo();
            borderPanel.getResetDicesBtn().setEnabled(false);
            borderPanel.getThrowIntoStrongCombo().setVisible(false);
            borderPanel.updateUI();

        });

    }

    private void applyTextStrongCombo() {

        if(validation.isDoubles())
            borderPanel.getStrongComboLabel().setText("Doubles!");

        else if(validation.isStraight())
            borderPanel.getStrongComboLabel().setText("Straight!");

        else if(validation.isFullhouse())
            borderPanel.getStrongComboLabel().setText("Fullhouse!");

        else{
            validation.setDefault();
            borderPanel.getStrongComboLabel().setText("");
        }

    }

    private void validationWasSuccessful() {
        game.calculateScore();
        applyCurrentScore();

        int score = game.getScore();

        borderPanel.getSubmitScoreBtn().setEnabled(score >= 350);
        borderPanel.getKeepDicesBtn().setEnabled(score > 0 || game.getKeptDices().size() == 6);
    }

    private void initDicePanelComponents() {
        for(Dice dice : dices
        ){
            dice.addActionListener(e -> {

                        game.keepDice(dice);
                        //set kept and all dices for validation
                        validation.setLists(game.getKeptDices(), dices);

                        //if it is impossible to throw strong combo with only one die, set frequencies for only kept dices
                        if(game.getKeptDices().size() != 1)
                            validation.setFrequencies(game.getFrequencies(game.getKeptDices()));
                        else
                            validation.setFrequencies(game.getFrequencies(dices));

                        //check if kept dices were legal to be kept
                        if(validation.validate())
                            validationWasSuccessful();
                        else
                            validationWasFalse();

                        applyTextStrongCombo();

                        borderPanel.getThrowIntoStrongCombo().setVisible(validation.isPossibleStrongestCombo());

                        borderPanel.getResetDicesBtn().setEnabled(true);
                        borderPanel.updateUI();
                    }
            );
        }
    }

    private void initBorderPanelComponents() {

        initResetDiceListener();

        initKeepDiceListener();

        initEndRoundListener(borderPanel.getSubmitScoreBtn());

        initEndRoundListener(borderPanel.getPassRoundBtn());

        initStrongComboButton();

        applyCurrentScore();

    }

    private void applyCurrentScore() {
        if(player1.isOnTurn())
            borderPanel.getScoreLabel().setText(player1.getName() + ": " + game.getScore());
        else
            borderPanel.getScoreLabel().setText(player2.getName() + ": " + game.getScore());
    }

    private void swapPlayers() {

        if(player1.isOnTurn()){
            player1.addScore(game.getScore());
            player1.setOnTurn(false);
            player2.setOnTurn(true);
        } else{
            player2.addScore(game.getScore());
            player1.setOnTurn(true);
            player2.setOnTurn(false);
        }


    }

    private void initEndRoundListener(JButton btnImplementingListener) {

        btnImplementingListener.addActionListener(e -> {

                    swapPlayers();

                    game.checkHighscore();

                    if(checkWinner.foundWinner()){
                        int dialogRes = JOptionPane.showConfirmDialog(null,
                                "Winner is " + checkWinner.getWinner() +
                                        "\n Do you want to play again?",
                                "",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.PLAIN_MESSAGE);

                        //RESET GAME
                        if(dialogRes == JOptionPane.YES_OPTION){

                            player1 = new Player(nameValidation(true));
                            player2 = new Player(nameValidation(false));

                            player1.setOnTurn(true);

                            checkWinner = new CheckWinner(player1, player2);
                            createNewDices();
                            game.resetGame();
                            game.resetHighscore();
                        } else
                            System.exit(0);
                    } else{
                        game.resetGame();
                        createNewDices();
                    }

                    applyCurrentScore();
                    applyTextStrongCombo();

                    borderPanel.getPlayer1ScoreLabel().setText(player1.toString());
                    borderPanel.getPlayer2ScoreLabel().setText(player2.toString());
                    borderPanel.getHighscoreLabel().setText("Highest score: " + game.getHighestScore());

                    borderPanel.getKeepDicesBtn().setEnabled(false);
                }
        );

    }

    private void initKeepDiceListener() {
        borderPanel.getKeepDicesBtn().addActionListener(e -> {

            //set selected dices to null for further checking purposes
            for(Dice d : game.getKeptDices()
            ){
                dices.set(dices.indexOf(d), null);
            }

            reloadScore(game.getScore());

            if(!wasEveryDiceKept())
                dicesPanel.setDices(dices);
            else
                createNewDices();

            dicesPanel.layoutComponents();
            borderPanel.getKeepDicesBtn().setEnabled(false);
            borderPanel.getSubmitScoreBtn().setEnabled(false);
            borderPanel.getThrowIntoStrongCombo().setVisible(false);

            dicesPanel.updateUI();

        });
    }

    private boolean wasEveryDiceKept() {
        boolean everyDiceKept = true;
        for(Dice die : dices
        ){
            //at least one die was not kept
            if(die != null){
                die.Throw();
                everyDiceKept = false;
            }
        }
        return everyDiceKept;


    }

    private void validationWasFalse() {
        borderPanel.getSubmitScoreBtn().setEnabled(false);
        borderPanel.getKeepDicesBtn().setEnabled(false);

    }

    private void createNewDices() {
        dicesPanel.generateDices();
        dices = dicesPanel.getDices();
        initDicePanelComponents();
        borderPanel.getThrowIntoStrongCombo().setVisible(false);
        dicesPanel.layoutComponents();
        dicesPanel.updateUI();
    }

    private void initResetDiceListener() {
        borderPanel.getResetDicesBtn().addActionListener(e -> {

                    dices.forEach(d -> {
                        if(d != null){
                            d.setEnabled(true);
                        }
                    });

                    reloadScore(game.getPreviousScore());
                    if(player1.isOnTurn())
                        borderPanel.getScoreLabel().setText(player1.getName() + ": " + game.getPreviousScore());
                    else
                        borderPanel.getScoreLabel().setText(player2.getName() + ": " + game.getPreviousScore());

                    borderPanel.getKeepDicesBtn().setEnabled(false);
                    borderPanel.getThrowIntoStrongCombo().setVisible(false);
                }
        );
    }

    private void reloadScore(int newScore) {
        game.resetGame();
        game.setPreviousScore(newScore);

    }

}
