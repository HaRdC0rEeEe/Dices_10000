package Dices;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Random;

public class Dice extends JButton implements ActionListener{

    private static final Random rnd = new Random();
    private int roll;

    public Dice() {

        Throw();
        setBorder(BorderFactory.createCompoundBorder());
        setFocusable(false);
        setBackground(Color.WHITE);

        Dimension size = new Dimension(150, 150);
        setPreferredSize(size);
        setEnabled(true);
        addActionListener(this);
        setVisible(true);

    }

    public int getThrow() {
        return roll;
    }

    public void Throw() {
        roll = rnd.nextInt(6) + 1;
        String rollStr = String.valueOf(roll);

        String path = String.format("resources/dice_%s.png", rollStr);

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(path)));

        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);

        icon = new ImageIcon(newImg);
        setHorizontalTextPosition(SwingConstants.CENTER);

        setIcon(icon);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Dice clicked = (Dice) e.getSource();
        clicked.setEnabled(false);

    }

}
