package homework.gui.components;

import homework.Main;
import homework.Utils;
import homework.gui.HasSearchBar;
import homework.gui.MainFrame;
import net.miginfocom.swing.MigLayout;

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

    center.setLayout(new MigLayout("insets 0 100 0 100, aligny center"));

    logoImage = new ImageIcon(getClass().getResource("/images/logo.JPG"));
    logo = new JLabel();
    logo.setIcon(Utils.scale(logoImage, 400, 150));
    center.add(logo, "span 2, alignx center, wrap");

    tf = new JTextField();
    tf.setFont(tf.getFont().deriveFont(18f));
    tf.setColumns(Main.frame.getWidth() / 25);
    center.add(tf, "growx, pushx");

    sb = new JButton(tr("Search"));
    sb.addActionListener(new SearchAction());
    center.add(sb, "aligny top");

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