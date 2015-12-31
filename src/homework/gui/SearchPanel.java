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
    if (centerPanel == null) {
      centerPanel = new JPanel();
      centerPanel.setLayout(new GridBagLayout());
      JScrollPane scroll = new JScrollPane(centerPanel);
      scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      add(scroll, BorderLayout.CENTER);
    }
    GridBagConstraints c = new GridBagConstraints();
    c.anchor = GridBagConstraints.PAGE_START;
    c.gridx = 0;
    c.gridy = 0;
    c.weighty = 1;
    centerPanel.removeAll();
    JPanel t = new JPanel();
    t.setLayout(new GridBagLayout());
    List<Cruise> results = Search.search(searchBar.getText());
    for (Cruise cruise : results) {
      SearchEntry entry = new SearchEntry(cruise);
      entry.setMaximumSize(new Dimension(entry.getMaximumSize().width, entry.getPreferredSize().height));
      t.add(entry, c);
      c.gridy++;
    }
    c.gridy = 0;
    centerPanel.add(t, c);
  }
}
