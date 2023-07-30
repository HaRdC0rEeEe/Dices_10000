package GUI;

import javax.swing.*;
import java.awt.*;

public class scoreLabel extends JLabel{

    public scoreLabel(String txt) {
        super(txt);
        setVisible(true);
        setFont(new Font("Calibri", Font.PLAIN, 16));
        setFocusable(false);

    }

    @Override
    public void setText(String text) {
        super.setText(text);
    }
}
