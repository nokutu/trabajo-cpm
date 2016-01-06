package homework.gui;

import homework.gui.components.SearchBar;

/**
 * Used in the components that have a search bar.
 */
public interface HasSearchBar extends HasNavbar {

  SearchBar getSearchBar();
}
