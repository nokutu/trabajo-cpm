package homework.gui;

import homework.Main;
import homework.gui.components.HomeLogo;
import homework.gui.components.ScrollablePanel;
import homework.gui.components.ShoppingCart;
import homework.models.CabinBook;
import homework.models.Cruise;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static homework.I18n.tr;
import static homework.I18n.trc;

/**
 * Created by nokutu on 04/01/2016.
 */
public class PassengerInfoPanel extends JPanel {

  private Cruise cruise;

  private ScrollablePanel center;
  private List<BookPane> bookPanes = new ArrayList<>();
  private List<CabinBook> cabinBooks;

  public PassengerInfoPanel() {
    setLayout(new BorderLayout());
    add(new HomeLogo(), BorderLayout.NORTH);
    center = new ScrollablePanel();
    center.setLayout(new MigLayout("alignx center"));
    add(new JScrollPane(center), BorderLayout.CENTER);

    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new MigLayout());

    JButton back = new JButton(tr("Back"));
    back.addActionListener(new BackAction());
    btnPanel.add(back, "alignx right, pushx");

    JButton next = new JButton(tr("Next"));
    next.addActionListener(new NextAction());
    btnPanel.add(next, "alignx right");
    add(btnPanel, BorderLayout.SOUTH);
  }

  public void setBooks(ShoppingCart sc) {
    setBooks(sc.getBooks());
  }

  public void setBooks(CabinBook cb) {
    setBooks(Arrays.asList(new CabinBook[]{cb}));
  }

  private void setBooks(List<CabinBook> cbs) {
    cabinBooks = cbs;
    cruise = cbs.get(0).getCruise();
    center.removeAll();
    bookPanes.clear();

    int i = 1;
    for (CabinBook cb : cbs) {
      BookPane pane = new BookPane(cb);
      pane.setBorder(new TitledBorder(tr("Cabin") + " " + i++));
      bookPanes.add(pane);
      center.add(pane, i % 2 == 1 ? "wrap" : "");
    }

    revalidate();
    repaint();
  }


  private class BookPane extends ScrollablePanel {

    private List<JTextField> names = new ArrayList<>();
    private List<JSpinner> ages = new ArrayList<>();
    private boolean hasExtraBed;

    public BookPane(CabinBook book) {
      hasExtraBed = book.hasExtraBed();
      setLayout(new MigLayout("alignx left"));
      add(new JLabel(book.getCabin().getName()), "spanx 3, wrap");
      add(new JLabel(tr("Name")), "skip");
      add(new JLabel(tr("Age")), "wrap");

      for (int i = 1; i <= book.getPeople(); i++) {
        add(new JLabel(tr("Person") + " " + i + ":"));
        JTextField name = new JTextField(20);
        names.add(name);
        add(name);
        JSpinner age = new JSpinner();
        SpinnerNumberModel snm = new SpinnerNumberModel();
        snm.setMinimum(0);
        snm.setMaximum(150);
        snm.setStepSize(1);
        age.setModel(snm);
        ages.add(age);
        add(age, "wrap");
      }
    }

    public boolean hasExtraBed() {
      return hasExtraBed;
    }

    public List<JSpinner> getAges() {
      return ages;
    }

    public List<JTextField> getNames() {
      return names;
    }
  }


  private class NextAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      String error = "";
      for (BookPane pane : bookPanes) {
        for (JTextField area : pane.getNames()) {
          area.setBorder(new JTextField().getBorder());
          if (area.getText().equals("")) {
            error += tr("Don't leave any name empty") + "\n";
            area.setBorder(new LineBorder(Color.red));
          }
        }
      }
      if (!cruise.isMinorAllowed()) {
        boolean correctAges = true;
        for (BookPane bp : bookPanes) {
          for (JSpinner age : bp.getAges()) {
            age.setBorder(new JSpinner().getBorder());
            if ((int) age.getValue() < 16) {
              age.setBorder(new LineBorder(Color.red));
              correctAges = false;
            }
          }
        }
        if (!correctAges) {
          JOptionPane.showMessageDialog(Main.frame, tr("Minors are not allowed in this cruise"));
          return;
        }
      }
      int i = 1;
      for (BookPane bp : bookPanes) {
        if (bp.hasExtraBed) {
          boolean hasMinor16 = false;
          for (JSpinner age : bp.getAges()) {
            age.setBorder(new JSpinner().getBorder());
            if ((int) age.getValue() < 16) {
              hasMinor16 = true;
            }
          }
          if (!hasMinor16) {
            error += trc("minor16error", new Object[]{i}) + "\n";
          }
        }
        i++;
      }
      if (!error.equals("")) {
        JOptionPane.showMessageDialog(Main.frame, error);
        return;
      }
      Main.frame.payp.setBill(cabinBooks);
      Main.frame.cl.show(Main.frame.getContentPane(), MainFrame.PAYMENT_PANEL);
    }
  }

  private class BackAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      Main.frame.cl.show(Main.frame.getContentPane(), MainFrame.CRUISE_PANEL);
    }
  }
}
