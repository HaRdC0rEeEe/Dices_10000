package GUI;

import Dices.Dice;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class DicesPanel extends JPanel{

    private ArrayList<Dice> dices;

    public DicesPanel() {
        Dimension dim = getPreferredSize();
        dim.width = 780;
        dim.height = 400;
        setSize(dim);


        Border innerBorder = BorderFactory.createTitledBorder("Board");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        setLayout(new GridBagLayout());

    }

    public ArrayList<Dice> getDices() {
        return dices;
    }

    public void setDices(ArrayList<Dice> dices) {
        this.dices = dices;
    }

    public void generateDices() {

        dices = new ArrayList<>();
        for(int i = 1; i <= 6; i++){
            dices.add(new Dice());
        }

    }

    public void layoutComponents() {

        this.removeAll();
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.NONE;
        gc.gridx = -1;
        gc.gridy = 0;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(0, 0, 0, 0);

        for(int i = 0; i < dices.size(); i++){
            gc.gridx++;
            if(i % 3 == 0){
                gc.gridy++;
                gc.gridx = 0;
            }
            if(dices.get(i) != null)
                add(dices.get(i), gc);
        }

    }
}













