package homework.gui;

import homework.Main;
import homework.gui.components.HomeLogo;
import homework.models.Order;
import homework.models.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static homework.I18n.tr;

/**
 * Latest panel, allows the user to export the bill and go back to the initial panel.
 */
public class FinalPanel extends JPanel {

  private JTextArea bill;
  private String text;

  public FinalPanel() {
    setLayout(new BorderLayout());
    add(new HomeLogo(), BorderLayout.NORTH);

    JPanel center = new JPanel();
    center.setLayout(new MigLayout("align center center"));

    bill = new JTextArea();
    bill.setLineWrap(true);
    bill.setWrapStyleWord(true);
    bill.setColumns(80);
    bill.setBackground(new JPanel().getBackground());
    bill.setEditable(false);
    bill.setFocusable(false);
    center.add(new JScrollPane(bill), "wrap");

    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new MigLayout());
    btnPanel.add(new JPanel(), "pushx, growx");

    JButton export = new JButton(tr("Export"));
    export.setMnemonic('e');
    export.setToolTipText(tr("Export the bill into a local directory"));
    export.addActionListener(new ExportAction());
    btnPanel.add(export);

    JButton finish = new JButton(tr("Finish"));
    finish.setToolTipText(tr("Go back to the initial window"));
    finish.setMnemonic('f');
    finish.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Main.frame.show(MainFrame.INITIAL_PANEL);
      }
    });
    btnPanel.add(finish);

    add(center, BorderLayout.CENTER);
    add(btnPanel, BorderLayout.SOUTH);
  }

  public void setBillText(String billText) {
    bill.setText(billText);
    text = billText;
  }

  private class ExportAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      JFileChooser chooser = new JFileChooser();
      chooser.showSaveDialog(Main.frame);

      File f = chooser.getSelectedFile();
      if (!f.exists()) {
        try {
          f.createNewFile();
          BufferedWriter bw = new BufferedWriter(new FileWriter(f));
          bw.write(bill.getText());
          bw.close();
        } catch (IOException e1) {
          Main.log.e(e1);
        }
      }
    }
  }
}
