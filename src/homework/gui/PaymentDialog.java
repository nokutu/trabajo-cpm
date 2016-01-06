package homework.gui;

import homework.Main;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static homework.I18n.tr;

/**
 * Dialog that asks the user for his credit card information in order to complete the payment.
 */
public class PaymentDialog extends JDialog {

  private final JTextField name;
  private final JTextField date;
  private final JTextField num;
  private final JPasswordField code;

  public PaymentDialog(int amount) {
    super(Main.frame, true);
    setTitle(tr("Payment"));

    JPanel center = new JPanel();
    center.setLayout(new MigLayout());

    JLabel amountLabel = new JLabel(tr("Amount") + ": " + amount + " \u20ac");
    amountLabel.setFont(new Font("default", Font.BOLD, 14));
    center.add(amountLabel, "spanx, alignx center, wrap");

    JLabel numLabel = new JLabel(tr("Card number") + ":");
    num = new JTextField(20);
    numLabel.setDisplayedMnemonic('n');
    numLabel.setLabelFor(num);
    center.add(numLabel);
    center.add(num, "spanx, wrap");

    JLabel dateLabel = new JLabel(tr("Expiry date") + ":");
    date = new JTextField(8);
    numLabel.setDisplayedMnemonic('d');
    date.setToolTipText(tr("Use mm/yy format"));
    numLabel.setLabelFor(date);
    center.add(dateLabel);
    center.add(date);

    JLabel codeLabel = new JLabel(tr("CVC") + ":");
    code = new JPasswordField(3);
    code.setToolTipText(tr("3 digit code in the back of the card"));
    numLabel.setDisplayedMnemonic('c');
    numLabel.setLabelFor(code);
    center.add(codeLabel);
    center.add(code, "wrap");

    JLabel nameLabel = new JLabel(tr("Full name") + ":");
    name = new JTextField(20);
    numLabel.setDisplayedMnemonic('n');
    numLabel.setLabelFor(name);
    center.add(nameLabel);
    center.add(name, "spanx, wrap");

    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new MigLayout());
    btnPanel.add(new JPanel(), "pushx");

    JButton pay = new JButton(tr("Pay"));
    pay.setMnemonic('p');
    pay.setToolTipText(tr("Authorize the payment"));
    pay.addActionListener(new PayAction());
    btnPanel.add(pay);

    JButton cancel = new JButton(tr("Cancel"));
    cancel.setToolTipText(tr("Cancel the payment"));
    cancel.setMnemonic('c');
    cancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    btnPanel.add(cancel);

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
      code.setBorder(new JPasswordField().getBorder());
      date.setBorder(new JTextField().getBorder());
      num.setBorder(new JTextField().getBorder());
      name.setBorder(new JTextField().getBorder());

      boolean valid = true;
      if (code.getPassword().length < 3) {
        code.setBorder(new LineBorder(Color.red));
        valid = false;
      }
      if (!num.getText().matches("[0-9-]*")) {
        num.setBorder(new LineBorder(Color.red));
        valid = false;
      }
      if (name.getText().equals("")) {
        name.setBorder(new LineBorder(Color.red));
        valid = false;
      }
      if (!date.getText().matches("[0-9][0-9]/[0-9][0-9]")) {
        date.setBorder(new LineBorder(Color.red));
        valid = false;
      }
      if (valid) {
        Main.frame.payp.payed();
        dispose();
      } else {
        JOptionPane.showMessageDialog(PaymentDialog.this, tr("Some fields are invalid"));
      }
    }
  }
}
