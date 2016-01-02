package homework.gui;

import homework.Main;
import homework.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static homework.I18n.tr;

/**
 * Search bar.
 */
public class SearchBar extends JPanel {

  private final Navbar nb;
  private JTextField tf;
  private JButton sb;
  private JLabel logo;
  private ImageIcon logoImage;

  public SearchBar() {
    setLayout(new BorderLayout());
    JPanel center = new JPanel();

    getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("ENTER"),
            "search");
    getActionMap().put("search", new SearchAction());

    center.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    c.gridwidth = 2;
    logoImage = new ImageIcon(getClass().getResource("/images/logo.JPG"));
    logo = new JLabel();
    logo.setIcon(Utils.scale(logoImage, 400, 150));
    center.add(logo, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1;

    c.insets = new Insets(0, 100, 0, 0);
    c.gridwidth = 1;
    c.gridy = 1;
    tf = new JTextField();
    tf.setFont(tf.getFont().deriveFont(18f));
    tf.setColumns(Main.frame.getWidth() / 25);
    tf.setMaximumSize(new Dimension(8000, 8000));
    center.add(tf, c);

    c.weightx = 0;
    c.insets = new Insets(0, 0, 0, 100);
    c.gridx = 1;
    sb = new JButton(tr("Search"));
    sb.addActionListener(new SearchAction());
    center.add(sb, c);

    add(center, BorderLayout.CENTER);
    add(nb = new Navbar(), BorderLayout.NORTH);
  }

  public String getText() {
    return tf.getText();
  }

  public Navbar getNavbar() {
    return nb;
  }

  private class SearchAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
      Main.frame.cl.show(Main.frame.getContentPane(), MainFrame.SEARCH_PANEL);
      for (JPanel p : Main.frame.getPanels()) {
        if (p instanceof HasSearchBar) {
          ((HasSearchBar) p).getSearchBar().tf.setText(tf.getText());
        }
      }
      Main.frame.sp.search();
    }
  }
}
