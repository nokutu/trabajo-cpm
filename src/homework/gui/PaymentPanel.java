package homework.gui;

import homework.Main;
import homework.Utils;
import homework.gui.components.HomeLogo;
import homework.gui.components.TextButton;
import homework.models.CabinBook;
import homework.models.Extra;
import homework.models.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import static homework.I18n.tr;

/**
 * Created by nokutu on 04/01/2016.
 */
public class PaymentPanel extends JPanel {

  private JPanel center;
  private JTextArea bill;
  private List<CabinBook> cabinBooks;
  private boolean waitingForLoginToSetBill;

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

    JButton back = new JButton(tr("Back"));
    back.addActionListener(new BackAction());
    btnPanel.add(back, "alignx right, pushx");

    JButton next = new JButton(tr("Finish"));
    next.addActionListener(new NextAction());
    btnPanel.add(next, "alignx right");
    add(btnPanel, BorderLayout.SOUTH);

    refresh();

  }

  private void refresh() {
    if(cabinBooks != null && waitingForLoginToSetBill) {
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
    text += User.getLoggedUser().getFullName() + " - " + User.getLoggedUser().getNif() + " - " + User.getLoggedUser().getTlfNumber() + "\n\n";
    text += "** " + tr("Cruise data") + " **\n";
    text += tr("Cruise") + ": " + books.get(0).getCruise().getCode() + "\n";
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
    text += "    "  + (priceCabins + priceExtras - offer)  + "\u20ac";

    bill.setText(text);
    waitingForLoginToSetBill = false;
    refresh();
  }

  private class NextAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
    }
  }

  private class BackAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      Main.frame.cl.show(Main.frame.getContentPane(), MainFrame.PASSENGER_INFO_PANEL);
    }
  }
}
