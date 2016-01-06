package homework.gui;

import homework.Main;
import homework.Utils;
import homework.gui.components.HomeLogo;
import homework.gui.components.TextButton;
import homework.models.CabinBook;
import homework.models.Extra;
import homework.models.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import static homework.I18n.tr;

/**
 * Last panel of the booking process. The bill is shown and when clicking next, credit card information is asked.
 * At the end it is possible to export the bill into a local directory.
 */
public class PaymentPanel extends JPanel {

  private JButton back;
  private JButton next;
  private JPanel center;
  private JTextArea bill;
  private List<CabinBook> cabinBooks;
  private boolean waitingForLoginToSetBill;

  private int totalPrice;

  public PaymentPanel() {
    setLayout(new BorderLayout());
    add(new HomeLogo(), BorderLayout.NORTH);
    center = new JPanel();
    center.setLayout(new MigLayout("align center center"));
    add(center, BorderLayout.CENTER);

    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new MigLayout());

    bill = new JTextArea();
    bill.setLineWrap(true);
    bill.setWrapStyleWord(true);
    bill.setColumns(80);
    bill.setBackground(new JPanel().getBackground());
    bill.setEditable(false);
    bill.setFocusable(false);

    back = new JButton(tr("Back"));
    back.addActionListener(new BackAction());
    btnPanel.add(back, "alignx right, pushx");

    next = new JButton(tr("Finish and pay"));
    next.addActionListener(new NextAction());
    btnPanel.add(next, "alignx right");
    add(btnPanel, BorderLayout.SOUTH);

    refresh();

  }

  private void refresh() {
    if (cabinBooks != null && waitingForLoginToSetBill) {
      setBill(cabinBooks);
    }
    center.removeAll();
    if (User.isLogged()) {
      center.add(new JScrollPane(bill));
    } else {
      TextButton loginButton = new TextButton(tr("login"));
      loginButton.addActionListener(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
          JDialog d = new LoginDialog();
          d.setVisible(true);
          Main.log.i("Login");
          refresh();
        }
      });
      loginButton.setForeground(Color.blue);
      center.add(loginButton);
      center.add(new JLabel(tr("or")));
      TextButton registerButton = new TextButton(tr("register"));
      registerButton.addActionListener(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
          JDialog d = new RegisterDialog();
          d.setVisible(true);
          Main.log.i("Register");
          refresh();
        }
      });
      registerButton.setForeground(Color.blue);
      center.add(registerButton);
      center.add(new JLabel(tr("before continuing")));
    }

    revalidate();
    repaint();
  }

  public void setBill(List<CabinBook> books) {
    cabinBooks = books;
    if (!User.isLogged()) {
      waitingForLoginToSetBill = true;
      return;
    }
    String text = "";
    text += tr("Booking proof") + " -- Cruises S.A. -- " + Utils.df.format(Calendar.getInstance().getTime()) + "\n";
    text += "------------------------------------------------------------------------------\n";
    text += User.getLoggedUser().getFullName() + " - " + User.getLoggedUser().getId() + " - " + User.getLoggedUser().getTlfNumber() + "\n\n";
    text += "** " + tr("Cruise data") + " **\n";
    text += tr("Cruise") + ": " + books.get(0).getCruise().getDenomination() + " / " + books.get(0).getCruise().getCode() + "\n";
    text += tr("Ship") + ": " + books.get(0).getCruise().getShip().getCode() + "\n";
    text += tr("Departure date") + ": " + books.get(0).getCruiseDate() + "\n";
    text += tr("Days") + ": " + books.get(0).getCruise().getDuration() + "\n";

    int people = 0;
    int priceCabins = 0;
    int priceExtras = 0;
    int offer = 0;
    String cabins = "";
    for (CabinBook book : books) {
      people += book.getPeople();
      priceCabins += book.getPriceCabin();
      priceExtras += book.getPriceExtras();
      offer += book.getOffer();
      cabins += "; " + book.getCabin().getName() + " / ";
      for (Extra extra : book.getExtras()) {
        cabins += extra.getName() + ", ";
      }
      cabins = cabins.substring(0, cabins.length() - 2);
    }
    cabins = cabins.substring(2, cabins.length());

    text += tr("Amount of passengers") + ": " + people + "\n";
    text += tr("Cabins") + ": " + cabins + "\n\n";
    text += "    ** " + tr("Booking paid") + " **\n";
    text += tr("Cabins") + ":\n";
    text += "    " + priceCabins + " \u20ac" + "\n";
    text += tr("Extras") + ":\n";
    text += "    " + priceExtras + " \u20ac" + "\n";
    text += tr("Offer") + ":\n";
    text += "    " + offer + " \u20ac" + "\n\n";
    text += tr("Total price") + ":\n";
    totalPrice = priceCabins + priceExtras - offer;
    text += "    " + (totalPrice) + " \u20ac";

    bill.setText(text);
    waitingForLoginToSetBill = false;
    refresh();
  }

  public void payed() {
    Main.frame.fp.setBillText(bill.getText());
    Main.frame.cl.show(Main.frame.getContentPane(), MainFrame.FINAL_PANEL);
  }

  private class NextAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      PaymentDialog pay = new PaymentDialog(totalPrice);
      pay.setVisible(true);
    }
  }

  private class BackAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      Main.frame.cl.show(Main.frame.getContentPane(), MainFrame.PASSENGER_INFO_PANEL);
    }
  }
}
