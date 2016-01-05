package homework.gui;

import homework.Main;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    JLabel dateLabel = new JLabel(tr("Expiry date") + ":");
    JTextField label = new JTextField(8);
    numLabel.setDisplayedMnemonic('d');
    numLabel.setLabelFor(label);
    center.add(dateLabel);
    center.add(label);

    JLabel codeLabel = new JLabel(tr("CVC") + ":");
    JTextField code = new JPasswordField(3);
    numLabel.setDisplayedMnemonic('c');
    numLabel.setLabelFor(code);
    center.add(codeLabel);
    center.add(code, "wrap");

    JLabel nameLabel = new JLabel(tr("Full name") + ":");
    JTextField name = new JPasswordField(3);
    numLabel.setDisplayedMnemonic('n');
    numLabel.setLabelFor(name);
    center.add(nameLabel);
    center.add(name, "wrap");

    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new MigLayout());
    btnPanel.add(new JPanel(), "pushx");

    JButton pay = new JButton(tr("Pay"));
    pay.addActionListener(new PayAction());

    JButton cancel = new JButton(tr("Cancel"));
    cancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });

    add(center, BorderLayout.CENTER);
    add(btnPanel, BorderLayout.SOUTH);

    setResizable(false);
    pack();
    setLocationRelativeTo(Main.frame);
    getRootPane().setDefaultButton(null);
  }

  private class PayAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      // TODO
    }
  }
}