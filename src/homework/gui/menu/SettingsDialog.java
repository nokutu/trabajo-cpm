package homework.gui.menu;

import homework.Main;
import homework.gui.RegisterDialog;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static homework.I18n.tr;

/**
 * Dialog that allows the user to modify the application settings, such as language or tooltips behaviour.
 */
class SettingsDialog extends JDialog {

  private JButton ok;
  private JButton cancel;

  private JCheckBox disableTooltips;

  private JComboBox<LocaleWrapper> language;

  public SettingsDialog() {
    super(Main.frame, true);
    JPanel center = new JPanel();
    add(center, BorderLayout.CENTER);

    center.setLayout(new MigLayout("alignx left"));

    disableTooltips = new JCheckBox(tr("Disable tooltips"));
    disableTooltips.setSelected(Main.prefs.getBoolean("disable-tooltips", false));
    disableTooltips.setMnemonic('d');
    center.add(disableTooltips, "spanx, wrap");

    language = new JComboBox<>();
    List<LocaleWrapper> locales = new ArrayList<>();
    locales.add(new LocaleWrapper(tr("English"), "EN"));
    locales.add(new LocaleWrapper(tr("Spanish"), "ES"));
    Collections.sort(locales, new Comparator<LocaleWrapper>() {
      @Override
      public int compare(LocaleWrapper o1, LocaleWrapper o2) {
        return o1.compareTo(o2);
      }
    });
    for (LocaleWrapper lw : locales) {
      language.addItem(lw);
    }
    language.setSelectedItem(new LocaleWrapper("Def", Locale.getDefault().getLanguage()));
    JLabel languageLabel = new JLabel(tr("Language") + ":");
    languageLabel.setLabelFor(language);
    languageLabel.setDisplayedMnemonic('l');
    center.add(languageLabel);
    center.add(language, "gapleft 20, wrap");

    JButton delete = new JButton(tr("Delete configuration files"));
    delete.setMnemonic('d');
    delete.setToolTipText(tr("Delete the configuration files from the computer, including users and order history"));
    delete.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Main.prefs.clear();
        RestartDialog reg = new RestartDialog(SettingsDialog.this);
        reg.setVisible(true);
      }
    });
    center.add(delete, "spanx, wrap");

    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new MigLayout("alignx right"));
    ok = new JButton(tr("OK"));
    ok.addActionListener(new OKAction());
    btnPanel.add(new JPanel(), "pushx");
    btnPanel.add(ok);
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
      boolean requireRestart = false;

      ToolTipManager.sharedInstance().setEnabled(!disableTooltips.isSelected());
      Main.prefs.putBoolean("disable-tooltips", disableTooltips.isSelected());

      if (!((LocaleWrapper) language.getSelectedItem()).getLocale().getLanguage().equals(Locale.getDefault().getLanguage())) {
        Main.prefs.put("language", ((LocaleWrapper) language.getSelectedItem()).getLocale().getLanguage());
        requireRestart = true;
      }

      if (requireRestart) {
        JDialog restartDialog = new RestartDialog(SettingsDialog.this);
        restartDialog.setVisible(true);
      }

      dispose();
    }
  }
}
