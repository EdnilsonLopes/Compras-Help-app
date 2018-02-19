package com.comprashelp.viewcontroller.dialogs;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Ednilson Brito Lopes
 */
public class DialogSaveButton extends Button {

  /**
   * Serial Version ID
   */
  private static final long serialVersionUID = 1L;

  /**
   * Construtor.
   */
  public DialogSaveButton() {
    super();
    setCaption("Salvar");
    addStyleName(ValoTheme.BUTTON_PRIMARY);
    setIcon(VaadinIcons.HARDDRIVE_O);
  }

}
