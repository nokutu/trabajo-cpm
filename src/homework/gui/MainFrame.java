package homework.gui;

import javax.swing.*;
import java.awt.*;

import static homework.I18n.tr;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 2740437090361841747L;

    private static final String INITIAL_PANEL = "Initial panel";

    private CardLayout cl;

    public MainFrame() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 450));
        setTitle(tr("title"));
    }

    public void start() {
        if (cl != null) {
            throw new IllegalStateException("Program has already started");
        }
        setContentPane(new JPanel());
        cl = new CardLayout();
        getContentPane().setLayout(cl);
        getContentPane().add(new InitialPanel(), INITIAL_PANEL);
        setVisible(true);
    }

}
