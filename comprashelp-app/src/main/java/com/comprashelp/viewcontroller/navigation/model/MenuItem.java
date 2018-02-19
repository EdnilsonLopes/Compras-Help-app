package com.comprashelp.viewcontroller.navigation.model;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.server.FontIcon;

/**
 * Classe responsável por representar a estrutura visual de um Menu em árvore.
 *
 * @author Emiliano Pessoa
 */
public class MenuItem implements Cloneable {

  /**
   * Id único para identificar o {@link MenuItem}.
   */
  private Integer id;

  /**
   * Título do {@link MenuItem}.
   */
  private String title;

  /**
   * Lista dos submenus deste {@link MenuItem}
   */
  private List<MenuItem> submenus = new ArrayList<>();
  ;

	/**
	 * Icone deste {@link MenuItem}
	 */
	private FontIcon icon;

  /**
   * Representa o {@link MenuItem} que está um nivel acima deste
   * {@link MenuItem}
   */
  private MenuItem parent;

  /**
   * Classe de visão que este {@link MenuItem} mostrará.
   */
  private View view;

  /**
   * Flag que indica se este item de menu é para ser editado
   */
  private boolean edit = false;

  /**
   * Contém os bytes de dashboard
   */
  private String dashboardBytes;

  /**
   * Tag utilizadas para localizar este {@link MenuItem}
   */
  private String tag;

  /**
   * Construtor do {@link MenuItem}
   *
   */
  public MenuItem() {
    super();
  }

  /**
   * Construtor do {@link MenuItem}
   *
   * @param title
   */
  public MenuItem(String title) {
    super();
    this.title = title;
  }

  /**
   * Construtor do {@link MenuItem}
   *
   * @param title
   * @param icon
   */
  public MenuItem(String title, FontIcon icon) {
    super();
    this.title = title;
    this.icon = icon;
  }

  /**
   * Construtor do {@link MenuItem}
   *
   * @param title
   * @param icon
   * @param view
   */
  public MenuItem(String title, FontIcon icon, View view) {
    super();
    this.title = title;
    this.icon = icon;
    this.view = view;
  }

  /**
   * Construtor do {@link MenuItem}
   *
   * @param title
   * @param icon
   * @param view
   * @param tag
   */
  public MenuItem(String title, FontIcon icon, View view, String tag) {
    super();
    this.title = title;
    this.icon = icon;
    this.view = view;
    this.tag = tag;
  }

  public MenuItem(String title, FontIcon icon, View view, String dashboardBytes, Integer id, String tag) {
    super();
    this.title = title;
    this.icon = icon;
    this.view = view;
    this.dashboardBytes = dashboardBytes;
    this.id = id;
    this.tag = tag;
  }

  /**
   * Adiciona um {@link MenuItem} na lista {@link #submenus}. Porém se já
   * existir um item de menu com o mesmo título a view do mesmo será
   * sobrescrita. Observação: Este método só serve para fins de visualização do
   * Menu. Não utiliza-lo quando este será transformado em objeto a ser
   * persistido em banco.
   *
   * @param submenu
   * @param changeIcon Indica se o ícone do menu já existente será sobrescrito
   */
  public void addOrReplaceSubmenu(MenuItem submenu, boolean changeIcon) {
    MenuItem replacedMenuItem = findMenuItemByTitle(submenu.getTitle(), getSubmenus());
    if (replacedMenuItem != null) {
      replacedMenuItem.setView(submenu.getView());
      replacedMenuItem.setIcon(changeIcon ? submenu.getIcon() : replacedMenuItem.getIcon());
    } else {
      submenu.setParent(this);
      this.setView(null);
      submenus.add(submenu);
    }
  }

  private MenuItem findMenuItemByTitle(String title, List<MenuItem> menuItemList) {
    for (MenuItem menuItem : menuItemList) {
      if (menuItem.getSubmenus() != null && !menuItem.getSubmenus().isEmpty()) {
        return findMenuItemByTitle(title, menuItem.getSubmenus());
      } else if (menuItem.getTitle().equalsIgnoreCase(title)) {
        return menuItem;
      }
    }
    return null;
  }

  /**
   * Adiciona um {@link MenuItem} na lista {@link #submenus}
   *
   * @param submenu
   */
  public void addSubmenu(MenuItem submenu) {
    submenu.setParent(this);
    this.setView(null);
    submenus.add(submenu);
  }

  /**
   * Adiciona um {@link MenuItem} (filho) dentro de outro {@link MenuItem} (pai)
   *
   * @param parent
   * @param submenu
   */
  public void addSubmenu(MenuItem parent, MenuItem submenu) {
    submenu.setParent(parent);
    parent.getSubmenus().add(submenu);
    parent.setView(null);
  }

  /**
   * @return {@link #id}
   */
  public Integer getId() {
    return id;
  }

  /**
   * @param id
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * @return {@link #title}
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @return {@link #submenus}
   */
  public List<MenuItem> getSubmenus() {
    return submenus;
  }

  /**
   * @param submenus
   */
  public void setSubmenus(List<MenuItem> submenus) {
    this.submenus = submenus;
  }

  /**
   * @return {@link #icon}
   */
  public FontIcon getIcon() {
    return icon;
  }

  /**
   * @param icon
   */
  public void setIcon(FontIcon icon) {
    this.icon = icon;
  }

  /**
   * @return {@link #parent}
   */
  public MenuItem getParent() {
    return parent;
  }

  /**
   * @param parent
   */
  public void setParent(MenuItem parent) {
    this.parent = parent;
  }

  /**
   * @return {@link #view}
   */
  public View getView() {
    return view;
  }

  /**
   * @param view
   */
  public void setView(View view) {
    this.view = view;
  }

  /**
   * @return {@link #edit}
   */
  public boolean isEdit() {
    return edit;
  }

  /**
   * @param edit
   */
  public void setEdit(boolean edit) {
    this.edit = edit;
  }

  public String getDashboardBytes() {
    return dashboardBytes;
  }

  public void setDashboardBytes(String dashboardBytes) {
    this.dashboardBytes = dashboardBytes;
  }

  /**
   * @return {@link #tag}
   */
  public String getTag() {
    return tag;
  }

  /**
   * @param tag
   */
  public void setTag(String tag) {
    this.tag = tag;
  }

  /**
   * Clona o Objeto
   *
   * @return
   */
  @Override
  public MenuItem clone() {
    try {
      MenuItem clone = (MenuItem) super.clone();
      return clone;
    } catch (CloneNotSupportedException ignore) {
      ignore.printStackTrace();
      return null;
    }
  }

}
