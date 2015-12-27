package homework.gui;

import homework.Main;
import homework.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ResourceBundle;

import static homework.I18n.tr;

/**
 * Created by Jorge López on 27/12/15.
 */
public class SearchBar extends JPanel {

    private JTextField tf;
    private JButton sb;
    private JLabel logo;
    private ImageIcon logoImage;

    public SearchBar() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JPanel container = new JPanel();
        container.setLayout(new GridBagLayout());
        c.gridwidth = 2;

        logoImage = new ImageIcon(getClass().getResource("/images/logo.JPG"));
        logo = new JLabel();
        logo.setIcon(Utils.scale(logoImage, 400, 150));
        container.add(logo, c);

        c.gridwidth = 1;

        c.gridy = 1;
        tf = new JTextField();
        tf.setColumns(Main.frame.getWidth() / 25);
        tf.setFont(tf.getFont().deriveFont(18f));
        container.add(tf, c);
        c.gridx = 1;
        sb = new JButton(tr("Search"));
        container.add(sb, c);
        add(container);

        Main.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                int width = Main.frame.getWidth();
                logo.setIcon(Utils.scale(logoImage, width / 2, width / 6));
                tf.setColumns(width / 25);
            }
        });
    }
}
