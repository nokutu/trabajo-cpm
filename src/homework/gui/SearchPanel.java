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
    centerPanel.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(5, 20, 5, 20);
    c.anchor = GridBagConstraints.PAGE_START;
    c.gridx = 0;
    c.gridy = 0;
    c.fill = GridBagConstraints.BOTH ;
    c.weightx = 1;
    c.weighty = 0.1;
    for (Cruise cruise : results) {
      centerPanel.add(new SearchEntry(cruise), c);
      c.gridy++;
    }
    JScrollPane scroll = new JScrollPane(centerPanel);
    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    add(scroll, BorderLayout.CENTER);
  }
}
