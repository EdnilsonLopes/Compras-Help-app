/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comprashelp.viewcontroller.autentication;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import gov.nist.javax.sdp.fields.EmailField;
import java.io.Serializable;

/**
 * Tela para Login.
 *
 * @author Ednilson Brito Lopes
 * @since 16/02/2018
 */
public class LoginScreen
    extends Window
    implements View {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Campo para obtenção do nome do usuário.VerticalLayout
   */
  private TextField email;

  /**
   * Campo para obtenção da senha.
   */
  private PasswordField password;
  /**
   * Botão para efetuar o login.
   */
  private Button login;
  /**
   * Botão para recuperação de senha.horizontalLayout
   */
  private Button forgotPassword;
  /**
   * Objeto que será notificado de um login bem sucedido.
   */
  private LoginListener loginListener;
  /**
   * Serviço de autenticação.
   */
  private Boolean accessControl;

  /**
   * Construtor.
   *
   * @param accessControl objeto que realizará o login.
   * @param loginListener objeto que será notificado do login.
   */
  public LoginScreen(Boolean accessControl, LoginListener loginListener) {

    this.loginListener = loginListener;
    this.accessControl = accessControl;
    
    this.setWidth(600, Sizeable.Unit.PIXELS);
    this.setHeight(400, Sizeable.Unit.PIXELS);
//    this.setWidth("30%");
//    this.setHeight("30%");
    this.setModal(true);
    this.setResizable(false);

    Component horizontalLayout = createHorizontalLayout();
    horizontalLayout.addStyleNames("login-screen");
    horizontalLayout.setSizeFull();
    email.focus();

    this.setContent(horizontalLayout);
  }

  private HorizontalLayout createHorizontalLayout() {
    HorizontalLayout hl = new HorizontalLayout();
    hl.setMargin(true);
    hl.setSpacing(false);
    Component loginBox = createLoginBox();
    hl.setSizeFull();
    hl.addComponent(loginBox);
    hl.setExpandRatio(loginBox, 1);
    return hl;
  }

  /**
   * Cria a área de login com o formulário de login ao centro.
   *
   * @return o componente criado.
   */
  private Component createLoginBox() {
    VerticalLayout loginBox = new VerticalLayout();
    loginBox.setSizeFull();
    loginBox.setMargin(true);
    Component loginForm = buildLoginForm();
    loginBox.addComponent(loginForm);
    loginBox.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
    return loginBox;
  }

  /**
   * Cria o formulário de login.
   *
   * @return o componente criado.
   */
  private Component buildLoginForm() {
    VerticalLayout loginForm = new VerticalLayout();
    loginForm.addStyleName("login-form");
    loginForm.setSizeFull();
    loginForm.setMargin(false);

    loginForm.addComponent(createUserNameField());
    loginForm.addComponent(createPasswordField());
    loginForm.addComponent(createButtons());
    return loginForm;
  }

  /**
   * Cria o campo de informação do nome do usuário.
   *
   * @return o componente criado.
   */
  private Component createUserNameField() {
    email = new TextField("Email");
    email.setWidth(100, Sizeable.Unit.PERCENTAGE);
    email.setHeight(3, Sizeable.Unit.EM);
    return email;
  }

  /**
   * Cria o campo de informação do nome da senha.
   *
   * @return o componente criado.
   */
  private Component createPasswordField() {
    password = new PasswordField("Senha");
    password.setWidth(100, Sizeable.Unit.PERCENTAGE);
    password.setHeight(3, Sizeable.Unit.EM);
    password.setDescription("Write anything");
    password.setValueChangeMode(ValueChangeMode.EAGER);
    return password;
  }

  /**
   * Cria a barra de botões de informação da senha.
   *
   * @return o componente criado.
   */
  private Component createButtons() {
    VerticalLayout buttons = new VerticalLayout();
    buttons.setMargin(false);
    buttons.setStyleName("buttons");
    buttons.addComponents(createLoginButon(), createForgotButton());
    return buttons;
  }

  /**
   * Cria o botão de login.
   *
   * @return o componente criado.
   */
  private Component createLoginButon() {
    login = new Button("Login");
    login.setSizeFull();
    login.setHeight(3, Sizeable.Unit.EM);
    login.setDisableOnClick(true);
    login.addClickListener(e -> {
      try {
        login();
      } finally {
        login.setEnabled(true);
      }
    });

    login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    login.addStyleName(ValoTheme.BUTTON_FRIENDLY);
    return login;
  }

  /**
   * Cria o botão de recuperação de senha.
   *
   * @return o componente criado.
   */
  private Component createForgotButton() {
    forgotPassword = new Button("Esqueceu sua senha?");
    forgotPassword.addClickListener(e -> showNotification(new Notification("Hint: Try anything")));
    forgotPassword.addStyleName(ValoTheme.BUTTON_LINK);
    return forgotPassword;
  }

  /**
   * Realiza o Login na fachada.
   */
  private void login() {
    try {
//      accessControl.signIn(username.getValue(), password.getValue());
      accessControl = true;
      loginListener.loginSuccessful();
    } catch (Exception e) {
      Notification.show("Erro: " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
      e.printStackTrace();
      email.focus();
      throw e;
    }
  }

  /**
   * Apresenta uma mensagem com um tempo
   *
   * @param notification
   */
  private void showNotification(Notification notification) {
    // keep the notification visible a little while after moving the
    // mouse, or until clicked
    notification.setDelayMsec(5000);
    notification.show(Page.getCurrent());
  }

  /**
   * Interfaces para classes interessadas em eventos de login bem sucedidos.
   *
   * @author Ednilson Brito Lopes.
   *
   */
  public interface LoginListener
      extends Serializable {

    void loginSuccessful();
  }
}
