package com.comprashelp.viewcontroller.autentication;

import com.comprashelp.model.persistence.genericFactory.Login.LoginDAO;
import com.comprashelp.security.model.LoginVO;
import com.comprashelp.util.exceptions.ValidationException;
import com.comprashelp.util.viewcontroller.ComprasHelpFacedeManager;
import com.comprashelp.viewcontroller.form.BeanForm;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Formulário de Cadastro Geral. Nessa classe faz a primeira parte do cadastro
 * seja usuário ou fornecedor.
 *
 * @author Ednilson Brito Lopes
 */
public class SignUpGeneralScreen extends Window implements View {

  private TextField email;

  private PasswordField senha;

  private ComboBox tipo;

  private TextField telefone;

  private Button btCadastrar;

  public SignUpGeneralScreen() {

    this.setWidth(400, Sizeable.Unit.PIXELS);
    this.setHeight(600, Sizeable.Unit.PIXELS);
    this.setModal(true);
    this.setResizable(false);

    singUpForm().addStyleName("login-screen");
    setContent(singUpForm());
  }

  private Component singUpForm() {
    VerticalLayout layout = new VerticalLayout();
    HorizontalLayout hl = new HorizontalLayout();
    hl.setWidth("100%");
    hl.addComponent(createButtonProx());
    hl.setDefaultComponentAlignment(Alignment.BOTTOM_RIGHT);
    layout.addComponent(emailField());
    layout.addComponent(senhaField());
    layout.addComponent(telefoneField());
    layout.addComponent(tipoField());
    layout.addComponent(hl);

    return layout;
  }

  private Component emailField() {
    email = new TextField("Email: ");
    email.setWidth("100%");
    return email;
  }

  private Component senhaField() {
    senha = new PasswordField("Senha: ");
    senha.setWidth("100%");
    return senha;
  }

  private Component tipoField() {
    tipo = new ComboBox("Tipo de Usuário: ");
    tipo.setWidth("100%");
    tipo.setItems("Cliente", "Fornecedor");
    return tipo;
  }

  private Component telefoneField() {
    telefone = new TextField("Telefone:");
    telefone.setWidth("100%");
    return telefone;
  }

  private Component createButtonProx() {
    btCadastrar = new Button("Proximo");
    btCadastrar.addStyleName(ValoTheme.BUTTON_FRIENDLY);
    btCadastrar.setIcon(VaadinIcons.ARROWS);
    btCadastrar.setDisableOnClick(true);
    btCadastrar.addClickListener(e -> {
      try {
        cadastrar();
      } finally {
        btCadastrar.setEnabled(true);
      }
    });
        

    return btCadastrar;
  }
  
  private void cadastrar(){
    try {
      ComprasHelpFacedeManager.getFacade().insertLogin(new LoginVO());
    } catch (SQLException | ValidationException ex) {
      Notification.show("Erro " + ex.getMessage(), Notification.Type.ERROR_MESSAGE);
    }
  }

}
