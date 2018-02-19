package com.comprashelp.viewcontroller.dialogs;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Ednilson Brito Lopes
 */
public class DialogOkButton extends Button {

  /**
   * Serial Version ID
   */
  private static final long serialVersionUID = 1L;

  public DialogOkButton() {
    super();
    setCaption("Ok");
    addStyleName(ValoTheme.BUTTON_FRIENDLY);
    setIcon(VaadinIcons.CHECK);
  }

}
