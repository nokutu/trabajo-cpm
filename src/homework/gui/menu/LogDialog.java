package homework.gui.menu;

import homework.Main;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static homework.I18n.tr;

/**
 * Dialog that shows the log content
 */
public class LogDialog extends JDialog {

  public LogDialog(JDialog dialog) {
    super(dialog, false);
    setTitle(tr("Log"));
    setLocationRelativeTo(dialog);
    setMaximumSize(new Dimension(600, 800));
    setResizable(false);

    JPanel center = new JPanel();
    center.setLayout(new MigLayout("alignx center"));
    JTextArea area = new JTextArea();
    area.setEditable(false);
    area.setText(Main.log.getLog());
    center.add(new JScrollPane(area));

    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new MigLayout("alignx right"));
    btnPanel.add(new JPanel(), "growx, spanx");

    JButton copy = new JButton(tr("Copy"));
    copy.setToolTipText(tr("Copy to clipboard"));
    copy.setMnemonic('c');
    copy.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(Main.log.getLog()), new StringSelection(Main.log.getLog()));
      }
    });
    btnPanel.add(copy);

    JButton close = new JButton(tr("Close"));
    close.setToolTipText(tr("Close the window"));
    close.setMnemonic('l');
    close.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Main.log.i("Log panel closed");
        dispose();
      }
    });
    btnPanel.add(close);

    add(center, BorderLayout.CENTER);
    add(btnPanel, BorderLayout.SOUTH);
    pack();
  }
}
