package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class BorderPanel extends JPanel{

    private final scoreLabel scoreLabel;
    private final scoreLabel strongComboLabel;
    private final scoreLabel highscoreLabel;
    private final JButton resetDicesBtn;
    private final JButton submitScoreBtn;
    private final JButton keepDicesBtn;
    private final JButton passRoundBtn;
    private final GridBagConstraints gc = new GridBagConstraints();
    private final JButton throwIntoStrongCombo;
    private final scoreLabel player1ScoreLabel;
    private final scoreLabel player2ScoreLabel;

    public BorderPanel() {
        Dimension dim = getPreferredSize();
        dim.width = 780;
        dim.height = 400;
        setSize(dim);

        submitScoreBtn = new JButton("Keep dices and end round");
        keepDicesBtn = new JButton("Keep dices and re-roll");
        resetDicesBtn = new JButton("Change selected dices");
        passRoundBtn = new JButton("Pass round");
        throwIntoStrongCombo = new JButton("Try strong combo");
        scoreLabel = new scoreLabel("");
        player1ScoreLabel = new scoreLabel("");
        player2ScoreLabel = new scoreLabel("");
        highscoreLabel = new scoreLabel("");
        strongComboLabel = new scoreLabel("");

        Dimension btnSize = new Dimension(300, 50);
        submitScoreBtn.setPreferredSize(btnSize);
        keepDicesBtn.setPreferredSize(btnSize);
        resetDicesBtn.setPreferredSize(btnSize);
        passRoundBtn.setPreferredSize(new Dimension((int) btnSize.getHeight() * 3, (int) btnSize.getHeight()));
        throwIntoStrongCombo.setPreferredSize(new Dimension((int) btnSize.getHeight() * 6, (int) btnSize.getHeight()));

        submitScoreBtn.setEnabled(false);
        keepDicesBtn.setEnabled(false);
        resetDicesBtn.setEnabled(false);
        passRoundBtn.setEnabled(true);
        throwIntoStrongCombo.setVisible(false);

        Border innerBorder = BorderFactory.createBevelBorder(1);
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        setLayout(new GridBagLayout());

        layoutComponents();

    }

    public GUI.scoreLabel getStrongComboLabel() {
        return strongComboLabel;
    }

    public scoreLabel getHighscoreLabel() {
        return highscoreLabel;
    }

    public JButton getThrowIntoStrongCombo() {
        return throwIntoStrongCombo;
    }

    public scoreLabel getPlayer1ScoreLabel() {
        return player1ScoreLabel;
    }

    public scoreLabel getPlayer2ScoreLabel() {
        return player2ScoreLabel;
    }

    public JButton getPassRoundBtn() {
        return passRoundBtn;
    }

    public JButton getSubmitScoreBtn() {
        return submitScoreBtn;
    }

    public JButton getKeepDicesBtn() {
        return keepDicesBtn;
    }

    public JButton getResetDicesBtn() {
        return resetDicesBtn;
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    private void layoutComponents() {

        gc.fill = GridBagConstraints.NONE;
        gc.gridx = 0;
        gc.gridy = 0;

        gc.weightx = 1;
        gc.weighty = 1;


        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 10, 5, 5);
        add(scoreLabel, gc);

        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx++;
        add(strongComboLabel, gc);


        gc.insets = new Insets(5, 5, 5, 5);
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridy++;
        gc.gridx--;
        add(keepDicesBtn, gc);

        gc.weightx = 0.1;
        gc.gridx++;
        add(submitScoreBtn, gc);

        gc.gridy++;
        gc.gridwidth = 2;
        gc.gridx = 0;
        add(resetDicesBtn, gc);

        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridwidth = 1;
        gc.gridx++;
        add(passRoundBtn, gc);

        gc.gridy++;
        add(throwIntoStrongCombo, gc);

        gc.gridx = 0;
        gc.gridy++;

        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 10, 5, 5);
        add(player1ScoreLabel, gc);

        gc.gridy++;
        gc.insets = new Insets(5, 10, 5, 5);
        add(player2ScoreLabel, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(5, 10, 5, 20);
        add(highscoreLabel, gc);

    }


}
