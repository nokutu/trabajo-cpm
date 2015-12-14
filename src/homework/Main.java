package homework;

import homework.gui.MainFrame;

public class Main {

    public static Log log;
    public static Pref prefs;

    public static void main(String[] args) {
        log = new Log();
        prefs = new Pref();
        try {
            new Main().run();
        } catch (Exception e) {
            log.e("Program closed because of an exception e: " + Utils.getStackTrace(e));
        }
    }

    private void run() {
        log.i("Program starts");

        MainFrame frame = new MainFrame();
        frame.start();
    }
}
