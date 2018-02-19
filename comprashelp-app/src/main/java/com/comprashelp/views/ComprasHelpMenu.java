package com.comprashelp.views;

import com.comprashelp.app.ComprasHelpUI;
import com.comprashelp.viewcontroller.navigation.model.MenuItem;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Iterator;
import java.util.List;

/**
 * Classe responsável por criar o Menu do App.
 *
 * @author Ednilson Brito Lopes
 */
public class ComprasHelpMenu extends CssLayout {

  private static final long serialVersionUID = 1L;

  /**
   * Navigator deste Menu
   */
  private Navigator navigator;

  /**
   * Objeto que representa o Menu Principal
   */
  private MenuItem rootMenu = new MenuItem("root");

  /**
   * Representa o Layout de cada item de menu
   */
  private CssLayout menuItemsLayout = new CssLayout();

  /**
   * Construtor
   *
   * @param viewDisplay
   * @param ui
   * @param rootMenu MenuItem Pai que contém uma listagem de menus
   */
//  public PortalMenu(ComponentContainer viewDisplay, PortalUI ui, MenuItem rootMenu, UserVO userVO) {
  public ComprasHelpMenu(ComponentContainer viewDisplay, ComprasHelpUI ui, MenuItem rootMenu) {
    this.rootMenu = rootMenu;
    createNavigator(ui, viewDisplay);
    setId("portal_menu");
    addStyleName(ValoTheme.MENU_PART);

    //TOPO
    HorizontalLayout top = new HorizontalLayout();
    top.setWidth("100%");
    top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
    top.addStyleName(ValoTheme.MENU_TITLE);
    addComponent(top);
    Button showMenu = new Button("Menu", new Button.ClickListener() {
      private static final long serialVersionUID = 1L;

      @Override
      public void buttonClick(Button.ClickEvent event) {
        if (getStyleName().contains("valo-menu-visible")) {
          removeStyleName("valo-menu-visible");
        } else {
          addStyleName("valo-menu-visible");
        }
      }
    });
    showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
    showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
    showMenu.addStyleName("valo-menu-toggle");
    showMenu.setIcon(VaadinIcons.LIST);
    addComponent(showMenu);

//    if (userVO != null) {
//      MenuBar settings = createLogoutComponent(userVO);
//      addComponent(settings);
//    }
    menuItemsLayout.setPrimaryStyleName("valo-menuitems");
    addComponent(menuItemsLayout);
    createTreeMenu(rootMenu.getSubmenus());

  }

  /**
   * Cria o Navegador responsável pela navegação de views
   *
   * @param ui Componente UI principal
   * @param viewContainer Container onde o navigator colocará as views
   * @return
   */
  private void createNavigator(UI ui, ComponentContainer viewContainer) {
    navigator = new Navigator(ui, viewContainer);
    navigator.addViewChangeListener(new ViewChangeListener() {
      private static final long serialVersionUID = 1L;

      @Override
      public boolean beforeViewChange(ViewChangeListener.ViewChangeEvent event) {
        event.getNewView().getViewComponent().setStyleName("portal-view");
        return true;
      }

      @Override
      public void afterViewChange(ViewChangeListener.ViewChangeEvent event) {
        for (Iterator<Component> it = menuItemsLayout.iterator(); it.hasNext();) {
          it.next().removeStyleName("selected");
        }
        for (MenuItem item : rootMenu.getSubmenus()) {
          if (event.getViewName().equals(item.getTitle())) {
            for (Iterator<Component> it = menuItemsLayout.iterator(); it.hasNext();) {
              Component c = it.next();
              if (c.getCaption() != null && c.getCaption().startsWith(item.getTitle())) {
                c.addStyleName("selected");
                break;
              }
            }
            break;
          }
        }
        removeStyleName("valo-menu-visible");
      }
    });

    /**
     * Classe responsável por criar um layout de Pagina Inválida.
     */
    class ErrorView
        extends VerticalLayout
        implements View {

      private static final long serialVersionUID = 1L;

      public ErrorView() {
        Label invalidPage = new Label("<center class='Vaadin-Icons' style=\"font-size:40px; margin-top:10px;\"> PÁGINA INVÁLIDA</center>", ContentMode.HTML);
        invalidPage.setSizeFull();
        addComponent(invalidPage);
      }
    }
    navigator.setErrorView(new ErrorView());

    String f = Page.getCurrent().getUriFragment();
    if (f == null || f.equals("")) {
      //Navega para o Primeiro Elemento disponivel
      if (rootMenu.getSubmenus().size() > 0) {
        for (MenuItem item : rootMenu.getSubmenus()) {
          if (item.getSubmenus().isEmpty()) {
            if (item.getDashboardBytes() != null && !item.getDashboardBytes().equals("")) {
              navigator.navigateTo(item.getTitle());
              break;
            } else if (item.getView() != null) {
              navigator.navigateTo(item.getTitle());
              break;
            }
          }
        }
      }
    }
  }

