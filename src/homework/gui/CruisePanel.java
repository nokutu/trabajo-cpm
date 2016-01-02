package homework.gui;

import homework.Main;
import homework.models.Cabin;
import homework.models.Cruise;
import homework.models.CruiseDate;
import homework.models.Extra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static homework.I18n.tr;
import static homework.I18n.trn;


/**
 * Created by nokutu on 01/01/2016.
 */
public class CruisePanel extends JPanel implements HasSearchBar {

  private ScrollablePanel centerPanel;
  private JScrollPane scroll;

  private SearchBar sb;

  private JTextArea description;
  private JLabel rute;
  private JLabel zone;
  private JLabel duration;
  private JLabel denomination;
  private List<JCheckBox> extras = new ArrayList<>();

  private JComboBox<Cabin> cabins;
  private JComboBox<CruiseDate> dates;
  private DateChangeListener dateChangeListener;
  private JSpinner people;


  public CruisePanel() {
    setLayout(new BorderLayout());
    add(sb = new SearchBar(), BorderLayout.NORTH);

    description = new JTextArea();
    description.setLineWrap(true);
    description.setWrapStyleWord(true);
    rute = new JLabel();
    zone = new JLabel();
    duration = new JLabel();
    denomination = new JLabel();

    GridBagConstraints c = new GridBagConstraints();

    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 2;
    getCenterPanel().add(denomination, c);

    c.gridy = 1;
    getCenterPanel().add(zone, c);

    c.gridy = 2;
    getCenterPanel().add(rute, c);

    c.gridy = 3;
    getCenterPanel().add(duration, c);

    c.gridwidth = 1;
    c.gridy = 4;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1;
    c.weighty = 1;
    c.insets = new Insets(10, 10, 10, 10);
    c.gridheight = 2;
    getCenterPanel().add(description, c);

    c.insets = new Insets(0, 0, 0, 0);
    c.fill = GridBagConstraints.NONE;
    c.gridy = 4;
    c.gridx = 1;
    c.gridheight = 1;
    c.weightx = 0;
    c.weighty = 0;
    c.anchor = GridBagConstraints.PAGE_START;
    c.insets = new Insets(0, 0, 0, 5);
    getCenterPanel().add(createExtrasPanel(), c);

    c.gridy = 5;
    getCenterPanel().add(createBookPanel(), c);
  }

  private ScrollablePanel createExtrasPanel() {
    ScrollablePanel extrasPanel = new ScrollablePanel();
    extrasPanel.setBorder(BorderFactory.createTitledBorder(tr("Extras")));
    extrasPanel.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.anchor = GridBagConstraints.LINE_START;
    for (Extra e : Main.db.getExtras().values()) {
      JCheckBox checkBox = new JCheckBox(e.getName() + " (" + e.getPrice() + " " + e.getUnit() + ")");
      extras.add(checkBox);
      extrasPanel.add(checkBox, c);
      c.gridy++;
    }
    return extrasPanel;
  }

  private ScrollablePanel createBookPanel() {
    ScrollablePanel bookPanel = new ScrollablePanel();
    bookPanel.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    c.anchor = GridBagConstraints.LINE_START;
    c.gridx = 0;
    c.gridy = 0;
    c.insets = new Insets(5, 0, 5, 5);
    bookPanel.add(new JLabel(tr("Date") + ":"), c);

    c.gridy++;
    bookPanel.add(new JLabel(tr("Cabin") + ":"), c);

    c.gridy++;
    bookPanel.add(new JLabel(tr("People") + ":"), c);

    c.gridx = 1;
    c.gridy = 0;
    dates = new JComboBox<>();
    dates.addActionListener(dateChangeListener = new DateChangeListener());
    bookPanel.add(dates, c);

    c.gridy++;
    cabins = new JComboBox<>();
    bookPanel.add(cabins, c);

    c.gridy++;
    people = new JSpinner();
    SpinnerNumberModel model = new SpinnerNumberModel();
    model.setMaximum(5);
    model.setMinimum(1);
    model.setStepSize(1);
    model.setValue(1);
    people.setModel(model);
    bookPanel.add(people, c);

    c.fill = GridBagConstraints.BOTH;
    c.anchor = GridBagConstraints.PAGE_START;
    c.gridy++;
    c.gridx = 0;
    c.gridwidth = 2;
    JButton book = new JButton(tr("Book"));
    bookPanel.add(book, c);

    return bookPanel;
  }


  public ScrollablePanel getCenterPanel() {
    if (centerPanel == null) {
      centerPanel = new ScrollablePanel();
      centerPanel.setLayout(new GridBagLayout());
      scroll = new JScrollPane();
      scroll.setViewportView(centerPanel);
      add(scroll, BorderLayout.CENTER);
    }
    return centerPanel;
  }

  public void setCruise(Cruise cruise) {
    description.setText(cruise.getDescription());
    denomination.setText(cruise.getDenomination());
    rute.setText(cruise.getRute().toString());
    zone.setText(cruise.getZone().toString());
    duration.setText(cruise.getDuration() + " " + trn("day", cruise.getDuration()));

    dates.removeAllItems();
    for (CruiseDate date : cruise.getDates()) {
      dates.addItem(date);
    }
    dateChangeListener.setCruise(cruise);

    cabins.removeAllItems();
    for (Cabin cabin : cruise.getDefaultCabins((CruiseDate) dates.getSelectedItem())) {
      cabins.addItem(cabin);
    }

    for (JCheckBox checkBox : extras) {
      checkBox.setSelected(false);
    }
    people.setValue(1);

    revalidate();
    repaint();

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        Main.frame.cp.scroll.getViewport().setViewPosition(new Point(0, 0));
      }
    });
  }

  @Override
  public Navbar getNavbar() {
    return sb.getNavbar();
  }

  @Override
  public SearchBar getSearchBar() {
    return sb;
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
}
