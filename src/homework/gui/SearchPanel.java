package homework.gui;

import homework.Search;
import homework.models.Cruise;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * JPanel representing the search results page.
 */
public class SearchPanel extends JPanel {

  public SearchBar searchBar;
  public JPanel centerPanel;

  public SearchPanel() {
    setLayout(new BorderLayout());
    add(searchBar = new SearchBar(), BorderLayout.NORTH);
  }

  public void search() {
    List<Cruise> results = Search.search(searchBar.getText());
    centerPanel = new JPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    for (Cruise cruise : results) {
      SearchEntry entry = new SearchEntry(cruise);
      entry.setMaximumSize(new Dimension(getPreferredSize().width, getPreferredSize().height));
      centerPanel.add(entry);
    }
    JScrollPane scroll = new JScrollPane(centerPanel);
    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    add(scroll, BorderLayout.CENTER);
  }
}