  /**
   * Cria uma arvore de Menus e adiciona cada view ao navigator
   *
   * @param menuItens
   */
  private void createTreeMenu(List<MenuItem> menuItens) {
    for (MenuItem item : menuItens) {
      if (item.getSubmenus().isEmpty()) {
//        if (item.getView() instanceof DashboardView) {
//          navigator.addView(item.getTitle(),
//              new DashboardView(1L, item, DashboardColumn.getFactory(), DashboardRow.getFactory(),
//                  ChartBoardlet.getFactory(), QueryResultBoardlet.getFactory(),
//                  QueryListBoardlet.getFactory(), NewsCarouselBoardlet.getFactory(),
//                  GaugeBoardlet.getFactory(), LinkListBoardlet.getFactory(),
//                  ContentGridBoardlet.getFactory(),
//                  ContentViewerBoardlet.getFactory(),
//                  //Add Produto e Fornecedor
//                  ProdutoBoardlet.getFatory(),
//                  FornecedorBoardlet.getFatory()));
//        } else {
        navigator.addView(item.getTitle(), item.getView());

        Button b = new Button(item.getTitle(), new Button.ClickListener() {
          private static final long serialVersionUID = 1L;

          @Override
          public void buttonClick(Button.ClickEvent event) {
            //se for uma instância de Window o comportamento para abrir a popup é diferente do padrão.
            if (item.getView() instanceof Window) {
              Window popup = (Window) item.getView();
              UI.getCurrent().addWindow(popup);
            } else {
              navigator.navigateTo(item.getTitle());
            }

          }
        });
        b.setCaptionAsHtml(true);
        b.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        b.setIcon(item.getIcon());
        menuItemsLayout.addComponent(b);
      } else {
        Label label = new Label(item.getTitle(), ContentMode.HTML);
        label.setPrimaryStyleName(ValoTheme.MENU_SUBTITLE);
        label.addStyleName(ValoTheme.LABEL_H4);
        label.setSizeUndefined();
        menuItemsLayout.addComponent(label);
        createTreeMenu(item.getSubmenus());
      }
    }
  }

//  /**
//   * Cria o componente que permite realizar o logout.
//   *
//   * @return o componente criado.
//   */
//  private MenuBar createLogoutComponent(UserVO userVO) {
//    MenuBar logoutMenu = new MenuBar();
//
//    ThemeResource resource = new ThemeResource("img/profile-pic-300px.jpg");
//    com.vaadin.ui.MenuBar.MenuItem nameUser = logoutMenu.addItem(userVO.getNome(), resource, null);
//
//    //Adiciona o Logout
//    nameUser.addItem("Logout", VaadinIcons.SIGN_OUT, new MenuBar.Command() {
//      private static final long serialVersionUID = 1L;
//
//      @Override
//      public void menuSelected(com.vaadin.ui.MenuBar.MenuItem selectedItem) {
//        VaadinSession.getCurrent().getSession().invalidate();
//        Page.getCurrent().reload();
//      }
//    });
//
//    logoutMenu.addStyleName("user-menu");
//    return logoutMenu;
//  }
}
