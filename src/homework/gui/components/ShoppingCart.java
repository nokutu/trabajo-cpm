package homework.gui.components;

import homework.models.Cabin;
import homework.models.CabinBook;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static homework.I18n.tr;

/**
 * Created by nokutu on 04/01/2016.
 */
public class ShoppingCart extends ScrollablePanel {

  List<CabinBook> books = new ArrayList<>();
  List<JLabel> lines = new ArrayList<>();
  List<JButton> removeButtons = new ArrayList<>();

  public ShoppingCart() {
    setLayout(new MigLayout());
    JLabel cart = new JLabel(tr("Cart"));
    cart.setFont(new Font("default", Font.BOLD, 16));
    add(cart, "wrap");
  }

  public void addBook(CabinBook book) {
    JLabel line = new JLabel(book.getShoppingCartString());
    add(line, "alignx left");
    TextButton btn = new TextButton(tr("Remove"));
    btn.setForeground(Color.blue);
    btn.addActionListener(new RemoveAction(book));
    add(btn, "alignx right, pushx, wrap");
    lines.add(line);
    removeButtons.add(btn);
    books.add(book);
  }

  private class RemoveAction implements ActionListener {

    private CabinBook book;

    private RemoveAction(CabinBook book) {
      this.book = book;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      int i = books.indexOf(book);
      remove(lines.get(i));
      remove(removeButtons.get(i));
      lines.remove(i);
      removeButtons.remove(i);
      books.remove(i);
      revalidate();
      repaint();
    }
  }
}
