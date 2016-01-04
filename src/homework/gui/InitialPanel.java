package homework.gui;

import homework.gui.components.Navbar;
import homework.gui.components.SearchBar;

import javax.swing.*;
import java.awt.*;

public class InitialPanel extends JPanel implements HasSearchBar{

    private static final long serialVersionUID = 3817197481081978522L;

    private SearchBar sb;

    public InitialPanel() {
        setLayout(new BorderLayout());
        sb = new SearchBar();
        add(sb, BorderLayout.CENTER);
    }

    @Override
    public Navbar getNavbar() {
        return sb.getNavbar();
    }

    @Override
    public SearchBar getSearchBar() {
        return sb;
    }
}
