package homework.gui.components;

import javax.swing.JPanel;
import javax.swing.Scrollable;
import java.awt.Dimension;
import java.awt.Rectangle;

/**
 * JPanel implementing Scrollable. Needed because there is a bag with JTextAreas inside of JPanels inside of JScrollPanes, that makes the JTextArea unable to Shrink.
 * Copied the code from <a href="http://stackoverflow.com/questions/15783014/jtextarea-on-jpanel-inside-jscrollpane-does-not-resize-properly">here</a>.
 */
public class ScrollablePanel extends JPanel implements Scrollable {

  public Dimension getPreferredScrollableViewportSize() {
    return super.getPreferredSize(); //tell the JScrollPane that we want to be our 'preferredSize' - but later, we'll say that vertically, it should scroll.
  }

  public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
    return 16;//set to 16 because that's what you had in your code.
  }

  public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
    return 16;//set to 16 because that's what you had set in your code.
  }

  public boolean getScrollableTracksViewportWidth() {
    return true;//track the width, and re-size as needed.
  }

  public boolean getScrollableTracksViewportHeight() {
    return false; //we don't want to track the height, because we want to scroll vertically.
  }
}
