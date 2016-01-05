package homework.gui.menu;

import homework.Main;
import homework.models.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static homework.I18n.tr;

/**
 * Created by nokutu on 05/01/2016.
 */
public class RestartDialog extends JDialog {

  public RestartDialog(SettingsDialog parent) {
    super(parent, true);

    JPanel center = new JPanel();
    add(center, BorderLayout.CENTER);
    center.setLayout(new MigLayout());
    center.add(new JLabel(tr("Restart is requiered in order to apply some of the selected configurations.")));

    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new MigLayout());
    btnPanel.add(new JPanel(), "pushx");
    JButton restart = new JButton(tr("Restart"));
    getRootPane().setDefaultButton(restart);
    restart.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Main.frame.dispose();
        User.logout();
        EventQueue.invokeLater(new Main());
      }
    });
    JButton cancel = new JButton(tr("Cancel"));
    cancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    btnPanel.add(cancel);
    btnPanel.add(restart);
    add(btnPanel, BorderLayout.SOUTH);

    pack();
    setLocationRelativeTo(parent);

  }
}
