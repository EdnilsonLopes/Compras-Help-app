package com.comprashelp.views;

import com.comprashelp.app.ComprasHelpUI;
import com.comprashelp.viewcontroller.autentication.LoginScreen;
import com.comprashelp.viewcontroller.autentication.LoginScreen.LoginListener;
import com.comprashelp.viewcontroller.autentication.SignUpGeneralScreen;
import com.comprashelp.viewcontroller.navigation.model.MenuItem;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Tela que compõe o template da pagina.
 *
 * @author Ednilson Brito Lopes
 */
public class MainScreen
    extends VerticalLayout {
//private static AccessControl accessControl = new PortalAccessControl();

  private static boolean accessControl = false;
  private static final long serialVersionUID = 1L;

  /**
   * Construtor.
   *
   * @param ui {@link UI da aplicação.}
   */
  public MainScreen(ComprasHelpUI ui) {
    //Configuraçõs da UI
    ui.addStyleName(ValoTheme.UI_WITH_MENU);
    Responsive.makeResponsive(ui);
    setSpacing(false);

    //Configurações do MainScreen
    setStyleName("portal-main-screen");
    setWidth("100%");
    Responsive.makeResponsive(this);
    
    //Cria o Cabeçalho
    CustomLayout header = new CustomLayout("headerLayout");
    Responsive.makeResponsive(header);
    header.setWidth("100%");

    //Cria os componentes centrais, composto por Menu e Container para visualizar o menu
    HorizontalLayout center = new HorizontalLayout();
    center.setWidth("100%");
    Responsive.makeResponsive(center);

    {
      //Cria o componente central
      CssLayout contentArea = new CssLayout();
      contentArea.setPrimaryStyleName("valo-content");
      contentArea.addStyleName("v-scrollable");
      contentArea.addStyleName("top-bottom-padding");
      contentArea.setSizeFull();

      //Cria o menu
      CssLayout menuArea = new CssLayout();
      menuArea.setPrimaryStyleName(ValoTheme.MENU_ROOT);

      MenuItem menuRoot = new MenuItem("Menu");

      MenuItem loginMenu = new MenuItem("Autenticação", VaadinIcons.LOCK);
      loginMenu.addSubmenu(new MenuItem("Login",
          VaadinIcons.LOCK,
          getViewLogin()));
      loginMenu.addSubmenu(new MenuItem("SingUp",
          VaadinIcons.SIGN_IN,
          getViewSignUp()));
      menuRoot.addSubmenu(loginMenu);

      menuArea.addComponent(new ComprasHelpMenu(contentArea, ui, menuRoot));
      menuArea.setHeight((ui.getPage().getBrowserWindowHeight() - 100) + "px");
      Page.getCurrent().addBrowserWindowResizeListener(new BrowserWindowResizeListener() {
        private static final long serialVersionUID = 1L;

        @Override
        public void browserWindowResized(Page.BrowserWindowResizeEvent event) {
          menuArea.setHeight((ui.getPage().getBrowserWindowHeight() - 100) + "px");
        }

      });

      center.addComponents(menuArea, contentArea);
      center.setExpandRatio(contentArea, 1);
    }

    //Cria o Rodapé
    CustomLayout footer = new CustomLayout("footerLayout");
    Responsive.makeResponsive(footer);

    //Adiciona os componentes
    addComponents(header, center, footer);
  }

  private static LoginScreen getViewLogin() {
    LoginScreen viewLogin = new LoginScreen(accessControl, new LoginListener() {
      private static final long serialVersionUID = 1L;

      @Override
      public void loginSuccessful() {
        System.out.println("login efetuado com sucesso");
        UI.getCurrent().getPage().reload();
      }
    });

    return viewLogin;
  }
  
  private static SignUpGeneralScreen getViewSignUp(){
    SignUpGeneralScreen view = new SignUpGeneralScreen();
    
    return view;
  }

}
