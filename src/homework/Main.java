package homework;

import homework.gui.MainFrame;

public class Main {

    public static Log log;

    public static void main(String[] args) {
        log = new Log();
        try {
            new Main().run();
        } catch (Exception e) {
            log.e("Program closed because of an exception e: " + e);
        }
    }

    private void run() {
        log.i("Program starts");

        MainFrame frame = new MainFrame();
        frame.start();
    }
}
