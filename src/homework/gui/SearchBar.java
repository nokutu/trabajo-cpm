package homework.gui;

import homework.Main;
import homework.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static homework.I18n.tr;

/**
 * Search bar.
 */
public class SearchBar extends JPanel {

  private JTextField tf;
  private JButton sb;
  private JLabel logo;
  private ImageIcon logoImage;

  public SearchBar() {

    getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("ENTER"),
            "search");
    getActionMap().put("search", new SearchAction());

    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    JPanel container = new JPanel();
    container.setLayout(new GridBagLayout());

    c.gridwidth = 2;
    logoImage = new ImageIcon(getClass().getResource("/images/logo.JPG"));
    logo = new JLabel();
    logo.setIcon(Utils.scale(logoImage, 300, 100));
    container.add(logo, c);

    c.insets = new Insets(0, 0, 10, 0);
    c.gridwidth = 1;
    c.gridy = 1;
    tf = new JTextField();
    tf.setFont(tf.getFont().deriveFont(18f));
    tf.setColumns(Main.frame.getWidth() / 25);
    container.add(tf, c);

    c.gridx = 1;
    sb = new JButton(tr("Search"));
    sb.addActionListener(new SearchAction());
    container.add(sb, c);
    add(container);

    Main.frame.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        super.componentResized(e);
        int width = Main.frame.getWidth();
        //logo.setIcon(Utils.scale(logoImage, width / 2, 200));
        tf.setColumns(width / 25);
      }
    });
  }

  public String getText() {
    return tf.getText();
  }

  private class SearchAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
      // TODO: switch to another view to display search results
      Main.frame.cl.show(Main.frame.getContentPane(), MainFrame.SEARCH_PANEL);
      Main.frame.sp.searchBar.tf.setText(getText());
      Main.frame.sp.search();
    }
  }
}
