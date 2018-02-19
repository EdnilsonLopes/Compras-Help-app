/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comprashelp.viewcontroller.dialogs;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author Ednilson Brito Lopes
 */
public class DialogCancelButton extends Button {

  /**
   * Serial Version ID.
   */
  private static final long serialVersionUID = 1L;

  public DialogCancelButton() {
    super();
    setCaption("Cancelar");
    addStyleName(ValoTheme.BUTTON_DANGER);
    setIcon(VaadinIcons.CLOSE);
  }

}
