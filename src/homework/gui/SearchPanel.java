package homework.gui;

import homework.Main;
import homework.Search;
import homework.models.Cruise;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.RunnableFuture;

/**
 * JPanel representing the search results page.
 */
public class SearchPanel extends JPanel {

  public SearchBar searchBar;
  public ScrollablePanel centerPanel;
  public JScrollPane scroll;

  public SearchPanel() {
    setLayout(new BorderLayout());
    add(searchBar = new SearchBar(), BorderLayout.NORTH);
  }

  public void search() {
    if (centerPanel == null) {
      centerPanel = new ScrollablePanel();
      centerPanel.setLayout(new GridBagLayout());
      scroll = new JScrollPane();
      scroll.setViewportView(centerPanel);
      add(scroll, BorderLayout.CENTER);
    }
    GridBagConstraints c = new GridBagConstraints();
    c.anchor = GridBagConstraints.PAGE_START;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(0, 100, 0, 100);
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 1;
    centerPanel.removeAll();
    JPanel t = new JPanel();
    t.setLayout(new GridBagLayout());
    List<Cruise> results = Search.search(searchBar.getText());
    for (Cruise cruise : results) {
      SearchEntry entry = new SearchEntry(cruise);
      centerPanel.add(entry, c);
      c.gridy++;
    }
    c.weighty = 1;
    centerPanel.add(new JPanel(), c);

    Main.frame.revalidate();
    Main.frame.repaint();

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        Main.frame.sp.scroll.getViewport().setViewPosition(new Point(0,0));
      }
    });
  }
}
