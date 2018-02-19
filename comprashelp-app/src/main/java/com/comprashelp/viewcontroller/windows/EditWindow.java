package com.comprashelp.viewcontroller.windows;

import com.comprashelp.viewcontroller.dialogs.DialogCancelButton;
import com.comprashelp.viewcontroller.dialogs.DialogCloseButton;
import com.comprashelp.viewcontroller.dialogs.DialogOkButton;
import com.comprashelp.viewcontroller.dialogs.DialogSaveButton;
import com.comprashelp.viewcontroller.form.BeanForm;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Classe que servirá de ponto de partida para edição de objetos.
 *
 * @author Ednilson Brito Lopes
 */
public class EditWindow<T> extends Window {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Enumeração que define os tipos de botão de confirmação da janela de edição.
   */
  public enum ConfirmButtonType {
    /**
     * Botão Ok, usado para confirmação de um objeto parte no objeto principal.
     */
    OK,
    /**
     * Botão Salvar, usado para persistência do objeto principal no banco de
     * dados.
     */
    SAVE,
    /**
     * Botão Fechar, usado para fechar o diálogo nenhuma alteração no modelo.
     */
    CLOSE
  }

  /**
   * Objeto que está sendo editado.
   */
  private T object;

  /**
   * Determina se o formulário apresenta os objetos somente para leitura.
   */
  private boolean readOnly;

  /**
   * @return {@link #object}
   */
  public T getObject() {
    return object;
  }

  /**
   * Qual botão será disponível para a confirmação do diálogo.
   */
  ConfirmButtonType confirmButtonType = ConfirmButtonType.SAVE;

  /**
   * Construtor.
   */
  public EditWindow() {
    super();
  }

  /**
   * Construtor.
   *
   * @param caption título da janela.
   * @param obj objeto que será editado.
   * @param form formulário que permite a alteração dos campos do objeto.
   * @param confirmButtonType tipo de botão para confirmação da edição.
   * @param readOnly se {@code true}, a janela será usada apenas para a
   * visualização dos dados do objeto informado.
   * @param width largura da janela a ser instanciada.
   * @param height altura da janela a ser instanciada.
   */
  public EditWindow(String caption, T obj, BeanForm<T> form, ConfirmButtonType confirmButtonType, boolean readOnly,
      int width, int height) {
    this.confirmButtonType = confirmButtonType;
    setWidth(width, Unit.PIXELS);
    setHeight(height, Unit.PIXELS);
    VerticalLayout vLayout = new VerticalLayout();
    setModal(true);
    center();
    setCaption(caption);
    this.object = obj;
    this.readOnly = readOnly;
    vLayout.setSpacing(true);
    if (form != null) {
      form.setObject(obj, readOnly);
      vLayout.addComponent(form);
      if (height != -1 && form.getHeight() != -1) {
        vLayout.setExpandRatio(form, 1);
        vLayout.setSizeFull();
      }
    }
    vLayout.addComponents(createButtonsBar());
    vLayout.setMargin(true);
    setContent(vLayout);
  }

  /**
   * Cria a barra de botões que permite confirmar ou cancelar a edição.
   *
   * @return o {@link HorizontalLayout} contendo os botões.
   */
  public HorizontalLayout createButtonsBar() {
    HorizontalLayout hLayout = new HorizontalLayout();
    hLayout.addComponentsAndExpand(new Label(""));
    hLayout.addComponents(createConfirmButton(), createCancelButton());
    hLayout.setSpacing(true);
    return hLayout;
  }

  /**
   * Cria o botão que confirma as alterações realizadas no objeto que está sendo
   * editado.
   *
   * @return o botão criado.
   */
  private Button createConfirmButton() {
    Button confirmButton = null;
    if (confirmButtonType == ConfirmButtonType.SAVE) {
      confirmButton = new DialogSaveButton();
    } else if (confirmButtonType == ConfirmButtonType.OK) {
      confirmButton = new DialogOkButton();
    } else if (confirmButtonType == ConfirmButtonType.CLOSE) {
      confirmButton = new DialogCloseButton();
    }

    confirmButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    confirmButton.addClickListener(event -> {
      try {
        confirm(object);
        close();
      } catch (Exception e) {
        Notification.show("Erro: " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
      }
    });
    return confirmButton;
  }

  /**
   * Cria o botão que cancela as alterações realizadas no objeto que está sendo
   * alterado.
   *
   * @return o botão criado.
   */
  private Button createCancelButton() {
    Button cancelButton = new DialogCancelButton();
    cancelButton.setVisible(!readOnly);
    cancelButton.addClickListener(event -> {
      cancel(object);
      close();
    });
    return cancelButton;
  }

  /**
   * Método que permite definir o comportamento ao confirmar a edição.
   *
   * @param obj objeto que será persistido.
   * @throws Exception qualquer problema ocorrido durante a confirmação.
   */
  public void confirm(T obj) throws Exception {
    // opcional na visualização de objetos.
  }

  /**
   * Método que permite definir o comportamento ao cancelar a edição.
   *
   * @param obj objeto que estava sendo editado e cuja edição foi cancelada.
   */
  public void cancel(T obj) {
    // opcional
  }
}
