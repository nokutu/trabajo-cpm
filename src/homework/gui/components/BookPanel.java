package homework.gui.components;

import homework.Main;
import homework.gui.MainFrame;
import homework.models.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static homework.I18n.tr;

/**
 * Created by nokutu on 04/01/2016.
 */
public class BookPanel extends ScrollablePanel {

  private TextButton multipleCabins;
  private JComboBox<Cabin> cabins;
  private JComboBox<CruiseDate> dates;
  private DateChangeListener dateChangeListener;
  private JSpinner people;

  private JLabel priceLabel;

  private List<JCheckBox> extrasChecboxes = new ArrayList<>();
  private List<Extra> extras = new ArrayList<>();

  private Cruise cruise;

  private ShoppingCart shoppingCart;
  private int price;
  private int priceCabin;
  private int priceExtras;
  private int offer;



  public BookPanel() {
    ScrollablePanel extrasPanel = new ScrollablePanel();
    extrasPanel.setBorder(BorderFactory.createTitledBorder(tr("Extras")));
    extrasPanel.setLayout(new MigLayout("alignx left"));
    extras = new ArrayList<>(Main.db.getExtras().values());
    for (Extra e : extras) {
      JCheckBox checkBox = new JCheckBox(e.getName() + " (" + e.getPrice() + " " + e.getUnit() + ")");
      extrasChecboxes.add(checkBox);
      extrasPanel.add(checkBox, "wrap");
      checkBox.addActionListener(new PriceRefreshAction());
      if (e.isSuplementaryBed()) {
        checkBox.addActionListener(new CabinChangeListener());
      }
    }

    setLayout(new MigLayout("alignx left, insets 5 0 5 5"));

    add(extrasPanel, "spanx 2, wrap");

    add(new JLabel(tr("Date") + ":"));

    dates = new JComboBox<>();
    dates.addActionListener(dateChangeListener = new DateChangeListener());
    dates.addActionListener(new PriceRefreshAction());
    add(dates, "wrap");

    add(new JLabel(tr("Cabin") + ":"));

    cabins = new JComboBox<>();
    cabins.addActionListener(new PriceRefreshAction());
    cabins.addActionListener(new CabinChangeListener());
    add(cabins, "wrap");
    add(new JLabel(tr("People") + ":"));

    people = new JSpinner();
    people.addChangeListener(new PriceRefreshAction());
    SpinnerNumberModel model = new SpinnerNumberModel();
    model.setMaximum(5);
    model.setMinimum(1);
    model.setStepSize(1);
    model.setValue(1);
    people.setModel(model);
    add(people, "wrap");

    priceLabel = new JLabel();
    priceLabel.setFont(new Font("default", Font.BOLD, 16));
    add(priceLabel, "span, alignx center, wrap");

    JButton book = new JButton(tr("Book"));
    book.addActionListener(new BookAction());
    add(book, "span, growx, wrap");

    multipleCabins = new TextButton(tr("Book multiple cabins"));
    multipleCabins.setForeground(Color.blue);
    multipleCabins.addActionListener(new MultipleCabinsActions());
    add(multipleCabins, "span, alignx center, growx, wrap");
  }

  public void addToCart() {
    if (shoppingCart == null) {
      multipleCabins.setText(tr("Add"));
      shoppingCart = new ShoppingCart();
      add(shoppingCart, "span, alignx center, growx");
    }

    CabinBook book = new CabinBook(cruise, (Cabin) cabins.getSelectedItem(), (int) people.getValue(), (CruiseDate) dates.getSelectedItem(), getExtrasSelected(), priceCabin, priceExtras, offer);
    shoppingCart.addBook(book);

    revalidate();
    repaint();
  }

  private List<Extra> getExtrasSelected() {
    List<Extra> extrasSelected = new ArrayList<>();
    for (int i = 0; i < extras.size(); i++) {
      if (extrasChecboxes.get(i).isSelected()) {
        extrasSelected.add(extras.get(i));
      }
    }
    return extrasSelected;
  }

  public void setCruise(Cruise cruise) {
    if (shoppingCart != null) {
      remove(shoppingCart);
    }
    shoppingCart = null;
    multipleCabins.setText(tr("Book multiple cabins"));

    this.cruise = cruise;
    dates.removeAllItems();
    for (CruiseDate date : cruise.getDates()) {
      dates.addItem(date);
    }
    dateChangeListener.setCruise(cruise);

    cabins.removeAllItems();
    for (Cabin cabin : cruise.getDefaultCabins((CruiseDate) dates.getSelectedItem())) {
      cabins.addItem(cabin);
    }

    for (JCheckBox checkBox : extrasChecboxes) {
      checkBox.setSelected(false);
    }
    people.setValue(1);
  }

  private void updatePrice(Cruise cruise) {
    if (cruise == null || cabins.getSelectedItem() == null) {
      return;
    }
    priceExtras = 0;
    for (int i = 0; i < extrasChecboxes.size(); i++) {
      if (extrasChecboxes.get(i).isSelected()) {
        priceExtras += extras.get(i).getTotalPrice((int) people.getValue(), cruise.getDuration());
      }
    }
    priceCabin = 0;
    priceCabin += ((Cabin) cabins.getSelectedItem()).getPrice() * ((Cabin) cabins.getSelectedItem()).getCapacity() * cruise.getDuration();

    offer = 0;
    //TODO offer system

    price = priceCabin + priceExtras - offer;
    this.priceLabel.setText(price + " \u20ac");
  }

  private class DateChangeListener implements ActionListener {

    private Cruise cruise;

    public DateChangeListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (cruise != null) {
        cabins.removeAllItems();
        for (Cabin cabin : cruise.getDefaultCabins((CruiseDate) dates.getSelectedItem())) {
          cabins.addItem(cabin);
        }
        revalidate();
        repaint();
      }
    }

    private void setCruise(Cruise cruise) {
      this.cruise = cruise;
    }
  }

  private class CabinChangeListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (cabins.getSelectedItem() == null) {
        return;
      }
      int capacity = ((Cabin) cabins.getSelectedItem()).getCapacity();
      for (int i = 0; i < extras.size(); i++) {
        if (extras.get(i).isSuplementaryBed()) {
          if (extrasChecboxes.get(i).isSelected()) {
            capacity++;
          }
          break;
        }
      }
      ((SpinnerNumberModel) people.getModel()).setMaximum(capacity);
    }
  }

  private class PriceRefreshAction implements ActionListener, ChangeListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      updatePrice(cruise);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
      updatePrice(cruise);
    }
  }

  private class MultipleCabinsActions implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      addToCart();
    }
  }

  private class BookAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (shoppingCart == null) {
        CabinBook book = new CabinBook(cruise, (Cabin) cabins.getSelectedItem(), (int) people.getValue(), (CruiseDate) dates.getSelectedItem(), getExtrasSelected(), priceCabin, priceExtras, offer);
        Main.frame.pip.setBooks(book);
      } else {
        Main.frame.pip.setBooks(shoppingCart);
      }
      Main.frame.cl.show(Main.frame.getContentPane(), MainFrame.PASSENGER_INFO_PANEL);
    }
  }
}
