package homework.gui.components;

import homework.Main;
import homework.gui.MainFrame;
import homework.models.Cabin;
import homework.models.Cruise;
import homework.models.CruiseDate;
import homework.models.Extra;
import homework.models.Order;
import net.miginfocom.swing.MigLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import static homework.I18n.tr;

/**
 * This panel contains the list of extras that can be chosen. Also, it stores the shopping cart that keeps track of how many rooms the user buys.
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

  private JCheckBox extraBedCheckbox;

  private Cruise cruise;

  private ShoppingCart shoppingCart;
  private int price;


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
        extraBedCheckbox = checkBox;
        checkBox.addActionListener(new CabinChangeListener());
      }
    }

    setLayout(new MigLayout("alignx left, insets 5 0 5 5"));

    add(extrasPanel, "spanx 2, wrap");

    add(new JLabel(tr("Date") + ":"));

    dates = new JComboBox<>();
    dates.addActionListener(dateChangeListener = new DateChangeListener());
    dates.addActionListener(new PriceRefreshAction());
    for (MouseWheelListener mwl : dates.getMouseWheelListeners()) {
      dates.removeMouseWheelListener(mwl);
    }
    dates.setToolTipText(tr("Select the cruise date"));
    add(dates, "wrap");

    add(new JLabel(tr("Cabin") + ":"));

    cabins = new JComboBox<>();
    cabins.addActionListener(new PriceRefreshAction());
    cabins.addActionListener(new CabinChangeListener());
    for (MouseWheelListener mwl : cabins.getMouseWheelListeners()) {
      cabins.removeMouseWheelListener(mwl);
    }
    cabins.setToolTipText(tr("Select the type of cabin"));
    add(cabins, "wrap");
    add(new JLabel(tr("People") + ":"));

    people = new JSpinner();
    people.addChangeListener(new PriceRefreshAction());
    people.setToolTipText(tr("Set how many people will be in the cabin"));
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
    book.setMnemonic('b');
    book.setToolTipText(tr("Set your booking and start with the passenger information and payment"));
    book.addActionListener(new BookAction());
    add(book, "span, growx, wrap");

    multipleCabins = new TextButton(tr("Book multiple cabins"));
    multipleCabins.setForeground(Color.blue);
    multipleCabins.addActionListener(new MultipleCabinsActions());
    multipleCabins.setMnemonic('m');
    multipleCabins.setToolTipText(tr("Book multiple cabins in a single order"));
    add(multipleCabins, "span, alignx center, growx, wrap");
  }

  private void addToCart() {
    if (shoppingCart == null) {
      multipleCabins.setText(tr("Add"));
      shoppingCart = new ShoppingCart(dates);
      add(shoppingCart, "span, alignx center, growx");
    }

    shoppingCart.addCabin(cruise, (CruiseDate) dates.getSelectedItem(), (Cabin) cabins.getSelectedItem(), (Integer) people.getValue(), getExtrasSelected());

    if (shoppingCart.getLines().size() == 1) {
      dates.setEnabled(false);
    }

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
    extraBedCheckbox.setEnabled(cruise.isMinorAllowed());
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
    int priceExtras = 0;
    for (int i = 0; i < extrasChecboxes.size(); i++) {
      if (extrasChecboxes.get(i).isSelected()) {
        priceExtras += extras.get(i).getTotalPrice((int) people.getValue(), cruise.getDuration());
      }
    }
    int priceCabin = ((Cabin) cabins.getSelectedItem()).getPrice() * ((Cabin) cabins.getSelectedItem()).getCapacity() * cruise.getDuration();

    int offer = (int) ((priceCabin + priceExtras) * cruise.getOffer());

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
        Order order = new Order(cruise, (CruiseDate) dates.getSelectedItem());
        order.addCabin((Cabin) cabins.getSelectedItem(), (Integer) people.getValue(), getExtrasSelected());
        Main.frame.pip.setOrder(order);
      } else {
        Main.frame.pip.setOrder(shoppingCart.getOrder());
      }
      Main.frame.cl.show(Main.frame.getContentPane(), MainFrame.PASSENGER_INFO_PANEL);
    }
  }
}
