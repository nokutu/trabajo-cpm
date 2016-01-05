package homework.gui;

import homework.Main;
import net.miginfocom.swing.MigLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;

import static homework.I18n.tr;

/**
 * Created by nokutu on 05/01/2016.
 */
public class PaymentDialog extends JDialog {

  public PaymentDialog(int amount) {
    super(Main.frame, true);

    JPanel center = new JPanel();
    center.setLayout(new MigLayout());

    JLabel numLabel = new JLabel(tr("Card number") + ":");
    JTextField num = new JTextField(20);
    numLabel.setDisplayedMnemonic('n');
    numLabel.setLabelFor(num);
    center.add(numLabel);
    center.add(num, "spanx, wrap");

    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new MigLayout());

    add(center, BorderLayout.CENTER);
    add(btnPanel, BorderLayout.SOUTH);

    setResizable(false);
    pack();
    setLocationRelativeTo(Main.frame);
    getRootPane().setDefaultButton(null);
  }
}
