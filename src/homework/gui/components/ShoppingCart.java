package homework.gui.components;

import homework.models.Cabin;
import homework.models.Cruise;
import homework.models.CruiseDate;
import homework.models.Extra;
import homework.models.Order;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static homework.I18n.tr;
import static homework.I18n.trn;

/**
 * Stores and shows the shopping cart (list of items that the user is planning to buy).
 */
public class ShoppingCart extends ScrollablePanel {

  private final JLabel priceLabel;
  private List<JLabel> lines = new ArrayList<>();
  private List<JButton> removeButtons = new ArrayList<>();
  private JComboBox<CruiseDate> dates;
  private Order order;

  public ShoppingCart(JComboBox<CruiseDate> dates) {
    this.dates = dates;
    setLayout(new MigLayout());
    JLabel cart = new JLabel(tr("Cart"));
    cart.setFont(new Font("default", Font.BOLD, 16));
    add(cart, "alignx left");

    priceLabel = new JLabel();
    priceLabel.setFont(new Font("default", Font.BOLD, 14));
    add(priceLabel, "alignx right, pushx, wrap");
  }

  public void addCabin(Cruise cruise, CruiseDate date,  Cabin cabin, int people, List<Extra> extras) {
    if (lines.size() == 0) {
      order = new Order(cruise, date);
    }

    String lineTxt = "";
    lineTxt += cabin.getName() + "-" + people + " " + trn("person", people);
    JLabel line = new JLabel(lineTxt);
    add(line, "alignx left");
    TextButton btn = new TextButton(tr("Remove"));
    btn.setForeground(Color.blue);
    btn.addActionListener(new RemoveAction(line));
    add(btn, "alignx right, pushx, wrap");
    lines.add(line);
    removeButtons.add(btn);

    order.addCabin(cabin, people, extras);
    refreshPrice();
  }

  public Order getOrder() {
    return order;
  }

  private void refreshPrice() {
    int price = 0;
    for (int i = 0; i < order.getPriceCabin().size(); i++) {
      price += order.getPriceCabin().get(i) + order.getPriceExtras().get(i) - order.getOffer().get(i);
    }
    priceLabel.setText(price + " \u20ac");
  }

  public List<JLabel> getLines() {
    return lines;
  }

  public List<JButton> getRemoveButtons() {
    return removeButtons;
  }

  private class RemoveAction implements ActionListener {

    private JLabel line;

    private RemoveAction(JLabel line) {
      this.line = line;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      int i = lines.indexOf(line);
      remove(lines.get(i));
      remove(removeButtons.get(i));
      lines.remove(i);
      removeButtons.remove(i);
      refreshPrice();

      order.remove(i);

      if (lines.size() == 0) {
        dates.setEnabled(true);
        order.destroy();
        order = null;
      }


      revalidate();
      repaint();
    }
  }
}
