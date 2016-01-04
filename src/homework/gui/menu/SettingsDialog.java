package homework.gui.menu;

import homework.Main;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static homework.I18n.tr;

/**
 * Created by nokutu on 04/01/2016.
 */
public class SettingsDialog extends JDialog {

  private JButton ok;
  private JButton cancel;

  private JCheckBox disableTooltips;

  public SettingsDialog() {
    JPanel center = new JPanel();
    add(center, BorderLayout.CENTER);

    center.setLayout(new MigLayout("alignx left"));
    disableTooltips = new JCheckBox(tr("Disable tooltips"));
    disableTooltips.setSelected(Main.prefs.getBoolean("disable-tooltips", false));
    center.add(disableTooltips, "wrap");

    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new MigLayout("alignx right"));
    ok = new JButton(tr("OK"));
    ok.addActionListener(new OKAction());
    btnPanel.add(ok, "pushx");
    cancel = new JButton(tr("Cancel"));
    cancel.addActionListener(new CancelAction());
    btnPanel.add(cancel);
    add(btnPanel, BorderLayout.SOUTH);

    pack();
    setLocationRelativeTo(Main.frame);
  }

  private class CancelAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      dispose();
    }
  }

  private class OKAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      ToolTipManager.sharedInstance().setEnabled(!disableTooltips.isSelected());
      Main.prefs.putBoolean("disable-tooltips", disableTooltips.isSelected());

      dispose();
    }
  }
}
