package homework.gui;

import homework.Main;
import homework.Search;
import homework.gui.components.Navbar;
import homework.gui.components.ScrollablePanel;
import homework.gui.components.SearchBar;
import homework.gui.components.SearchEntry;
import homework.models.Cruise;
import net.miginfocom.swing.MigLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.util.List;

/**
 * JPanel representing the search results page.
 */
public class SearchPanel extends JPanel implements HasSearchBar {

  public SearchBar sb;
  public ScrollablePanel centerPanel;
  public JScrollPane scroll;

  public SearchPanel() {
    setLayout(new BorderLayout());
    add(sb = new SearchBar(), BorderLayout.NORTH);
  }

  public void search() {
    if (centerPanel == null) {
      centerPanel = new ScrollablePanel();
      centerPanel.setLayout(new MigLayout("insets 0 100 0 100"));
      scroll = new JScrollPane();
      scroll.setViewportView(centerPanel);
      add(scroll, BorderLayout.CENTER);
    }
    GridBagConstraints c = new GridBagConstraints();
    c.anchor = GridBagConstraints.PAGE_START;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 1;
    centerPanel.removeAll();
    JPanel t = new JPanel();
    t.setLayout(new GridBagLayout());
    List<Cruise> results = Search.search(sb.getText());
    for (Cruise cruise : results) {
      SearchEntry entry = new SearchEntry(cruise);
      centerPanel.add(entry, "grow, pushx, aligny top, wrap");
      c.gridy++;
    }
    c.weighty = 1;

    Main.frame.revalidate();
    Main.frame.repaint();

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        Main.frame.sp.scroll.getViewport().setViewPosition(new Point(0, 0));
      }
    });
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
