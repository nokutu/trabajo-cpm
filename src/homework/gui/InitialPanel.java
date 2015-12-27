package homework.gui;

import javax.swing.*;
import java.awt.*;

public class InitialPanel extends JPanel {

    private static final long serialVersionUID = 3817197481081978522L;

    private SearchBar sb;

    public InitialPanel() {
        setLayout(new BorderLayout());
        add(new Navbar(), BorderLayout.NORTH);
        sb = new SearchBar();
        sb.setAlignmentX(Component.CENTER_ALIGNMENT);
        sb.setAlignmentY(Component.CENTER_ALIGNMENT);
        add(sb, BorderLayout.CENTER);
    }

}
