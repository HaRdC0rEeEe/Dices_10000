package GUI;

import Controller.Controller;
import Dices.Game;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{

    private final DicesPanel dicesPanel;
    private final BorderPanel borderPanel;

    public MainFrame() {
        super("Game of Dices");

        borderPanel = new BorderPanel();
        dicesPanel = new DicesPanel();
        Game game = new Game();

        new Controller(dicesPanel, borderPanel, game);

        layoutFrame();
    }

    private void layoutFrame() {
        setSize(1200, 800);

        setMinimumSize(new Dimension(dicesPanel.getWidth() - 130, dicesPanel.getHeight() + borderPanel.getHeight() - 150));

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridy = 0;
        gc.gridx = 0;

        add(dicesPanel, gc);

        gc.gridy++;
        gc.anchor = GridBagConstraints.WEST;
        add(borderPanel, gc);

        setVisible(true);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}

